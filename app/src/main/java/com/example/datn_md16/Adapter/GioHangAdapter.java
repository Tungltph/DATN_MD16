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

import com.example.datn_md16.DTO.GioHangDTO;
import com.example.datn_md16.R;


import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {

    public interface OnTotalPriceChangeListener {
        void onTotalPriceChanged(int totalPrice);
    }

    private List<GioHangDTO> gioHangList;
    private Context context;
    private OnTotalPriceChangeListener listener;

    public GioHangAdapter(List<GioHangDTO> gioHangList, Context context) {
        this.gioHangList = gioHangList;
        this.context = context;
    }

    public void setOnTotalPriceChangeListener(OnTotalPriceChangeListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_giohang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHangDTO gioHang = gioHangList.get(position);

        holder.checkbox.setChecked(gioHang.isChecked());
        holder.productName.setText(gioHang.getProductName());
        holder.productColor.setText(gioHang.getProductColor());
        holder.productPrice.setText(gioHang.getProductPrice());
        holder.tvQuantity.setText(String.valueOf(gioHang.getQuantity()));

        holder.btnDecrease.setOnClickListener(v -> {
            if (gioHang.getQuantity() > 0) {
                gioHang.setQuantity(gioHang.getQuantity() - 1);
                holder.tvQuantity.setText(String.valueOf(gioHang.getQuantity()));
                updateTotalPrice();
            }
        });

        holder.btnIncrease.setOnClickListener(v -> {
            gioHang.setQuantity(gioHang.getQuantity() + 1);
            holder.tvQuantity.setText(String.valueOf(gioHang.getQuantity()));
            updateTotalPrice();
        });

        holder.removeItemButton.setOnClickListener(v -> {
            gioHangList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, gioHangList.size());
            updateTotalPrice();
        });

        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            gioHang.setChecked(isChecked);
            updateTotalPrice();
        });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkbox;
        ImageView productImage;
        TextView productName;
        TextView productColor;
        TextView productPrice;
        TextView tvQuantity;
        TextView btnDecrease;
        TextView btnIncrease;
        ImageView removeItemButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productColor = itemView.findViewById(R.id.productColor);
            productPrice = itemView.findViewById(R.id.productPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnCong);
            removeItemButton = itemView.findViewById(R.id.removeItemButton);
        }
    }

    private void updateTotalPrice() {
        int totalPrice = 0;
        for (GioHangDTO item : gioHangList) {
            if (item.isChecked()) {
                totalPrice += Integer.parseInt(item.getProductPrice().replace(".", "").replace("đ", "")) * item.getQuantity();
            }
        }
        if (listener != null) {
            listener.onTotalPriceChanged(totalPrice);
        }
    }
}
