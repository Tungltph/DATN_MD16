package com.example.datn_md16.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IphoneAdapter extends RecyclerView.Adapter<IphoneAdapter.IphoneViewHolder> {
    private Context context;
    private List<ProductHome> productList;

    public IphoneAdapter(Context context, List<ProductHome> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public IphoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_san_pham, parent, false);
        return new IphoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IphoneViewHolder holder, int position) {
        ProductHome product = productList.get(position);

        holder.tvProductName.setText(product.getTenDienThoai());
        if (product.getMauSchema() != null && !product.getMauSchema().isEmpty()) {
            holder.tvPrice.setText(product.getMauSchema().get(0).getGiaTien() + " VND");
        }
        holder.tvRating.setText(String.valueOf(product.getRating()));

        // Sử dụng Picasso để tải ảnh từ URL
        Picasso.get().load(product.getHinhAnh()).into(holder.imgSanPham);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class IphoneViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSanPham;
        TextView tvProductName, tvPrice, tvRating;
        RatingBar rbSaos;

        public IphoneViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvRating = itemView.findViewById(R.id.tvRating);
            rbSaos = itemView.findViewById(R.id.rbSaos);
        }
    }
}
