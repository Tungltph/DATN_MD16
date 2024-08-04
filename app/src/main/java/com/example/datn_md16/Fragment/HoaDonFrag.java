package com.example.datn_md16.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.DonHangHomeAdapter;
import com.example.datn_md16.DTO.DonHangDTO;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HoaDonFrag extends Fragment {

    private ApiService apiService;
    private RecyclerView recyclerView;
    private DonHangHomeAdapter adapter;
    private List<DonHangDTO.DonHang> donHangList = new ArrayList<>();
    private Button btnChoXacNhan, btnChoGiaoHang, btnDangGiao, btnDaGiao, btnDaHuy;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_don_hang, container, false);

        // Lấy ID người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);

        Log.d("HoaDonFrag", "User ID retrieved: " + userId);
        if (userId == null) {
            Toast.makeText(getContext(), "Không có thông tin người dùng", Toast.LENGTH_SHORT).show();
            return view;
        }

        recyclerView = view.findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DonHangHomeAdapter(donHangList, getContext());
        recyclerView.setAdapter(adapter);

        // Khởi tạo Retrofit với cấu hình OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.8:3000/api/donHang/")  // Đảm bảo URL gốc không có đường dẫn cụ thể
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Khởi tạo các nút trạng thái
        btnChoXacNhan = view.findViewById(R.id.btnChoXacNhan);
        btnChoGiaoHang = view.findViewById(R.id.btnChoGiaoHang);
        btnDangGiao = view.findViewById(R.id.btnDangGiao);
        btnDaGiao = view.findViewById(R.id.btnDaGiao);
        btnDaHuy = view.findViewById(R.id.btnDaHuy);

        // Gán sự kiện cho các nút trạng thái
        btnChoXacNhan.setOnClickListener(v -> filterDonHang("Chờ xác nhận"));
        btnChoGiaoHang.setOnClickListener(v -> filterDonHang("Đang xử lý"));
        btnDangGiao.setOnClickListener(v -> filterDonHang("Đang giao hàng"));
        btnDaGiao.setOnClickListener(v -> filterDonHang("Đã giao hàng"));
        btnDaHuy.setOnClickListener(v -> filterDonHang("Đã hủy"));

        // Gọi API để lấy danh sách đơn hàng
        loadDonHang();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Gọi phương thức để tải lại dữ liệu khi fragment được hiển thị lại
        loadDonHang();
    }

    private void loadDonHang() {
        Log.d("HoaDonFrag", "Loading orders for user ID: " + userId); // Log ID người dùng
        apiService.getDonHangByUser(userId).enqueue(new Callback<DonHangDTO>() {
            @Override
            public void onResponse(@NonNull Call<DonHangDTO> call, @NonNull Response<DonHangDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("HoaDonFrag", "Orders loaded: " + response.body().getData().size()); // Log số lượng đơn hàng tải được
                    donHangList.clear();
                    donHangList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DonHangDTO> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi tải dữ liệu: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterDonHang(String status) {
        apiService.getDonHangByUser(userId).enqueue(new Callback<DonHangDTO>() {
            @Override
            public void onResponse(@NonNull Call<DonHangDTO> call, @NonNull Response<DonHangDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DonHangDTO.DonHang> allDonHangList = response.body().getData();
                    List<DonHangDTO.DonHang> filteredList = new ArrayList<>();
                    for (DonHangDTO.DonHang donHang : allDonHangList) {
                        if (donHang.getTrangThaiDonHang().equals(status)) {
                            filteredList.add(donHang);
                        }
                    }

                    donHangList.clear();
                    donHangList.addAll(filteredList);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Đơn hàng trạng thái: " + status, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu với trạng thái: " + status, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DonHangDTO> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi tải dữ liệu: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
