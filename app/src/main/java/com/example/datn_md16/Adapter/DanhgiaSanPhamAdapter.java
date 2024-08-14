package com.example.datn_md16.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.datn_md16.DTO.DanhGiaDTO;
import com.example.datn_md16.R;
import java.util.List;

public class DanhgiaSanPhamAdapter extends RecyclerView.Adapter<DanhgiaSanPhamAdapter.ViewHolder> {

    private List<DanhGiaDTO> danhGiaList;

    public DanhgiaSanPhamAdapter(List<DanhGiaDTO> danhGiaList) {
        this.danhGiaList = danhGiaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemdanhgiasanpham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DanhGiaDTO danhGia = danhGiaList.get(position);

        holder.tvTenNguoiDung.setText(danhGia.getIdKH().getHoTen());
        holder.ratingBarDanhGia.setRating(danhGia.getDiemDanhGia());
        holder.tvTenSanPham.setText(danhGia.getIdSP().getTenDienThoai());
        holder.tvNoiDungDanhGia.setText(danhGia.getNoiDung());
    }

    @Override
    public int getItemCount() {
        return danhGiaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenNguoiDung;
        RatingBar ratingBarDanhGia;
        TextView tvTenSanPham;
        TextView tvNoiDungDanhGia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenNguoiDung = itemView.findViewById(R.id.tvTenNguoiDung);
            ratingBarDanhGia = itemView.findViewById(R.id.ratingBarDanhGia);
            tvTenSanPham = itemView.findViewById(R.id.tvTenSanPham);
            tvNoiDungDanhGia = itemView.findViewById(R.id.tvNoiDungDanhGia);
        }
    }
}
