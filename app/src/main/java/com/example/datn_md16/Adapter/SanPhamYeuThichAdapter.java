package com.example.datn_md16.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.DTO.SanPhamDTO;
import com.example.datn_md16.DTO.SanPhamYeuThichDTO;
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SanPhamYeuThichAdapter extends RecyclerView.Adapter<SanPhamYeuThichAdapter.ViewHolder> {
    private List<SanPhamYeuThichDTO> sanPhamYeuThichList;

    public SanPhamYeuThichAdapter(List<SanPhamYeuThichDTO> sanPhamYeuThichList) {
        this.sanPhamYeuThichList = sanPhamYeuThichList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yeu_thich, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPhamYeuThichDTO item = sanPhamYeuThichList.get(position);

        String sanPhamId = item.getId_sanPham();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getProductById(sanPhamId).enqueue(new Callback<SanPhamDTO>() {
            @Override
            public void onResponse(Call<SanPhamDTO> call, Response<SanPhamDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SanPhamDTO sanPham = response.body();
                    holder.tvTenYT.setText(sanPham.getTenSanPham());

                    Picasso.get().load(sanPham.getHinhAnh()).into(holder.imgSanPhamYT);
                } else {
                    // Hiển thị thông báo lỗi hoặc placeholder
                    holder.tvTenYT.setText("Thông tin sản phẩm không có sẵn");
                    holder.rbSao.setVisibility(View.GONE);
                    holder.txtRb.setVisibility(View.GONE);
                    holder.imgSanPhamYT.setImageResource(R.drawable.img_1);
                }
            }

            @Override
            public void onFailure(Call<SanPhamDTO> call, Throwable t) {
                // Xử lý lỗi kết nối và hiển thị thông báo lỗi
                holder.tvTenYT.setText("Lỗi kết nối. Không thể tải dữ liệu.");
                holder.rbSao.setVisibility(View.GONE);
                holder.txtRb.setVisibility(View.GONE);
                holder.imgSanPhamYT.setImageResource(R.drawable.img_1);
            }
        });

        holder.imgYeuThich.setImageResource(R.drawable.ic_favorite); // Hoặc thay đổi dựa trên trạng thái yêu thích
    }



    @Override
    public int getItemCount() {
        return sanPhamYeuThichList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSanPhamYT;
        TextView tvTenYT;
        RatingBar rbSao;
        TextView txtRb;
        ImageView imgYeuThich;

        public ViewHolder(View itemView) {
            super(itemView);
            imgSanPhamYT = itemView.findViewById(R.id.imgSanPhamYT);
            tvTenYT = itemView.findViewById(R.id.tvTenYT);
            rbSao = itemView.findViewById(R.id.rbSao);
            txtRb = itemView.findViewById(R.id.txtRb);
            imgYeuThich = itemView.findViewById(R.id.imgYeuThich);
        }
    }
}
