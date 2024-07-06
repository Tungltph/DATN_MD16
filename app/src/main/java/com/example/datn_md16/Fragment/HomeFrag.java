package com.example.datn_md16.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Activitys.Acti_GioHang;
import com.example.datn_md16.Activitys.Acti_TimKiem;
import com.example.datn_md16.Adapter.HomeAdapter;
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

public class HomeFrag extends Fragment {

    private RecyclerView rvHotProducts;
    private RecyclerView rvNewProducts;
    private HomeAdapter hotProductsAdapter;
    private HomeAdapter newProductsAdapter;
    private List<ProductHome> hotProducts = new ArrayList<>();
    private List<ProductHome> newProducts = new ArrayList<>();
    private ApiService apiService;

    private TextView tvSearchHome;
    private ImageView idGioHangHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_home, container, false);

        // Initialize UI components
        rvHotProducts = rootView.findViewById(R.id.rvHotProducts);
        rvNewProducts = rootView.findViewById(R.id.rvNewProducts);
        tvSearchHome = rootView.findViewById(R.id.tvSearchHome);
        idGioHangHome = rootView.findViewById(R.id.idGioHangHome);

        // Set up RecyclerViews
        rvHotProducts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvNewProducts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        hotProductsAdapter = new HomeAdapter(getContext(), hotProducts);
        newProductsAdapter = new HomeAdapter(getContext(), newProducts);

        rvHotProducts.setAdapter(hotProductsAdapter);
        rvNewProducts.setAdapter(newProductsAdapter);

        // Set click listeners
        tvSearchHome.setOnClickListener(v -> startActivity(new Intent(getActivity(), Acti_TimKiem.class)));
        idGioHangHome.setOnClickListener(v -> startActivity(new Intent(getActivity(), Acti_GioHang.class)));

        // Retrofit setup
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4:3000/api/sanPham/") // Change base URL here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Fetch data
        getHotProducts();
        getNewProducts();

        return rootView;
    }

    private void getHotProducts() {
        apiService.getHotProducts().enqueue(new Callback<List<ProductHome>>() {
            @Override
            public void onResponse(Call<List<ProductHome>> call, Response<List<ProductHome>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    hotProducts.clear();
                    hotProducts.addAll(response.body());
                    hotProductsAdapter.notifyDataSetChanged();
                } else {
                    showToast("Unable to retrieve data from server");
                }
            }

            @Override
            public void onFailure(Call<List<ProductHome>> call, Throwable t) {
                showToast("Connection error");
                Log.e("HomeFrag", "Error: " + t.getMessage(), t);
            }
        });
    }

    private void getNewProducts() {
        apiService.getNewProducts().enqueue(new Callback<List<ProductHome>>() {
            @Override
            public void onResponse(Call<List<ProductHome>> call, Response<List<ProductHome>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    newProducts.clear();
                    newProducts.addAll(response.body());
                    newProductsAdapter.notifyDataSetChanged();
                } else {
                    showToast("Unable to retrieve data from server");
                }
            }

            @Override
            public void onFailure(Call<List<ProductHome>> call, Throwable t) {
                showToast("Connection error");
                Log.e("HomeFrag", "Error: " + t.getMessage(), t);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
