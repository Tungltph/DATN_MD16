package com.example.datn_md16.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.DTO.GioHangDTO;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.R;

import java.util.List;

public class MauAdapter extends RecyclerView.Adapter<MauAdapter.MauViewHolder> {
    private List<ProductHome.MauSchema> mauList;
    private OnMauClickListener onMauClickListener;
    private int selectedPosition = -1; // Để theo dõi item được chọn

    public MauAdapter(List<ProductHome.MauSchema> mauList, OnMauClickListener onMauClickListener) {
        this.mauList = mauList;
        this.onMauClickListener = onMauClickListener;
    }

    @NonNull
    @Override
    public MauViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mau, parent, false);
        return new MauViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MauViewHolder holder, int position) {
        ProductHome.MauSchema mau = mauList.get(position);
        holder.tvTenMau.setText(mau.getMau());

        holder.itemView.setSelected(selectedPosition == position);

        holder.itemView.setOnClickListener(v -> {
            int previousSelectedPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();

            // Cập nhật selectedColor cho GioHangDTO tại vị trí đã chọn
            ProductHome.MauSchema selectedGioHang = mauList.get(selectedPosition);
            selectedGioHang.setSelectedColor(mau.getMau()); // Cập nhật màu được chọn

            // Cập nhật giao diện
            notifyItemChanged(previousSelectedPosition);
            notifyItemChanged(selectedPosition);

            // Gọi giao diện để xử lý sự kiện màu được chọn
            if (onMauClickListener != null) {
                onMauClickListener.onMauClick(mau);
                Toast.makeText(v.getContext(), "Màu: " + mau.getMau(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return mauList.size();
    }

    public static class MauViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenMau;

        public MauViewHolder(View itemView) {
            super(itemView);
            tvTenMau = itemView.findViewById(R.id.tvTenMau);
        }
    }

    public interface OnMauClickListener {
        void onMauClick(ProductHome.MauSchema mau);
    }
}

