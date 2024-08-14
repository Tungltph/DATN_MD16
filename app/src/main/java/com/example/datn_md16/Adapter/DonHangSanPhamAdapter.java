package com.example.datn_md16.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.datn_md16.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.DTO.DonHangDTO;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class DonHangSanPhamAdapter extends RecyclerView.Adapter<DonHangSanPhamAdapter.ViewHolder> {
    private List<DonHangDTO.DonHang> donHangList;
    private Context context;

    public DonHangSanPhamAdapter(List<DonHangDTO.DonHang> donHangList, Context context) {
        this.donHangList = donHangList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_don_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHangDTO.DonHang donHang = donHangList.get(position);
        List<DonHangDTO.SanPhamTrongDonHang> sanPhamTrongDonHangList = donHang.getSanPhamTrongDonHang();

        if (sanPhamTrongDonHangList != null && !sanPhamTrongDonHangList.isEmpty()) {
            holder.productContainer.removeAllViews(); // Clear previous views

            for (DonHangDTO.SanPhamTrongDonHang sanPhamTrongDonHang : sanPhamTrongDonHangList) {
                DonHangDTO.SanPham sanPham = sanPhamTrongDonHang.getSanPham();

                // Inflate item_san_pham layout
                View productView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.item_donhang, holder.productContainer, false);

                // Find views in item_san_pham layout
                TextView productName = productView.findViewById(R.id.productName);
                TextView productColor = productView.findViewById(R.id.productColor);
                TextView soLuong = productView.findViewById(R.id.soLuong);
                TextView productPrice = productView.findViewById(R.id.productPrice);
                ImageView productImage = productView.findViewById(R.id.productImage);

                // Update product information
                String tenSanPham = sanPham.getTenDienThoai();
                int soLuongSanPham = sanPhamTrongDonHang.getSoLuong();
                productName.setText(tenSanPham);
                soLuong.setText("Số lượng: " + soLuongSanPham);

                String color = "Màu: " + (sanPham.getMauSchema() != null && !sanPham.getMauSchema().isEmpty() ? sanPham.getMauSchema().get(0).getMau() : "N/A");
                productColor.setText(color);

                double giaTien = sanPham.getMauSchema().get(0).getGiaTien();
                productPrice.setText(formatPrice(giaTien));

                productImage.setImageResource(R.drawable.product_background); // Placeholder image
                String imageUrl = sanPham.getHinhAnh();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Picasso.get().load(imageUrl).into(productImage);
                }

                // Add product view to container
                holder.productContainer.addView(productView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return donHangList != null ? donHangList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout productContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            productContainer = itemView.findViewById(R.id.productContainer);
        }
    }

    // Phương thức tiện ích để định dạng giá với dấu phân cách hàng nghìn
    private String formatPrice(double price) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);
        return "₫" + decimalFormat.format(price);
    }
}


