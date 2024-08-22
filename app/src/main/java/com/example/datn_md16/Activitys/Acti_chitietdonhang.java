package com.example.datn_md16.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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


    private TextView  tvTenKhachHang, tvSdt, tvNgayDatHang, tvNgayNhanHangDuKien, tvDiaChi,  tvPhuongThucThanhToan,tvTongTienHoaDon;
    private ChitietdonhangAdapter chitietdonhangAdapter;
    private RecyclerView recyclerView;
    DonHangDTO.DonHang donHang = new DonHangDTO.DonHang();
    ArrayList<DonHangDTO.SanPhamTrongDonHang> sanPhamList = new ArrayList<>();
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
        tvTongTienHoaDon = findViewById(R.id.tvTongTienHoaDon);
        TextView soLuongHoaDon = findViewById(R.id.tvsoLuongHoaDon);






        Toolbar toolbar = findViewById(R.id.toolbarChiTietDonHang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiển thị nút back
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Đặt tiêu đề cho Toolbar từ chuỗi trong strings.xml
        setTitle(getString(R.string.toolbarChiTietDonHang));




        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            DonHangDTO.DonHang infoOrder = (DonHangDTO.DonHang) bundle.getSerializable("infoOrder");

            recyclerView = findViewById(R.id.rcvchitietdonhang);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            chitietdonhangAdapter = new ChitietdonhangAdapter(infoOrder.getSanPhamTrongDonHang());
            recyclerView.setAdapter(chitietdonhangAdapter);

            tvTenKhachHang.setText("Tên người nhận hàng: "+infoOrder.getIdKH().getHoTen());
            tvSdt.setText("Số điện thoại người nhận: "+infoOrder.getIdDiaChi().getSdt());
            tvNgayDatHang.setText(infoOrder.getNgayDatHang());
            tvNgayNhanHangDuKien.setText(infoOrder.getNgayNhanHang());
            tvDiaChi.setText(infoOrder.getDiaChiGiaoHang());
            tvPhuongThucThanhToan.setText(infoOrder.getPhuongThucThanhToan());

            chitietdonhangAdapter.notifyDataSetChanged();



            // Định dạng số tiền và hiển thị
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
            numberFormat.setGroupingUsed(true);
            tvTongTienHoaDon.setText(numberFormat.format(infoOrder.getTongTien())+" VNĐ");
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