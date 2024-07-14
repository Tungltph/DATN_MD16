package com.example.datn_md16.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.R;
import com.squareup.picasso.Picasso;

public class Acti_ChiTietSP extends AppCompatActivity {

    private TextView txtProductName, txtPrice, txtRating;
    private ImageView imgProduct;
    private RatingBar ratingBar;
    private TextView tvCamera, tvCameraTruoc, tvKichThuoc, tvCPU, tvRam, tvSim, tvPin, tvHeDieuHanh, tvNamSanXuat, tvCongNgheManHinh, tvMoTaThem, tvDoPhanGiai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);

        // Ánh xạ các view
        txtProductName = findViewById(R.id.tvTenDienThoai);
        txtPrice = findViewById(R.id.tvGiaGoc);
        imgProduct = findViewById(R.id.ivHinhAnh);
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

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String tenDienThoai = intent.getStringExtra("TenDienThoai");
        String giaGoc = intent.getStringExtra("GiaGoc");
        String hinhAnh = intent.getStringExtra("HinhAnh");
        String camera = intent.getStringExtra("Camera");
        String cameraTruoc = intent.getStringExtra("CameraTruoc");
        String kichThuoc = intent.getStringExtra("KichThuoc");
        String cpu = intent.getStringExtra("CPU");
        String ram = intent.getStringExtra("Ram");
        String sim = intent.getStringExtra("Sim");
        String pin = intent.getStringExtra("Pin");
        String heDieuHanh = intent.getStringExtra("HeDieuHanh");
        String namSanXuat = intent.getStringExtra("NamSanXuat");
        String congNgheManHinh = intent.getStringExtra("CongNgheManHinh");
        String moTaThem = intent.getStringExtra("MoTaThem");
        String doPhanGiai = intent.getStringExtra("DoPhanGiai");

        // Hiển thị dữ liệu lên các view
        txtProductName.setText(tenDienThoai);
        txtPrice.setText(giaGoc);
        Picasso.get().load(hinhAnh).into(imgProduct);
        tvCamera.setText(camera);
        tvCameraTruoc.setText(cameraTruoc);
        tvKichThuoc.setText(kichThuoc);
        tvCPU.setText(cpu);
        tvRam.setText(ram);
        tvSim.setText(sim);
        tvPin.setText(pin);
        tvHeDieuHanh.setText(heDieuHanh);
        tvNamSanXuat.setText(namSanXuat);
        tvCongNgheManHinh.setText(congNgheManHinh);
        tvMoTaThem.setText(moTaThem);
        tvDoPhanGiai.setText(doPhanGiai);
    }
}
