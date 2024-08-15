package com.example.datn_md16.Adapter;

import static com.example.datn_md16.Activitys.DangNhap.PREFS_NAME;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Activitys.Acti_chitietdonhang;
import com.example.datn_md16.DTO.DanhGiaSendDTO;
import com.example.datn_md16.DTO.DiaChiDTO;
import com.example.datn_md16.DTO.DonHangDTO;
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.R;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonHangHomeAdapter extends RecyclerView.Adapter<DonHangHomeAdapter.ViewHolder> {

    private List<DonHangDTO.DonHang> donHangList;
    private Context context;
    private ApiService apiService;
    private static final String KEY_USER_ID = "user_id";

    public DonHangHomeAdapter(List<DonHangDTO.DonHang> donHangList, Context context) {
        this.donHangList = donHangList;
        apiService = ApiClient.getClient().create(ApiService.class);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_don_hang, parent, false);
        return new DonHangHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHangDTO.DonHang donHang = donHangList.get(position);
        List<DonHangDTO.SanPhamTrongDonHang> sanPhamTrongDonHangList = donHang.getSanPhamTrongDonHang();

        if (sanPhamTrongDonHangList != null && !sanPhamTrongDonHangList.isEmpty()) {
            holder.productContainer.removeAllViews(); // Xóa các view cũ

            for (DonHangDTO.SanPhamTrongDonHang sanPhamTrongDonHang : sanPhamTrongDonHangList) {
                DonHangDTO.SanPham sanPham = sanPhamTrongDonHang.getSanPham();

                if (sanPham != null) {  // Kiểm tra nếu sanPham không null
                    // Inflate item_san_pham layout
                    View productView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.item_donhang, holder.productContainer, false);

                    // Find views in item_san_pham layout
                    TextView productName = productView.findViewById(R.id.productName);
                    TextView productColor = productView.findViewById(R.id.productColor);
                    TextView soLuong = productView.findViewById(R.id.soLuong);
                    TextView productPrice = productView.findViewById(R.id.productPrice);
                    ImageView productImage = productView.findViewById(R.id.productImage);
                    TextView btnHuy = productView.findViewById(R.id.btnHuy);
                    TextView btnXemChiTiet = productView.findViewById(R.id.btnXemChiTiet);
                    TextView tvDanhGia = productView.findViewById(R.id.tvDanhGia);

                    tvDanhGia.setOnClickListener(v -> {
                        Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.dialog_danhgia);

                        RatingBar ratingBar = dialog.findViewById(R.id.rbSao);
                        TextInputEditText edtComment = dialog.findViewById(R.id.etNoidungdanhgia);
                        Button btnSubmitRating = dialog.findViewById(R.id.btndanhgia);

                        btnSubmitRating.setOnClickListener(v1 -> {
                            // Get user input
                            String noiDung = edtComment.getText().toString().trim();
                            int diemDanhGia = (int) ratingBar.getRating();

                            // Check if rating is greater than 0
                            if (diemDanhGia <= 0) {
                                Toast.makeText(context, "Vui lòng chọn đánh giá sao.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Check if comment is empty
                            if (noiDung.isEmpty()) {
                                Toast.makeText(context, "Vui lòng nhập nội dung đánh giá.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Prepare data for sending
                            String thoiGian = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                            // Get user ID from SharedPreferences
                            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                            String userId = sharedPreferences.getString("user_id", null);

                            // Create DanhGiaDTO object
                            DanhGiaSendDTO danhGiaDTO = new DanhGiaSendDTO(noiDung, thoiGian, diemDanhGia, userId, sanPham.getId());

                            // Send data to server via Retrofit
                            Call<Void> call = apiService.themDanhGia(danhGiaDTO);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(context, "Đánh giá đã được gửi thành công!", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    } else {
                                        // Display error details
                                        Toast.makeText(context, "Gửi đánh giá thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    // Display error details
                                    Toast.makeText(context, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        });


                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.show();
                    });




                    // Cập nhật thông tin sản phẩm
                    String tenSanPham = sanPham.getTenDienThoai() != null ? sanPham.getTenDienThoai() : "Tên không xác định";
                    int soLuongSanPham = sanPhamTrongDonHang.getSoLuong();
                    productName.setText(tenSanPham);
                    soLuong.setText("Số lượng: " + soLuongSanPham);

                    // Kiểm tra và cập nhật màu sắc sản phẩm
                    String color = "Màu: " + (sanPham.getMauSchema() != null && !sanPham.getMauSchema().isEmpty() ? sanPham.getMauSchema().get(0).getMau() : "N/A");
                    productColor.setText(color);

                    // Kiểm tra và cập nhật giá tiền sản phẩm
                    double giaTien = sanPham.getMauSchema() != null && !sanPham.getMauSchema().isEmpty() ? sanPham.getMauSchema().get(0).getGiaTien() : 0.0;
                    productPrice.setText(formatPrice(giaTien));

                    // Kiểm tra và cập nhật hình ảnh sản phẩm
                    productImage.setImageResource(R.drawable.product_background); // Hình ảnh mặc định
                    String imageUrl = sanPham.getHinhAnh();
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Picasso.get().load(imageUrl).into(productImage);
                    }

                    // Thêm productView vào productContainer
                    holder.productContainer.addView(productView);

                    // Cập nhật trạng thái nút hủy và đánh giá
                    if ("Chờ xác nhận".equals(donHang.getTrangThaiDonHang()) || "Đang xử lý".equals(donHang.getTrangThaiDonHang())) {
                        btnHuy.setVisibility(View.VISIBLE);
                    } else {
                        btnHuy.setVisibility(View.GONE);
                    }

                    if ("Đã giao hàng".equals(donHang.getTrangThaiDonHang())) {
                        tvDanhGia.setVisibility(View.VISIBLE);
                    } else {
                        tvDanhGia.setVisibility(View.GONE);
                    }

                    btnHuy.setOnClickListener(v -> {
                        // Xử lý sự kiện khi người dùng nhấn nút "Hủy Đơn"
                        // Thêm logic hủy đơn hàng ở đây
                    });

                    btnXemChiTiet.setOnClickListener(v -> {

                            DiaChiDTO diaChiDTO = new DiaChiDTO();

                            Intent intent = new Intent(context, Acti_chitietdonhang.class);
                            intent.putExtra("tenDienThoai", sanPham.getTenDienThoai());
                            intent.putExtra("mauSchema", sanPham.getMauSchema() != null && !sanPham.getMauSchema().isEmpty() ? sanPham.getMauSchema().get(0).getMau() : "N/A");
                            intent.putExtra("soLuong", sanPhamTrongDonHang.getSoLuong());
                            intent.putExtra("tongTien", sanPham.getMauSchema().get(0).getGiaTien());
                            intent.putExtra("hoTen", donHang.getIdDiaChi().getTen());
                            intent.putExtra("sdt", donHang.getIdDiaChi().getSdt()); // đảm bảo bạn có trường này trong model
                            intent.putExtra("ngayDatHang", donHang.getNgayDatHang());
                            intent.putExtra("ngayNhanHang", donHang.getNgayNhanHang());
                            intent.putExtra("diaChiGiaoHang", donHang.getIdDiaChi().getDiaChi());
                            intent.putExtra("trangThaiDonHang", donHang.getTrangThaiDonHang());
                            intent.putExtra("phuongThucThanhToan", donHang.getPhuongThucThanhToan());
                            intent.putExtra("hinhAnhUrl", sanPham.getHinhAnh());

                            context.startActivity(intent);
                    });
                } else {
                    Log.e("DonHangHomeAdapter", "sanPham is null at position " + position);
                }
            }
        } else {
            Log.e("DonHangHomeAdapter", "sanPhamTrongDonHangList is null or empty at position " + position);
        }
    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout productContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productContainer = itemView.findViewById(R.id.productContainer);
        }
    }

    private String formatPrice(double price) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", symbols);
        return decimalFormat.format(price) + " VND";
    }
}
