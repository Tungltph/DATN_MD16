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
import com.example.datn_md16.DTO.AccountResponse;
import com.example.datn_md16.DTO.DanhGiaDTO;
import com.example.datn_md16.DTO.DiaChiDTO;
import com.example.datn_md16.DTO.DonHangDTO;
import com.example.datn_md16.DTO.ProductHome;
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
import retrofit2.Retrofit;

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
            holder.productContainer.removeAllViews(); // Clear previous views

            for (DonHangDTO.SanPhamTrongDonHang sanPhamTrongDonHang : sanPhamTrongDonHangList) {
                DonHangDTO.SanPham sanPham = sanPhamTrongDonHang.getSanPham();

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
                        String noiDung = edtComment.getText().toString();
                        int diemDanhGia = (int) ratingBar.getRating();
                        String thoiGian = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                        // Lấy thông tin người dùng từ SharedPreferences
                        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                        String userId = sharedPreferences.getString("user_id", null);
                        String userName = sharedPreferences.getString("user_name", null);

                        // Tạo đối tượng AccountResponse.Account từ thông tin đã lưu
                        AccountResponse.Account currentUser = new AccountResponse.Account();
                        currentUser._id = userId;
                        currentUser.taiKhoan = userName;

                        // Tạo đối tượng DanhGiaDTO
                        DanhGiaDTO danhGiaDTO = new DanhGiaDTO(noiDung, thoiGian, diemDanhGia, userId, sanPham.getId()
                        );

                        // Gửi dữ liệu đánh giá lên server qua Retrofit
                        Call<Void> call = apiService.themDanhGia(danhGiaDTO);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Đánh giá đã được gửi thành công!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(context, "Gửi đánh giá thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(context, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });

                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.show();
                });


                // Update product information
                String tenSanPham = sanPham.getTenDienThoai();
                int soLuongSanPham = sanPhamTrongDonHang.getSoLuong();
                productName.setText(tenSanPham);
                soLuong.setText("Số lượng: " + soLuongSanPham);
                String color = "Màu: " + (sanPham.getMauSchema() != null && !sanPham.getMauSchema().isEmpty() ? sanPham.getMauSchema().get(0).getMau() : "N/A");
                productColor.setText(color);
                double giaTien = sanPham.getMauSchema().get(0).getGiaTien();
                productPrice.setText(formatPrice(giaTien));
                productImage.setImageResource(R.drawable.product_background); // Placeholder image
                String imageUrl = sanPham.getHinhAnh();
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Picasso.get().load(imageUrl).into(productImage);
                }

                // Add product view to container
                holder.productContainer.addView(productView);


                // Cập nhật trạng thái nút hủy và đánh giá


                if (donHang.getTrangThaiDonHang().equals("Chờ xác nhận") || donHang.getTrangThaiDonHang().equals("Đang xử lý")) {
                    btnHuy.setVisibility(View.VISIBLE);
                } else {
                    btnHuy.setVisibility(View.GONE);
                }

                if (donHang.getTrangThaiDonHang().equals("Đã giao hàng")) {
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
            }
        }


    }

    // Phương thức tiện ích để định dạng giá với dấu phân cách hàng nghìn
    // Phương thức tiện ích để định dạng giá với dấu phân cách hàng nghìn
    private String formatPrice(double price) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);
        return "₫" + decimalFormat.format(price);
    }


    public void updateData(List<DonHangDTO.DonHang> newDonHangList) {
        this.donHangList = newDonHangList;
        notifyDataSetChanged(); // Thông báo cho RecyclerView cập nhật dữ liệu
    }

    @Override
    public int getItemCount() {
        return donHangList != null ? donHangList.size() : 0;
    }

public static class ViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout productContainer;

    public ViewHolder(View itemView) {
        super(itemView);
        productContainer = itemView.findViewById(R.id.productContainer);

    }
}
}
