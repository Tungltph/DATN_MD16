package com.example.datn_md16.Activitys;

import static com.example.datn_md16.Activitys.DangNhap.PREFS_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datn_md16.DTO.AccountRequest;
import com.example.datn_md16.DTO.AccountResponse;
import com.example.datn_md16.DTO.HoSoDTO;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.Interfa.HoSoResponse;
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


    private TextInputEditText tvTenNguoiDungHoso;
    private TextInputEditText tvSoDienThoai;
    private static final String TAG = "NguoiDungActivity";

    private Button btnThayDoiTT;

    private ApiService apiService;
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

        // Ánh xạ các TextInputEditText từ layout
        tvTenNguoiDungHoso = findViewById(R.id.tvTenNguoiDungHoso);
        tvSoDienThoai = findViewById(R.id.tvSoDienThoai);
        fetchUserInfo();

        btnThayDoiTT = findViewById(R.id.btnThayDoiTT);

        fetchUserInfo();

        Retrofit retrofit = ApiClient.getClient();

        apiService = retrofit.create(ApiService.class);

        // Thiết lập sự kiện nhấn nút
        btnThayDoiTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccountInfo();
            }
        });
    }

    private void updateAccountInfo() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);

        // Lấy dữ liệu từ các EditText
        String tenNguoiDung = tvTenNguoiDungHoso.getText().toString().trim();
        String soDienThoai = tvSoDienThoai.getText().toString().trim();

        // Validate inputs
        if (tenNguoiDung.isEmpty()) {
            Toast.makeText(this, "Tên người dùng không được để trống.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (soDienThoai.isEmpty()) {
            Toast.makeText(this, "Số điện thoại không được để trống.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate phone number format
        if (!isValidPhoneNumber(soDienThoai)) {
            Toast.makeText(this, "Số điện thoại không hợp lệ.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng AccountDTO để gửi đến API
        HoSoDTO updatedAccount = new HoSoDTO();
        updatedAccount.setHoTen(tenNguoiDung);
        updatedAccount.setSdt(soDienThoai);

        // Gửi yêu cầu PUT đến API
        Call<HoSoDTO> call = apiService.updateAccountInfo(userId, updatedAccount);
        call.enqueue(new Callback<HoSoDTO>() {
            @Override
            public void onResponse(Call<HoSoDTO> call, Response<HoSoDTO> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Acti_hoSoCuaToi.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Acti_hoSoCuaToi.this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HoSoDTO> call, Throwable t) {
                Toast.makeText(Acti_hoSoCuaToi.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Check if the phone number starts with '0' and has exactly 10 digits
        return phoneNumber.matches("^0\\d{9}$");
    }


    private void fetchUserInfo() {
        Retrofit retrofit = ApiClient.getClient();

        ApiService apiService = retrofit.create(ApiService.class);

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);

//        String accountId = "668fcb250711941e32e4b16c";

        Call<HoSoResponse> call = apiService.getAccByID(userId);
        call.enqueue(new Callback<HoSoResponse>() {
            @Override
            public void onResponse(Call<HoSoResponse> call, Response<HoSoResponse> response) {
                if (response.isSuccessful()) {
                    HoSoResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getData() != null) {
                        HoSoDTO account = apiResponse.getData();
                        tvTenNguoiDungHoso.setText(account.getHoTen());
                        tvSoDienThoai.setText(account.getSdt());
                    } else {
                        Log.d(TAG, "Account data is null");
                    }
                } else {
                    Log.e(TAG, "Response unsuccessful: " + response.message());
                    Toast.makeText(Acti_hoSoCuaToi.this, "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HoSoResponse> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
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