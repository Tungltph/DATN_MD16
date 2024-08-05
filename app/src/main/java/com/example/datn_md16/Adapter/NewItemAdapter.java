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
import com.example.datn_md16.R;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class NewItemAdapter extends RecyclerView.Adapter<NewItemAdapter.ViewHolder> {

    private Context context;
    private List<ProductHome> productList;

    public NewItemAdapter(Context context, List<ProductHome> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (productList == null || productList.isEmpty()) return;

        ProductHome item = productList.get(position);

        // Set image using Picasso
        Picasso.get().load(item.getHinhAnh()).into(holder.imgProduct);

        holder.txtProductName.setText(item.getTenDienThoai());

        if (item.getMauSchema() != null && !item.getMauSchema().isEmpty()) {
            double giaTien = item.getMauSchema().get(0).getGiaTien();

            // Định dạng giá trị giaTien chỉ hiển thị phần nguyên
            NumberFormat formatter = NumberFormat.getIntegerInstance(Locale.GERMANY);
            holder.txtPrice.setText("₫" + formatter.format(giaTien));

            // Tính toán giá gốc
            double phanTram = Double.parseDouble(item.getGiamGia());
            double giaGoc = giaTien / (1 - (phanTram / 100));

            // Định dạng giá trị giaGoc chỉ hiển thị phần nguyên
            holder.tvgiaGocNew.setText("₫" + formatter.format(giaGoc));
        }

        holder.tvPhanTramNew.setText("-" + item.getGiamGia() + "%");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item != null) {
                    Gson gson = new Gson();
                    String itemJson = gson.toJson(item);

                    Intent intent = new Intent(context, Acti_ChiTietSP.class);
                    intent.putExtra("sanPhamJson", itemJson);
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

        TextView txtProductName, txtPrice, tvgiaGocNew, tvPhanTramNew;
        ImageView imgProduct;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.tvProductNameNew);
            txtPrice = itemView.findViewById(R.id.tvPriceNew);
            imgProduct = itemView.findViewById(R.id.imgSanPhamNew);
            tvgiaGocNew = itemView.findViewById(R.id.tv_giaGoc_new);
            tvPhanTramNew = itemView.findViewById(R.id.tv_phanTram_hot);
        }
    }
}
