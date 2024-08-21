package com.example.datn_md16.Activitys;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datn_md16.Adapter.IphoneAdapter;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.R;
import com.example.datn_md16.Interfa.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Acti_Samsung extends AppCompatActivity {

    private RecyclerView recyclerView;
    private IphoneAdapter adapter;
    private EditText edtTimKiemSamSung;

    private TextView btnip, btnss, btnvv, btnxm, btnop;
    private TextView noResultsTextView;

    private static final String HANG_SX_ID = "6675aa0fff75b8dfd1e641e6"; // Oppo's manufacturer ID

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samsung);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        noResultsTextView = findViewById(R.id.tv_no_resultsSamSUng); // Initialize TextView for no results

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarSamsung);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
        setTitle(getString(R.string.toolbarOppo));

        // Initialize Buttons
        btnxm = findViewById(R.id.btnXiaomi);
        btnip = findViewById(R.id.btnIphone);
        btnop = findViewById(R.id.btnOppo);
        btnss = findViewById(R.id.btnss);
        btnvv = findViewById(R.id.btnVivo);
        edtTimKiemSamSung = findViewById(R.id.edtTimKiemSamsung);

        // Initialize Adapter
        adapter = new IphoneAdapter(this, new ArrayList<>(), noResultsTextView);
        recyclerView.setAdapter(adapter);

        // Set up TextWatcher for search input
        edtTimKiemSamSung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filterData(s.toString()); // Call filterData with current search string
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        edtTimKiemSamSung.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edtTimKiemSamSung.getRight() - edtTimKiemSamSung.getCompoundDrawables()[2].getBounds().width())) {
                        // Xóa nội dung của EditText
                        edtTimKiemSamSung.setText("");
                        // Gọi phương thức lọc lại dữ liệu
                        adapter.filterData("");
                        return true;
                    }
                }
                return false;
            }
        });

        // Set up Button Click Listeners
        btnip.setOnClickListener(v -> navigateTo(Acty_iphone.class, btnip));
        btnop.setOnClickListener(v -> navigateTo(Acti_Oppo.class, btnop));
        btnss.setOnClickListener(v -> navigateTo(Acti_Samsung.class, btnss));
        btnvv.setOnClickListener(v -> navigateTo(Acti_vivo.class, btnvv));
        btnxm.setOnClickListener(v -> navigateTo(Acti_Xiaomi.class, btnxm));

        updateButtonStyles(btnss);
        // Fetch data from API
        fetchData();
    }

    private void navigateTo(Class<?> targetActivity, TextView selectedButton) {
        Intent intent = new Intent(getApplicationContext(), targetActivity);
        startActivity(intent);
        updateButtonStyles(selectedButton);
    }

    private void updateButtonStyles(TextView selectedButton) {
        // List of status buttons
        TextView[] buttons = {btnip, btnss, btnvv, btnxm, btnop};

        // Update style for selected button
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

    private void fetchData() {
        Retrofit retrofit = ApiClient.getClient();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<ProductHome>> call = apiService.getProducts();

        call.enqueue(new Callback<List<ProductHome>>() {
            @Override
            public void onResponse(Call<List<ProductHome>> call, Response<List<ProductHome>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductHome> allProducts = response.body();
                    List<ProductHome> filteredProducts = filterProductsByHangSX(allProducts, HANG_SX_ID);
                    if (!filteredProducts.isEmpty()) {
                        adapter = new IphoneAdapter(Acti_Samsung.this, filteredProducts, noResultsTextView);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(Acti_Samsung.this, "Không có sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Acti_Samsung.this, "Không có sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductHome>> call, Throwable t) {
                Toast.makeText(Acti_Samsung.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<ProductHome> filterProductsByHangSX(List<ProductHome> productList, String hangSXId) {
        List<ProductHome> filteredList = new ArrayList<>();
        for (ProductHome product : productList) {
            if (hangSXId.equals(product.getIdHangSX())) {
                filteredList.add(product);
            }
        }
        return filteredList;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Handle back button on Toolbar
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
