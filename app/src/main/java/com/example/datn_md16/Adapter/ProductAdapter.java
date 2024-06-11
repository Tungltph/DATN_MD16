package com.example.datn_md16.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.datn_md16.DTO.Product;
import com.example.datn_md16.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yeu_thich, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.imgSanPham.setImageResource(product.getImage());
        holder.textViewName.setText(product.getName());
        holder.rbSao.setRating(product.getRating());
        holder.txtRb.setText(String.valueOf(product.getRating()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSanPham;
        TextView textViewName, txtRb;
        RatingBar rbSao;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            textViewName = itemView.findViewById(R.id.textViewName);
            rbSao = itemView.findViewById(R.id.rbSao);
            txtRb = itemView.findViewById(R.id.txtRb);
        }
    }
}
