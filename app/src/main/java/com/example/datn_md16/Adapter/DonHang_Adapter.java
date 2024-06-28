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

public class DonHang_Adapter extends RecyclerView.Adapter<DonHang_Adapter.ViewHolder> {
    private List<Product_DonHang> productList;

    public DonHang_Adapter(List<Product_DonHang> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product_DonHang product = productList.get(position);
        holder.nameTextView.setText(product.getName());
        holder.colorTextView.setText(product.getColor());
        holder.priceTextView.setText(product.getPrice());
        holder.soluongTextView.setText(product.getSoluong());
        holder.imageView.setImageResource(product.getImageResId());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateList(List<Product_DonHang> newList) {
        productList = newList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView colorTextView;
        public TextView priceTextView;
        public TextView soluongTextView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.productName);
            colorTextView = itemView.findViewById(R.id.productColor);
            priceTextView = itemView.findViewById(R.id.productPrice);
            soluongTextView = itemView.findViewById(R.id.soLuong);
            imageView = itemView.findViewById(R.id.productImage);
        }
    }
}
