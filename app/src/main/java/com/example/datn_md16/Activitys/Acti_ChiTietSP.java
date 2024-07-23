package com.example.datn_md16.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datn_md16.DTO.ChiTietDienThoaiDTO;
import com.example.datn_md16.DTO.GioHangDTO;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Acti_ChiTietSP extends AppCompatActivity {

    private TextView txtProductName, txtPrice, txtRating;
    private ImageView imgProduct;
    private TextView tvCamera, tvCameraTruoc, tvKichThuoc, tvCPU, tvRam, tvSim, tvPin, tvHeDieuHanh, tvNamSanXuat, tvCongNgheManHinh, tvMoTaThem, tvDoPhanGiai;
    private Button btnadd_GioHang;
    private ApiService apiService;
    private int quantity = 1; // Giá trị ban đầu của số lượng
    private ChiTietDienThoaiDTO sanPham;

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
        btnadd_GioHang = findViewById(R.id.button_add_to_cart);

        Button buttonDecrease = findViewById(R.id.button_decrease);
        Button buttonIncrease = findViewById(R.id.button_increase);
        TextView textQuantity = findViewById(R.id.text_quantity);
        Button btnDen = findViewById(R.id.btnDen);
        Button btnTrang = findViewById(R.id.btnTrang);
        Button btnGhi = findViewById(R.id.btnGhi);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.2:3000/api/") // Địa chỉ API của bạn
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        btnadd_GioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    textQuantity.setText(String.valueOf(quantity));
                }
            }
        });

        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                textQuantity.setText(String.valueOf(quantity));
            }
        });

        btnDen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSelectedColor(btnDen  );
            }
        });

        btnTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSelectedColor(btnTrang);
            }
        });

        btnGhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSelectedColor(btnGhi);
            }
        });

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

        // Khởi tạo đối tượng ChiTietDienThoaiDTO từ dữ liệu nhận được qua Intent
        sanPham = new ChiTietDienThoaiDTO();
        sanPham.setId(intent.getStringExtra("SanPhamId"));
        sanPham.setTenDienThoai(tenDienThoai);
        sanPham.setGiaGoc(giaGoc);
        sanPham.setHinhAnh(hinhAnh);
        sanPham.setCamera(camera);
        sanPham.setCameraTruoc(cameraTruoc);
        sanPham.setKichThuoc(kichThuoc);
        sanPham.setcPU(cpu);
        sanPham.setRam(ram);
        sanPham.setSim(sim);
        sanPham.setPin(pin);
        sanPham.setHeDieuHanh(heDieuHanh);
        sanPham.setNamSanXuat(namSanXuat);
        sanPham.setCongNgheManHinh(congNgheManHinh);
        sanPham.setMoTaThem(moTaThem);
        sanPham.setDoPhanGiai(doPhanGiai);

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

    private void addToCart() {
        if (sanPham == null) {
            Toast.makeText(this, "Không thể thêm vào giỏ hàng, sản phẩm không tồn tại.", Toast.LENGTH_SHORT).show();
            return;
        }

        GioHangDTO gioHangDTO = new GioHangDTO();
        gioHangDTO.setIdSanPham(sanPham.getId());
        gioHangDTO.setIdAccount(gioHangDTO.getIdAccount()); // Đặt id tài khoản của bạn
        gioHangDTO.setSoLuong(quantity); // Không cần set idMau

        apiService.adddToCart(gioHangDTO).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Acti_ChiTietSP.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    // Lấy thông tin chi tiết lỗi nếu có
                    try {
                        String errorMessage = response.errorBody().string();
                        Toast.makeText(Acti_ChiTietSP.this, "Thêm vào giỏ hàng thất bại: " + errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Acti_ChiTietSP.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void highlightSelectedColor(Button selectedButton) {
        // Đặt màu nền cho tất cả nút thành màu mặc định
        resetColorButtons();

        // Đặt màu nền cho nút được chọn thành màu xanh
        selectedButton.setBackgroundColor(getResources().getColor(R.color.green)); // Chọn màu xanh
    }

    private void resetColorButtons() {
        Button btnDen = findViewById(R.id.btnDen);
        Button btnTrang = findViewById(R.id.btnTrang);
        Button btnGhi = findViewById(R.id.btnGhi);


        btnDen.setBackgroundResource(R.drawable.border); // Màu nền mặc định
        btnTrang.setBackgroundResource(R.drawable.border);
        btnGhi.setBackgroundResource(R.drawable.border);
    }
}
