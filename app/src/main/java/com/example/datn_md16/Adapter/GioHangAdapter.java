package com.example.datn_md16.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
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
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.Interface.ApiService;
import com.example.datn_md16.R;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangViewHolder> {
    private List<GioHangDTO> gioHangList;
    private Context context;
    private OnTotalPriceChangeListener onTotalPriceChangeListener;
    private ApiService productService;
    private String selectedColor;

    public GioHangAdapter(List<GioHangDTO> gioHangList, Context context) {
        this.gioHangList = gioHangList;
        this.context = context;

        // Khởi tạo Retrofit
        Retrofit retrofit = ApiClient.getClient();

        productService = retrofit.create(ApiService.class);
    }

    public void removeItem(int position) {
        if (position >= 0 && position < gioHangList.size()) {
            gioHangList.remove(position);
            notifyItemRemoved(position);
            if (onTotalPriceChangeListener != null) {
                // Notify total price changed after removing item
                onTotalPriceChangeListener.onTotalPriceChanged(calculateTotalPrice());
            }
        }
    }

    private int calculateTotalPrice() {
        int total = 0;
        for (GioHangDTO item : gioHangList) {
            if (item.isChecked()) {
                total += item.getPrice() * item.getQuantity();
            }
        }
        return total;
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

            // Lấy danh sách màu sắc của sản phẩm
            List<ProductHome.MauSchema> mauSchemaList = gioHang.getSanPham().getMauSchema();

            // Tìm màu sắc tương ứng với idMau đã chọn
            for (ProductHome.MauSchema mauSchema : mauSchemaList) {
                if (mauSchema.get_id().equals(gioHang.getIdMau())) {
                    int maxQuantityInStock = mauSchema.getSoLuong();
                    // Kiểm tra và tăng số lượng nếu còn hàng trong kho
                    if (quantity < maxQuantityInStock) {
                        quantity++;
                        gioHang.setSoLuong(quantity);
                        holder.tvQuantity.setText(String.valueOf(quantity));
                        updateTotalPrice();
                    } else {
                        Toast.makeText(context, "Không đủ hàng trong kho", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        });


        // Xử lý sự kiện xóa sản phẩm
        holder.xoa.setOnClickListener(v -> {
            // Xác nhận và gọi API để xóa sản phẩm
            new AlertDialog.Builder(context)
                    .setTitle("Xóa sản phẩm")
                    .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                    .setPositiveButton("Có", (dialog, which) -> {
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
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });
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




    private void showDeleteConfirmationDialog(GioHangDTO gioHang, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProductFromCart(gioHang.get_id(), position);
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private void deleteProductFromCart(String productId, int position) {
        productService.deleteItemFromCart(productId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    gioHangList.remove(position);
                    notifyItemRemoved(position);
                    updateTotalPrice();
                    Toast.makeText(context, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
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

    private void updateTotalPrice() {
        int totalPrice = 0;
        for (GioHangDTO gioHang : gioHangList) {
            if (gioHang.isChecked() && gioHang.getSanPham() != null) { // Chỉ tính tổng cho các sản phẩm đã được chọn
                ProductHome sanPham = gioHang.getSanPham();
                int price = 0;
                if (sanPham.getMauSchema() != null && !sanPham.getMauSchema().isEmpty()) {
                    for (ProductHome.MauSchema mauSchema : sanPham.getMauSchema()) {
                        if (mauSchema.get_id() != null && mauSchema.get_id().equals(gioHang.getIdMau())) {
                            // Lấy giá từ mauSchema tương ứng với idMau
                            price = mauSchema.getGiaTien();
                            break;
                        }
                    }
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
        TextView productName, productPrice, tvQuantity, mau, btnDecrease, btnIncrease;
        ImageView productImage, xoa;
        CheckBox checkBox;

        public GioHangViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            mau = itemView.findViewById(R.id.productColorGioHang);
            productImage = itemView.findViewById(R.id.productImage);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            xoa = itemView.findViewById(R.id.xoagiohang);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }

    public interface OnTotalPriceChangeListener {
        void onTotalPriceChanged(int totalPrice);


    }
}