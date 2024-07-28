package com.example.datn_md16.Activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datn_md16.DTO.GioHangDTO;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.DTO.SanPhamDTO;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);

        // Initialize views
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
        buttonBuyNow = findViewById(R.id.button_buy_now);
        btnthemgiohang = findViewById(R.id.button_add_to_cart);

        btnthemgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sanPham != null && sanPham.getMauSchema() != null) {
                    showBottom_Them_gio_hang(sanPham.getMauSchema());
                }
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
            // Parse JSON data to ChiTietDienThoaiDTO object
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

        // Khai báo các phần tử giao diện
        ImageView imgGioHang = sheetView.findViewById(R.id.img_gioHang);
        TextView tvGiamGiaGioHang = sheetView.findViewById(R.id.tv_giamGia_gioHang);
        TextView tvSoLuong = sheetView.findViewById(R.id.tv_soLuong);
        TextView tvKQ = sheetView.findViewById(R.id.tvKQ); // TextView cho số lượng hiện tại
        LinearLayout lnDen = sheetView.findViewById(R.id.lnDen);
        LinearLayout lnXanh = sheetView.findViewById(R.id.lnXanh);
        LinearLayout lnDo = sheetView.findViewById(R.id.lnDo);
        TextView tvGiam = sheetView.findViewById(R.id.tvGiam);
        TextView tvTang = sheetView.findViewById(R.id.tvTang);

        // Thiết lập giá mặc định cho màu đầu tiên trong danh sách
        if (!mauList.isEmpty()) {
            updatePriceAndQuantity(mauList, mauList.get(0).getMau(), tvGiamGiaGioHang, tvSoLuong);
        }

        // Thiết lập sự kiện cho các màu sắc
        lnDen.setOnClickListener(view -> updatePriceAndQuantity(mauList, "Đen", tvGiamGiaGioHang, tvSoLuong));
        lnXanh.setOnClickListener(view -> updatePriceAndQuantity(mauList, "Xanh", tvGiamGiaGioHang, tvSoLuong));
        lnDo.setOnClickListener(view -> updatePriceAndQuantity(mauList, "Đỏ", tvGiamGiaGioHang, tvSoLuong));

        // Thiết lập sự kiện cho nút tăng giảm số lượng
        tvGiam.setOnClickListener(view -> {
            int quantity = Integer.parseInt(tvKQ.getText().toString());
            if (quantity > 1) {
                tvKQ.setText(String.valueOf(quantity - 1));
                // Cập nhật số lượng và giá (nếu cần)
                updatePriceAndQuantity(mauList, getSelectedColor(), tvGiamGiaGioHang, tvSoLuong);
            }
        });

        tvTang.setOnClickListener(view -> {
            int quantity = Integer.parseInt(tvKQ.getText().toString());
            tvKQ.setText(String.valueOf(quantity + 1));
            // Cập nhật số lượng và giá (nếu cần)
            updatePriceAndQuantity(mauList, getSelectedColor(), tvGiamGiaGioHang, tvSoLuong);
        });

        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }

    private void showBottom_Them_gio_hang(List<ProductHome.MauSchema> mauList) {
        bottomSheetDialog = new BottomSheetDialog(Acti_ChiTietSP.this);
        View sheetView = getLayoutInflater().inflate(R.layout.bottom_them_gio_hang, null);

        // Khai báo các phần tử giao diện
        ImageView imgGioHang = sheetView.findViewById(R.id.img_gioHang);
        TextView tvGiamGiaGioHang = sheetView.findViewById(R.id.tv_giamGia_gioHang);
        TextView tvSoLuong = sheetView.findViewById(R.id.tv_soLuong);
        TextView tvKQ = sheetView.findViewById(R.id.tvKQ); // TextView cho số lượng hiện tại
        LinearLayout lnDen = sheetView.findViewById(R.id.lnDen);
        LinearLayout lnXanh = sheetView.findViewById(R.id.lnXanh);
        LinearLayout lnDo = sheetView.findViewById(R.id.lnDo);
        TextView tvGiam = sheetView.findViewById(R.id.tvGiam);
        TextView tvTang = sheetView.findViewById(R.id.tvTang);
        Button btnThemGioHang = sheetView.findViewById(R.id.btn_them_gio_hang);

        // Thiết lập giá mặc định cho màu đầu tiên trong danh sách
        if (!mauList.isEmpty()) {
            updatePriceAndQuantity(mauList, mauList.get(0).getMau(), tvGiamGiaGioHang, tvSoLuong);
        }

        // Thiết lập sự kiện cho các màu sắc
        lnDen.setOnClickListener(view -> updatePriceAndQuantity(mauList, "Đen", tvGiamGiaGioHang, tvSoLuong));
        lnXanh.setOnClickListener(view -> updatePriceAndQuantity(mauList, "Xanh", tvGiamGiaGioHang, tvSoLuong));
        lnDo.setOnClickListener(view -> updatePriceAndQuantity(mauList, "Đỏ", tvGiamGiaGioHang, tvSoLuong));

        // Thiết lập sự kiện cho nút tăng giảm số lượng
        tvGiam.setOnClickListener(view -> {
            int quantity = Integer.parseInt(tvKQ.getText().toString());
            if (quantity > 1) {
                tvKQ.setText(String.valueOf(quantity - 1));
                // Cập nhật số lượng và giá (nếu cần)
                updatePriceAndQuantity(mauList, getSelectedColor(), tvGiamGiaGioHang, tvSoLuong);
            }
        });

        tvTang.setOnClickListener(view -> {
            int quantity = Integer.parseInt(tvKQ.getText().toString());
            tvKQ.setText(String.valueOf(quantity + 1));
            // Cập nhật số lượng và giá (nếu cần)
            updatePriceAndQuantity(mauList, getSelectedColor(), tvGiamGiaGioHang, tvSoLuong);
        });

        // Sự kiện khi bấm nút Thêm giỏ hàng
        btnThemGioHang.setOnClickListener(view -> {
            // Lấy thông tin sản phẩm và màu sắc đã chọn
            String selectedColor = getSelectedColor();
            int quantity = Integer.parseInt(tvKQ.getText().toString());

            // Tạo đối tượng GioHangDTO
            GioHangDTO gioHang = new GioHangDTO();
            gioHang.setIdSanPham(sanPham.get_id());
            gioHang.setIdAccount(gioHang.getIdAccount()); // Thay thế bằng idAccount thực tế của người dùng
            gioHang.setSoLuong(quantity);
            gioHang.setIdMau(selectedColor);

            // Gọi phương thức thêm giỏ hàng
            addToCart(gioHang);
        });

        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }

    private String getSelectedColor() {
        // Implement the logic to return the selected color
        // This is just a placeholder
        return "Màu đã chọn";
    }

    private void updatePriceAndQuantity(List<ProductHome.MauSchema> mauList, String color, TextView tvGiamGiaGioHang, TextView tvSoLuong) {
        for (ProductHome.MauSchema mau : mauList) {
            if (mau.getMau().equals(color)) {
                tvGiamGiaGioHang.setText(mau.getGiaTien() + " VND");
                tvSoLuong.setText("Số lượng: " + mau.getSoLuong());
                break;
            }
        }
    }

    private void addToCart(GioHangDTO gioHang) {
        // Sử dụng Retrofit để gửi yêu cầu
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.8:3000") // Chỉ cần URL gốc
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<Void> call = apiService.adddToCart(gioHang);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Thêm sản phẩm vào giỏ hàng thành công
                    Toast.makeText(Acti_ChiTietSP.this, "Đã thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    // Xử lý khi thêm sản phẩm vào giỏ hàng thất bại
                    Toast.makeText(Acti_ChiTietSP.this, "Không thể thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Xử lý khi có lỗi xảy ra
                Toast.makeText(Acti_ChiTietSP.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
