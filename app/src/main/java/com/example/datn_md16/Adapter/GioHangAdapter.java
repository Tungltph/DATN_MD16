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
                .baseUrl("http://192.168.1.8:3000/") // Thay đổi địa chỉ của bạn
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
        loadProductInfo(gioHang, holder);

        holder.tvQuantity.setText(String.valueOf(gioHang.getSoLuong()));
        holder.checkBox.setChecked(gioHang.isChecked());

        // Xử lý sự kiện thay đổi checkbox
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            gioHang.setChecked(isChecked);
            updateTotalPrice();
        });

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

        // Xử lý sự kiện xóa sản phẩm
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xác nhận và gọi API để xóa sản phẩm
                new AlertDialog.Builder(context)
                        .setTitle("Xóa sản phẩm")
                        .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Gọi API để xóa sản phẩm
                                productService.deleteItemFromCart(gioHang.get_id()).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {
                                            Toast.makeText(context, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                            // Cập nhật danh sách giỏ hàng
                                            gioHangList.remove(holder.getAdapterPosition());
                                            notifyItemRemoved(holder.getAdapterPosition());
                                            updateTotalPrice();
                                        } else {
                                            Toast.makeText(context, "Lỗi: " + response.message(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(context, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Không", null)
                        .show();
            }
        });

    }

    private void loadProductInfo(GioHangDTO gioHang, GioHangViewHolder holder) {
        productService.getProductById(gioHang.getIdSanPham()).enqueue(new Callback<SanPhamDTO>() {
            @Override
            public void onResponse(Call<SanPhamDTO> call, Response<SanPhamDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SanPhamDTO sanPham = response.body();
                    gioHang.setSanPham(sanPham);

                    // Lấy giá từ mauSchema
                    String priceText = "Không có thông tin giá";
                    String colorText = "Không có thông tin màu";
                    if (sanPham.getMauSchema() != null && !sanPham.getMauSchema().isEmpty()) {
                        priceText = sanPham.getMauSchema().get(0).getGiaTien() + "đ";
                        colorText = "Màu điện thoại : " + sanPham.getMauSchema().get(0).getMau();
                    }

                    holder.productName.setText(sanPham.getTenSanPham());
                    holder.productPrice.setText(priceText);
                    holder.mau.setText(colorText);
                    Glide.with(context).load(sanPham.getHinhAnh()).into(holder.productImage);
                } else {
                    holder.productName.setText("Sản phẩm không tìm thấy");
                    holder.productPrice.setText("Không có thông tin giá");
                    holder.mau.setText("Không có thông tin màu");
                }
            }

            @Override
            public void onFailure(Call<SanPhamDTO> call, Throwable t) {
                holder.productName.setText("Sản phẩm không tìm thấy");
                holder.productPrice.setText("Không có thông tin giá");
                holder.mau.setText("Không có thông tin màu");
            }
        });
    }


    private void showDeleteConfirmationDialog(GioHangDTO gioHang, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?")
                .setPositiveButton("Có", (dialog, which) -> {
                    // Xóa sản phẩm khỏi giỏ hàng
                    deleteItemFromCart(gioHang.getIdSanPham(), position);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void deleteItemFromCart(String id, int position) {
        productService.deleteItemFromCart(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Xóa sản phẩm khỏi danh sách và cập nhật RecyclerView
                    gioHangList.remove(position);
                    notifyItemRemoved(position);
                    updateTotalPrice();
                    Toast.makeText(context, "Sản phẩm đã được xóa", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Lỗi khi xóa sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class GioHangViewHolder extends RecyclerView.ViewHolder {
        private TextView productName, productPrice, tvQuantity, btnDecrease, btnIncrease,mau;
        private ImageView productImage, xoa;
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
            xoa = itemView.findViewById(R.id.xoagiohang);
            mau = itemView.findViewById(R.id.productColor);
        }
    }

    private void updateTotalPrice() {
        int totalPrice = 0;
        for (GioHangDTO gioHang : gioHangList) {
            if (gioHang.isChecked() && gioHang.getSanPham() != null) { // Chỉ tính tổng cho các sản phẩm đã được chọn
                SanPhamDTO sanPham = gioHang.getSanPham();
                int price = 0;
                if (sanPham.getMauSchema() != null && !sanPham.getMauSchema().isEmpty()) {
                    price = sanPham.getMauSchema().get(0).getGiaTien(); // Lấy giá từ mauSchema
                }
                totalPrice += price * gioHang.getSoLuong();
            }
        }
        if (onTotalPriceChangeListener != null) {
            onTotalPriceChangeListener.onTotalPriceChanged(totalPrice);
        }
    }

    public interface OnTotalPriceChangeListener {
        void onTotalPriceChanged(int totalPrice);
    }
}
