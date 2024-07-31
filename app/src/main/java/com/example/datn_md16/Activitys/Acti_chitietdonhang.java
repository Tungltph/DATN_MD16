package com.example.datn_md16.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.datn_md16.R;
import com.squareup.picasso.Picasso;

public class Acti_chitietdonhang extends AppCompatActivity {

    private ImageView ivHinhAnh;
    private TextView tvTenDienThoai, tvMau, tvSoLuong, tvTenKhachHang, tvSdt, tvNgayDatHang, tvNgayNhanHangDuKien, tvDiaChi, tvTrangThaiDonHang, tvPhuongThucThanhToan, tvTongtien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_chitietdonhang);

        // Ánh xạ các view
        ivHinhAnh = findViewById(R.id.ivHinhAnh);
        tvTenDienThoai = findViewById(R.id.tvTenDienThoai);
        tvMau = findViewById(R.id.tvMau);
        tvSoLuong = findViewById(R.id.tvSl);
        tvTenKhachHang = findViewById(R.id.tvTenKhachHang);
        tvSdt = findViewById(R.id.tvSdt);
        tvNgayDatHang = findViewById(R.id.tvNgayDatHang);
        tvNgayNhanHangDuKien = findViewById(R.id.tvNgayNhanHangDuKien);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvTrangThaiDonHang = findViewById(R.id.tvTrangThaiDonHang);
        tvPhuongThucThanhToan = findViewById(R.id.tvPhuongThucThanhToan);
        tvTongtien = findViewById(R.id.tvTongtien);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String tenDienThoai = intent.getStringExtra("tenDienThoai");
        String mauSchema = intent.getStringExtra("mauSchema");
        int soLuong = intent.getIntExtra("soLuong", 0);
        double tongTien = intent.getDoubleExtra("tongTien", 0.0);
        String hoTen = intent.getStringExtra("hoTen");
        String sdt = intent.getStringExtra("sdt");
        String ngayDatHang = intent.getStringExtra("ngayDatHang");
        String ngayNhanHang = intent.getStringExtra("ngayNhanHang");
        String diaChiGiaoHang = intent.getStringExtra("diaChiGiaoHang");
        String trangThaiDonHang = intent.getStringExtra("trangThaiDonHang");
        String phuongThucThanhToan = intent.getStringExtra("phuongThucThanhToan");
        String hinhAnhUrl = intent.getStringExtra("hinhAnhUrl");

        // Gán dữ liệu lên các view
        tvTenDienThoai.setText(tenDienThoai);
        tvMau.setText(mauSchema);
        tvSoLuong.setText(String.valueOf(soLuong));
        tvTenKhachHang.setText(hoTen);
        tvSdt.setText(sdt);
        tvNgayDatHang.setText(ngayDatHang);
        tvNgayNhanHangDuKien.setText(ngayNhanHang);
        tvDiaChi.setText(diaChiGiaoHang);
        tvTrangThaiDonHang.setText(trangThaiDonHang);
        tvPhuongThucThanhToan.setText(phuongThucThanhToan);
        tvTongtien.setText(String.format("%.0fđ", tongTien));

        // Tải hình ảnh bằng Picasso
        Picasso.get().load(hinhAnhUrl).into(ivHinhAnh);
    }
}

