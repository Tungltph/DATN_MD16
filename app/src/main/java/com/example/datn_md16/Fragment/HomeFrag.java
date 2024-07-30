package com.example.datn_md16.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Activitys.Acti_GioHang;
import com.example.datn_md16.Activitys.Acti_Oppo;
import com.example.datn_md16.Activitys.Acti_Samsung;
import com.example.datn_md16.Activitys.Acti_TimKiem;
import com.example.datn_md16.Activitys.Acti_Xiaomi;
import com.example.datn_md16.Activitys.Acti_vivo;
import com.example.datn_md16.Activitys.Acty_iphone;
import com.example.datn_md16.Adapter.HotItemAdapter;
import com.example.datn_md16.Adapter.NewItemAdapter;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.R;
import com.example.datn_md16.Interfa.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFrag extends Fragment {

    private RecyclerView rvHotProducts, rvNewProducts;
    private HotItemAdapter hotItemAdapter;
    private NewItemAdapter newItemAdapter;
    ImageView idGioHangHome;

    private Button btnip, btnss, btnvv, btnxm, btnop;
    TextView tvSearchHome;

    private static final String BASE_URL = "http://192.168.9.104:3000/api/sanPham/"; // Thay thế bằng URL thực tế của bạn

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home, container, false);

        rvHotProducts = view.findViewById(R.id.rvHotProducts);
        rvNewProducts = view.findViewById(R.id.rvNewProducts);
        idGioHangHome = view.findViewById(R.id.idGioHangHome);
        btnxm = view.findViewById(R.id.btnXiaomi);
        btnip = view.findViewById(R.id.btnIphone);
        btnop = view.findViewById(R.id.btnOppo);
        btnss = view.findViewById(R.id.btnss);
        btnvv = view.findViewById(R.id.btnVivo);

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
            }
        });
        btnop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Acti_Oppo.class);
                startActivity(intent);
            }
        });
        btnss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Acti_Samsung.class);
                startActivity(intent);
            }
        });
        btnvv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Acti_vivo.class);
                startActivity(intent);
            }
        });
        btnxm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Acti_Xiaomi.class);
                startActivity(intent);
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
                .baseUrl(BASE_URL)
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
                    newItemAdapter = new NewItemAdapter(getContext(), newProductList);
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
