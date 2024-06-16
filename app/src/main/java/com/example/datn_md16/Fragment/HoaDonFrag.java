package com.example.datn_md16.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.DonHang_Adapter;
import com.example.datn_md16.DTO.Product_DonHang;
import com.example.datn_md16.R;

import java.util.ArrayList;
import java.util.List;

public class HoaDonFrag extends Fragment {

    private RecyclerView recyclerView;
    private DonHang_Adapter adapter;
    private List<Product_DonHang> productList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_don_hang, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize product list
        productList = new ArrayList<>();
        productList.add(new Product_DonHang("iPhone 15 Pro", "Đen", "15.000.000đ", "1", R.drawable.img_1));
        // Add more products to the list

        // Initialize adapter
        adapter = new DonHang_Adapter(productList);
        recyclerView.setAdapter(adapter);

        // Set up button listeners
        Button btnChoXacNhan = view.findViewById(R.id.btnChoXacNhan);
        Button btnChoGiaoHang = view.findViewById(R.id.btnChoGiaoHang);
        Button btnDangGiao = view.findViewById(R.id.btnDangGiao);
        Button btnDaGiao = view.findViewById(R.id.btnDaGiao);
        Button btnDaHuy = view.findViewById(R.id.btnDaHuy);

        // Example button click listeners
        btnChoXacNhan.setOnClickListener(v -> filterProducts("ChoXacNhan"));
        btnChoGiaoHang.setOnClickListener(v -> filterProducts("ChoGiaoHang"));
        btnDangGiao.setOnClickListener(v -> filterProducts("DangGiao"));
        btnDaGiao.setOnClickListener(v -> filterProducts("DaGiao"));
        btnDaHuy.setOnClickListener(v -> filterProducts("DaHuy"));

        return view;
    }

    private void filterProducts(String category) {
        // Filter product list based on category and update adapter
        List<Product_DonHang> filteredList = new ArrayList<>();
        for (Product_DonHang product : productList) {
            // Add filter condition based on category
            if (category.equals("ChoXacNhan")) {
                // Add your condition for "Chờ xác nhận"
                filteredList.add(product);
            } else if (category.equals("ChoGiaoHang")) {
                // Add your condition for "Chờ giao hàng"
                filteredList.add(product);
            } else if (category.equals("DangGiao")) {
                // Add your condition for "Đang giao"
                filteredList.add(product);
            } else if (category.equals("DaGiao")) {
                // Add your condition for "Đã giao"
                filteredList.add(product);
            } else if (category.equals("DaHuy")) {
                // Add your condition for "Đã hủy"
                filteredList.add(product);
            }
        }
        adapter.updateList(filteredList);
    }
}
