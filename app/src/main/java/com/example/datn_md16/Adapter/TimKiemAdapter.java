package com.example.datn_md16.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Activitys.Acti_ChiTietSP;
import com.example.datn_md16.DTO.TimKiemDTO;
import com.example.datn_md16.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TimKiemAdapter extends RecyclerView.Adapter<TimKiemAdapter.ViewHolder> {
    private List<TimKiemDTO> dataList;
    private Context context;

    public TimKiemAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
    }

    public void setData(List<TimKiemDTO> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void sortDataList(final boolean ascending) {
        Collections.sort(dataList, new Comparator<TimKiemDTO>() {
            @Override
            public int compare(TimKiemDTO o1, TimKiemDTO o2) {
                // Remove dots and convert price from string to integer for comparison
                int gia1 = Integer.parseInt(o1.getGiamGia().replaceAll("\\.", ""));
                int gia2 = Integer.parseInt(o2.getGiamGia().replaceAll("\\.", ""));
                return ascending ? Integer.compare(gia1, gia2) : Integer.compare(gia2, gia1);
            }
        });
        notifyDataSetChanged();
    }

    public void sortDefault() {
        // Perform default sorting logic here based on MongoDB default order
        // Example:
        // return o1.getId().compareTo(o2.getId()); // Sort by ID ascending
        // return 0; // Example if no change in order
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_timkiem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (dataList == null || dataList.isEmpty()) return;

        TimKiemDTO item = dataList.get(position);

        // Set data to views in item_timkiem.xml layout
        holder.tenSanPhamTextView.setText(item.getTenSanPham());
        holder.giamGiaTextView.setText(item.getGiamGia());
        holder.giaGocTextView.setText(item.getGiaGoc());

        // Load image using Picasso/Glide or any other image loading library
        Picasso.get().load(item.getHinhAnh()).into(holder.hinhAnhImageView);

        // Handle item click to open detail activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Acti_ChiTietSP and pass necessary data
                Intent intent = new Intent(context, Acti_ChiTietSP.class);
                intent.putExtra("sanPhamPosition", position); // Pass item position as identifier
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this line if needed
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
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
