package com.example.datn_md16.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datn_md16.DTO.AccountRequest;
import com.example.datn_md16.DTO.AccountResponse;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Acti_hoSoCuaToi extends AppCompatActivity {
    private TextInputEditText tvTenNguoiDung, tvSoDienThoai;
    ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ho_so_cua_toi);

        // Thiết lập Toolbar và hiển thị nút back
        Toolbar toolbar = findViewById(R.id.toolbarHoSo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiển thị nút back
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Đặt tiêu đề cho Toolbar từ chuỗi trong strings.xml
        setTitle(getString(R.string.toolbarHoSo));

        tvTenNguoiDung = findViewById(R.id.tvTenNguoiDungHoso);
        tvSoDienThoai = findViewById(R.id.tvSoDienThoai);

        SharedPreferences sharedPreferences = getSharedPreferences(DangNhap.PREFS_NAME, Context.MODE_PRIVATE);
        String accountId = sharedPreferences.getString(DangNhap.KEY_USER_ID, "");

        if (accountId.isEmpty()) {
            Toast.makeText(this, "Chưa đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gọi API lấy thông tin tài khoản
        setupRetrofit();
        fetchAccountById(accountId);
    }
    public void setupRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = ApiClient.getClient();
        apiService = retrofit.create(ApiService.class);
    }

    private void fetchAccountById(String accountId) {
        Call<AccountRequest> call = apiService.getAccountById(accountId);
        call.enqueue(new Callback<AccountRequest>() {
            @Override
            public void onResponse(Call<AccountRequest> call, Response<AccountRequest> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AccountRequest account = response.body();
                    if (account != null) {
                        tvTenNguoiDung.setText(account.getHoTen() != null ? account.getHoTen() : "Không có tên");
                        tvSoDienThoai.setText(account.getSdt() != null ? account.getSdt() : "Không có số điện thoại");
                    } else {
                        Log.d("Account Data", "Account is null");
                    }
                } else {
                    Log.d("API_RESPONSE", "Response failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<AccountRequest> call, Throwable t) {
                Log.e("APIFailure", t.getMessage());
                Toast.makeText(Acti_hoSoCuaToi.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
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