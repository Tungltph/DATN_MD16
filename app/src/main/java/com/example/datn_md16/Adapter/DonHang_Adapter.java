package com.example.datn_md16.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.DTO.Product_DonHang;
import com.example.datn_md16.R;

import java.util.List;

public class DonHang_Adapter extends RecyclerView.Adapter<DonHang_Adapter.ProductViewHolder> {
    private List<Product_DonHang> productList;

    public DonHang_Adapter(List<Product_DonHang> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Get the data model based on position
        Product_DonHang product = productList.get(position);

        // Set item views based on your views and data model
        holder.productName.setText(product.getName());
        holder.productColor.setText("Màu: " + product.getColor());
        holder.productPrice.setText(product.getPrice());
        holder.productImage.setImageResource(product.getImageResId());
        holder.productQuantity.setText("Số lượng: " + product.getSoluong());

        // Set up listeners for buttons if needed
        holder.btnHuy.setOnClickListener(v -> {
            // Handle "Hủy Đơn" button click
        });

        holder.btnXemChiTiet.setOnClickListener(v -> {
            // Handle "Xem chi tiết" button click
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateList(List<Product_DonHang> newList) {
        productList = newList;
        notifyDataSetChanged();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName, productColor, productPrice, productQuantity, btnHuy, btnXemChiTiet;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productColor = itemView.findViewById(R.id.productColor);
            productPrice = itemView.findViewById(R.id.productPrice);
            productQuantity = itemView.findViewById(R.id.soLuong);
            btnHuy = itemView.findViewById(R.id.btnHuy);
            btnXemChiTiet = itemView.findViewById(R.id.btnXemChiTiet);
        }
    }
}
