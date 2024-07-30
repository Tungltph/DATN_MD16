package com.example.datn_md16.Adapter;

import android.content.Context;
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

import java.util.List;

public class DonHangHomeAdapter extends RecyclerView.Adapter<DonHangHomeAdapter.ViewHolder> {

    private List<DonHangDTO.DonHang> donHangList;
    private Context context;

    public DonHangHomeAdapter(List<DonHangDTO.DonHang> donHangList, Context context) {
        this.donHangList = donHangList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHangDTO.DonHang donHang = donHangList.get(position);

        if (donHang.getSanPhamList() != null && !donHang.getSanPhamList().isEmpty()) {
            DonHangDTO.SanPham sanPham = donHang.getSanPhamList().get(0);

            holder.productName.setText(sanPham.getTenDienThoai());

            // Xử lý màu và giá
            String color = "Màu: " + (sanPham.getMauSchema() != null && !sanPham.getMauSchema().isEmpty() ? sanPham.getMauSchema().get(0).getMau() : "N/A");
            holder.productColor.setText(color);

            int price = (sanPham.getMauSchema() != null && !sanPham.getMauSchema().isEmpty()) ? (int) sanPham.getMauSchema().get(0).getGiaTien() : 0;
            holder.productPrice.setText(price + "đ");

            int quantity = (sanPham.getMauSchema() != null && !sanPham.getMauSchema().isEmpty()) ? sanPham.getMauSchema().get(0).getSoLuong() : 0;
            holder.soLuong.setText("Số lượng: " + quantity);

            // Load image using Picasso
            Picasso.get().load(sanPham.getHinhAnh()).into(holder.productImage);
        }

        holder.btnHuy.setOnClickListener(v -> {
            // Handle "Hủy Đơn" button click
            // Add your logic to handle cancellation here
        });

        holder.btnXemChiTiet.setOnClickListener(v -> {
            // Handle "Xem chi tiết" button click
            // Add your logic to handle viewing details here
        });
    }

    @Override
    public int getItemCount() {
        return donHangList != null ? donHangList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productColor, productPrice, soLuong, btnHuy, btnXemChiTiet;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productColor = itemView.findViewById(R.id.productColor);
            productPrice = itemView.findViewById(R.id.productPrice);
            soLuong = itemView.findViewById(R.id.soLuong);
            productImage = itemView.findViewById(R.id.productImage);
            btnHuy = itemView.findViewById(R.id.btnHuy);
            btnXemChiTiet = itemView.findViewById(R.id.btnXemChiTiet);
        }
    }
}
