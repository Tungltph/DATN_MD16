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
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TimKiemAdapter extends RecyclerView.Adapter<TimKiemAdapter.ViewHolder> {
    private List<TimKiemDTO> dataList;
    private List<TimKiemDTO> originalDataList;
    private Context context;
    private TextView noResultsTextView;

    public TimKiemAdapter(Context context, TextView noResultsTextView) {
        this.context = context;
        this.dataList = new ArrayList<>();
        this.originalDataList = new ArrayList<>();
        this.noResultsTextView = noResultsTextView;
    }

    public void setData(List<TimKiemDTO> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        this.originalDataList.clear();
        this.originalDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void sortDataList(final boolean ascending) {
        Collections.sort(dataList, new Comparator<TimKiemDTO>() {
            @Override
            public int compare(TimKiemDTO o1, TimKiemDTO o2) {
                try {
                    int gia1 = Integer.parseInt(o1.getGiamGia().replaceAll("[\\D]", ""));
                    int gia2 = Integer.parseInt(o2.getGiamGia().replaceAll("[\\D]", ""));
                    return ascending ? Integer.compare(gia1, gia2) : Integer.compare(gia2, gia1);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        notifyDataSetChanged();
    }

    public void filterData(String query) {
        List<TimKiemDTO> filteredList = new ArrayList<>();
        if (query == null || query.isEmpty()) {
            filteredList.addAll(this.originalDataList);
        } else {
            query = query.toLowerCase().trim();
            for (TimKiemDTO item : this.originalDataList) {
                if (item.getTenSanPham() != null && item.getTenSanPham().toLowerCase().contains(query)) {
                    filteredList.add(item);
                }
            }
        }

        // Cập nhật dữ liệu và thông báo cho RecyclerView
        this.dataList.clear();
        this.dataList.addAll(filteredList);
        notifyDataSetChanged();

        // Hiển thị thông báo nếu không có kết quả
        if (filteredList.isEmpty()) {
            noResultsTextView.setVisibility(View.VISIBLE);
        } else {
            noResultsTextView.setVisibility(View.GONE);
        }
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

        if (item.getMauSchema() != null && !item.getMauSchema().isEmpty()) {
            holder.giamGiaTextView.setText(item.getMauSchema().get(0).getGiaTien() + " VND");
        }
        holder.tenSanPhamTextView.setText(item.getTenSanPham());

        Picasso.get().load(item.getHinhAnh()).into(holder.hinhAnhImageView);

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
        return dataList != null ? dataList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenSanPhamTextView, giamGiaTextView;
        ImageView hinhAnhImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenSanPhamTextView = itemView.findViewById(R.id.tv_ten_sanPham_timKiem);
            giamGiaTextView = itemView.findViewById(R.id.tv_giamGia_timKiem);
            hinhAnhImageView = itemView.findViewById(R.id.iv_product_image);
        }
    }
}
