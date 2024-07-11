package com.example.datn_md16.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.datn_md16.DTO.ChiTietDienThoaiDTO;
import com.example.datn_md16.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class Acti_ChiTietSP extends AppCompatActivity {
    private ImageView ivHinhAnh;
    private TextView tvTenDienThoai, tvCamera, tvCameraTruoc, tvKichThuoc, tvCPU, tvRam, tvSim, tvPin, tvHeDieuHanh, tvNamSanXuat, tvCongNgheManHinh, tvMoTaThem, tvDoPhanGiai, tvGiaGoc, tvGiamGia, tvTrangThai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);

        // Thiết lập Toolbar và hiển thị nút back
        Toolbar toolbar = findViewById(R.id.toolbarChiTietSP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiển thị nút back
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Button buttonDecrease = findViewById(R.id.button_decrease);
        Button buttonIncrease = findViewById(R.id.button_increase);
        TextView textQuantity = findViewById(R.id.text_quantity);

        final int[] quantity = {1}; // Giá trị ban đầu của số lượng

// Xử lý sự kiện khi bấm nút giảm
        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity[0] > 1) {
                    quantity[0]--;
                    textQuantity.setText(String.valueOf(quantity[0]));
                }
            }
        });

// Xử lý sự kiện khi bấm nút tăng
        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity[0]++;
                textQuantity.setText(String.valueOf(quantity[0]));
            }
        });


        // Đặt tiêu đề cho Toolbar từ chuỗi trong strings.xml
        setTitle(getString(R.string.toolbarChiTietSP_title));

        // Khởi tạo các TextView và ImageView
        ivHinhAnh = findViewById(R.id.ivHinhAnh);
        tvTenDienThoai = findViewById(R.id.tvTenDienThoai);
        tvCamera = findViewById(R.id.tvCamera);
        tvCameraTruoc = findViewById(R.id.tvCameraTruoc);
        tvKichThuoc = findViewById(R.id.tvKichThuoc);
        tvCPU = findViewById(R.id.tvCPU);
        tvRam = findViewById(R.id.tvRam);
        tvSim = findViewById(R.id.tvSim);
        tvPin = findViewById(R.id.tvPin);
        tvHeDieuHanh = findViewById(R.id.tvHeDieuHanh);
        tvNamSanXuat = findViewById(R.id.tvNamSanXuat);
        tvCongNgheManHinh = findViewById(R.id.tvCongNgheManHinh);
        tvMoTaThem = findViewById(R.id.tvMoTaThem);
        tvDoPhanGiai = findViewById(R.id.tvDoPhanGiai);
        tvGiaGoc = findViewById(R.id.tvGiaGoc);
        tvGiamGia = findViewById(R.id.tvGiamGia);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String sanPhamJson = intent.getStringExtra("sanPhamJson");

        // Chuyển đổi JSON thành đối tượng ChiTietDienThoaiDTO
        if (sanPhamJson != null) {
            Gson gson = new Gson();
            ChiTietDienThoaiDTO sanPham = gson.fromJson(sanPhamJson, ChiTietDienThoaiDTO.class);

            // Hiển thị dữ liệu sản phẩm lên các TextView và ImageView
            if (sanPham != null) {
                tvTenDienThoai.setText(sanPham.getTenDienThoai());
                tvCamera.setText(sanPham.getCamera());
                tvCameraTruoc.setText(sanPham.getCameraTruoc());
                tvKichThuoc.setText(sanPham.getKichThuoc());
                tvCPU.setText(sanPham.getcPU());
                tvRam.setText(sanPham.getRam());
                tvSim.setText(sanPham.getSim());
                tvPin.setText(sanPham.getPin());
                tvHeDieuHanh.setText(sanPham.getHeDieuHanh());
                tvNamSanXuat.setText(sanPham.getNamSanXuat());
                tvCongNgheManHinh.setText(sanPham.getCongNgheManHinh());
                tvMoTaThem.setText(sanPham.getMoTaThem());
                tvDoPhanGiai.setText(sanPham.getDoPhanGiai());
                tvGiaGoc.setText(sanPham.getGiaGoc());
                tvGiamGia.setText(sanPham.getGiamGia());

                // Sử dụng Picasso để tải hình ảnh
                Picasso.get().load(sanPham.getHinhAnh()).into(ivHinhAnh);


                // Log các giá trị để kiểm tra
                Log.d("ChiTietSP", "Tên điện thoại: " + sanPham.getTenDienThoai());
                Log.d("ChiTietSP", "Camera: " + sanPham.getCamera());
                Log.d("ChiTietSP", "Camera trước: " + sanPham.getCameraTruoc());
                Log.d("ChiTietSP", "Kích thước: " + sanPham.getKichThuoc());
                Log.d("ChiTietSP", "CPU: " + sanPham.getcPU());
                Log.d("ChiTietSP", "RAM: " + sanPham.getRam());
                Log.d("ChiTietSP", "Sim: " + sanPham.getSim());
                Log.d("ChiTietSP", "Pin: " + sanPham.getPin());
                Log.d("ChiTietSP", "Hệ điều hành: " + sanPham.getHeDieuHanh());
                Log.d("ChiTietSP", "Năm sản xuất: " + sanPham.getNamSanXuat());
                Log.d("ChiTietSP", "Công nghệ màn hình: " + sanPham.getCongNgheManHinh());
                Log.d("ChiTietSP", "Mô tả thêm: " + sanPham.getMoTaThem());
                Log.d("ChiTietSP", "Độ phân giải: " + sanPham.getDoPhanGiai());
                Log.d("ChiTietSP", "Giá gốc: " + sanPham.getGiaGoc());
                Log.d("ChiTietSP", "Giảm giá: " + sanPham.getGiamGia());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Xử lý khi nhấn nút back trên Toolbar
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
