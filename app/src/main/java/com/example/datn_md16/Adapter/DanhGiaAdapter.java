package com.example.datn_md16.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.datn_md16.DTO.DanhGiaReceiveDTO;
import com.example.datn_md16.R;

import java.util.List;

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.ViewHolder> {
    private List<DanhGiaReceiveDTO> danhGiaList;

    public DanhGiaAdapter(List<DanhGiaReceiveDTO> danhGiaList) {
        this.danhGiaList = danhGiaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_danh_gia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DanhGiaReceiveDTO danhGia = danhGiaList.get(position);
        holder.noiDungTextView.setText("Nội dung: "+danhGia.getNoiDung());
        holder.thoiGianTextView.setText(danhGia.getThoiGian());
        holder.diemDanhGiaTextView.setText(String.valueOf(danhGia.getDiemDanhGia()));
        holder.hoTenTextView.setText(danhGia.getIdKH().getHoTen());
        holder.tenDienThoaiTextView.setText("sản phẩm: "+danhGia.getIdSP().getTenDienThoai());
    }

    @Override
    public int getItemCount() {
        return danhGiaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView noiDungTextView;
        TextView thoiGianTextView;
        TextView diemDanhGiaTextView;
        TextView hoTenTextView;
        TextView sdtTextView;
        TextView tenDienThoaiTextView;
        // ImageView hinhAnhImageView; // Nếu bạn cần hiển thị hình ảnh sản phẩm

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noiDungTextView = itemView.findViewById(R.id.textNoiDung);
            thoiGianTextView = itemView.findViewById(R.id.textThoiGian);
            diemDanhGiaTextView = itemView.findViewById(R.id.textDiemDanhGia);
            hoTenTextView = itemView.findViewById(R.id.textHoTen);
            tenDienThoaiTextView = itemView.findViewById(R.id.textTenDienThoai);
            // hinhAnhImageView = itemView.findViewById(R.id.imageHinhAnh); // Nếu bạn cần hiển thị hình ảnh
        }
    }
}
