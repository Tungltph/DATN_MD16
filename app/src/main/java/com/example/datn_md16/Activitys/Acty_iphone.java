package com.example.datn_md16.Activitys;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

    private static final String TARGET_ID_HANG_SX = "6675a1077b2677a73fc0032a";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iphone);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        fetchProducts();
    }

    private void fetchProducts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.102:3000/api/sanPham/")
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
