package com.example.datn_md16.Activitys;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.datn_md16.Adapter.MauAdapter;
import com.example.datn_md16.DTO.GioHangDTO;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.R;
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

    private ApiService apiService; // Khai báo ApiService

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

        // Khởi tạo Retrofit và ApiService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.8:3000/") // Thay thế bằng URL thực tế của bạn
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

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

            // Set initial selected color to the first item in the list
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
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rcvMau.setLayoutManager(layoutManager);

        ImageView imgGioHang = sheetView.findViewById(R.id.img_gioHang);
        TextView tvGiamGiaGioHang = sheetView.findViewById(R.id.tv_giamGia_gioHang);
        TextView tvSoLuong = sheetView.findViewById(R.id.tv_soLuong);
        TextView tvKQ = sheetView.findViewById(R.id.tvKQ);
        TextView btnGiamSoLuong = sheetView.findViewById(R.id.tvGiam);
        TextView btnTangSoLuong = sheetView.findViewById(R.id.tvTang);

        // Set initial quantity
        int initialQuantity = 1;
        tvKQ.setText(String.valueOf(initialQuantity));

        if (mauList != null && !mauList.isEmpty()) {
            MauAdapter mauAdapter = new MauAdapter(mauList, mau -> {
                selectedColor = mau.getMau();
                updatePriceAndQuantity(mauList, selectedColor, tvGiamGiaGioHang,tvSoLuong);
            });

            rcvMau.setAdapter(mauAdapter);

            if (selectedColor != null) {
                updatePriceAndQuantity(mauList, selectedColor, tvGiamGiaGioHang, tvSoLuong);
            } else {
                ProductHome.MauSchema firstColor = mauList.get(0);
                selectedColor = firstColor.getMau();
                updatePriceAndQuantity(mauList, selectedColor, tvGiamGiaGioHang, tvSoLuong);
            }
        }

        btnGiamSoLuong.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(tvKQ.getText().toString());
            if (currentQuantity > 1) {
                tvKQ.setText(String.valueOf(currentQuantity - 1));
            }
        });

        btnTangSoLuong.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(tvKQ.getText().toString());
            tvKQ.setText(String.valueOf(currentQuantity + 1));
        });

        Button btnThemGioHang = sheetView.findViewById(R.id.btn_themgiohang);
        btnThemGioHang.setOnClickListener(v -> {
            if (sanPham != null) {
                GioHangDTO gioHangDTO = new GioHangDTO();
                gioHangDTO.setIdSanPham(sanPham.get_id());
                gioHangDTO.setIdMau(selectedColor);
                gioHangDTO.setSoLuong(Integer.parseInt(tvKQ.getText().toString()));

                Call<Void> call = apiService.adddToCart(gioHangDTO);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(Acti_ChiTietSP.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Acti_ChiTietSP.this, "Lỗi khi thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(Acti_ChiTietSP.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(Acti_ChiTietSP.this, "Sản phẩm không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });

        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }


    private void updatePriceAndQuantity(List<ProductHome.MauSchema> mauList, String selectedColor, TextView tvGiamGia, TextView tvSoLuong) {
        for (ProductHome.MauSchema mauSchema : mauList) {
            if (mauSchema.getMau().equals(selectedColor)) {
                tvGiamGia.setText(mauSchema.getGiaTien() + " VND");
                tvSoLuong.setText(String.valueOf(mauSchema.getSoLuong()));
                break;
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
