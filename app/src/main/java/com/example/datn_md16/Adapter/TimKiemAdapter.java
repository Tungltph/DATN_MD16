package com.example.datn_md16.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

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
                    String giamGia1 = o1.getGiamGia();
                    String giamGia2 = o2.getGiamGia();

                    if (giamGia1 == null) giamGia1 = "0";
                    if (giamGia2 == null) giamGia2 = "0";

                    int gia1 = Integer.parseInt(giamGia1.replaceAll("[\\D]", ""));
                    int gia2 = Integer.parseInt(giamGia2.replaceAll("[\\D]", ""));

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

        // Set image using Picasso
        Picasso.get().load(item.getHinhAnh()).into(holder.hinhAnhImageView);

        holder.tenSanPhamTextView.setText(item.getTenSanPham());

        if (item.getMauSchema() != null && !item.getMauSchema().isEmpty()) {
            double giaTien = item.getMauSchema().get(0).getGiaTien();

            // Định dạng giá trị giaTien chỉ hiển thị phần nguyên
            NumberFormat formatter = NumberFormat.getIntegerInstance(Locale.US);
            holder.giamGiaTextView.setText("₫" + formatter.format(giaTien));

            // Tính toán giá gốc
            double phanTram = Double.parseDouble(item.getGiamGia());
            double giaGoc = giaTien / (1 - (phanTram / 100));

            // Định dạng giá trị giaGoc chỉ hiển thị phần nguyên
            holder.giaGocTextView.setText("₫" + formatter.format(giaGoc));

            // Áp dụng gạch ngang cho giaGocTextView
            setStrikeThroughText(holder.giaGocTextView);
        }

        holder.phanTramTextView.setText("-" + item.getGiamGia() + "%");

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


    public void setStrikeThroughText(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }


    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenSanPhamTextView, giamGiaTextView, giaGocTextView, phanTramTextView;
        ImageView hinhAnhImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenSanPhamTextView = itemView.findViewById(R.id.tv_ten_sanPham_timKiem);
            giamGiaTextView = itemView.findViewById(R.id.tv_giamGia_timKiem);
            giaGocTextView = itemView.findViewById(R.id.tv_giaGoc_timKiem);
            phanTramTextView = itemView.findViewById(R.id.tv_phanTram_timKiem);
            hinhAnhImageView = itemView.findViewById(R.id.iv_product_image);
        }
    }
}
