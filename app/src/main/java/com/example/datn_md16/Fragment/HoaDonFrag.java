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

        // Khởi tạo danh sách sản phẩm
        productList = new ArrayList<>();
        productList.add(new Product_DonHang("iPhone 15 Pro", "Đen", "15.000.000đ", "1", R.drawable.img_1, "ChoXacNhan"));
        productList.add(new Product_DonHang("Samsung Galaxy S23", "Xanh", "20.000.000đ", "1", R.drawable.img_1, "ChoGiaoHang"));
        productList.add(new Product_DonHang("MacBook Pro", "Bạc", "30.000.000đ", "1", R.drawable.img_1, "DangGiao"));
        productList.add(new Product_DonHang("iPad Air", "Trắng", "12.000.000đ", "1", R.drawable.img_1, "DaGiao")); // Thêm sản phẩm "Đã Giao"
        productList.add(new Product_DonHang("iPhon 11", "Đỏ", "8.000.000đ", "1", R.drawable.img_1, "DaHuy")); // Thêm sản phẩm "Đã Hủy"
        // Thêm nhiều sản phẩm vào danh sách

        // Khởi tạo adapter
        adapter = new DonHang_Adapter(productList);
        recyclerView.setAdapter(adapter);

        // Cài đặt listener cho các nút
        Button btnChoXacNhan = view.findViewById(R.id.btnChoXacNhan);
        Button btnChoGiaoHang = view.findViewById(R.id.btnChoGiaoHang);
        Button btnDangGiao = view.findViewById(R.id.btnDangGiao);
        Button btnDaGiao = view.findViewById(R.id.btnDaGiao);
        Button btnDaHuy = view.findViewById(R.id.btnDaHuy);

        // Ví dụ về listener cho các nút
        btnChoXacNhan.setOnClickListener(v -> filterProducts("ChoXacNhan"));
        btnChoGiaoHang.setOnClickListener(v -> filterProducts("ChoGiaoHang"));
        btnDangGiao.setOnClickListener(v -> filterProducts("DangGiao"));
        btnDaGiao.setOnClickListener(v -> filterProducts("DaGiao"));
        btnDaHuy.setOnClickListener(v -> filterProducts("DaHuy"));

        return view;
    }

    private void filterProducts(String category) {
        // Lọc danh sách sản phẩm dựa trên category và cập nhật adapter
        List<Product_DonHang> filteredList = new ArrayList<>();
        for (Product_DonHang product : productList) {
            if (product.getStatus().equals(category)) {
                filteredList.add(product);
            }
        }
        adapter.updateList(filteredList);
    }
}
