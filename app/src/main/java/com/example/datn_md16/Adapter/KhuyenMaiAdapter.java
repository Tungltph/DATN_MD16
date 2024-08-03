package com.example.datn_md16.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.DTO.KhuyenMai;
import com.example.datn_md16.R;

import java.util.ArrayList;
import java.util.List;

public class KhuyenMaiAdapter extends RecyclerView.Adapter<KhuyenMaiAdapter.KhuyenMaiViewHolder> {

    private List<KhuyenMai> khuyenMaiList = new ArrayList<>();
    private Context context;

    public KhuyenMaiAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<KhuyenMai> khuyenMaiList) {
        this.khuyenMaiList.clear();
        this.khuyenMaiList.addAll(khuyenMaiList);
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
        holder.bind(khuyenMai);

        holder.btnapdungngay.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Đã chọn khuyến mãi", Toast.LENGTH_SHORT).show();

            // Tạo Intent và trả kết quả về Activity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selectedPromotion", khuyenMai);

            // Trả kết quả về Acti_ThanhToan
            ((Activity) context).setResult(Activity.RESULT_OK, resultIntent);
            ((Activity) context).finish();
        });
    }

    @Override
    public int getItemCount() {
        return khuyenMaiList.size();
    }

    public static class KhuyenMaiViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTenKhuyenMai, tvNgayBatDau, tvNgayKetThuc, tvSoLuong, tvGiaKhoiDiem, tvSoLanApDung;
        private Button btnapdungngay;

        public KhuyenMaiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenKhuyenMai = itemView.findViewById(R.id.tvTenKhuyenMai);
            tvNgayBatDau = itemView.findViewById(R.id.tvNgayBatDau);
            tvNgayKetThuc = itemView.findViewById(R.id.tvNgayKetThuc);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvGiaKhoiDiem = itemView.findViewById(R.id.tvGiaKhoiDiem);
            tvSoLanApDung = itemView.findViewById(R.id.tvSoLanApDung);
            btnapdungngay = itemView.findViewById(R.id.btnApDungngay);
        }

        public void bind(KhuyenMai khuyenMai) {
            tvTenKhuyenMai.setText(khuyenMai.getTen());
            tvNgayBatDau.setText(khuyenMai.getNgayBatDau());
            tvNgayKetThuc.setText(khuyenMai.getNgayKetThuc());
            tvSoLuong.setText(String.valueOf(khuyenMai.getSoLuong()));
            tvGiaKhoiDiem.setText(String.valueOf(khuyenMai.getGiaKhoiDiem()));
            tvSoLanApDung.setText(String.valueOf(khuyenMai.getSoLanApDung()));
        }
    }
}
