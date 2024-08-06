package com.example.datn_md16.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datn_md16.Adapter.IphoneAdapter;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Acti_Xiaomi extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IphoneAdapter adapter;

    private TextView btnip, btnss, btnvv, btnxm, btnop;

    private static final String HANG_SX_ID = "6675aa1eff75b8dfd1e641ea";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_xiaomi);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Thiết lập Toolbar và hiển thị nút back
        Toolbar toolbar = findViewById(R.id.toolbarXiaomi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiển thị nút back
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Đặt tiêu đề cho Toolbar từ chuỗi trong strings.xml
        setTitle(getString(R.string.toolbarXiaomi));
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

        fetchData();
    }

    private void fetchData() {
        Retrofit retrofit = ApiClient.getClient();

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
                        if (HANG_SX_ID.equals(product.getIdHangSX())) {
                            filteredProducts.add(product);
                        }
                    }
                    // Check if there are products to display
                    if (!filteredProducts.isEmpty()) {
                        IphoneAdapter IphoneAdapter = new IphoneAdapter(Acti_Xiaomi.this, filteredProducts);
                        recyclerView.setAdapter(IphoneAdapter);
                    } else {
                        Toast.makeText(Acti_Xiaomi.this, "Không có sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Acti_Xiaomi.this, "Không có sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductHome>> call, Throwable t) {
                Toast.makeText(Acti_Xiaomi.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<ProductHome> filterProductsByHangSX(List<ProductHome> productList, String hangSXId) {
        List<ProductHome> filteredList = new ArrayList<>();
        for (ProductHome product : productList) {
            if (hangSXId.equals(product.getIdHangSX())) {
                filteredList.add(product);
            }
        }
        return filteredList;
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