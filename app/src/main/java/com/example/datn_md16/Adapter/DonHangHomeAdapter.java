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
import com.example.datn_md16.DTO.DanhGiaDTO;
import com.example.datn_md16.DTO.DiaChiDTO;
import com.example.datn_md16.DTO.DonHangDTO;
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.Interface.ApiService;
import com.example.datn_md16.R;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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

//                tvDanhGia.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        final Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
//                        dialog.setContentView(R.layout.dialog_danhgia);
//
//                        RatingBar start = dialog.findViewById(R.id.rbSao);
//                        TextInputEditText ednoidung = dialog.findViewById(R.id.etNoidungdanhgia);
//                        Button btndanhgia = dialog.findViewById(R.id.btndanhgia);
//
//                        if (start == null || ednoidung == null || btndanhgia == null) {
//                            Toast.makeText(context, "Có lỗi khi khởi tạo giao diện", Toast.LENGTH_SHORT).show();
//                            return;
//                        }


//                btndanhgia.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Retrofit retrofit = ApiClient.getClient();
//                        apiService = retrofit.create(ApiService.class);
//
//                        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//                        String userId = sharedPreferences.getString(KEY_USER_ID, null);
//                        if (userId == null) {
//                            Toast.makeText(context, "Bạn cần đăng nhập để đánh giá", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//                        // Kiểm tra nếu danh sách không rỗng
//                        if (sanPhamTrongDonHangList.isEmpty()) {
//                            Toast.makeText(context, "Không có sản phẩm để đánh giá", Toast.LENGTH_SHORT).show();
//                            dialog.dismiss();
//                            return;
//                        }
//
//                        DonHangDTO.SanPham sanPham = sanPhamTrongDonHangList.get(0).getSanPham();
//                        DanhGiaDTO danhGiaDTO = new DanhGiaDTO();
//                        danhGiaDTO.setNoiDung(ednoidung.getText().toString());
//                        danhGiaDTO.setThoiGian(donHang.getNgayDatHang());
//                        danhGiaDTO.setDiemanhgia((int) start.getRating());
//                        danhGiaDTO.setIdKh(userId);
//                        danhGiaDTO.setIdsp(sanPham.getId()); // Truyền đúng ID sản phẩm vào đây
//
//                        Call<DanhGiaDTO> call = apiService.adddanhgia(danhGiaDTO);
//
//                        Log.d("DanhGia", "idKH: " + userId);
//                        Log.d("DanhGia", "idSP: " + sanPham.getId());
//                        Log.d("DanhGia", "diemDanhGia: " + (int) start.getRating());
//
//                        call.enqueue(new Callback<DanhGiaDTO>() {
//                            @Override
//                            public void onResponse(Call<DanhGiaDTO> call, Response<DanhGiaDTO> response) {
//                                if (response.isSuccessful()) {
//                                    Toast.makeText(context, "Đánh giá thành công", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(context, "Không thể đánh giá", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<DanhGiaDTO> call, Throwable t) {
//                                Toast.makeText(context, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                        dialog.dismiss();
//                    }
//                });

//                        dialog.show();
//                    }
//                });
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
