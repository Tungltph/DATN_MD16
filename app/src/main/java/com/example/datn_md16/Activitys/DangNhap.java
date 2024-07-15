package com.example.datn_md16.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    private ApiService apiService;
    private List<AccountResponse.Account> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerTextView = findViewById(R.id.registerTextView);

        setupRetrofit();
        fetchUserData();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (validateLogin(username, password)) {
                    Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                    // Chuyển đến HomeActivity (hoặc bất kỳ hoạt động nào bạn muốn)
                    Intent intent = new Intent(DangNhap.this, MainActivity.class);
                    startActivity(intent);

                    // Kết thúc hoạt động hiện tại (tùy chọn, phụ thuộc vào luồng điều hướng của bạn)
                    finish();

                } else {
                    Toast.makeText(DangNhap.this, "Thông tin đăng nhập không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhap.this, DangKi.class);
                startActivity(intent);
            }
        });
    }

    private void setupRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.9.102:3000/api/account/")
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
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                // Xử lý thất bại
                Toast.makeText(DangNhap.this, "Không thể lấy dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        if (userList == null) return false;

        for (AccountResponse.Account user : userList) {
            if (user.taiKhoan.equals(username) && user.matKhau.equals(password) && user.tenQuyen.equals("User")) {
                return true;
            }
        }
        return false;
    }
}
