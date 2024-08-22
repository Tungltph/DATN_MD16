package com.example.datn_md16.Adapter;

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
        DonHangDTO.SanPhamTrongDonHang sanPhamTrongDonHang = sanPhamList.get(position);
        DonHangDTO.SanPham sanPham = sanPhamTrongDonHang.getSanPham();




        holder.tvProductName.setText(sanPham.getTenDienThoai());
        holder.tvProductColor.setText("Màu: " + sanPham.getMauSchema().get(0).getMau());
        holder.tvProductPrice.setText(formatCurrency(sanPham.getMauSchema().get(0).getGiaTien()));
        holder.tvProductQuantity.setText("Số lượng: " + sanPhamTrongDonHang.getSoLuong());

        // Tải hình ảnh sản phẩm bằng Picasso
        Picasso.get().load(sanPham.getHinhAnh())
                .placeholder(R.drawable.product_background) // Hình nền mặc định khi chưa load xong
                .into(holder.ivProductImage);
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvProductName, tvProductColor, tvProductPrice, tvProductQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductColor = itemView.findViewById(R.id.tvProductColor);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductQuantity = itemView.findViewById(R.id.tvsl);
        }
    }

    private String formatCurrency(double amount) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return numberFormat.format(amount);
    }
}
