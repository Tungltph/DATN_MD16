package com.example.datn_md16.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datn_md16.DTO.AccountResponse;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.R;

import java.util.List;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DangNhap extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registerTextView;
    private CheckBox saveAccountCheckBox;

    private ApiService apiService;
    private List<AccountResponse.Account> userList;

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_SAVE_ACCOUNT = "save_account";
    private static final String KEY_SAVED_USERNAME = "saved_username";
    private static final String KEY_SAVED_PASSWORD = "saved_password";

    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerTextView = findViewById(R.id.registerTextView);
        saveAccountCheckBox = findViewById(R.id.saveAccountCheckBox);

        setupRetrofit();
        fetchUserData();

        // Load saved credentials if they exist
        loadSavedCredentials();

        loginButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (validateLogin(username, password)) {
                Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DangNhap.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(DangNhap.this, "Thông tin đăng nhập không chính xác", Toast.LENGTH_SHORT).show();
            }
        });

        registerTextView.setOnClickListener(view -> {
            Intent intent = new Intent(DangNhap.this, DangKi.class);
            startActivity(intent);
        });

        passwordEditText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[2].getBounds().width())) {
                    // Toggle password visibility
                    if (isPasswordVisible) {
                        passwordEditText.setInputType(129); // Hide password
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_off, 0);
                    } else {
                        passwordEditText.setInputType(145); // Show password
                        passwordEditText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_on, 0);
                    }
                    passwordEditText.setSelection(passwordEditText.getText().length()); // Move cursor to the end
                    isPasswordVisible = !isPasswordVisible;
                    return true;
                }
            }
            return false;
        });
    }

    private void setupRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.8:3000/api/account/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    private void fetchUserData() {
        Call<AccountResponse> call = apiService.getAccounts();
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userList = response.body().data;
                    Log.d("DangNhap", "User data fetched: " + userList);
                } else {
                    Toast.makeText(DangNhap.this, "Không có dữ liệu người dùng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Toast.makeText(DangNhap.this, "Không thể lấy dữ liệu: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        if (userList == null) return false;

        for (AccountResponse.Account user : userList) {
            if (user.taiKhoan.equals(username) && user.matKhau.equals(password) && user.tenQuyen.equals("User")) {
                // Lưu _id của người dùng vào SharedPreferences
                saveUserId(user._id);
                saveUserName(user.taiKhoan);

                if (saveAccountCheckBox.isChecked()) {
                    saveCredentials(username, password);
                } else {
                    clearSavedCredentials();
                }

                return true;
            }
        }
        return false;
    }

    private void saveUserId(String userId) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_ID, userId);
        editor.apply();
        Log.d("DangNhap", "User ID saved: " + userId);
    }

    private void saveUserName(String userName) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_NAME, userName);
        editor.apply();
        Log.d("DangNhap", "User Name saved: " + userName);
    }

    private void saveCredentials(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_SAVE_ACCOUNT, true);
        editor.putString(KEY_SAVED_USERNAME, username);
        editor.putString(KEY_SAVED_PASSWORD, password);
        editor.apply();
        Log.d("DangNhap", "Credentials saved");
    }

    private void loadSavedCredentials() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isSaveAccount = sharedPreferences.getBoolean(KEY_SAVE_ACCOUNT, false);
        if (isSaveAccount) {
            String savedUsername = sharedPreferences.getString(KEY_SAVED_USERNAME, "");
            String savedPassword = sharedPreferences.getString(KEY_SAVED_PASSWORD, "");
            usernameEditText.setText(savedUsername);
            passwordEditText.setText(savedPassword);
            saveAccountCheckBox.setChecked(true);
            Log.d("DangNhap", "Loaded saved credentials: " + savedUsername);
        }
    }

    private void clearSavedCredentials() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_SAVE_ACCOUNT);
        editor.remove(KEY_SAVED_USERNAME);
        editor.remove(KEY_SAVED_PASSWORD);
        editor.apply();
        Log.d("DangNhap", "Saved credentials cleared");
    }
}
