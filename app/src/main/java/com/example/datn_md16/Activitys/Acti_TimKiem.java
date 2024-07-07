package com.example.datn_md16.Activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.TimKiemAdapter;
import com.example.datn_md16.DTO.TimKiemDTO;
import com.example.datn_md16.Interfa.ApiResponseKhuyenMai;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Acti_TimKiem extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TimKiemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        // Thiết lập Toolbar và hiển thị nút back
        Toolbar toolbar = findViewById(R.id.toolbarTimKiem);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiển thị nút back
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Đặt tiêu đề cho Toolbar từ chuỗi trong strings.xml
        setTitle(getString(R.string.toolbarTimKiem));

        recyclerView = findViewById(R.id.rcv_TimKiem);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TimKiemAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.9.103:3000/api/sanPham/") // Thay thế địa chỉ IP của server Node.js của bạn
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Tạo đối tượng dịch vụ API từ Retrofit
        ApiService apiService = retrofit.create(ApiService.class);

        // Gọi API để lấy phản hồi ApiResponseKhuyenMai
        Call<List<TimKiemDTO>> call = apiService.getTimKiem();
        call.enqueue(new Callback<List<TimKiemDTO>>() {
            @Override
            public void onResponse(Call<List<TimKiemDTO>> call, Response<List<TimKiemDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TimKiemDTO> timKiemDTOList = response.body();
                    adapter.setData(timKiemDTOList); // Đặt dữ liệu vào adapter để hiển thị trên RecyclerView
                } else {
                    Toast.makeText(Acti_TimKiem.this, "Không thể lấy dữ liệu từ server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TimKiemDTO>> call, Throwable t) {
                Toast.makeText(Acti_TimKiem.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("Acti_TimKiem", "Error: " + t.getMessage());
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
