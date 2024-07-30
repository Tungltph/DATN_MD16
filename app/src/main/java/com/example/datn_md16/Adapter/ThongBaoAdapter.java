package com.example.datn_md16.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.DTO.ThongBaoDTO;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongBaoAdapter extends RecyclerView.Adapter<ThongBaoAdapter.ViewHolder> {
    private List<ThongBaoDTO> thongBaoList;
    private Context context;

    public ThongBaoAdapter(List<ThongBaoDTO> thongBaoList, Context context) {
        this.thongBaoList = thongBaoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thongbao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThongBaoDTO thongBao = thongBaoList.get(position);
        holder.txtTieuDe.setText(thongBao.getTieuDe());
        holder.txtNoiDung.setText(thongBao.getNoiDung());
        holder.txtThoiGian.setText(thongBao.getFormattedThoiGian());
    }

    @Override
    public int getItemCount() {
        return thongBaoList.size();
    }

    public void removeItem(final int position) {
        final ThongBaoDTO thongBao = thongBaoList.get(position);
        String id = thongBao.get_id();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Void> call = apiService.deleteThongBao(id);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    thongBaoList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Đã xóa thông báo", Toast.LENGTH_SHORT).show();
                } else {
                    thongBaoList.add(position, thongBao); // Khôi phục thông báo nếu xóa thất bại
                    notifyItemInserted(position);
                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                thongBaoList.add(position, thongBao); // Khôi phục thông báo nếu xóa thất bại
                notifyItemInserted(position);
                Toast.makeText(context, "Lỗi khi xóa thông báo: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTieuDe;
        public TextView txtNoiDung;
        public TextView txtThoiGian;

        public ViewHolder(View view) {
            super(view);
            txtTieuDe = view.findViewById(R.id.txtTieuDe);
            txtNoiDung = view.findViewById(R.id.txtNoiDung);
            txtThoiGian = view.findViewById(R.id.txtThoiGian);
        }
    }
}
