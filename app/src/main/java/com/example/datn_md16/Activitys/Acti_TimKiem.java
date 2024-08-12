package com.example.datn_md16.Activitys;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_md16.Adapter.TimKiemAdapter;
import com.example.datn_md16.DTO.ProductHome;
import com.example.datn_md16.DTO.TimKiemDTO;
import com.example.datn_md16.Interfa.ApiService;
import com.example.datn_md16.Interface.ApiClient;
import com.example.datn_md16.R;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Acti_TimKiem extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TimKiemAdapter adapter;
    private Spinner spinnerGia;
    private TextView btnMoiNhat,btnBanChay,btnLienQuan;
    private EditText edtSearch;
    private TextView noResultsTextView;

    private void updateButtonStyles(TextView selectedButton) {
        // Danh sách các nút trạng thái
        TextView[] buttons = {btnMoiNhat,btnBanChay,btnLienQuan};

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

    private void updateSpinnerStyle() {
        // Đổi màu chữ và màu nền của Spinner
        spinnerGia.setBackgroundColor(getResources().getColor(R.color.red));
        // Cập nhật màu chữ cho Spinner bằng cách tạo một ArrayAdapter mới
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_items_gia, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGia.setAdapter(spinnerAdapter);
    }

    private void resetSpinnerStyle() {
        // Đổi màu chữ và màu nền của Spinner về trạng thái ban đầu
        spinnerGia.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        // Cập nhật màu chữ cho Spinner bằng cách tạo một ArrayAdapter mới
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_items_gia, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGia.setAdapter(spinnerAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        // Thiết lập Toolbar và hiển thị nút back
        Toolbar toolbar = findViewById(R.id.toolbarTimKiem);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiển thị nút back
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Đặt tiêu đề cho Toolbar từ chuỗi trong strings.xml
        setTitle(getString(R.string.toolbarTimKiem));

        recyclerView = findViewById(R.id.rcv_TimKiem);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        noResultsTextView = findViewById(R.id.tv_no_results); // Khởi tạo TextView thông báo

        adapter = new TimKiemAdapter(getApplicationContext(), noResultsTextView);
        recyclerView.setAdapter(adapter);

        edtSearch = findViewById(R.id.edtSearch);
        spinnerGia = findViewById(R.id.spinner_gia);
        btnMoiNhat = findViewById(R.id.btnMoiNhat);
        btnBanChay = findViewById(R.id.btnBanChay);
        btnLienQuan = findViewById(R.id.btnLienQuan);

        // Thiết lập Adapter cho Spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_items_gia, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGia.setAdapter(spinnerAdapter);

        // Lắng nghe sự thay đổi của EditText để tìm kiếm
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filterData(s.toString()); // Gọi filterData với chuỗi tìm kiếm hiện tại
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // Xử lý sự kiện nhấn vào biểu tượng xóa
        edtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edtSearch.getRight() - edtSearch.getCompoundDrawables()[2].getBounds().width())) {
                        // Xóa nội dung của EditText
                        edtSearch.setText("");
                        // Gọi phương thức lọc lại dữ liệu
                        adapter.filterData("");
                        return true;
                    }
                }
                return false;
            }
        });

        // Lắng nghe sự kiện chạm vào Spinner
        spinnerGia.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    updateSpinnerStyle();
                }
                return false;
            }
        });

        spinnerGia.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    resetSpinnerStyle();
                }
            }
        });


        // Lắng nghe sự kiện chọn của Spinner
        spinnerGia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    // Giá từ thấp đến cao
                    adapter.sortDataList(true);
                } else if (position == 1) {
                    // Giá từ cao đến thấp
                    adapter.sortDataList(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì khi không chọn gì
            }
        });

        // Lắng nghe sự kiện click của Button "Mới nhất"
        btnMoiNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load dữ liệu từ API và sắp xếp lại theo mặc định (trên MongoDB)
                loadAndSortDefaultData();
                updateButtonStyles(btnMoiNhat);
            }
        });

        btnLienQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonStyles(btnLienQuan);
            }
        });

        btnBanChay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load dữ liệu từ API và sắp xếp lại theo mặc định (trên MongoDB)
                TopBanChay();
                updateButtonStyles(btnBanChay);
            }
        });


        // Khởi tạo Retrofit
        Retrofit retrofit = ApiClient.getClient();

        // Tạo đối tượng dịch vụ API từ Retrofit
        ApiService apiService = retrofit.create(ApiService.class);

        // Gọi API để lấy dữ liệu
        Call<List<TimKiemDTO>> call = apiService.getTimKiem();
        call.enqueue(new Callback<List<TimKiemDTO>>() {
            @Override
            public void onResponse(Call<List<TimKiemDTO>> call, Response<List<TimKiemDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TimKiemDTO> timKiemDTOList = response.body();
                    adapter.setData(timKiemDTOList); // Đặt dữ liệu vào adapter để hiển thị trên RecyclerView
                    adapter.sortDataList(true); // Sắp xếp theo giá từ thấp đến cao ngay sau khi nhận dữ liệu
                } else {
                    Toast.makeText(Acti_TimKiem.this, "Không thể lấy dữ liệu từ server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TimKiemDTO>> call, Throwable t) {
                Toast.makeText(Acti_TimKiem.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("Acti_TimKiem", "Error: " + t.getMessage());
            }
        });
    }

    private void TopBanChay() {
        Retrofit retrofit = ApiClient.getClient();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<TimKiemDTO>> call = apiService.getBanChay();
        call.enqueue(new Callback<List<TimKiemDTO>>() {
            @Override
            public void onResponse(Call<List<TimKiemDTO>> call, Response<List<TimKiemDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TimKiemDTO> timKiemDTOList = response.body();
                    // Thực hiện sắp xếp theo mặc định (trên MongoDB)
                    // Ví dụ: Collections.sort(timKiemDTOList, new YourDefaultComparator());
                    // Sau khi sắp xếp, cập nhật dữ liệu vào adapter:
                    adapter.setData(timKiemDTOList);
                } else {
                    Toast.makeText(Acti_TimKiem.this, "Không thể lấy dữ liệu từ server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TimKiemDTO>> call, Throwable t) {
                Toast.makeText(Acti_TimKiem.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("Acti_TimKiem", "Error: " + t.getMessage());
            }
        });
    }

    // Phương thức để load dữ liệu từ API và sắp xếp theo mặc định (trên MongoDB)
    private void loadAndSortDefaultData() {
        Retrofit retrofit = ApiClient.getClient();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<TimKiemDTO>> call = apiService.getTimKiem();
        call.enqueue(new Callback<List<TimKiemDTO>>() {
            @Override
            public void onResponse(Call<List<TimKiemDTO>> call, Response<List<TimKiemDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TimKiemDTO> timKiemDTOList = response.body();
                    // Thực hiện sắp xếp theo mặc định (trên MongoDB)
                    // Ví dụ: Collections.sort(timKiemDTOList, new YourDefaultComparator());
                    // Sau khi sắp xếp, cập nhật dữ liệu vào adapter:
                    adapter.setData(timKiemDTOList);
                } else {
                    Toast.makeText(Acti_TimKiem.this, "Không thể lấy dữ liệu từ server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TimKiemDTO>> call, Throwable t) {
                Toast.makeText(Acti_TimKiem.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("Acti_TimKiem", "Error: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Xử lý khi nhấn nút back trên Toolbar
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
