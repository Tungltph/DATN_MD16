package com.example.datn_md16.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.GioHangAdapter;
import com.example.datn_md16.DTO.DiaChiDTO;
import com.example.datn_md16.DTO.GioHangDTO;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.StreamHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class Acti_GioHang extends AppCompatActivity implements GioHangAdapter.OnTotalPriceChangeListener {
    private TextView totalPriceTextView, tvsoLuongGioHang;
    private GioHangAdapter adapter;
    private List<GioHangDTO> gioHangList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarGioHang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.toolbarGioHang));
        // Khởi tạo RecyclerView và Adapter
        RecyclerView recyclerView = findViewById(R.id.rcGioHang);
        totalPriceTextView = findViewById(R.id.totalPrice);
        tvsoLuongGioHang = findViewById(R.id.tvsoLuongGioHang);
        Button btnthanhtoan = findViewById(R.id.btnthanhtoan);
        btnthanhtoan.setOnClickListener(v -> processPayment());

        Retrofit retrofit = ApiClient.getClient();

        ApiService apiService = retrofit.create(ApiService.class);
        // Gọi API và lấy dữ liệu
        apiService.getGioHang().enqueue(new Callback<List<GioHangDTO>>() {
            @Override
            public void onResponse(Call<List<GioHangDTO>> call, Response<List<GioHangDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    gioHangList = response.body();
//
//        // Tính số lượng sản phẩm trong giỏ hàng
                   int itemCount = gioHangList.size();
                   tvsoLuongGioHang.setText(String.valueOf(itemCount));


                    gioHangList = response.body();
                    adapter = new GioHangAdapter(gioHangList, Acti_GioHang.this);
                    adapter.setOnTotalPriceChangeListener(Acti_GioHang.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Acti_GioHang.this));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(Acti_GioHang.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<GioHangDTO>> call, Throwable t) {
                Toast.makeText(Acti_GioHang.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void processPayment() {
        if (gioHangList == null || gioHangList.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lọc các sản phẩm đã được chọn
        List<GioHangDTO> selectedItems = new ArrayList<>();
        for (GioHangDTO item : gioHangList) {
            if (item.isChecked()) {
                selectedItems.add(item);
            }
        }

        if (selectedItems.isEmpty()) {
            Toast.makeText(this, "Chưa chọn sản phẩm nào để thanh toán", Toast.LENGTH_SHORT).show();
            return;
        }

        // Khởi tạo Retrofit và ApiService
        Retrofit retrofit = ApiClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);

        // Lấy địa chỉ đã chọn (nếu cần)
        DiaChiDTO diaChi = getSelectedAddress();

        // Khởi tạo Intent và truyền dữ liệu
        Intent intent = new Intent(Acti_GioHang.this, Acti_ThanhToan.class);
        intent.putParcelableArrayListExtra("selectedItems", new ArrayList<>(selectedItems));

        if (diaChi != null) {
            intent.putExtra("selectedAddress", diaChi);
        }

        startActivity(intent);
    }



    private DiaChiDTO getSelectedAddress() {
        // Trả về địa chỉ đã chọn, ví dụ như từ một danh sách địa chỉ hoặc một cơ sở dữ liệu
        return new DiaChiDTO("Tên Địa Chỉ", "Số điện thoại", "Địa chỉ đầy đủ","");
    }



    @Override
    public void onTotalPriceChanged(int totalPrice) {

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
        numberFormat.setGroupingUsed(true);
        String formattedPrice = numberFormat.format(totalPrice);
        totalPriceTextView.setText("Tổng thanh toán:\n " +"₫"+ formattedPrice);

        if (adapter != null) {
            int itemCount = adapter.getItemCount();
            tvsoLuongGioHang.setText(String.valueOf(itemCount));
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}