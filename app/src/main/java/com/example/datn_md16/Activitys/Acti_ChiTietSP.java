package com.example.datn_md16.Activitys;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.datn_md16.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.MauAdapter;
import com.example.datn_md16.DTO.GioHangDTO;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.Interfa.ApiService;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Acti_ChiTietSP extends AppCompatActivity {
    private TextView txtProductName, txtPrice;
    private ImageView imgProduct;
    private TextView tvCamera, tvCameraTruoc, tvKichThuoc, tvCPU, tvRam, tvSim, tvPin, tvHeDieuHanh, tvNamSanXuat, tvCongNgheManHinh, tvMoTaThem, tvDoPhanGiai;
    private Button buttonBuyNow, btnthemgiohang;
    private BottomSheetDialog bottomSheetDialog;
    private ProductHome sanPham;
    private String selectedColor; // Biến để lưu màu đã chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarChiTietSP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.toolbarChiTietSP_title));

        // Initialize views
        txtProductName = findViewById(R.id.tvTenDienThoai);
        txtPrice = findViewById(R.id.tvGiaMau);
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
        buttonBuyNow = findViewById(R.id.button_buy_now);
        btnthemgiohang = findViewById(R.id.button_add_to_cart);

        btnthemgiohang.setOnClickListener(v -> {
            if (sanPham != null && sanPham.getMauSchema() != null) {
                them_gio_hang(sanPham.getMauSchema());
            }
        });

        buttonBuyNow.setOnClickListener(v -> {
            if (sanPham != null && sanPham.getMauSchema() != null) {
                showBottomSheetDialog(sanPham.getMauSchema());
            }
        });

        // Retrieve JSON data from Intent
        String sanPhamJson = getIntent().getStringExtra("sanPhamJson");

        if (sanPhamJson != null) {
            // Parse JSON data to ProductHome object
            Gson gson = new Gson();
            sanPham = gson.fromJson(sanPhamJson, ProductHome.class);

            // Update UI elements with product details
            updateUI(sanPham);
        }
    }

    private void updateUI(ProductHome sanPham) {
        if (sanPham != null) {
            // Update TextViews
            txtProductName.setText(sanPham.getTenDienThoai());
            if (sanPham.getMauSchema() != null && !sanPham.getMauSchema().isEmpty()) {
                txtPrice.setText(sanPham.getMauSchema().get(0).getGiaTien() + " VND");
            }
            tvCamera.setText(sanPham.getCamera());
            tvCameraTruoc.setText(sanPham.getCameraTruoc());
            tvKichThuoc.setText(sanPham.getKichThuoc());
            tvCPU.setText(sanPham.getcPU());
            tvRam.setText(sanPham.getRam());
            tvSim.setText(sanPham.getSim());
            tvPin.setText(sanPham.getPin());
            tvHeDieuHanh.setText(sanPham.getHeDieuHanh());
            tvNamSanXuat.setText(String.valueOf(sanPham.getNamSanXuat()));
            tvCongNgheManHinh.setText(sanPham.getCongNgheManHinh());
            tvMoTaThem.setText(sanPham.getMoTaThem());
            tvDoPhanGiai.setText(sanPham.getDoPhanGiai());
            Picasso.get().load(sanPham.getHinhAnh()).into(imgProduct);
        }
    }

    private String getSelectedColor() {
        return selectedColor != null ? selectedColor : "";
    }

    private void showBottomSheetDialog(List<ProductHome.MauSchema> mauList) {
        bottomSheetDialog = new BottomSheetDialog(Acti_ChiTietSP.this);
        View sheetView = getLayoutInflater().inflate(R.layout.sheet_dialog_giohang, null);

        RecyclerView rcvMau = sheetView.findViewById(R.id.rcv_Mau);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3); // 3 cột
        rcvMau.setLayoutManager(layoutManager);

        if (mauList != null && !mauList.isEmpty()) {
            MauAdapter mauAdapter = new MauAdapter(mauList, mau -> {
                selectedColor = mau.getMau();
                updatePriceAndQuantity(mauList, selectedColor, sheetView.findViewById(R.id.tv_giamGia_gioHang), sheetView.findViewById(R.id.tv_soLuong));
            });

            rcvMau.setAdapter(mauAdapter);

            ProductHome.MauSchema firstColor = mauList.get(0);
            selectedColor = firstColor.getMau();
            updatePriceAndQuantity(mauList, selectedColor, sheetView.findViewById(R.id.tv_giamGia_gioHang), sheetView.findViewById(R.id.tv_soLuong));
        }
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }

    private void them_gio_hang(List<ProductHome.MauSchema> mauList) {
        bottomSheetDialog = new BottomSheetDialog(Acti_ChiTietSP.this);
        View sheetView = getLayoutInflater().inflate(R.layout.bottom_them_gio_hang, null);

        RecyclerView rcvMau = sheetView.findViewById(R.id.rcv_Mau);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3); // 3 cột
        rcvMau.setLayoutManager(layoutManager);

        TextView tvGiamGiaGioHang = sheetView.findViewById(R.id.tv_giamGia_gioHang);
        TextView tvSoLuong = sheetView.findViewById(R.id.tv_soLuong);
        TextView tvKQ = sheetView.findViewById(R.id.tvKQ);

        if (mauList != null && !mauList.isEmpty()) {
            // Khởi tạo selectedColor nếu chưa có giá trị
            if (selectedColor == null) {
                selectedColor = mauList.get(0).getMau(); // Chọn màu đầu tiên làm mặc định
            }

            MauAdapter mauAdapter = new MauAdapter(mauList, mau -> {
                selectedColor = mau.getMau(); // Cập nhật màu đã chọn
                updatePriceAndQuantity(mauList, selectedColor, tvGiamGiaGioHang, tvSoLuong);
            });

            rcvMau.setAdapter(mauAdapter);

            // Cập nhật giá và số lượng dựa trên màu đã chọn
            updatePriceAndQuantity(mauList, selectedColor, tvGiamGiaGioHang, tvSoLuong);
        }

        Button btnthemgiohang = sheetView.findViewById(R.id.btn_themgiohang);
        TextView tvGiam = sheetView.findViewById(R.id.tvGiam);
        TextView tvTang = sheetView.findViewById(R.id.tvTang);

        tvGiam.setOnClickListener(view -> {
            int quantity = Integer.parseInt(tvKQ.getText().toString());
            if (quantity > 1) {
                tvKQ.setText(String.valueOf(quantity - 1));
                updatePriceAndQuantity(mauList, selectedColor, tvGiamGiaGioHang, tvSoLuong);
            }
        });

        tvTang.setOnClickListener(view -> {
            int quantity = Integer.parseInt(tvKQ.getText().toString());
            tvKQ.setText(String.valueOf(quantity + 1));
            updatePriceAndQuantity(mauList, selectedColor, tvGiamGiaGioHang, tvSoLuong);
        });

        btnthemgiohang.setOnClickListener(view -> {
            int quantity = Integer.parseInt(tvKQ.getText().toString());
            if (selectedColor != null && !selectedColor.trim().isEmpty()) {
                addToCart(sanPham.get_id(), selectedColor, quantity);
                bottomSheetDialog.dismiss();
                Toast.makeText(Acti_ChiTietSP.this, "Đã chọn màu " + selectedColor, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Acti_ChiTietSP.this, "Vui lòng chọn màu hợp lệ trước khi thêm vào giỏ hàng.", Toast.LENGTH_SHORT).show();
            }
        });

        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }




    private void updatePriceAndQuantity(List<ProductHome.MauSchema> mauList, String selectedColor, TextView tvPrice, TextView tvQuantity) {
        for (ProductHome.MauSchema mau : mauList) {
            if (mau.getMau().equals(selectedColor)) {
                tvPrice.setText(mau.getGiaTien() + " VND");
                tvQuantity.setText(String.valueOf(mau.getSoLuong()));
                break;
            }
        }
    }



    private void addToCart(String productId, String color, int quantity) {
        // Kiểm tra giá trị của col


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.8:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        GioHangDTO gioHang = new GioHangDTO();
        gioHang.setIdSanPham(productId);
        gioHang.setIdMau(color); // Sử dụng màu đã chọn
        gioHang.setSoLuong(quantity);

        Call<Void> call = apiService.adddToCart(gioHang);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Acti_ChiTietSP.this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Acti_ChiTietSP.this, "Thêm vào giỏ hàng thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(Acti_ChiTietSP.this, "Lỗi mạng, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Hoặc sử dụng onBackPressed()
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
