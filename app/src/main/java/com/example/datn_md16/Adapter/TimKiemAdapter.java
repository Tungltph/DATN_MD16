package com.example.datn_md16.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.DTO.TimKiemDTO;
import com.example.datn_md16.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TimKiemAdapter extends RecyclerView.Adapter<TimKiemAdapter.ViewHolder> {
    private List<TimKiemDTO> dataList;
    private Context context;

    public TimKiemAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>(); // Khởi tạo dataList để tránh NullPointerException
    }

    public void setData(List<TimKiemDTO> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_timkiem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (dataList == null || dataList.isEmpty()) return; // Kiểm tra null hoặc rỗng để tránh lỗi

        TimKiemDTO item = dataList.get(position);

        // Set data to views in item_timkiem.xml layout
        holder.tenSanPhamTextView.setText(item.getTenSanPham());
        holder.giamGiaTextView.setText(item.getGiamGia());
        holder.giaGocTextView.setText(item.getGiaGoc());

        // Load image using Picasso/Glide or any other image loading library
        Picasso.get().load(item.getHinhAnh()).into(holder.hinhAnhImageView);
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0; // Trả về số lượng item, nếu dataList null thì trả về 0
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenSanPhamTextView, giamGiaTextView, giaGocTextView;
        ImageView hinhAnhImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenSanPhamTextView = itemView.findViewById(R.id.tv_ten_sanPham_timKiem);
            giamGiaTextView = itemView.findViewById(R.id.tv_giamGia_timKiem);
            giaGocTextView = itemView.findViewById(R.id.tv_giaGocTimKiem);
            hinhAnhImageView = itemView.findViewById(R.id.iv_product_image);
        }
    }
}
