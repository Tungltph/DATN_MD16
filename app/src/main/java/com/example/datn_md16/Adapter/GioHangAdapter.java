package com.example.datn_md16.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.datn_md16.DTO.GioHangDTO;
import com.example.datn_md16.DTO.SanPhamDTO;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangViewHolder> {
    private List<GioHangDTO> gioHangList;
    private Context context;
    private OnTotalPriceChangeListener onTotalPriceChangeListener;
    private ApiService productService;

    public GioHangAdapter(List<GioHangDTO> gioHangList, Context context) {
        this.gioHangList = gioHangList;
        this.context = context;

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.63:3000/api/gioHang/") // Thay đổi địa chỉ của bạn
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        productService = retrofit.create(ApiService.class);
    }

    public void setOnTotalPriceChangeListener(OnTotalPriceChangeListener listener) {
        this.onTotalPriceChangeListener = listener;
    }

    @NonNull
    @Override
    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new GioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangViewHolder holder, int position) {
        GioHangDTO gioHang = gioHangList.get(position);

        // Gọi API để lấy thông tin sản phẩm
        loadProductInfo(gioHang.getIdSanPham(), holder);

        holder.tvQuantity.setText(String.valueOf(gioHang.getSoLuong()));

        // Xử lý sự kiện tăng giảm số lượng
        holder.btnDecrease.setOnClickListener(v -> {
            int quantity = gioHang.getSoLuong();
            if (quantity > 0) {
                quantity--;
                gioHang.setSoLuong(quantity);
                holder.tvQuantity.setText(String.valueOf(quantity));
                updateTotalPrice();
            }
        });

        holder.btnIncrease.setOnClickListener(v -> {
            int quantity = gioHang.getSoLuong();
            quantity++;
            gioHang.setSoLuong(quantity);
            holder.tvQuantity.setText(String.valueOf(quantity));
            updateTotalPrice();
        });
    }

    private void loadProductInfo(String productId, GioHangViewHolder holder) {
        productService.getProductById(productId).enqueue(new Callback<SanPhamDTO>() {
            @Override
            public void onResponse(Call<SanPhamDTO> call, Response<SanPhamDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SanPhamDTO sanPham = response.body();
                    holder.productName.setText(sanPham.getTenSanPham());
                    holder.productPrice.setText(String.valueOf(sanPham.getGia()));
                    Glide.with(context).load(sanPham.getHinhAnh()).into(holder.productImage);
                }
            }

            @Override
            public void onFailure(Call<SanPhamDTO> call, Throwable t) {
                // Xử lý lỗi khi không thể lấy thông tin sản phẩm
                holder.productName.setText("Sản phẩm không tìm thấy");
                holder.productPrice.setText("0đ");
            }
        });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class GioHangViewHolder extends RecyclerView.ViewHolder {
        private TextView productName, productPrice, tvQuantity,btnDecrease,btnIncrease;
        private ImageView productImage;

        private CheckBox checkBox;

        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            productImage = itemView.findViewById(R.id.productImage);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

    private void updateTotalPrice() {
        final int[] totalPrice = {0};
        for (GioHangDTO gioHang : gioHangList) {
            // Gọi lại API để lấy giá cho từng sản phẩm
            productService.getProductById(gioHang.getIdSanPham()).enqueue(new Callback<SanPhamDTO>() {
                @Override
                public void onResponse(Call<SanPhamDTO> call, Response<SanPhamDTO> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        SanPhamDTO sanPham = response.body();
                        int price = Integer.parseInt(sanPham.getGia().replaceAll("[^\\d.]", ""));
                        totalPrice[0] += price * gioHang.getSoLuong();
                        if (onTotalPriceChangeListener != null) {
                            onTotalPriceChangeListener.onTotalPriceChanged(totalPrice[0]);
                        }
                    }
                }

                @Override
                public void onFailure(Call<SanPhamDTO> call, Throwable t) {
                    // Xử lý lỗi khi không thể lấy thông tin sản phẩm
                }
            });
        }
    }

    public interface OnTotalPriceChangeListener {
        void onTotalPriceChanged(int totalPrice);
    }
}
