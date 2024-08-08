package com.example.datn_md16.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

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
        if (productList == null || productList.isEmpty()) return;

        ProductHome item = productList.get(position);

        // Set image using Picasso
        Picasso.get().load(item.getHinhAnh()).into(holder.imgProduct);

        holder.txtProductName.setText(item.getTenDienThoai());

        if (item.getMauSchema() != null && !item.getMauSchema().isEmpty()) {
            double giaTien = item.getMauSchema().get(0).getGiaTien();

            // Định dạng giá trị giaTien chỉ hiển thị phần nguyên
            NumberFormat formatter = NumberFormat.getIntegerInstance(Locale.US);
            holder.txtPrice.setText("₫" + formatter.format(giaTien));

            // Tính toán giá gốc
            double phanTram = Double.parseDouble(item.getGiamGia());
            double giaGoc = giaTien / (1 - (phanTram / 100));

            // Định dạng giá trị giaGoc chỉ hiển thị phần nguyên
            holder.tvgiaGocHot.setText("₫" + formatter.format(giaGoc));
            setStrikeThroughText(holder.tvgiaGocHot);
        }

        holder.tvPhanTramHot.setText("-" + item.getGiamGia() + "%");

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

        // Thêm hiệu ứng phóng to khi chạm vào
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.animate().scaleX(1.1f).scaleY(1.1f).setDuration(300).start();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.animate().scaleX(1f).scaleY(1f).setDuration(300).start();
                        break;
                }
                return false;
            }
        });
    }

    public void setStrikeThroughText(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }



    @Override
    public int getItemCount() {
        return productList.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtProductName, txtPrice, tvgiaGocHot,tvPhanTramHot;
        ImageView imgProduct;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductNameHot);
            txtPrice = itemView.findViewById(R.id.txtPriceHot);
            imgProduct = itemView.findViewById(R.id.imgSanPhamHot);
            tvgiaGocHot = itemView.findViewById(R.id.tv_giaGoc_hot);
            tvPhanTramHot = itemView.findViewById(R.id.tv_phanTram_hot);
        }
    }
}
