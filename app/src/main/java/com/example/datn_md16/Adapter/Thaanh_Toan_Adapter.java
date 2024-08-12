package com.example.datn_md16.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn_md16.DTO.GioHangDTO;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.DTO.SanPhamDTO;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.R;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Thaanh_Toan_Adapter extends RecyclerView.Adapter<Thaanh_Toan_Adapter.GioHangViewHolder>{
    private List<GioHangDTO> gioHangList;
    private Context context;
    private OnTotalPriceChangeListener onTotalPriceChangeListener;
    private ApiService productService;

    public Thaanh_Toan_Adapter(List<GioHangDTO> gioHangList, Context context) {
        this.gioHangList = gioHangList;
        this.context = context;

        // Khởi tạo Retrofit
        Retrofit retrofit = ApiClient.getClient();

        productService = retrofit.create(ApiService.class);
    }

    public void setOnTotalPriceChangeListener(OnTotalPriceChangeListener listener) {
        this.onTotalPriceChangeListener = listener;
    }

    @NonNull
    @Override
    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham_thanh_toan, parent, false);
        return new GioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangViewHolder holder, int position) {
        GioHangDTO gioHang = gioHangList.get(position);

        // Gọi API để lấy thông tin sản phẩm
        loadProductInfo(gioHang, holder);

        holder.tvQuantity.setText(String.valueOf(gioHang.getSoLuong()));


    }

    private void loadProductInfo(GioHangDTO gioHang, GioHangViewHolder holder) {
        // Giả sử productService có phương thức để lấy thông tin sản phẩm theo ID
        productService.getProductById(gioHang.getIdSanPham()).enqueue(new Callback<ProductHome>() {
            @Override
            public void onResponse(Call<ProductHome> call, Response<ProductHome> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductHome product = response.body();
                    gioHang.setSanPham(product);

                    // Khởi tạo các biến để lưu giá và màu sắc
                    String priceText = "Không có thông tin giá";
                    String colorText = "Không có thông tin màu";

                    if (product.getMauSchema() != null && !product.getMauSchema().isEmpty()) {
                        for (ProductHome.MauSchema mauSchema : product.getMauSchema()) {
                            if (mauSchema.get_id() != null && mauSchema.get_id().equals(gioHang.getIdMau())) {
                                // Nếu màu sắc phù hợp với idMau của gioHang
                                priceText = "₫" + new DecimalFormat("#,###").format(mauSchema.getGiaTien());
                                colorText = "Màu: " + mauSchema.getMau();
                                break;
                            }
                        }
                    }

                    holder.productName.setText(product.getTenDienThoai());
                    holder.productPrice.setText(priceText);
                    holder.mau.setText(colorText);
                    Glide.with(context).load(product.getHinhAnh()).into(holder.productImage);

                    // Cập nhật tổng tiền sau khi tải thông tin sản phẩm
                    updateTotalPrice();
                } else {
                    holder.productName.setText("Sản phẩm không tìm thấy");
                    holder.productPrice.setText("Không có thông tin giá");
                    holder.mau.setText("Không có thông tin màu");
                }
            }

            @Override
            public void onFailure(Call<ProductHome> call, Throwable t) {
                holder.productName.setText("Sản phẩm không tìm thấy");
                holder.productPrice.setText("Không có thông tin giá");
                holder.mau.setText("Không có thông tin màu");
            }
        });
    }

    private void updateTotalPrice() {
        double totalPrice = 0.0;
        for (GioHangDTO gioHang : gioHangList) {
            if (gioHang.isChecked() && gioHang.getSanPham() != null) {
                double price = 0.0;
                if (gioHang.getSanPham().getMauSchema() != null && !gioHang.getSanPham().getMauSchema().isEmpty()) {
                    price = gioHang.getSanPham().getMauSchema().get(0).getGiaTien();
                }
                totalPrice += price * gioHang.getSoLuong();
            }
        }

        if (onTotalPriceChangeListener != null) {
            onTotalPriceChangeListener.onTotalPriceChanged(totalPrice);
        }
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public static class GioHangViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, tvQuantity, mau;
        ImageView productImage, xoa;

        public GioHangViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            mau = itemView.findViewById(R.id.productColor);
            productImage = itemView.findViewById(R.id.productImage);
//            xoa = itemView.findViewById(R.id.xoagiohang);
        }
    }

    public interface OnTotalPriceChangeListener {
        void onTotalPriceChanged(double totalPrice);
    }
}
