package com.example.datn_md16.Activitys;

import static com.example.datn_md16.Activitys.DangNhap.PREFS_NAME;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.datn_md16.Adapter.DanhGiaAdapter;
import com.example.datn_md16.Adapter.MauAdapter;
import com.example.datn_md16.Adapter.TimKiemAdapter;
import com.example.datn_md16.DTO.AccountResponse;

import com.example.datn_md16.DTO.DanhGiaReceiveDTO;
import com.example.datn_md16.DTO.DanhGiaSendDTO;
import com.example.datn_md16.DTO.DonHangDTO;
import com.example.datn_md16.DTO.GioHangDTO;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.DTO.SanPhamDTO;
import com.example.datn_md16.DTO.SanPhamYeuThichDTO;
import com.example.datn_md16.DTO.TimKiemDTO;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.Interfa.DanhGiaResponse;
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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


    private LinearLayout lnMoRong;
    private TextView tvMoRong, tvPhanTramChiTiet, tvGiaGoc;

    RecyclerView recyclerView;

    private TimKiemAdapter adapter;

    private boolean isFavorite = false; // Biến để theo dõi trạng thái yêu thích

    private static final String KEY_USER_ID = "user_id";

    private AccountResponse account;
    private ApiService apiService; // Khai báo ApiService

    private RecyclerView recyclerView2;
    //private DanhgiaSanPhamAdapter adapter2;
    private List<DanhGiaSendDTO> danhGiaList;

    private RecyclerView rcvDanhGia;

    private DanhGiaAdapter danhGiaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);

        Retrofit retrofit = ApiClient.getClient();

        apiService = retrofit.create(ApiService.class);

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
        tvPhanTramChiTiet = findViewById(R.id.tv_phanTram_chiTiet);
        tvDoPhanGiai = findViewById(R.id.tvDoPhanGiai);
        buttonBuyNow = findViewById(R.id.button_buy_now);
        btnthemgiohang = findViewById(R.id.btnThemGioHang);
        tvGiaGoc = findViewById(R.id.tvGiaGoc);
        recyclerView2 = findViewById(R.id.rcvdanhgia);
        danhGiaList = new ArrayList<>();

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarChiTietSP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.toolbarChiTietSP_title));

        getData();
        updateUI(sanPham);


        rcvDanhGia = findViewById(R.id.rcvdanhgia);
        rcvDanhGia.setLayoutManager(new LinearLayoutManager(this));
//        String idSP = "668ea5681403cda3c0c969a2";
        Log.d("zzzzz", "onCreate: "+ sanPham.get_id());
        Call<DanhGiaResponse> call = apiService.getDanhGiaByIdSP(sanPham.get_id());
        call.enqueue(new Callback<DanhGiaResponse>() {
            @Override
            public void onResponse(Call<DanhGiaResponse> call, Response<DanhGiaResponse> response) {
                if (response.isSuccessful()) {
                    DanhGiaResponse danhGiaResponse = response.body();
                    if (danhGiaResponse != null && danhGiaResponse.getData() != null) {
                        List<DanhGiaReceiveDTO> danhGiaList = danhGiaResponse.getData();
                        danhGiaAdapter = new DanhGiaAdapter(danhGiaList);
                        rcvDanhGia.setAdapter(danhGiaAdapter);
                    } else {
                        Log.e("DanhGiaActivity", "DanhGiaResponse data is null");
                    }
                } else {
                    Log.e("DanhGiaActivity", "Response not successful: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DanhGiaResponse> call, Throwable t) {
                Log.e("DanhGiaActivity", "API call failed: " + t.getMessage());
                Toast.makeText(Acti_ChiTietSP.this, "Lỗi khi lấy dữ liệu: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });






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


        ImageView imgYeuThich = findViewById(R.id.ic_yeuThichTimKiem);

        // Load favorite status from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String productId = getIntent().getStringExtra("productId");
        isFavorite = sharedPreferences.getBoolean(productId, false);
        updateFavoriteIcon(imgYeuThich);

        // Set onClick listener for the favorite image
        imgYeuThich.setOnClickListener(v -> {
            if (sanPham != null) {
                if (isFavorite) {
                    // Remove from favorites
                    removeFromFavorites(sanPham.get_id());
                } else {
                    // Add to favorites
                    addToFavorites(sanPham.get_id());
                }
                isFavorite = !isFavorite;
                updateFavoriteIcon(imgYeuThich);
            }
        });

        lnMoRong = findViewById(R.id.lnMoRong);
        tvMoRong = findViewById(R.id.tvMoRong);

        // Kiểm tra trạng thái của lnMoRong và đặt trạng thái ban đầu cho tvMoRong
        if (lnMoRong.getVisibility() == View.GONE) {
            tvMoRong.setText("Xem thêm \u25BC"); // Mũi tên chỉ xuống khi ẩn
        } else {
            tvMoRong.setText("Thu gọn \u25B2"); // Mũi tên chỉ lên khi hiện
        }


        tvMoRong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle visibility of lnMoRong and update arrow
                if (lnMoRong.getVisibility() == View.GONE) {
                    lnMoRong.setVisibility(View.VISIBLE);
                    tvMoRong.setText("Thu gọn \u25B2"); // Mũi tên chỉ lên (Unicode)
                } else {
                    lnMoRong.setVisibility(View.GONE);
                    tvMoRong.setText("Xem thêm \u25BC"); // Mũi tên chỉ xuống (Unicode)
                }
            }
        });

        recyclerView = findViewById(R.id.rcv_tuongTu);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TimKiemAdapter(getApplicationContext(), null);
        recyclerView.setAdapter(adapter);
        loadAndSortDefaultData();


        // Retrieve JSON data from Intent
//        String sanPhamJson = getIntent().getStringExtra("sanPhamJson");
//        if (sanPhamJson != null) {
//            Gson gson = new Gson();
//            sanPham = gson.fromJson(sanPhamJson, ProductHome.class);
//            sanPham.get_id();
//            Log.d("SanPham", sanPham.get_id());
//
//            // Log URL hình ảnh để kiểm tra
//            Log.d("ImageURL", sanPham.getHinhAnh());
//            updateUI(sanPham);
//        }

    }

    public void setStrikeThroughText(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private void addToFavorites(String productId) {
        Retrofit retrofit = ApiClient.getClient();

        ApiService apiService = retrofit.create(ApiService.class);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString(KEY_USER_ID, null);

        SanPhamYeuThichDTO sanPhamYeuThichDTO = new SanPhamYeuThichDTO(productId, userId);

        Call<Void> call = apiService.addYeuThich(sanPhamYeuThichDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Acti_ChiTietSP.this, "Sản phẩm đã được thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Acti_ChiTietSP.this, "Không thể thêm sản phẩm vào yêu thích", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Acti_ChiTietSP.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFavoriteIcon(ImageView imgYeuThich) {
        if (isFavorite) {
            imgYeuThich.setImageResource(R.drawable.ic_yeuthich_do); // Hình ảnh trái tim màu đỏ
        } else {
            imgYeuThich.setImageResource(R.drawable.ic_favorite); // Hình ảnh trái tim màu xám
        }
    }

    public void getData() {
        String sanPhamJson = getIntent().getStringExtra("sanPhamJson");
        if (sanPhamJson != null) {
            Gson gson = new Gson();
            sanPham = gson.fromJson(sanPhamJson, ProductHome.class);
//            sanPham.get_id();
//            Log.d("SanPham", sanPham.get_id());

            // Log URL hình ảnh để kiểm tra
            Log.d("ImageURL", sanPham.getTenDienThoai());
//            updateUI(sanPham);
        }
    }


    private void removeFromFavorites(String productId) {
        Retrofit retrofit = ApiClient.getClient();

        ApiService apiService = retrofit.create(ApiService.class);

        // Gọi phương thức removeFavorite với productId
        Call<Void> call = apiService.removeFavorite(productId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Acti_ChiTietSP.this, "Sản phẩm đã được xóa khỏi yêu thích", Toast.LENGTH_SHORT).show();
                    // Remove favorite status from shared preferences
                    SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(productId, false);
                    editor.apply();
                } else {
                    Toast.makeText(Acti_ChiTietSP.this, "Không thể xóa sản phẩm khỏi yêu thích", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Acti_ChiTietSP.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadAndSortDefaultData() {
        Retrofit retrofit = ApiClient.getClient();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<TimKiemDTO>> call = apiService.getTimKiem();
        call.enqueue(new Callback<List<TimKiemDTO>>() {
            @Override
            public void onResponse(Call<List<TimKiemDTO>> call, Response<List<TimKiemDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TimKiemDTO> timKiemDTOList = response.body();
                    // Thực hiện sắp xếp theo mặc định (trên MongoDB)
                    // Ví dụ: Collections.sort(timKiemDTOList, new YourDefaultComparator());
                    // Sau khi sắp xếp, cập nhật dữ liệu vào adapter:
                    adapter.setData(timKiemDTOList);
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<TimKiemDTO>> call, Throwable t) {
                Log.e("Acti_TimKiem", "Error: " + t.getMessage());
            }
        });
    }

    private void updateUI(ProductHome sanPham) {
        Log.d("zzzz", "updateUI: "+ sanPham.getTenDienThoai());
        if (sanPham != null) {
            // Update TextViews
            txtProductName.setText(sanPham.getTenDienThoai());
            setStrikeThroughText(tvGiaGoc);
            if (sanPham.getMauSchema() != null && !sanPham.getMauSchema().isEmpty()) {
                double giaTien = sanPham.getMauSchema().get(0).getGiaTien();
                // Định dạng giá trị giaTien chỉ hiển thị phần nguyên và cứ 3 số sẽ có 1 dấu chấm
                NumberFormat formatter = NumberFormat.getIntegerInstance(Locale.GERMANY);
                txtPrice.setText("₫" + formatter.format(giaTien));

                // Lấy phần trăm giảm giá
                double phanTramGiamGia = Double.parseDouble(sanPham.getGiamGia());

                // Tính toán giá gốc từ giá hiện tại và phần trăm giảm giá
                double giaGoc = giaTien / (1 - (phanTramGiamGia / 100));

                // Định dạng giá trị giaGoc chỉ hiển thị phần nguyên
                tvGiaGoc.setText("₫" + formatter.format(giaGoc));
            }
            tvCamera.setText("- " + sanPham.getCamera());
            tvCameraTruoc.setText("- " + sanPham.getCameraTruoc());
            tvKichThuoc.setText("- " + sanPham.getKichThuoc());
            tvCPU.setText("- " + sanPham.getcPU());
            tvRam.setText("- " + sanPham.getRam());
            tvSim.setText("- " + sanPham.getSim());
            tvPin.setText("- " + sanPham.getPin());
            tvHeDieuHanh.setText("- " + sanPham.getHeDieuHanh());
            tvNamSanXuat.setText(String.valueOf("- " + sanPham.getNamSanXuat()));
            tvCongNgheManHinh.setText("- " + sanPham.getCongNgheManHinh());
            tvMoTaThem.setText("- " + sanPham.getMoTaThem());
            tvDoPhanGiai.setText("- " + sanPham.getDoPhanGiai());
            tvPhanTramChiTiet.setText("-" + sanPham.getGiamGia() + "%");
            Picasso.get().load(sanPham.getHinhAnh()).into(imgProduct);
        }
    }

    private void showBottomSheetDialog(List<ProductHome.MauSchema> mauList) {
        bottomSheetDialog = new BottomSheetDialog(Acti_ChiTietSP.this);
        View sheetView = getLayoutInflater().inflate(R.layout.sheet_dialog_giohang, null);
        RecyclerView rcvMau = sheetView.findViewById(R.id.rcv_Mau);
        ImageView imgGioHang = sheetView.findViewById(R.id.img_gioHang);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3); // 3 cột
        rcvMau.setLayoutManager(layoutManager);
        Picasso.get().load(sanPham.getHinhAnh()).placeholder(R.drawable.img_sale).error(R.drawable.img).into(imgGioHang);

        TextView tvGiamGiaGioHang = sheetView.findViewById(R.id.tv_giamGia_gioHang);
        TextView tvSoLuong = sheetView.findViewById(R.id.tv_soLuong);
        TextView tvKQ = sheetView.findViewById(R.id.tvKQ);
        TextView btnGiamSoLuong = sheetView.findViewById(R.id.tvGiam);
        TextView btnTangSoLuong = sheetView.findViewById(R.id.tvTang);
        Button btnThanhToanNgay = sheetView.findViewById(R.id.btn_thanhToan);

        // Load dữ liệu mặc định
        Picasso.get().load(sanPham.getHinhAnh()).placeholder(R.drawable.img_sale).error(R.drawable.img).into(imgGioHang);
        int initialQuantity = 1;
        tvKQ.setText(String.valueOf(initialQuantity));

        // Cài đặt adapter và chọn màu sắc đầu tiên
        if (mauList != null && !mauList.isEmpty()) {
            MauAdapter mauAdapter = new MauAdapter(mauList, mau -> {
                selectedColor = mau.get_id();
                updatePriceAndQuantity(mauList, selectedColor, tvGiamGiaGioHang, tvSoLuong);
            });

            rcvMau.setAdapter(mauAdapter);

            ProductHome.MauSchema firstColor = mauList.get(0);
            selectedColor = firstColor.get_id();
            updatePriceAndQuantity(mauList, selectedColor, tvGiamGiaGioHang, tvSoLuong);
        }

        // Điều chỉnh số lượng
        btnGiamSoLuong.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(tvKQ.getText().toString());
            if (currentQuantity > 1) {
                currentQuantity--;
                tvKQ.setText(String.valueOf(currentQuantity));
            }
        });

        btnTangSoLuong.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(tvKQ.getText().toString());
            currentQuantity++;
            tvKQ.setText(String.valueOf(currentQuantity));
        });

        // Xử lý khi nhấn nút Thanh toán ngay
        btnThanhToanNgay.setOnClickListener(v -> {
            // Kiểm tra đăng nhập
            SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString(KEY_USER_ID, null);
            if (userId == null) {
                Toast.makeText(Acti_ChiTietSP.this, "Bạn cần đăng nhập để thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }



            List<GioHangDTO> selectedItems = new ArrayList<>();
            // Tạo đối tượng GioHangDTO và thiết lập các thuộc tính
            GioHangDTO gioHangDTO = new GioHangDTO();
            gioHangDTO.setIdSanPham(sanPham.get_id());
            gioHangDTO.setSanPham(sanPham);
            gioHangDTO.setIdMau(selectedColor);
            gioHangDTO.setSoLuong(Integer.parseInt(tvKQ.getText().toString()));
            gioHangDTO.setIdAccount(userId);
            // Thêm đối tượng GioHangDTO vào danh sách selectedItems
            selectedItems.add(gioHangDTO);
            Log.d("zzz",""+selectedItems);
            // Chuyển sang Activity thanh toán
            int soLuong = Integer.parseInt(tvSoLuong.getText().toString());
            int soLuongKho = Integer.parseInt(tvKQ.getText().toString());
            if (soLuong <= 0) {
                Toast.makeText(Acti_ChiTietSP.this, "Hàng trong kho đã hết", Toast.LENGTH_SHORT).show();
            } else if (soLuong < soLuongKho) {
                Toast.makeText(Acti_ChiTietSP.this, "Số lượng lớn hơn số hàng trong kho", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Acti_ChiTietSP.this, Acti_ThanhToan.class);
                intent.putParcelableArrayListExtra("selectedItems", new ArrayList<>(selectedItems));
                startActivity(intent);
                bottomSheetDialog.dismiss();
            }
            // Đóng BottomSheetDialog

        });



        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }


    private void them_gio_hang(List<ProductHome.MauSchema> mauList) {
        bottomSheetDialog = new BottomSheetDialog(Acti_ChiTietSP.this);
        View sheetView = getLayoutInflater().inflate(R.layout.sheet_dialog_giohang, null);
        RecyclerView rcvMau = sheetView.findViewById(R.id.rcv_Mau);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rcvMau.setLayoutManager(layoutManager);

        ImageView imgGioHang = sheetView.findViewById(R.id.img_gioHang);
        TextView tvGiamGiaGioHang = sheetView.findViewById(R.id.tv_giamGia_gioHang);
        TextView tvSoLuong = sheetView.findViewById(R.id.tv_soLuong);
        TextView tvKQ = sheetView.findViewById(R.id.tvKQ);
        TextView btnGiamSoLuong = sheetView.findViewById(R.id.tvGiam);
        TextView btnTangSoLuong = sheetView.findViewById(R.id.tvTang);
        Picasso.get().load(sanPham.getHinhAnh()).placeholder(R.drawable.img_sale).error(R.drawable.img).into(imgGioHang);
        Button btnthemgiohang = sheetView.findViewById(R.id.btn_thanhToan);
        btnthemgiohang.setText("Thêm vào Giỏ Hàng");
        // Set initial quantity
        int initialQuantity = 1;
        tvKQ.setText(String.valueOf(initialQuantity));

        if (mauList != null && !mauList.isEmpty()) {
            MauAdapter mauAdapter = new MauAdapter(mauList, mau -> {
                selectedColor = mau.get_id();

                updatePriceAndQuantity(mauList, selectedColor, tvGiamGiaGioHang, tvSoLuong);
            });

            rcvMau.setAdapter(mauAdapter);

            // Ensure the correct initial selection
            if (selectedColor != null) {
                updatePriceAndQuantity(mauList, selectedColor, tvGiamGiaGioHang, tvSoLuong);
            } else {
                // No need to set default color; use color selected from adapter
                ProductHome.MauSchema firstColor = mauList.get(0);
                selectedColor = firstColor.get_id();
                updatePriceAndQuantity(mauList, selectedColor, tvGiamGiaGioHang, tvSoLuong);
            }
        }

        btnGiamSoLuong.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(tvKQ.getText().toString());
            if (currentQuantity > 1) {
                currentQuantity--;
                tvKQ.setText(String.valueOf(currentQuantity));
            }
        });

        btnTangSoLuong.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(tvKQ.getText().toString());
            currentQuantity++;
            tvKQ.setText(String.valueOf(currentQuantity));
        });

        btnthemgiohang.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString(KEY_USER_ID, null);
            if (userId == null) {
                Toast.makeText(Acti_ChiTietSP.this, "Bạn cần đăng nhập để thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                return;
            }

            SanPhamDTO.MauSchemaDTO dto = new SanPhamDTO.MauSchemaDTO();


            GioHangDTO gioHangDTO = new GioHangDTO();
            gioHangDTO.setIdSanPham(sanPham.get_id());
            gioHangDTO.setIdMau(selectedColor);
            gioHangDTO.setSoLuong(Integer.parseInt(tvKQ.getText().toString()));
            // Comment dòng này lại
            gioHangDTO.setIdAccount(userId);

//            Log.d("ThemGioHang", "ID Sản Phẩm: " + gioHangDTO.getIdSanPham());
//            Log.d("ThemGioHang", "ID Màu: " + gioHangDTO.getIdMau());
//            Log.d("ThemGioHang", "Số Lượng: " + gioHangDTO.getSoLuong());
//            Log.d("ThemGioHang", "Số acou: " + gioHangDTO.getIdAccount());


            int soLuong = Integer.parseInt(tvSoLuong.getText().toString());
            int soLuongKho = Integer.parseInt(tvKQ.getText().toString());


            if (soLuong <= 0) {
                Toast.makeText(Acti_ChiTietSP.this, "Hàng trong kho đã hết", Toast.LENGTH_SHORT).show();
            } else if (soLuong < soLuongKho) {
                Toast.makeText(Acti_ChiTietSP.this, "Số lượng lớn hơn số hàng trong kho", Toast.LENGTH_SHORT).show();
            } else {

                Call<Void> call = apiService.adddToCart(gioHangDTO);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(Acti_ChiTietSP.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Acti_ChiTietSP.this, "Không thể thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(Acti_ChiTietSP.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();

    }


    private void updatePriceAndQuantity(List<ProductHome.MauSchema> mauList, String selectedColor, TextView tvGiamGiaGioHang, TextView tvSoLuong) {
        for (ProductHome.MauSchema mau : mauList) {
            if (mau.get_id().equals(selectedColor)) {
                tvGiamGiaGioHang.setText(String.valueOf(mau.getGiaTien()));
                tvSoLuong.setText(String.valueOf(mau.getSoLuong()));
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
//    private void fetchDanhGiaList2() {
//        Call<List<DanhGiaDTO>> call = apiService.getDanhGiaList();
//        call.enqueue(new Callback<List<DanhGiaDTO>>() {
//            @Override
//            public void onResponse(Call<List<DanhGiaDTO>> call, Response<List<DanhGiaDTO>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    danhGiaList.clear();  // Xóa dữ liệu cũ
//                    danhGiaList.addAll(response.body());  // Thêm dữ liệu mới vào danh sách
//                    adapter2.notifyDataSetChanged();  // Cập nhật RecyclerView
//                } else {
//                    // Xử lý khi server trả về kết quả không thành công
//                    Toast.makeText(Acti_ChiTietSP.this, "Không thể lấy dữ liệu đánh giá.", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<DanhGiaDTO>> call, Throwable t) {
//                // Xử lý lỗi kết nối hoặc lỗi khác
//                Toast.makeText(Acti_ChiTietSP.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


}