package com.example.datn_md16.Activitys;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.GioHangAdapter;

import com.example.datn_md16.DTO.GioHangDTO;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Acti_GioHang extends AppCompatActivity implements GioHangAdapter.OnTotalPriceChangeListener {

    private TextView totalPriceTextView;
    private GioHangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarGioHang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.toolbarGioHang));

        // Khởi tạo RecyclerView và Adapter
        RecyclerView recyclerView = findViewById(R.id.rcGioHang);
        totalPriceTextView = findViewById(R.id.totalPrice);

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.63:3000/") // Chỉ cần URL gốc
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Gọi API và lấy dữ liệu
        apiService.getGioHang().enqueue(new Callback<List<GioHangDTO>>() {
            @Override
            public void onResponse(Call<List<GioHangDTO>> call, Response<List<GioHangDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GioHangDTO> gioHangList = response.body();
                    adapter = new GioHangAdapter(gioHangList, Acti_GioHang.this);
                    adapter.setOnTotalPriceChangeListener(Acti_GioHang.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Acti_GioHang.this));
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(Acti_GioHang.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GioHangDTO>> call, Throwable t) {
                Toast.makeText(Acti_GioHang.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTotalPriceChanged(int totalPrice) {
        totalPriceTextView.setText("Tổng thanh toán:\n " + totalPrice + "đ");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
