package com.example.datn_md16.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.datn_md16.Activitys.Acti_ChiTietSP;
import com.example.datn_md16.DTO.ChiTietDienThoaiDTO;
import com.example.datn_md16.R;
import com.google.gson.Gson;

import java.util.List;

public class DienThoaiAdapter extends RecyclerView.Adapter<DienThoaiAdapter.DienThoaiViewHolder> {
    private Context context;
    private List<ChiTietDienThoaiDTO> dienThoaiList;

    public DienThoaiAdapter(Context context, List<ChiTietDienThoaiDTO> dienThoaiList) {
        this.context = context;
        this.dienThoaiList = dienThoaiList;
    }

    @NonNull
    @Override
    public DienThoaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new, parent, false);
        return new DienThoaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DienThoaiViewHolder holder, int position) {
        ChiTietDienThoaiDTO dienThoai = dienThoaiList.get(position);
        holder.tvTenDienThoai.setText(dienThoai.getTenDienThoai());
        holder.tvCamera.setText("Camera: " + dienThoai.getCamera());
        holder.tvCameraTruoc.setText("Camera trước: " + dienThoai.getCameraTruoc());
        holder.tvKichThuoc.setText("Kích thước: " + dienThoai.getKichThuoc());
        holder.tvCPU.setText("CPU: " + dienThoai.getcPU());
        holder.tvRam.setText("RAM: " + dienThoai.getRam());
        holder.tvSim.setText("Sim: " + dienThoai.getSim());
        holder.tvPin.setText("Pin: " + dienThoai.getPin());
        holder.tvHeDieuHanh.setText("Hệ điều hành: " + dienThoai.getHeDieuHanh());
        holder.tvNamSanXuat.setText("Năm sản xuất: " + dienThoai.getNamSanXuat());
        holder.tvCongNgheManHinh.setText("Công nghệ màn hình: " + dienThoai.getCongNgheManHinh());
        holder.tvMoTaThem.setText("Mô tả thêm: " + dienThoai.getMoTaThem());
        holder.tvDoPhanGiai.setText("Độ phân giải: " + dienThoai.getDoPhanGiai());
        holder.tvGiaGoc.setText("Giá gốc: " + dienThoai.getGiaGoc());
        holder.tvGiamGia.setText("Giảm giá: " + dienThoai.getGiamGia());


        // Load hình ảnh từ URL sử dụng Glide
        Glide.with(context).load(dienThoai.getHinhAnh()).into(holder.ivHinhAnh);

        // Xử lý sự kiện khi click vào item
        holder.itemView.setOnClickListener(v -> {
            Gson gson = new Gson();
            String chiTietDienThoaiJson = gson.toJson(dienThoai);

            Intent intent = new Intent(context, Acti_ChiTietSP.class);
            intent.putExtra("ChiTietDienThoai", chiTietDienThoaiJson);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dienThoaiList.size();
    }

    public static class DienThoaiViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenDienThoai, tvCamera, tvCameraTruoc, tvKichThuoc, tvCPU, tvRam, tvSim, tvPin, tvHeDieuHanh, tvNamSanXuat, tvCongNgheManHinh, tvMoTaThem, tvDoPhanGiai, tvGiaGoc, tvGiamGia, tvTrangThai;
        ImageView ivHinhAnh;

        public DienThoaiViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenDienThoai = itemView.findViewById(R.id.tvTenDienThoai);
            tvCamera = itemView.findViewById(R.id.tvCamera);
            tvCameraTruoc = itemView.findViewById(R.id.tvCameraTruoc);
            tvKichThuoc = itemView.findViewById(R.id.tvKichThuoc);
            tvCPU = itemView.findViewById(R.id.tvCPU);
            tvRam = itemView.findViewById(R.id.tvRam);
            tvSim = itemView.findViewById(R.id.tvSim);
            tvPin = itemView.findViewById(R.id.tvPin);
            tvHeDieuHanh = itemView.findViewById(R.id.tvHeDieuHanh);
            tvNamSanXuat = itemView.findViewById(R.id.tvNamSanXuat);
            tvCongNgheManHinh = itemView.findViewById(R.id.tvCongNgheManHinh);
            tvMoTaThem = itemView.findViewById(R.id.tvMoTaThem);
            tvDoPhanGiai = itemView.findViewById(R.id.tvDoPhanGiai);
            tvGiaGoc = itemView.findViewById(R.id.tvGiaGoc);
            tvGiamGia = itemView.findViewById(R.id.tvGiamGia);
            ivHinhAnh = itemView.findViewById(R.id.ivHinhAnh);
        }
    }
}
