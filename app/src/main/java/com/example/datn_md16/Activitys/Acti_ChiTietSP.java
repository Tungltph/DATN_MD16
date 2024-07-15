package com.example.datn_md16.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class    Acti_ChiTietSP extends AppCompatActivity {

    private TextView txtProductName, txtPrice;
    private ImageView imgProduct;
    private RatingBar ratingBar;
    private TextView tvCamera, tvCameraTruoc, tvKichThuoc, tvCPU, tvRam, tvSim, tvPin, tvHeDieuHanh, tvNamSanXuat, tvCongNgheManHinh, tvMoTaThem, tvDoPhanGiai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);

        // Thiết lập Toolbar và hiển thị nút back
        Toolbar toolbar = findViewById(R.id.toolbarChiTietSP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiển thị nút back
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getString(R.string.toolbarChiTietSP_title));

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
        String sanPhamJson = intent.getStringExtra("sanPhamJson");

        // Chuyển đổi JSON thành đối tượng ProductHome
        Gson gson = new Gson();
        ProductHome product = gson.fromJson(sanPhamJson, ProductHome.class);

        // Hiển thị dữ liệu lên các view
        if (product != null) {
            txtProductName.setText(product.getTenDienThoai());
            txtPrice.setText(product.getGiaGoc());
            Picasso.get().load(product.getHinhAnh()).into(imgProduct);
            tvCamera.setText(product.getCamera());
            tvCameraTruoc.setText(product.getCameraTruoc());
            tvKichThuoc.setText(product.getKichThuoc());
            tvCPU.setText(product.getcPU());
            tvRam.setText(product.getRam());
            tvSim.setText(product.getSim());
            tvPin.setText(product.getPin());
            tvHeDieuHanh.setText(product.getHeDieuHanh());
            tvNamSanXuat.setText(product.getNamSanXuat());
            tvCongNgheManHinh.setText(product.getCongNgheManHinh());
            tvMoTaThem.setText(product.getMoTaThem());
            tvDoPhanGiai.setText(product.getDoPhanGiai());
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
