package com.example.datn_md16.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.SanPhamYeuThichAdapter;
import com.example.datn_md16.DTO.SanPhamYeuThichDTO;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.Interfa.SanPhamYeuThichResponse;
import com.example.datn_md16.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YeuThichFrag extends Fragment {
    private SanPhamYeuThichAdapter adapter;
    private List<SanPhamYeuThichDTO> sanPhamYeuThichDTOS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_yeuthich, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rcv_YT);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Sử dụng GridLayoutManager với 2 cột

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.9.104:3000/") // Chỉ cần URL gốc
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Tạo đối tượng dịch vụ API từ Retrofit
        ApiService apiService = retrofit.create(ApiService.class);

        // Gọi API để lấy phản hồi ApiResponse
        Call<SanPhamYeuThichResponse> call = apiService.getSanPhamYeuThich();
        call.enqueue(new Callback<SanPhamYeuThichResponse>() {
            @Override
            public void onResponse(Call<SanPhamYeuThichResponse> call, Response<SanPhamYeuThichResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sanPhamYeuThichDTOS = response.body().getData();
                    adapter = new SanPhamYeuThichAdapter(sanPhamYeuThichDTOS, getContext());
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Không thể lấy dữ liệu từ server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SanPhamYeuThichResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("YeuThichFrag", "Error: " + t.getMessage());
            }
        });

        return view;
    }
}
