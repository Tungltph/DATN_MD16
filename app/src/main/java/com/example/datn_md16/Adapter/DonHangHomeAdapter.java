package com.example.datn_md16.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Activitys.Acti_chitietdonhang;
import com.example.datn_md16.DTO.DiaChiDTO;
import com.example.datn_md16.DTO.DonHangDTO;
import com.example.datn_md16.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

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

            // Hiển thị màu sắc
            String color = "Màu: " + (sanPham.getMauSchema() != null && !sanPham.getMauSchema().isEmpty() ? sanPham.getMauSchema().get(0).getMau() : "N/A");
            holder.productColor.setText(color);

            // Hiển thị số lượng và tổng tiền
            int quantity = donHang.getSoLuong(); // Số lượng từ đơn hàng
            double totalAmount = donHang.getTongTien(); // Tổng tiền từ đơn hàng

            holder.soLuong.setText("Số lượng: " + quantity);
            holder.productPrice.setText(formatPrice(totalAmount)); // Hiển thị tổng tiền với định dạng

            // Load image using Picasso
            String imageUrl = sanPham.getHinhAnh();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(holder.productImage);
            } else {
                holder.productImage.setImageResource(R.drawable.product_background); // Placeholder image if URL is empty
            }

            // Cập nhật trạng thái nút hủy và đánh giá
            if (donHang.getTrangThaiDonHang().equals("Chờ xác nhận") || donHang.getTrangThaiDonHang().equals("Đang xử lý")) {
                holder.btnHuy.setVisibility(View.VISIBLE);
            } else {
                holder.btnHuy.setVisibility(View.GONE);
            }

            if (donHang.getTrangThaiDonHang().equals("Đã giao hàng")) {
                holder.tvDanhGia.setVisibility(View.VISIBLE);
            } else {
                holder.tvDanhGia.setVisibility(View.GONE);
            }

            holder.btnHuy.setOnClickListener(v -> {
                // Handle "Hủy Đơn" button click
                // Add your logic to handle cancellation here
            });

            holder.btnXemChiTiet.setOnClickListener(v -> {
                DiaChiDTO diaChiDTO = new DiaChiDTO();

                Intent intent = new Intent(context, Acti_chitietdonhang.class);
                intent.putExtra("tenDienThoai", sanPham.getTenDienThoai());
                intent.putExtra("mauSchema", sanPham.getMauSchema() != null && !sanPham.getMauSchema().isEmpty() ? sanPham.getMauSchema().get(0).getMau() : "N/A");
                intent.putExtra("soLuong", donHang.getSoLuong());
                intent.putExtra("tongTien", donHang.getTongTien());
                intent.putExtra("hoTen", donHang.getIdDiaChi().getTen());
                intent.putExtra("sdt", donHang.getIdDiaChi().getSdt()); // đảm bảo bạn có trường này trong model
                intent.putExtra("ngayDatHang", donHang.getNgayDatHang());
                intent.putExtra("ngayNhanHang", donHang.getNgayNhanHang());
                intent.putExtra("diaChiGiaoHang", donHang.getDiaChiGiaoHang());
                intent.putExtra("trangThaiDonHang", donHang.getTrangThaiDonHang());
                intent.putExtra("phuongThucThanhToan", donHang.getPhuongThucThanhToan());
                intent.putExtra("hinhAnhUrl", sanPham.getHinhAnh());
               
                context.startActivity(intent);
            });
            holder.tvDanhGia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(v.getContext());
                    dialog.setContentView(R.layout.dialog_danhgia);

                    ImageView imgstar1 = dialog.findViewById(R.id.star1);
                }
            });
        }
    }

    // Utility method to format price with thousand separators
    private String formatPrice(double price) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);
        return "₫"+decimalFormat.format(price);
    }

    public void updateData(List<DonHangDTO.DonHang> newDonHangList) {
        this.donHangList = newDonHangList;
        notifyDataSetChanged(); // Thông báo cho RecyclerView cập nhật dữ liệu
    }

    @Override
    public int getItemCount() {
        return donHangList != null ? donHangList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productColor, productPrice, soLuong, btnHuy, btnXemChiTiet, tvDanhGia;
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
            tvDanhGia = itemView.findViewById(R.id.tvDanhGia);
        }
    }
}
