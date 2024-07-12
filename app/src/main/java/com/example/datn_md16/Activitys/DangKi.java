package com.example.datn_md16.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.datn_md16.DTO.AccountRequest;
import com.example.datn_md16.DTO.AccountResponse;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DangKi extends AppCompatActivity {

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);

        // Khởi tạo Retrofit và ApiService
        setupRetrofit();
    }

    // Phương thức khởi tạo Retrofit và ApiService
    private void setupRetrofit() {
        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.9.104:3000/api/account/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Khởi tạo ApiService từ Retrofit
        apiService = retrofit.create(ApiService.class);
    }

    // Phương thức xử lý khi nhấn nút "Đăng ký"
    public void register(View view) {
        EditText nameEditText = findViewById(R.id.Name);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText fullNameEditText = findViewById(R.id.fullNameEditText);
        EditText phoneEditText = findViewById(R.id.phoneEditText);
        CheckBox termsCheckBox = findViewById(R.id.termsCheckBox);

        String name = nameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String fullName = fullNameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        boolean termsAccepted = termsCheckBox.isChecked();

        // Kiểm tra tính hợp lệ của dữ liệu
        if (!validateInput(name, password, fullName, phone, termsAccepted)) {
            return;
        }

        // Gọi API để thêm tài khoản mới
        addNewAccount(name, password, fullName, phone);
    }

    // Phương thức gọi API để thêm tài khoản mới
    private void addNewAccount(String name, String password, String fullName, String phone) {
        AccountRequest newAccount = new AccountRequest();
        newAccount.setTaiKhoan(name); // Đặt tài khoản là email cho đơn giản
        newAccount.setMatKhau(password);
        newAccount.setHoTen(fullName);
        newAccount.setSdt(phone);

        // Gọi phương thức Retrofit để thực hiện yêu cầu POST
        Call<AccountRequest> call = apiService.registerAccount(newAccount);
        call.enqueue(new Callback<AccountRequest>() {
            @Override
            public void onResponse(Call<AccountRequest> call, Response<AccountRequest> response) {
                if (response.isSuccessful()) {
                    // Xử lý thành công
                    Toast.makeText(DangKi.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                    // Sau khi đăng ký thành công, bạn có thể chuyển người dùng đến màn hình đăng nhập hoặc màn hình chính
                    Intent intent = new Intent(DangKi.this, DangNhap.class);
                    startActivity(intent);
                    finish(); // Kết thúc hoạt động đăng ký sau khi chuyển sang màn hình đăng nhập
                } else {
                    // Xử lý lỗi khi không thành công
                    Toast.makeText(DangKi.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountRequest> call, Throwable t) {
                // Xử lý lỗi khi gọi API thất bại
                Toast.makeText(DangKi.this, "Lỗi khi đăng ký: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức kiểm tra và xử lý dữ liệu nhập từ người dùng
    private boolean validateInput(String name, String password, String fullName, String phone, boolean termsAccepted) {
        if (name.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên tài khoản", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty() || password.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (fullName.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập họ tên", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phone.isEmpty() || phone.length() < 10) {
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!termsAccepted) {
            Toast.makeText(this, "Vui lòng chấp nhận điều khoản và điều kiện", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Phương thức xử lý khi nhấn vào văn bản "Bạn đã có tài khoản? Đăng nhập"
    public void goToLogin(View view) {
        Intent intent = new Intent(DangKi.this, DangNhap.class);
        startActivity(intent);
        finish(); // Kết thúc hoạt động đăng ký sau khi chuyển sang màn hình đăng nhập
    }
}
