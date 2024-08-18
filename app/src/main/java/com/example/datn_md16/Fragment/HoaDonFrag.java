package com.example.datn_md16.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.DonHangHomeAdapter;
import com.example.datn_md16.DTO.DonHangDTO;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.R;

import java.io.IOException;
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
    private TextView btnChoXacNhan, btnChoGiaoHang, btnDangGiao, btnDaGiao, btnDaHuy;
    private String userId;

    EditText edtTimKiemDonHang;

    private void updateButtonStyles(TextView selectedButton) {
        // Danh sách các nút trạng thái
        TextView[] buttons = {btnChoXacNhan, btnChoGiaoHang, btnDangGiao, btnDaGiao, btnDaHuy};

        for (TextView button : buttons) {
            if (button == selectedButton) {
                // Áp dụng style cho nút được chọn
                button.setTextColor(getResources().getColor(R.color.white)); // Thay đổi màu chữ
                button.setBackgroundColor(getResources().getColor(R.color.red)); // Thay đổi màu nền
            } else {
                // Áp dụng style cho nút không được chọn
                button.setTextColor(getResources().getColor(R.color.black)); // Thay đổi màu chữ
                button.setBackgroundColor(getResources().getColor(android.R.color.transparent)); // Màu nền mặc định
                button.setPaintFlags(button.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
            }
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_don_hang, container, false);

        // Lấy ID người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);

        Log.d("HoaDonFrag", "User ID retrieved: " + userId);
        if (userId == null) {
            Toast.makeText(getContext(), "Không có thông tin người dùng", Toast.LENGTH_SHORT).show();
            return view; // Không tiếp tục nếu không có ID người dùng
        }

        recyclerView = view.findViewById(R.id.recyclerViewOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DonHangHomeAdapter(donHangList, getContext());
        recyclerView.setAdapter(adapter);


        Retrofit retrofit = ApiClient.getClient();
        apiService = retrofit.create(ApiService.class);

        // Khởi tạo các nút trạng thái
        btnChoXacNhan = view.findViewById(R.id.btnChoXacNhan);
        btnChoGiaoHang = view.findViewById(R.id.btnChoGiaoHang);
        btnDangGiao = view.findViewById(R.id.btnDangGiao);
        btnDaGiao = view.findViewById(R.id.btnDaGiao);
        btnDaHuy = view.findViewById(R.id.btnDaHuy);
        edtTimKiemDonHang = view.findViewById(R.id.edtTimKiemDonHang);

        // Gán sự kiện cho các nút trạng thái
        btnChoXacNhan.setOnClickListener(v -> {
            filterDonHang("Chờ xác nhận");
            updateButtonStyles(btnChoXacNhan);
        });
        btnChoGiaoHang.setOnClickListener(v -> {
            filterDonHang("Đang xử lý");
            updateButtonStyles(btnChoGiaoHang);
        });
        btnDangGiao.setOnClickListener(v -> {
            filterDonHang("Đang giao hàng");
            updateButtonStyles(btnDangGiao);
        });
        btnDaGiao.setOnClickListener(v -> {
            filterDonHang("Đã giao hàng");
            updateButtonStyles(btnDaGiao);
        });
        btnDaHuy.setOnClickListener(v -> {
            filterDonHang("Đã hủy");
            updateButtonStyles(btnDaHuy);
        });

        // Mặc định hiển thị trạng thái "Chờ xác nhận"
        updateButtonStyles(btnChoXacNhan);
        filterDonHang("Chờ xác nhận");

        loadDonHang();

        edtTimKiemDonHang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().trim();
                TextView[] buttons = {btnChoXacNhan, btnChoGiaoHang, btnDangGiao, btnDaGiao, btnDaHuy};
                String currentStatus = "";

                for (TextView button : buttons) {
                    if (button.getCurrentTextColor() == getResources().getColor(R.color.white)) {
                        currentStatus = button.getText().toString();
                        break;
                    }
                }

                if (keyword.isEmpty()) {
                    filterDonHang(currentStatus); // Tải lại dữ liệu theo trạng thái hiện tại nếu không có từ khóa
                } else {
                    searchDonHang(keyword, currentStatus); // Tìm kiếm trong trạng thái hiện tại
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần xử lý
            }
        });



        return view;
    }

    private void searchDonHang(String keyword, String currentStatus) {
        apiService.getDonHangByUser(userId).enqueue(new Callback<DonHangDTO>() {
            @Override
            public void onResponse(@NonNull Call<DonHangDTO> call, @NonNull Response<DonHangDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DonHangDTO.DonHang> allDonHangList = response.body().getData();
                    List<DonHangDTO.DonHang> filteredList = new ArrayList<>();
                    for (DonHangDTO.DonHang donHang : allDonHangList) {
                        if (donHang.getTrangThaiDonHang().equals(currentStatus) &&
                                donHang.getTenSanPham().toLowerCase().contains(keyword.toLowerCase())) {
                            filteredList.add(donHang);
                        }
                    }

                    donHangList.clear();
                    donHangList.addAll(filteredList);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không tìm thấy đơn hàng nào phù hợp với từ khóa: " + keyword, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DonHangDTO> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi tìm kiếm: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("HoaDonFrag", "Lỗi khi tìm kiếm " + t.getMessage(), t);
            }
        });
    }





    private void loadDonHang() {
        Log.d("HoaDonFrag", "Loading orders for user ID: " + userId);
        apiService.getDonHangByUser(userId).enqueue(new Callback<DonHangDTO>() {
            @Override
            public void onResponse(@NonNull Call<DonHangDTO> call, @NonNull Response<DonHangDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DonHangDTO.DonHang> newDonHangList = response.body().getData();
                    donHangList.clear();
                    donHangList.addAll(newDonHangList);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DonHangDTO> call, @NonNull Throwable t) {
                Log.e("HoaDonFrag", "Error loading orders: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Có lỗi xảy ra: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu với trạng thái: " + status, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DonHangDTO> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi tải dữ liệu: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("HoaDonFrag", "Lỗi khi tải dữ liệu " + t.getMessage(), t);

            }
        });
    }

}