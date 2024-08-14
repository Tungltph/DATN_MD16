package com.example.datn_md16.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.DTO.DonHangDTO;

import com.example.datn_md16.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ChitietdonhangAdapter extends RecyclerView.Adapter<ChitietdonhangAdapter.ViewHolder> {

    private List<DonHangDTO.SanPhamTrongDonHang> sanPhamList;

    public ChitietdonhangAdapter(List<DonHangDTO.SanPhamTrongDonHang> sanPhamList) {
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet_donhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHangDTO.SanPhamTrongDonHang sanPham = sanPhamList.get(position);
        DonHangDTO.SanPham donhang = new DonHangDTO.SanPham();

        holder.tvTenDienThoai.setText(sanPham.getSanPham().getTenDienThoai());
        holder.tvMau.setText("Màu: " + sanPham.getSanPham().getMauSchema());
        holder.tvSl.setText("Số lượng: " + sanPham.getSoLuong());

        // Định dạng số tiền
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
        numberFormat.setGroupingUsed(true);
        //String formattedTongTien = numberFormat.format(sanPham.);
        holder.tvTongtien.setText("₫" + donhang.getMauSchema().get(0).getGiaTien());

        // Tải hình ảnh bằng Picasso
        Picasso.get().load(donhang.getHinhAnh()).placeholder(R.drawable.product_background).into(holder.ivHinhAnh);




    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHinhAnh;
        TextView tvTenDienThoai, tvMau, tvTongtien, tvSl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinhAnh = itemView.findViewById(R.id.ivHinhAnh);
            tvTenDienThoai = itemView.findViewById(R.id.tvTenDienThoai);
            tvMau = itemView.findViewById(R.id.tvMau);
            tvTongtien = itemView.findViewById(R.id.tvTongtien);
            tvSl = itemView.findViewById(R.id.tvSl);
        }
    }
}
