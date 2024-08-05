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
        if (productList == null || productList.isEmpty()) return;

        ProductHome item = productList.get(position);

        // Set image using Picasso
        Picasso.get().load(item.getHinhAnh()).into(holder.imgSanPham);

        holder.tvProductName.setText(item.getTenDienThoai());

        if (item.getMauSchema() != null && !item.getMauSchema().isEmpty()) {
            double giaTien = item.getMauSchema().get(0).getGiaTien();

            // Định dạng giá trị giaTien chỉ hiển thị phần nguyên
            NumberFormat formatter = NumberFormat.getIntegerInstance(Locale.GERMANY);
            holder.tvPrice.setText("₫" + formatter.format(giaTien));

            // Tính toán giá gốc
            double phanTram = Double.parseDouble(item.getGiamGia());
            double giaGoc = giaTien / (1 - (phanTram / 100));

            // Định dạng giá trị giaGoc chỉ hiển thị phần nguyên
            holder.tvgiaGoc.setText("₫" + formatter.format(giaGoc));
        }

        holder.tvPhanTram.setText("-" + item.getGiamGia() + "%");

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

    public static class IphoneViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSanPham;
        TextView tvProductName, tvPrice, tvgiaGoc,tvPhanTram;

        public IphoneViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvgiaGoc = itemView.findViewById(R.id.tv_giaGoc_sp);
            tvPhanTram = itemView.findViewById(R.id.tv_phanTram_sp);
        }
    }
}
