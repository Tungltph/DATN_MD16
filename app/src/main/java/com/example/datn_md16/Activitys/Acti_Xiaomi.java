package com.example.datn_md16.Activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.datn_md16.Adapter.IphoneAdapter;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.Interfa.ApiService;
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
    private static final String HANG_SX_ID = "6675aa1eff75b8dfd1e641ea";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_xiaomi);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        fetchData();
    }

    private void fetchData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.102:3000/api/sanPham/") // Thay thế bằng URL API của bạn
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
}