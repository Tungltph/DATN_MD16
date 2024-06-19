package com.example.datn_md16.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Activitys.Acti_KhuyenMai;
import com.example.datn_md16.Activitys.Acti_TimKiem;
import com.example.datn_md16.DTO.KhuyenMai;
import com.example.datn_md16.R;

import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiAdapter extends RecyclerView.Adapter<KhuyenMaiAdapter.KhuyenMaiViewHolder> {

    private List<KhuyenMai> khuyenMaiList = new ArrayList<>();

    public void setData(List<KhuyenMai> khuyenMaiList) {
        this.khuyenMaiList = khuyenMaiList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KhuyenMaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khuyen_mai, parent, false);
        return new KhuyenMaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhuyenMaiViewHolder holder, int position) {
        KhuyenMai khuyenMai = khuyenMaiList.get(position);
        holder.btnApDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Acti_TimKiem.class);
                v.getContext().startActivity(intent);
            }
        });
        holder.bind(khuyenMai);
    }


    @Override
    public int getItemCount() {
        return khuyenMaiList.size();
    }

    public static class KhuyenMaiViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTen, tvGiamGia, tvThoiGian;
        Button btnApDung;

        public KhuyenMaiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvTenKhuyenMai);
            tvGiamGia = itemView.findViewById(R.id.tvGiamGia);
            tvThoiGian = itemView.findViewById(R.id.tvThoiGian);
            btnApDung = itemView.findViewById(R.id.btnApDung);
        }

        public void bind(KhuyenMai khuyenMai) {
            tvTen.setText(khuyenMai.getTen());
            tvGiamGia.setText(khuyenMai.getGiamGia());
            tvThoiGian.setText(khuyenMai.getThoiGian());
        }
    }
}
