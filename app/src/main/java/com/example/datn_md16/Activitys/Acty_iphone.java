package com.example.datn_md16.Activitys;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datn_md16.Adapter.IphoneAdapter;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.R;
import com.example.datn_md16.Interfa.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Acty_iphone extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IphoneAdapter iphoneAdapter;
    private TextView btnip, btnss, btnvv, btnxm, btnop;


    private static final String TARGET_ID_HANG_SX = "6675a1077b2677a73fc0032a";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iphone);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Thiết lập Toolbar và hiển thị nút back
        Toolbar toolbar = findViewById(R.id.toolbarIphone);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiển thị nút back
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Đặt tiêu đề cho Toolbar từ chuỗi trong strings.xml
        setTitle(getString(R.string.toolbarIphone));
        btnxm = findViewById(R.id.btnXiaomi);
        btnip = findViewById(R.id.btnIphone);
        btnop = findViewById(R.id.btnOppo);
        btnss = findViewById(R.id.btnss);
        btnvv = findViewById(R.id.btnVivo);

        btnip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Acty_iphone.class);
                startActivity(intent);
            }
        });
        btnop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Acti_Oppo.class);
                startActivity(intent);
            }
        });
        btnss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Acti_Samsung.class);
                startActivity(intent);
            }
        });
        btnvv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Acti_vivo.class);
                startActivity(intent);
            }
        });
        btnxm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Acti_Xiaomi.class);
                startActivity(intent);
            }
        });

        fetchProducts();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Xử lý khi nhấn nút back trên Toolbar
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchProducts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.9.104:3000/api/sanPham/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<ProductHome>> call = apiService.getProducts();
        call.enqueue(new Callback<List<ProductHome>>() {
            @Override
            public void onResponse(Call<List<ProductHome>> call, Response<List<ProductHome>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductHome> allProducts = response.body();
                    List<ProductHome> filteredProducts = new ArrayList<>();

                    // Filter products by idHangSX
                    for (ProductHome product : allProducts) {
                        if (TARGET_ID_HANG_SX.equals(product.getIdHangSX())) {
                            filteredProducts.add(product);
                        }
                    }

                    // Check if there are products to display
                    if (!filteredProducts.isEmpty()) {
                        iphoneAdapter = new IphoneAdapter(Acty_iphone.this, filteredProducts);
                        recyclerView.setAdapter(iphoneAdapter);
                    } else {
                        Toast.makeText(Acty_iphone.this, "Không có sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Acty_iphone.this, "Không có sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductHome>> call, Throwable t) {
                Toast.makeText(Acty_iphone.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
