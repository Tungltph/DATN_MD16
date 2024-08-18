package com.example.datn_md16.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn_md16.Activitys.Acti_ChiTietSP;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.DTO.SanPhamDTO;
import com.example.datn_md16.DTO.SanPhamYeuThichDTO;
import com.example.datn_md16.DTO.TimKiemDTO;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SanPhamYeuThichAdapter extends RecyclerView.Adapter<SanPhamYeuThichAdapter.ViewHolder> {
    private List<SanPhamYeuThichDTO> sanPhamYeuThichDTOS;
    private Context context;
    private ApiService productService;

    public SanPhamYeuThichAdapter(List<SanPhamYeuThichDTO> sanPhamYeuThichDTOS, Context context) {
        this.sanPhamYeuThichDTOS = sanPhamYeuThichDTOS;
        this.context = context;

        // Khởi tạo Retrofit
        Retrofit retrofit = ApiClient.getClient();

        productService = retrofit.create(ApiService.class);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yeu_thich, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPhamYeuThichDTO sanPhamYeuThichDTO = sanPhamYeuThichDTOS.get(position);

        if (sanPhamYeuThichDTO == null || sanPhamYeuThichDTOS.isEmpty()) return;

        // Kiểm tra idSanPham không null
        if (sanPhamYeuThichDTO.getId_sanPham() != null) {
            // Gọi API để lấy thông tin sản phẩm
            loadProductInfo(sanPhamYeuThichDTO, holder);
        } else {
            Log.e("SanPhamYeuThichAdapter", "idSanPham is null for position: " + position);
            holder.tvTenYT.setText("ID sản phẩm không hợp lệ");
        }

        // Xử lý sự kiện bấm vào imgYeuThich để xóa sản phẩm khỏi danh sách yêu thích
        holder.imgYeuThich.setOnClickListener(v -> {
            // Xóa sản phẩm khỏi danh sách yêu thích
            removeFavorite(sanPhamYeuThichDTO, position);
        });
    }

    private void removeFavorite(SanPhamYeuThichDTO sanPhamYeuThichDTO, int position) {
        // Gửi yêu cầu xóa sản phẩm khỏi danh sách yêu thích
        productService.removeFavorite(sanPhamYeuThichDTO.get_id())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Xóa thành công, cập nhật danh sách và giao diện
                            sanPhamYeuThichDTOS.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, sanPhamYeuThichDTOS.size());
                            Toast.makeText(context.getApplicationContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("SanPhamYeuThichAdapter", "Failed to remove favorite. Code: " + response.code() + ", Message: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("SanPhamYeuThichAdapter", "Error: " + t.getMessage());
                    }
                });
    }

    private void loadProductInfo(SanPhamYeuThichDTO sanPhamYeuThichDTO, ViewHolder holder) {
        productService.getProductById(sanPhamYeuThichDTO.getId_sanPham()).enqueue(new Callback<ProductHome>() {
            @Override
            public void onResponse(Call<ProductHome> call, Response<ProductHome> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductHome sanPham = response.body();
                    sanPhamYeuThichDTO.setSanPham(sanPham);

                    // Lấy giá từ mauSchema
                    String priceText = "Không có thông tin giá";
                    String colorText = "Không có thông tin màu";
                    if (sanPham.getMauSchema() != null && !sanPham.getMauSchema().isEmpty()) {
                        double giaTien = sanPham.getMauSchema().get(0).getGiaTien();

                        // Định dạng giá trị giaTien chỉ hiển thị phần nguyên
                        NumberFormat formatter = NumberFormat.getIntegerInstance(Locale.US);
                        holder.tvGiaGiam.setText("₫" + formatter.format(giaTien));

                        // Tính toán giá gốc
                        double phanTram = Double.parseDouble(sanPham.getGiamGia());
                        double giaGoc = giaTien / (1 - (phanTram / 100));

                        // Định dạng giá trị giaGoc chỉ hiển thị phần nguyên
                        holder.tvGiaGoc.setText("₫" + formatter.format(giaGoc));
                        setStrikeThroughText(holder.tvGiaGoc);
                    }
                    holder.tvTenYT.setText(sanPham.getTenDienThoai());
                    Glide.with(context).load(sanPham.getHinhAnh()).into(holder.imgSanPhamYT);
                    holder.tvPhanTram.setText("-"+sanPham.getGiamGia());
                } else {
                    holder.tvTenYT.setText("Sản phẩm không tìm thấy");
                    Log.e("SanPhamYeuThichAdapter", "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<ProductHome> call, Throwable t) {
                holder.tvTenYT.setText("Sản phẩm không tìm thấy");
                Log.e("SanPhamYeuThichAdapter", "Error: " + t.getMessage());
            }
        });
    }

    public void setStrikeThroughText(TextView textView) {
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return sanPhamYeuThichDTOS != null ? sanPhamYeuThichDTOS.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSanPhamYT;
        TextView tvTenYT, tvGiaGoc, tvGiaGiam, tvPhanTram;
        ImageView imgYeuThich;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPhamYT = itemView.findViewById(R.id.imgSanPhamYT);
            tvTenYT = itemView.findViewById(R.id.tvTenYT);
            imgYeuThich = itemView.findViewById(R.id.imgYeuThich);
            tvGiaGiam = itemView.findViewById(R.id.tvGiaGiamYT);
            tvGiaGoc = itemView.findViewById(R.id.tvGiaGocYT);
            tvPhanTram = itemView.findViewById(R.id.tv_phanTram_YT);
        }
    }
}
