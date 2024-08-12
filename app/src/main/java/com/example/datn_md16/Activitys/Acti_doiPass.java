package com.example.datn_md16.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.datn_md16.DTO.DoiPassDTO;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Acti_doiPass extends AppCompatActivity {

    private TextInputEditText edtOldPassword, edtNewPassword;
    private Button btnChangePassword;
    private ApiService apiService;
    private String accountId; // Lưu ID tài khoản

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_pass);

        // Thiết lập Toolbar và hiển thị nút back
        Toolbar toolbar = findViewById(R.id.toolbarDoiPass);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getString(R.string.toolbarDoiPass));

        // Khởi tạo Retrofit ApiService
        apiService = ApiClient.getClient().create(ApiService.class);

        // Lấy thông tin tài khoản từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences(DangNhap.PREFS_NAME, MODE_PRIVATE);
        accountId = prefs.getString(DangNhap.KEY_USER_ID, "");

        Log.d("Acti_doiPass", "Account ID: " + accountId);

        if (accountId == null || accountId.isEmpty()) {
            Toast.makeText(this, "Lỗi: Không tìm thấy ID tài khoản", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ánh xạ các view
        edtOldPassword = findViewById(R.id.tvMatKhauCu);
        edtNewPassword = findViewById(R.id.tvMatKhauMoi);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(v -> {
            String oldPassword = edtOldPassword.getText().toString().trim();
            String newPassword = edtNewPassword.getText().toString().trim();

            Log.d("Acti_doiPass", "Old Password: " + oldPassword);
            Log.d("Acti_doiPass", "New Password: " + newPassword);

            if (validateInputs(oldPassword, newPassword)) {
                changePassword(oldPassword, newPassword);
            }
        });
    }

    private boolean validateInputs(String oldPassword, String newPassword) {
        if (oldPassword == null || oldPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu cũ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (newPassword == null || newPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (newPassword.length() < 6 || newPassword.length() > 20) {
            Toast.makeText(this, "Mật khẩu mới phải từ 6 đến 20 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void changePassword(String currentPassword, String newPassword) {
        DoiPassDTO passwordDTO = new DoiPassDTO(currentPassword, newPassword);
        Call<ResponseBody> call = apiService.changePassword(accountId, passwordDTO);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        boolean success = jsonResponse.getBoolean("success");
                        String message = jsonResponse.getString("message");

                        if (success) {
                            Toast.makeText(Acti_doiPass.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Acti_doiPass.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(Acti_doiPass.this, "Lỗi xử lý phản hồi", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Acti_doiPass.this, "Lỗi hệ thống: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Acti_doiPass.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Xử lý khi nhấn nút back trên Toolbar
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
