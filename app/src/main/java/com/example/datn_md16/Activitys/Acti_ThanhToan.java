package com.example.datn_md16.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.GioHangAdapter;
import com.example.datn_md16.Adapter.MauAdapter;
import com.example.datn_md16.Adapter.Thaanh_Toan_Adapter;
import com.example.datn_md16.DTO.CreateOrder;
import com.example.datn_md16.DTO.DiaChiDTO;
import com.example.datn_md16.DTO.DonHangDTO;
import com.example.datn_md16.DTO.GioHangDTO;
import com.example.datn_md16.DTO.KhuyenMai;
import com.example.datn_md16.DTO.ProductHome;

import com.example.datn_md16.Fragment.HoaDonFrag;
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.Interface.ApiService;

import com.example.datn_md16.R;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;


public class Acti_ThanhToan extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_ADDRESS = 1;
    private static final int REQUEST_CODE_SELECT_PROMOTION = 2;
    private List<GioHangDTO> selectedItems = new ArrayList<>();
    private TextView textViewAddress,textViewAddress2;
    private RecyclerView recyclerViewProducts;
    private TextView tvKM,textViewProducts;
    private TextView textViewTotalAmount, tvten, tvsdt, tv_tongtiensanPham, tongtienkhuyenmai, tongtien;
    private Button buttonPlaceOrder;
    private LinearLayout btnKm;
    private RadioButton radioOnl, radioOff;
    private String idDiaChi;//thêm
    private String selectedColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thanhtoan);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        setTitle(getString(R.string.toolbarThanhtoan));


        textViewAddress2 = findViewById(R.id.textViewAddress2);
        textViewAddress = findViewById(R.id.textViewAddress);
        tvten = findViewById(R.id.tvten);
        tvsdt = findViewById(R.id.tvsdt);
        recyclerViewProducts = findViewById(R.id.listViewProducts);
        btnKm = findViewById(R.id.btnKM);
        radioOnl = findViewById(R.id.radioOnlinePayment);
        radioOff = findViewById(R.id.radioCashOnDelivery);
        tv_tongtiensanPham = findViewById(R.id.tv_tong_tien_sp);
        tongtienkhuyenmai = findViewById(R.id.tv_tong_tien_khuyenmai);
        tongtien = findViewById(R.id.tv_tongtien);

        textViewTotalAmount = findViewById(R.id.textViewTotalAmount);
        buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);
        tvKM = findViewById(R.id.tvkhuyenmai);
        textViewProducts = findViewById(R.id.textViewProducts);



        // Nhận khuyến mãi từ Intent
        KhuyenMai khuyenMai = getIntent().getParcelableExtra("selectedPromotion");
        if (khuyenMai != null) {
            tvKM.setText(String.valueOf(khuyenMai.getGiaKhoiDiem()));
        }

        btnKm.setOnClickListener(v -> {
            Intent intent = new Intent(Acti_ThanhToan.this, Acti_KhuyenMai.class);
            startActivityForResult(intent, REQUEST_CODE_SELECT_PROMOTION);
        });

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            DiaChiDTO diaChi = intent.getParcelableExtra("selectedAddress");
            selectedItems = intent.getParcelableArrayListExtra("selectedItems");
//            if (diaChi != null) {
//                textViewAddress.setText("Địa chỉ nhận hàng: ");
//                tvten.setText("Tên khách hàng: " );
//                tvsdt.setText("Số điện thoại: " );
//            }

            if (selectedItems != null) {
                updateUI(selectedItems);
            }
        }

//

        textViewAddress.setOnClickListener(v -> {
            Intent addressIntent = new Intent(Acti_ThanhToan.this, Acti_DiaChi.class);
            addressIntent.putParcelableArrayListExtra("selectedItems", new ArrayList<>(selectedItems));
            startActivityForResult(addressIntent, REQUEST_CODE_SELECT_ADDRESS);

        });


        buttonPlaceOrder.setOnClickListener(v -> placeOrder());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_ADDRESS && resultCode == RESULT_OK) {
            if (data != null) {
                DiaChiDTO selectedAddress = data.getParcelableExtra("selectedAddress");
                if (selectedAddress != null) {
                    textViewAddress2.setText(selectedAddress.getDiaChi());
                    tvten.setText(  selectedAddress.getTen());
                    tvsdt.setText( selectedAddress.getSdt());

                    idDiaChi = selectedAddress.getId();//thêm

                }
            }
            updateUI(selectedItems);
        }

        if (requestCode == REQUEST_CODE_SELECT_PROMOTION && resultCode == RESULT_OK) {
            if (data != null) {
                KhuyenMai khuyenMai = data.getParcelableExtra("selectedPromotion");
                if (khuyenMai != null) {
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    int giakhoidiem = khuyenMai.getGiaKhoiDiem();
                    int giatoida = khuyenMai.getGiaKhuyenMaiToiDa();
                    int phantramgiam = khuyenMai.getPhanTramGiamGia();
                   // int giatridonhang = tv_tongtiensanPham.get

                    tvKM.setText(numberFormat.format(khuyenMai.getGiaKhuyenMaiToiDa())+ " VNĐ");
                    updateUI(selectedItems); // Cập nhật giao diện khi khuyến mãi thay đổi
                }
            }
        }
    }

    private void updateUI(List<GioHangDTO> selectedItems) {

        Thaanh_Toan_Adapter adapter = new Thaanh_Toan_Adapter(selectedItems, this);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProducts.setAdapter(adapter);
        textViewProducts.setText("Số lượng sản phẩm : " + adapter.getItemCount());
        updateTotalAmount();
    }

    private int updateTotalAmount() {
        int totalAmount = calculateTotalAmount(selectedItems);
        int discountAmount = getDiscountFromTextView(); // Lấy giá trị khuyến mãi từ TextView tvKM

        // Sử dụng NumberFormat để định dạng số tiền
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);

        // Định dạng và hiển thị các giá trị
        tv_tongtiensanPham.setText("Tổng tiền sản phẩm: " + numberFormat.format(totalAmount) + " VNĐ");
        tongtienkhuyenmai.setText("Khuyến mãi: " + numberFormat.format(discountAmount) + " VNĐ");

        int finalAmount = totalAmount - discountAmount;
        tongtien.setText("Tổng tiền: " + numberFormat.format(finalAmount) + " VNĐ");
        textViewTotalAmount.setText("Tổng tiền: " + numberFormat.format(finalAmount) + " VNĐ");

        return finalAmount;
    }


    private int getDiscountFromTextView() {
        int discount = 0;
        try {
            // Loại bỏ ký tự không phải số (VD: " VNĐ")
            String discountText = tvKM.getText().toString().replaceAll("[^\\d]", "");
            float discountFloat = Float.parseFloat(discountText);
            discount = (int) discountFloat;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Log.d("zzzze", "lay từ km " + discount);
        return discount;
    }


    private int calculateTotalAmount(List<GioHangDTO> items) {
        int total = 0;

        for (GioHangDTO item : items) {
            Log.d("zzzz",""+item.getSanPham().getMauSchema());
            if (item.getSanPham() != null && !item.getSanPham().getMauSchema().isEmpty()) {

                // Tìm kiếm màu mà người dùng đã chọn
                for (ProductHome.MauSchema mau : item.getSanPham().getMauSchema()) {
                    if (mau.get_id().equals(item.getIdMau())) {
                        int giaTien = mau.getGiaTien();

                        total += item.getSoLuong() * giaTien;
                        break; // Khi đã tìm được màu phù hợp, thoát khỏi vòng lặp
                    }
                }
            }
        }
        return total;
    }


    private void placeOrder() {
        // Kiểm tra xem địa chỉ có được chọn không
        if (textViewAddress2.getText().toString().isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn địa chỉ giao hàng!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra phương thức thanh toán
        if (!radioOnl.isChecked() && !radioOff.isChecked()) {
            Toast.makeText(this, "Vui lòng chọn phương thức thanh toán!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(radioOff.isChecked()) {

            // Lấy ID khách hàng từ SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String userId = sharedPreferences.getString("user_id", null);

            if (userId == null) {
                Toast.makeText(this, "Lỗi xác thực người dùng!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo một đối tượng KhachHang
            DonHangDTO.KhachHang khachHang = new DonHangDTO.KhachHang();
            khachHang.setId(userId);


            // Nếu cần thiết, bạn có thể thiết lập các thuộc tính khác của KhachHang ở đây
            DiaChiDTO diaChi = new DiaChiDTO();
            diaChi.setId(idDiaChi);

            // Tạo một đơn hàng mới
            DonHangDTO.DonHang donHang = new DonHangDTO.DonHang();


            donHang.setSanPhamTrongDonHang(convertToSanPhamList(selectedItems));
            donHang.setSoLuong(calculateTotalQuantity(selectedItems));
            donHang.setTongTien(updateTotalAmount());
            donHang.setTrangThaiThanhToan(radioOnl.isChecked());
            donHang.setDiaChiGiaoHang(textViewAddress2.getText().toString());
            donHang.setIdDiaChi(diaChi);//them
            donHang.setPhuongThucThanhToan(radioOnl.isChecked() ? "Thẻ tín dụng" : "Tiền mặt");
            donHang.setTrangThaiDonHang("Chờ xác nhận");
            donHang.setIdKH(khachHang);



            // Đặt đối tượng KhachHang vào đơn hàng

            // Thực hiện gọi API để đặt hàng
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<DonHangDTO> call = apiService.createOrder(donHang);

            call.enqueue(new Callback<DonHangDTO>() {
                @Override
                public void onResponse(Call<DonHangDTO> call, Response<DonHangDTO> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(Acti_ThanhToan.this, "Đặt hàng thành công! Mời bạn tiếp tục mua sản phẩm mới", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Acti_ThanhToan.this, MainActivity.class);
                        startActivity(intent);
                        clearCart();
                        int soluongtrongkho = soluongtrongkho();
                        ProductHome.MauSchema mauSchema = new ProductHome.MauSchema();
                        mauSchema.setSoLuong(soluongtrongkho);
                        finish();
                    } else {
                        Toast.makeText(Acti_ThanhToan.this, "Đặt hàng thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DonHangDTO> call, Throwable t) {
                    Toast.makeText(Acti_ThanhToan.this, "Lỗi kết nối mạng!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (radioOnl.isChecked()){
            Zalopay();
        }
    }



private void Zalopay() {
    CreateOrder orderApi = new CreateOrder();
    DonHangDTO.DonHang donHang = new DonHangDTO.DonHang();
    String totalAmountString = textViewTotalAmount.getText().toString().replaceAll("[^\\d]", "");
    try {
        JSONObject data = orderApi.createOrder(totalAmountString);

        String code = data.getString("return_code");

        if (code.equals("1")) {
            String token = data.getString("zp_trans_token");

            ZaloPaySDK.getInstance().payOrder(Acti_ThanhToan.this, token, "demozpdk://app", new PayOrderListener() {
                @Override
                public void onPaymentSucceeded(String s, String s1, String s2) {
                    // Xử lý khi thanh toán thành công
                    Toast.makeText(Acti_ThanhToan.this, "Thanh toán onl", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                    String userId = sharedPreferences.getString("user_id", null);

                    if (userId == null) {
                        Toast.makeText(Acti_ThanhToan.this, "Lỗi xác thực người dùng!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Tạo một đối tượng KhachHang
                    DonHangDTO.KhachHang khachHang = new DonHangDTO.KhachHang();
                    khachHang.setId(userId);
                    // Nếu cần thiết, bạn có thể thiết lập các thuộc tính khác của KhachHang ở đây
                    DiaChiDTO diaChi = new DiaChiDTO();
                    diaChi.setId(idDiaChi);
                    // Tạo một đơn hàng mới
                    DonHangDTO.DonHang donHang = new DonHangDTO.DonHang();
                    donHang.setSanPhamTrongDonHang(convertToSanPhamList(selectedItems));
                    donHang.setSoLuong(calculateTotalQuantity(selectedItems));
                    donHang.setTongTien(updateTotalAmount());
                    donHang.setTrangThaiThanhToan(radioOnl.isChecked()); // Trạng thái thanh toán dựa trên lựa chọn
                    donHang.setDiaChiGiaoHang(textViewAddress2.getText().toString());
                    donHang.setIdDiaChi(diaChi);
                    donHang.setPhuongThucThanhToan(radioOnl.isChecked() ? "Thẻ tín dụng" : "Tiền mặt");
                    donHang.setTrangThaiDonHang("Chờ xác nhận");
                    donHang.setIdKH(khachHang); // Đặt đối tượng KhachHang vào đơn hàng





                    // Thực hiện gọi API để đặt hàng
                    ApiService apiService = ApiClient.getClient().create(ApiService.class);
                    Call<DonHangDTO> call = apiService.createOrder(donHang);

                    call.enqueue(new Callback<DonHangDTO>() {
                        @Override
                        public void onResponse(Call<DonHangDTO> call, Response<DonHangDTO> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(Acti_ThanhToan.this, "Đặt hàng thành công! Mời bạn tiếp tục mua sản phẩm mới", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Acti_ThanhToan.this, MainActivity.class);
                                startActivity(intent);
                                clearCart();
                                int soluongtrongkho = soluongtrongkho();
                                ProductHome.MauSchema mauSchema = new ProductHome.MauSchema();
                                mauSchema.setSoLuong(soluongtrongkho);
                                finish();
                            } else {
                                Toast.makeText(Acti_ThanhToan.this, "Đặt hàng thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DonHangDTO> call, Throwable t) {
                            Toast.makeText(Acti_ThanhToan.this, "Lỗi kết nối mạng!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.d("ZaloPay", "Payment Succeeded: " + s + ", " + s1 + ", " + s2);
                }

                @Override
                public void onPaymentCanceled(String s, String s1) {
                    // Xử lý khi thanh toán bị hủy
                    Log.d("ZaloPay", "Payment Canceled: " + s + ", " + s1);
                }

                @Override
                public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                    // Xử lý khi thanh toán gặp lỗi
                    Log.d("ZaloPay", "Payment Error: " + zaloPayError + ", " + s + ", " + s1);
                }
            });

        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    private List<DonHangDTO.SanPhamTrongDonHang> convertToSanPhamList(List<GioHangDTO> selectedItems) {
        List<DonHangDTO.SanPhamTrongDonHang> sanPhamList = new ArrayList<>();

        for (GioHangDTO item : selectedItems) {
            DonHangDTO.SanPhamTrongDonHang sanPhamTrongDonHang = new DonHangDTO.SanPhamTrongDonHang();
            DonHangDTO.SanPham sanPham = new DonHangDTO.SanPham();

            // Set thông tin sản phẩm
            sanPham.setId(item.getIdSanPham());
            sanPham.setTenDienThoai(item.getSanPham().getTenDienThoai()); // Giả sử bạn có tên sản phẩm từ GioHangDTO
            sanPham.setHinhAnh(item.getSanPham().getHinhAnh()); // Giả sử bạn có hình ảnh sản phẩm từ GioHangDTO

            // Set thông tin màu sắc và số lượng
            List<DonHangDTO.MauSchema> mauSchemaList = new ArrayList<>();
            DonHangDTO.MauSchema mauSchema = new DonHangDTO.MauSchema();
            mauSchema.setMau(item.getIdMau()); // Giả sử bạn sử dụng idMau làm tên màu
            mauSchema.setSoLuong(item.getSoLuong());
            mauSchemaList.add(mauSchema);

            sanPham.setMauSchema(mauSchemaList);
            sanPhamTrongDonHang.setSanPham(sanPham);
            sanPhamTrongDonHang.setSoLuong(item.getSoLuong());

            List<ProductHome.MauSchema> mauList = new ArrayList<>();
            // Set thông tin về idMau và giá tiền
            for (ProductHome.MauSchema mau : item.getSanPham().getMauSchema()) {
                if (mau.get_id().equals(item.getIdMau())) {
                    sanPhamTrongDonHang.setIdMau(mau.get_id());
                    sanPhamTrongDonHang.setMau(mau.getMau());
                    sanPhamTrongDonHang.setGiaTien(mau.getGiaTien());
                    break; // Dừng vòng lặp khi tìm thấy phần tử màu phù hợp
                }
            }
            sanPhamList.add(sanPhamTrongDonHang);
        }
        return sanPhamList;
    }

    private int soluongtrongkho() {
       
        ProductHome.MauSchema mauSchema = new ProductHome.MauSchema();
        DonHangDTO.SanPhamTrongDonHang sanPhamTrongDonHang = new DonHangDTO.SanPhamTrongDonHang();
        int sanphamtrongkho = mauSchema.getSoLuong();
        int sanphamtrongdon = sanPhamTrongDonHang.getSoLuong();
        int soluongMoi = sanphamtrongkho - sanphamtrongdon;
        mauSchema.setSoLuong(soluongMoi);
        return soluongMoi;
    }

    private int calculateTotalQuantity(List<GioHangDTO> items) {
        int quantity = 0;
        for (GioHangDTO item : items) {
            quantity += item.getSoLuong();
        }
        return quantity;
    }

    private void clearCart() {
        // Tạo Retrofit và ApiService
        Retrofit retrofit = ApiClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);

        // Xóa từng mục trong giỏ hàng theo ID
        List<GioHangDTO> itemsToDelete = new ArrayList<>(selectedItems); // Sử dụng danh sách sao chép để tránh lỗi đồng bộ hóa

        for (GioHangDTO item : itemsToDelete) {
            String itemId = item.get_id(); // Lấy ID của từng mục trong giỏ hàng

            // Gọi API xóa sản phẩm
            apiService.deleteItemFromCart(itemId).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                       // Toast.makeText(Acti_ThanhToan.this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        // Xóa mục khỏi danh sách và cập nhật UI
                        selectedItems.remove(item);
                        updateUI(selectedItems);
                    } else {
                        Toast.makeText(Acti_ThanhToan.this, "Lỗi sss: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
//                    Toast.makeText(Acti_ThanhToan.this, "Lỗi mua hàng : " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    Log.d("zzz1", "onFailure: "+t.getMessage());
                }
            });
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }


}
