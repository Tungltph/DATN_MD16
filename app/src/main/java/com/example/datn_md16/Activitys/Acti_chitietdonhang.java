package com.example.datn_md16.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.datn_md16.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

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
//        tvTrangThaiDonHang = findViewById(R.id.tvTrangThaiDonHang);
        tvPhuongThucThanhToan = findViewById(R.id.tvPhuongThucThanhToan);
        tvTongtien = findViewById(R.id.tvTongtien);
        TextView tvTongTienHoaDon = findViewById(R.id.tvTongTienHoaDon);
        TextView soLuongHoaDon = findViewById(R.id.tvsoLuongHoaDon);



        Toolbar toolbar = findViewById(R.id.toolbarChiTietDonHang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiển thị nút back
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Đặt tiêu đề cho Toolbar từ chuỗi trong strings.xml
        setTitle(getString(R.string.toolbarChiTietDonHang));

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
        tvMau.setText("Màu: "+mauSchema);
        tvSoLuong.setText(String.valueOf("Số lượng: "+soLuong));
        tvTenKhachHang.setText("Tên người nhận hàng: "+hoTen);
        tvSdt.setText("Số điện thoại người nhận: "+sdt);
        tvNgayDatHang.setText(ngayDatHang);
        soLuongHoaDon.setText(String.valueOf(soLuong));
        tvNgayNhanHangDuKien.setText(ngayNhanHang);
        tvDiaChi.setText(diaChiGiaoHang);
//        tvTrangThaiDonHang.setText(trangThaiDonHang);
        tvPhuongThucThanhToan.setText(phuongThucThanhToan);
        // Format the total price with dots as thousand separators
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
        numberFormat.setGroupingUsed(true);
        String formattedTongTien = numberFormat.format(tongTien);

        // Set the formatted total price to the TextView
        tvTongtien.setText("₫"+formattedTongTien);
        tvTongTienHoaDon.setText("₫"+formattedTongTien); // tạm


        // Tải hình ảnh bằng Picasso
        Picasso.get().load(hinhAnhUrl).into(ivHinhAnh);
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

