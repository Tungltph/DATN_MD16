package com.example.datn_md16.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.datn_md16.R;

import java.text.DecimalFormat;

public class Acti_ChitietKhuyenMai extends AppCompatActivity {

    private TextView tvTenKhuyenMai, tvSoLuong, tvphanTramGiamGia, tvgiaKhuyenMaiToiDa, tvgiaKhoiDiem, tvngayBatDau, tvngayKetThuc, tvgiaToiDa, tvsoLanApDung,tvDieuKienS;
    Button btnDOngY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiet_khuyenmai);

        Toolbar toolbar = findViewById(R.id.toolbarChiTietKM);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getString(R.string.toolbarChitietKM));

        // Initialize views
        tvTenKhuyenMai = findViewById(R.id.tvTenKhuyenMai);
        tvSoLuong = findViewById(R.id.tvSoLuong);
        tvphanTramGiamGia = findViewById(R.id.tvphanTramGiamGia);
        tvgiaKhuyenMaiToiDa = findViewById(R.id.tvgiaKhuyenMaiToiDa);
        tvgiaKhoiDiem = findViewById(R.id.tvgiaKhoiDiem);
        tvngayBatDau = findViewById(R.id.tvngayBatDau);
        tvngayKetThuc = findViewById(R.id.tvngayKetThuc);
//        tvgiaToiDa = findViewById(R.id.tvgiaToiDa);
        tvsoLanApDung = findViewById(R.id.tvsoLanApDung);
        tvDieuKienS = findViewById(R.id.tvDieuKienS);
        btnDOngY = findViewById(R.id.btnDongY);

        btnDOngY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Acti_ChitietKhuyenMai.this,Acti_KhuyenMai.class));
            }
        });

        // Retrieve data from Intent
        String tenKhuyenMai = getIntent().getStringExtra("tenKhuyenMai");
        String ngayBatDau = getIntent().getStringExtra("ngayBatDau");
        String ngayKetThuc = getIntent().getStringExtra("ngayKetThuc");
        int soLuong = getIntent().getIntExtra("soLuong", 0);
        int soLanApDung = getIntent().getIntExtra("soLanApDung", 0);
        int phanTramGiamGia = getIntent().getIntExtra("phanTramGiamGia", 0);
        int giaKhuyenMaiToiDa = getIntent().getIntExtra("giaKhuyenMaiToiDa", 0);
        int giaKhoiDiem = getIntent().getIntExtra("giaKhoiDiem", 0);
//        int giaToiDa = getIntent().getIntExtra("giaToiDa", 0);

        // Format currency
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        // Display data
        tvTenKhuyenMai.setText(tenKhuyenMai);
        tvngayBatDau.setText(ngayBatDau);
        tvngayKetThuc.setText(" - "+ngayKetThuc);
        tvSoLuong.setText("Số lượng: " + soLuong);
        tvsoLanApDung.setText("Số lần áp dụng: " + soLanApDung);
        tvphanTramGiamGia.setText("Giảm giá " + phanTramGiamGia + "%");
        tvgiaKhuyenMaiToiDa.setText("Giảm tối đa ₫" + decimalFormat.format(giaKhuyenMaiToiDa));
        tvgiaKhoiDiem.setText("Đơn tối thiểu ₫" + decimalFormat.format(giaKhoiDiem));
//        tvgiaToiDa.setText("Giá tối đa ₫" + decimalFormat.format(giaToiDa));
        tvDieuKienS.setText("+Sử dụng mã giảm giá cho các sản phẩm trong gian hàng. \n+Số lượt sử dụng có hạn, hãy chú ý sử dụng \n+Voucher giảm "+phanTramGiamGia+"% tối đa ₫"+decimalFormat.format(giaKhuyenMaiToiDa)+" cho đơn từ ₫"+decimalFormat.format(giaKhoiDiem)+" trên ứng dụng polyShop. hạn sử dụng "+ngayKetThuc+".Số lượng có hạn. Lưu ý: Voucher sẽ không được hoàn lại sau khi đã hết hiệu lực hoặc hết thời gian sử dụng trong bất kì trường hợp nào. ");

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
