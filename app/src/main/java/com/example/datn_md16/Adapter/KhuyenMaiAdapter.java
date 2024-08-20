package com.example.datn_md16.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Activitys.Acti_ChitietKhuyenMai;
import com.example.datn_md16.DTO.KhuyenMai;
import com.example.datn_md16.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class KhuyenMaiAdapter extends RecyclerView.Adapter<KhuyenMaiAdapter.KhuyenMaiViewHolder> {

    private List<KhuyenMai> khuyenMaiList = new ArrayList<>();
    private Context context;

    public KhuyenMaiAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<KhuyenMai> khuyenMaiList) {
        List<KhuyenMai> filteredList = new ArrayList<>();
        for (KhuyenMai khuyenMai : khuyenMaiList) {
            if (khuyenMai.isTrangThai()) {
                filteredList.add(khuyenMai);
            }
        }
        this.khuyenMaiList.clear();
        this.khuyenMaiList.addAll(filteredList);
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

        holder.tvDieuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Acti_ChitietKhuyenMai.class);
                // Pass all data to the next activity
                intent.putExtra("tenKhuyenMai", khuyenMai.getTen());
                intent.putExtra("ngayBatDau", khuyenMai.getNgayBatDau());
                intent.putExtra("ngayKetThuc", khuyenMai.getNgayKetThuc());
                intent.putExtra("soLuong", khuyenMai.getSoLuong());
                intent.putExtra("soLanApDung", khuyenMai.getSoLanApDung());
                intent.putExtra("phanTramGiamGia", khuyenMai.getPhanTramGiamGia());
                intent.putExtra("giaKhuyenMaiToiDa", khuyenMai.getGiaKhuyenMaiToiDa());
                intent.putExtra("giaKhoiDiem", khuyenMai.getGiaKhoiDiem());
                intent.putExtra("giaToiDa", khuyenMai.getGiaToiDa());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return khuyenMaiList.size();
    }

    public class KhuyenMaiViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTenKhuyenMai, tvSoLuong, tvphanTramGiamGia, tvgiaKhuyenMaiToiDa, tvgiaKhoiDiem, tvDieuKien;
        private TextView  tvngayBatDau, tvngayKetThuc, tvgiaToiDa, tvsoLanApDung;
        TextView btnapdungngay;

        public KhuyenMaiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenKhuyenMai = itemView.findViewById(R.id.tvTenKhuyenMai);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvphanTramGiamGia = itemView.findViewById(R.id.tvphanTramGiamGia);
            tvgiaKhuyenMaiToiDa = itemView.findViewById(R.id.tvgiaKhuyenMaiToiDa);
            tvgiaKhoiDiem = itemView.findViewById(R.id.tvgiaKhoiDiem);
            tvDieuKien = itemView.findViewById(R.id.tvDieuKien);
            btnapdungngay = itemView.findViewById(R.id.btnApDungngay);
            tvngayBatDau = itemView.findViewById(R.id.tvngayBatDau);
            tvngayKetThuc = itemView.findViewById(R.id.tvngayKetThuc);
            tvgiaToiDa = itemView.findViewById(R.id.tvgiaToiDa);
            tvsoLanApDung = itemView.findViewById(R.id.tvsoLanApDung);
        }

        public void bind(KhuyenMai khuyenMai) {
            // Kiểm tra trạng thái khuyến mãi
//            if (!khuyenMai.isTrangThai()) {
//                itemView.setVisibility(View.GONE);
//                return;
//            } else {
//                itemView.setVisibility(View.VISIBLE);
//            }
//
            if (khuyenMai.getSoLuong() == 0) {
                itemView.setVisibility(View.GONE);
            } else {
                tvSoLuong.setVisibility(View.VISIBLE);
                tvSoLuong.setText(String.valueOf("x" + khuyenMai.getSoLuong()));
            }

            // Hiển thị thông tin khuyến mãi
            tvTenKhuyenMai.setText(khuyenMai.getTen());
            tvngayBatDau.setText(khuyenMai.getNgayBatDau());
            tvsoLanApDung.setText(String.valueOf("Số lần áp dụng " + khuyenMai.getSoLanApDung()));
            tvphanTramGiamGia.setText(String.valueOf("Giảm giá " + khuyenMai.getPhanTramGiamGia() + "%"));

            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String giaKhuyenMaiToiDaFormatted = decimalFormat.format(khuyenMai.getGiaKhuyenMaiToiDa());
            tvgiaKhuyenMaiToiDa.setText("Giảm tối đa ₫" + giaKhuyenMaiToiDaFormatted);

            String giaKhoiDiemFormatted = decimalFormat.format(khuyenMai.getGiaKhoiDiem());
            tvgiaKhoiDiem.setText("Đơn tối thiểu ₫" + giaKhoiDiemFormatted);

            String giaToiDaFM = decimalFormat.format(khuyenMai.getGiaToiDa());
            tvgiaToiDa.setText("Giá tối đa ₫" + giaToiDaFM);
        }

    }

}
