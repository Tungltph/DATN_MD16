package com.example.datn_md16.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.TimKiemAdapter;
import com.example.datn_md16.DTO.TimKiemDTO;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.R;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Acti_TimKiem extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TimKiemAdapter adapter;
    private Spinner spinnerGia;
    private Button btnMoiNhat;

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

        spinnerGia = findViewById(R.id.spinner_gia);
        btnMoiNhat = findViewById(R.id.btnMoiNhat);

        // Thiết lập Adapter cho Spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_items_gia, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGia.setAdapter(spinnerAdapter);

        // Lắng nghe sự kiện chọn của Spinner
        spinnerGia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    // Giá từ thấp đến cao
                    adapter.sortDataList(true);
                } else if (position == 1) {
                    // Giá từ cao đến thấp
                    adapter.sortDataList(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì khi không chọn gì
            }
        });

        // Lắng nghe sự kiện click của Button "Mới nhất"
        btnMoiNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load dữ liệu từ API và sắp xếp lại theo mặc định (trên MongoDB)
                loadAndSortDefaultData();
            }
        });

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.9.102:3000/api/sanPham/") // Thay thế địa chỉ IP của server Node.js của bạn
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Tạo đối tượng dịch vụ API từ Retrofit
        ApiService apiService = retrofit.create(ApiService.class);

        // Gọi API để lấy dữ liệu
        Call<List<TimKiemDTO>> call = apiService.getTimKiem();
        call.enqueue(new Callback<List<TimKiemDTO>>() {
            @Override
            public void onResponse(Call<List<TimKiemDTO>> call, Response<List<TimKiemDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TimKiemDTO> timKiemDTOList = response.body();
                    adapter.setData(timKiemDTOList); // Đặt dữ liệu vào adapter để hiển thị trên RecyclerView
                    adapter.sortDataList(true); // Sắp xếp theo giá từ thấp đến cao ngay sau khi nhận dữ liệu
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

    // Phương thức để load dữ liệu từ API và sắp xếp theo mặc định (trên MongoDB)
    private void loadAndSortDefaultData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.9.102:3000/api/sanPham/") // Thay thế địa chỉ IP của server Node.js của bạn
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<TimKiemDTO>> call = apiService.getTimKiem();
        call.enqueue(new Callback<List<TimKiemDTO>>() {
            @Override
            public void onResponse(Call<List<TimKiemDTO>> call, Response<List<TimKiemDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TimKiemDTO> timKiemDTOList = response.body();
                    // Thực hiện sắp xếp theo mặc định (trên MongoDB)
                    // Ví dụ: Collections.sort(timKiemDTOList, new YourDefaultComparator());
                    // Sau khi sắp xếp, cập nhật dữ liệu vào adapter:
                    adapter.setData(timKiemDTOList);
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
