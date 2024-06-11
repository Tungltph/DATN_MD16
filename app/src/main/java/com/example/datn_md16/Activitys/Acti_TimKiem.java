package com.example.datn_md16.Activitys;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        recyclerView = findViewById(R.id.recyclerView);
        productList = getProducts(); // Lấy danh sách sản phẩm cần hiển thị

        // Khởi tạo và thiết lập Adapter
        adapter = new TimKiemProductAdapter(this, productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private List<Product_TimKiem> getProducts() {
        List<Product_TimKiem> products = new ArrayList<>();
        products.add(new Product_TimKiem(R.drawable.img_1, "Iphone 15 Pro", "9.000.000"));
        products.add(new Product_TimKiem(R.drawable.img_1, "Iphone 15 Pro", "9.000.000"));
        products.add(new Product_TimKiem(R.drawable.img_1, "Iphone 15 Pro", "9.000.000"));
        return products;
    }
}
