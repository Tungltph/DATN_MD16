package com.example.datn_md16.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Activitys.DangNhap;
import com.example.datn_md16.Adapter.ThongBaoAdapter;
import com.example.datn_md16.DTO.ThongBaoDTO;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.Interfa.ThongBaoResponse;
import com.example.datn_md16.R;
import com.example.datn_md16.Interface.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ThongBaoFrag extends Fragment {

    private RecyclerView recyclerView;
    private ThongBaoAdapter adapter;
    private List<ThongBaoDTO> thongBaoList = new ArrayList<>();
    ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_thongbao, container, false);

        // Khởi tạo RecyclerView và Adapter
        recyclerView = view.findViewById(R.id.rcv_thongBao);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ThongBaoAdapter(thongBaoList, getContext());
        recyclerView.setAdapter(adapter);


        SharedPreferences sharedPreferences = getContext().getSharedPreferences(DangNhap.PREFS_NAME, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString(DangNhap.KEY_USER_ID, null);
        Log.d("YeuThichFrag", "UserId: " + userId);

        // Gọi API để lấy dữ liệu
        Retrofit retrofit = ApiClient.getClient();
        apiService = retrofit.create(ApiService.class);
        Call<ThongBaoResponse> call = apiService.getThongBaoById(userId);
        call.enqueue(new Callback<ThongBaoResponse>() {
            @Override
            public void onResponse(Call<ThongBaoResponse> call, Response<ThongBaoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ThongBaoResponse thongBaoResponse = response.body();
                    thongBaoList.clear();
                    if (thongBaoResponse.getData() != null) {
                        thongBaoList.addAll(thongBaoResponse.getData());
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ThongBaoResponse> call, Throwable t) {
                Log.e("API_ERROR", "Lỗi khi gọi API: ", t);
                Toast.makeText(getContext(), "Lỗi khi lấy dữ liệu: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Thiết lập ItemTouchHelper để hỗ trợ vuốt
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapter.removeItem(position);
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);

        return view;
    }
}
