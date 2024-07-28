package com.example.datn_md16.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.SanPhamYeuThichAdapter;
import com.example.datn_md16.DTO.SanPhamYeuThichDTO;
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YeuThichFrag extends Fragment {

    private RecyclerView recyclerView;
    private SanPhamYeuThichAdapter adapter;
    private List<SanPhamYeuThichDTO> sanPhamYeuThichList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_yeuthich, container, false);

        recyclerView = view.findViewById(R.id.rcv_YT);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Số cột là 2

        sanPhamYeuThichList = new ArrayList<>();
        adapter = new SanPhamYeuThichAdapter(sanPhamYeuThichList);
        recyclerView.setAdapter(adapter);

        // Load data from API
        loadSanPhamYeuThich();

        return view;
    }

    private void loadSanPhamYeuThich() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        // Thay đổi endpoint và parameters theo yêu cầu của bạn
        Call<List<SanPhamYeuThichDTO>> call = apiService.getSanPhamYT();

        call.enqueue(new Callback<List<SanPhamYeuThichDTO>>() {
            @Override
            public void onResponse(Call<List<SanPhamYeuThichDTO>> call, Response<List<SanPhamYeuThichDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sanPhamYeuThichList.clear();
                    sanPhamYeuThichList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<SanPhamYeuThichDTO>> call, Throwable t) {
                // Xử lý lỗi
            }
        });
    }

}
