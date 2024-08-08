package com.example.datn_md16.Fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.datn_md16.Activitys.Acti_GioHang;
import com.example.datn_md16.Activitys.Acti_Oppo;
import com.example.datn_md16.Activitys.Acti_Samsung;
import com.example.datn_md16.Activitys.Acti_TimKiem;
import com.example.datn_md16.Activitys.Acti_Xiaomi;
import com.example.datn_md16.Activitys.Acti_vivo;
import com.example.datn_md16.Activitys.Acty_iphone;
import com.example.datn_md16.Adapter.BannerAdapter;
import com.example.datn_md16.Adapter.HotItemAdapter;
import com.example.datn_md16.Adapter.NewItemAdapter;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.R;
import com.example.datn_md16.Interfa.ApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFrag extends Fragment {

    private ViewPager2 bannerViewPager;
    private RecyclerView rvHotProducts;
    private RecyclerView rvNewProducts;
    private ImageView idGioHangHome;
    private List<String> bannerUrls;
    private HotItemAdapter hotItemAdapter;
    private NewItemAdapter newItemAdapter;
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;

    TextView tvSearchHome;
    private TextView btnip, btnss, btnvv, btnxm, btnop;
    private void updateButtonStyles(TextView selectedButton) {
        // Danh sách các nút trạng thái
        TextView[] buttons = {btnip, btnss, btnvv, btnxm, btnop};

        // Cập nhật style cho các nút
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




    private static final String BASE_URL = "http://192.168.1.8:3000/api/sanPham/"; // Thay thế bằng URL thực tế của bạn

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);
        bannerViewPager = view.findViewById(R.id.bannerViewPager);
        rvHotProducts = view.findViewById(R.id.rvHotProducts);
        rvNewProducts = view.findViewById(R.id.rvNewProducts);
        idGioHangHome = view.findViewById(R.id.idGioHangHome);
        btnxm = view.findViewById(R.id.btnXiaomi);
        btnip = view.findViewById(R.id.btnIphone);
        btnop = view.findViewById(R.id.btnOppo);
        btnss = view.findViewById(R.id.btnss);
        btnvv = view.findViewById(R.id.btnVivo);
        bannerUrls = new ArrayList<>();
        bannerUrls.add("https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone_15_pro_max_256gb_-_2_1_2.png");
        bannerUrls.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHAl5IVkr-viQsGd4EtN6fVXfKY24u25Fc_w&s");
        bannerUrls.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcREeIeaKYAgVyTZWDs7Y6Dauu_QHLUDMjBPXA&s");

        BannerAdapter bannerAdapter = new BannerAdapter(getContext(), bannerUrls);
        bannerViewPager.setAdapter(bannerAdapter);

        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == bannerUrls.size()) {
                    currentPage = 0;
                }
                bannerViewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000); // 3000ms = 3s
            }

        };
        handler.postDelayed(runnable, 3000);




        idGioHangHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Acti_GioHang.class);
                startActivity(intent);
            }
        });
        btnip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Acty_iphone.class);
                startActivity(intent);
                updateButtonStyles(btnip);
            }
        });
        btnop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Acti_Oppo.class);
                startActivity(intent);
                updateButtonStyles(btnop);
            }
        });
        btnss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Acti_Samsung.class);
                startActivity(intent);
                updateButtonStyles(btnss);
            }
        });
        btnvv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Acti_vivo.class);
                startActivity(intent);
                updateButtonStyles(btnvv);
            }
        });
        btnxm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Acti_Xiaomi.class);
                startActivity(intent);
                updateButtonStyles(btnxm);
            }
        });

        rvHotProducts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvNewProducts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        fetchHotProducts();
        fetchNewProducts();

        tvSearchHome = view.findViewById(R.id.tvSearchHome);
        tvSearchHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Acti_TimKiem.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void fetchHotProducts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.8:3000/api/sanPham/hot/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<ProductHome>> call = apiService.getHotProducts();
        call.enqueue(new Callback<List<ProductHome>>() {
            @Override
            public void onResponse(Call<List<ProductHome>> call, Response<List<ProductHome>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductHome> hotProductList = response.body();
                    hotItemAdapter = new HotItemAdapter(getContext(), hotProductList);
                    rvHotProducts.setAdapter(hotItemAdapter);
                } else {
                    Toast.makeText(getContext(), "Không thể lấy dữ liệu sản phẩm hot từ server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductHome>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối khi lấy sản phẩm hot", Toast.LENGTH_SHORT).show();
                Log.e("HomeFragment", "Error: " + t.getMessage());
            }
        });
    }

    private void fetchNewProducts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<ProductHome>> call = apiService.getNewProducts();
        call.enqueue(new Callback<List<ProductHome>>() {
            @Override
            public void onResponse(Call<List<ProductHome>> call, Response<List<ProductHome>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductHome> newProductList = response.body();
                    // Lọc sản phẩm có trạng thái là true
                    List<ProductHome> filteredNewProductList = newProductList.stream()
                            .filter(ProductHome::isTrangThai) // Sử dụng method reference
                            .collect(Collectors.toList());
                    newItemAdapter = new NewItemAdapter(getContext(), filteredNewProductList);
                    rvNewProducts.setAdapter(newItemAdapter);
                } else {
                    Toast.makeText(getContext(), "Không thể lấy dữ liệu sản phẩm mới từ server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductHome>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối khi lấy sản phẩm mới", Toast.LENGTH_SHORT).show();
                Log.e("HomeFragment", "Error: " + t.getMessage());
            }
        });

    }
}
