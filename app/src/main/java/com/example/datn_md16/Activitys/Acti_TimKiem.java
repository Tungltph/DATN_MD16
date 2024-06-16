package com.example.datn_md16.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.TimKiemProductAdapter;
import com.example.datn_md16.DTO.Product_TimKiem;
import com.example.datn_md16.R;

import java.util.ArrayList;
import java.util.List;

public class Acti_TimKiem extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TimKiemProductAdapter adapter;
    private List<Product_TimKiem> productList;
    ImageView imgGioHangTimKiem;

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
        productList = getProducts(); // Lấy danh sách sản phẩm cần hiển thị
        imgGioHangTimKiem = findViewById(R.id.imggioHnagTimKiem);
        imgGioHangTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Acti_TimKiem.this, Acti_GioHang.class));
            }
        });

        // Khởi tạo và thiết lập Adapter
        adapter = new TimKiemProductAdapter(this, productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Thiết lập sự kiện nhấp vào mục
        adapter.setOnItemClickListener(new TimKiemProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product_TimKiem product) {
                Intent intent = new Intent(Acti_TimKiem.this, Acti_ChiTietSP.class);
                startActivity(intent);
            }
        });
    }

    private List<Product_TimKiem> getProducts() {
        List<Product_TimKiem> products = new ArrayList<>();
        products.add(new Product_TimKiem(R.drawable.img_1, "IPhone 15 Pro Max 256GB\nChính hãng VNA", "9.000.000"));
        products.add(new Product_TimKiem(R.drawable.img_1, "IPhone 15 Pro Max 256GB\nChính hãng VNA", "9.000.000"));
        products.add(new Product_TimKiem(R.drawable.img_1, "IPhone 15 Pro Max 256GB\nChính hãng VNA", "9.000.000"));
        return products;
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
