package com.example.datn_md16.Activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.KhuyenMaiAdapter;
import com.example.datn_md16.DTO.KhuyenMai;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Acti_KhuyenMai extends AppCompatActivity {

    private RecyclerView recyclerView;
    private KhuyenMaiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khuyen_mai);


        // Thiết lập Toolbar và hiển thị nút back
        Toolbar toolbar = findViewById(R.id.toolbarKhuyenMai);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiển thị nút back
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Đặt tiêu đề cho Toolbar từ chuỗi trong strings.xml
        setTitle(getString(R.string.toolbarKhuyenMai));

        recyclerView = findViewById(R.id.recyclerViewKhuyenMai);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new KhuyenMaiAdapter();
        recyclerView.setAdapter(adapter);

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.106:3000/api/khuyenMai/") // Thay thế địa chỉ IP của server Node.js của bạn
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Tạo đối tượng dịch vụ API từ Retrofit
        ApiService apiService = retrofit.create(ApiService.class);

        // Gọi API để lấy danh sách khuyến mãi
        Call<List<KhuyenMai>> call = apiService.getKhuyenMai();
        call.enqueue(new Callback<List<KhuyenMai>>() {
            @Override
            public void onResponse(Call<List<KhuyenMai>> call, Response<List<KhuyenMai>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<KhuyenMai> khuyenMaiList = response.body();
                    adapter.setData(khuyenMaiList); // Đặt dữ liệu vào adapter để hiển thị trên RecyclerView
                } else {
                    Toast.makeText(Acti_KhuyenMai.this, "Không thể lấy dữ liệu từ server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<KhuyenMai>> call, Throwable t) {
                Toast.makeText(Acti_KhuyenMai.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("Acti_KhuyenMai", "Error: " + t.getMessage());
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
