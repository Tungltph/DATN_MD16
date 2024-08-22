package com.example.datn_md16.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.datn_md16.Adapter.ChitietdonhangAdapter;
import com.example.datn_md16.DTO.DonHangDTO;
import com.example.datn_md16.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Acti_chitietdonhang extends AppCompatActivity {


    private TextView  tvTenKhachHang, tvSdt, tvNgayDatHang, tvNgayNhanHangDuKien, tvDiaChi,  tvPhuongThucThanhToan;
    private ChitietdonhangAdapter chitietdonhangAdapter;
    private RecyclerView recyclerView;
    DonHangDTO.DonHang donHang = new DonHangDTO.DonHang();
    ArrayList<DonHangDTO.SanPhamTrongDonHang> sanPhamList = new ArrayList<>(donHang.getSanPhamTrongDonHang());
//    Intent intent = getIntent().putParcelableArrayListExtra("sanPhamList", (ArrayList<? extends Parcelable>) sanPhamList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acti_chitietdonhang);

        // Ánh xạ các view
        tvTenKhachHang = findViewById(R.id.tvTenKhachHang);
        tvSdt = findViewById(R.id.tvSdt);
        tvNgayDatHang = findViewById(R.id.tvNgayDatHang);
        tvNgayNhanHangDuKien = findViewById(R.id.tvNgayNhanHangDuKien);
        tvDiaChi = findViewById(R.id.tvDiaChi);
//        tvTrangThaiDonHang = findViewById(R.id.tvTrangThaiDonHang);
        tvPhuongThucThanhToan = findViewById(R.id.tvPhuongThucThanhToan);
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
        String hoTen = intent.getStringExtra("hoTen");
        String sdt = intent.getStringExtra("sdt");
//        String tenDienThoai = intent.getStringExtra("tenDienThoai");
//        String mauSchema = intent.getStringExtra("mauSchema");
//        int soLuong = intent.getIntExtra("soLuong", 0);
//        double tongTien = intent.getDoubleExtra("tongTien", 0.0);
        String ngayDatHang = intent.getStringExtra("ngayDatHang");
        String ngayNhanHang = intent.getStringExtra("ngayNhanHang");
        String diaChiGiaoHang = intent.getStringExtra("diaChiGiaoHang");
        String trangThaiDonHang = intent.getStringExtra("trangThaiDonHang");
        String phuongThucThanhToan = intent.getStringExtra("phuongThucThanhToan");
        String hinhAnhUrl = intent.getStringExtra("hinhAnhUrl");

        // Khởi tạo danh sách trước khi sử dụng

        chitietdonhangAdapter.notifyDataSetChanged();


        recyclerView = findViewById(R.id.rcvchitietdonhang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        chitietdonhangAdapter = new ChitietdonhangAdapter(sanPhamList);
        recyclerView.setAdapter(chitietdonhangAdapter);


        // Gán dữ liệu lên các view


        tvTenKhachHang.setText("Tên người nhận hàng: "+hoTen);
        tvSdt.setText("Số điện thoại người nhận: "+sdt);
        tvNgayDatHang.setText(ngayDatHang);

        tvNgayNhanHangDuKien.setText(ngayNhanHang);
        tvDiaChi.setText(diaChiGiaoHang);
//        tvTrangThaiDonHang.setText(trangThaiDonHang);
        tvPhuongThucThanhToan.setText(phuongThucThanhToan);
        // Format the total price with dots as thousand separators
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
        numberFormat.setGroupingUsed(true);
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