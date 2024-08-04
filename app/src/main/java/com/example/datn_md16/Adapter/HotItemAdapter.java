package com.example.datn_md16.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Activitys.Acti_ChiTietSP;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.DTO.TimKiemDTO;
import com.example.datn_md16.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HotItemAdapter extends RecyclerView.Adapter<HotItemAdapter.ViewHolder> {
    private Context context;
    private List<ProductHome> productList;

    public HotItemAdapter(Context context, List<ProductHome> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductHome product = productList.get(position);
        holder.txtProductName.setText(product.getTenDienThoai());
        if (product.getMauSchema() != null && !product.getMauSchema().isEmpty()) {
            holder.txtPrice.setText(product.getMauSchema().get(0).getGiaTien() + " VND");
        }
        holder.txtRating.setText(String.valueOf(product.getRating()));
        holder.ratingBar.setRating(product.getRating());
        Picasso.get().load(product.getHinhAnh()).into(holder.imgProduct);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product != null) {

                    Gson gson = new Gson();
                    String itemJson = gson.toJson(product);

                    // Start Acti_ChiTietSP and pass necessary data
                    Intent intent = new Intent(context, Acti_ChiTietSP.class);
                    intent.putExtra("sanPhamJson", itemJson); // Pass item as JSON
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return productList.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtProductName, txtPrice, txtRating;
        ImageView imgProduct;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductNameHot);
            txtPrice = itemView.findViewById(R.id.txtPriceHot);
            txtRating = itemView.findViewById(R.id.txtRatingHot);
            imgProduct = itemView.findViewById(R.id.imgSanPhamHot);
            ratingBar = itemView.findViewById(R.id.rbSaoHot);
        }
    }
}
