package com.example.datn_md16.Activitys;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.GioHangAdapter;
import com.example.datn_md16.DTO.GioHangDTO;
import com.example.datn_md16.R;


import java.util.ArrayList;
import java.util.List;

public class Acti_GioHang extends AppCompatActivity implements GioHangAdapter.OnTotalPriceChangeListener {

    private TextView totalPriceTextView;
    private GioHangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        // Thiết lập Toolbar và hiển thị nút back
        Toolbar toolbar = findViewById(R.id.toolbarGioHang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Đặt tiêu đề cho Toolbar từ chuỗi trong strings.xml
        setTitle(getString(R.string.toolbarGioHang));

        // Khởi tạo RecyclerView và Adapter
        RecyclerView recyclerView = findViewById(R.id.rcGioHang);
        totalPriceTextView = findViewById(R.id.totalPrice);

        List<GioHangDTO> gioHangList = createSampleData(); // Khởi tạo dữ liệu mẫu

        adapter = new GioHangAdapter(gioHangList, this);
        adapter.setOnTotalPriceChangeListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        updateTotalPrice(0); // Khởi tạo giá trị ban đầu cho tổng thanh toán
    }

    private List<GioHangDTO> createSampleData() {
        List<GioHangDTO> sampleData = new ArrayList<>();
        sampleData.add(new GioHangDTO("1", "iPhone 15 Pro", "Đen", "15000000đ", "url_image", 1, true));
        sampleData.add(new GioHangDTO("2", "Samsung Galaxy S21", "Trắng", "12000000đ", "url_image", 2, false));
        sampleData.add(new GioHangDTO("3", "Google Pixel 6", "Xanh", "10000000đ", "url_image", 1, true));
        return sampleData;
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

    private void updateTotalPrice(int totalPrice) {
        totalPriceTextView.setText("Tổng thanh toán:\n " + totalPrice + "đ");
    }
}
