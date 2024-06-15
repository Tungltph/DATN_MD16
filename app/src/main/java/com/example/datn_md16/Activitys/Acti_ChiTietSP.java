package com.example.datn_md16.Activitys;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.datn_md16.R;

public class Acti_ChiTietSP extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productPrice;
    private TextView tvGiam, tvTang, tvKQ;
    private int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);

        // Thiết lập Toolbar và hiển thị nút back
        Toolbar toolbar = findViewById(R.id.toolbarChiTietSP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Hiển thị nút back
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Đặt tiêu đề cho Toolbar từ chuỗi trong strings.xml
        setTitle(getString(R.string.toolbarChiTietSP_title));

        // Khởi tạo các view
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);

        tvGiam = findViewById(R.id.tvGiam);
        tvTang = findViewById(R.id.tvTang);
        tvKQ = findViewById(R.id.tvKQ);

        // Thiết lập sự kiện cho tvGiam
        tvGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 0) {
                    quantity--;
                    tvKQ.setText(String.valueOf(quantity));
                }
            }
        });

        // Thiết lập sự kiện cho tvTang
        tvTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                tvKQ.setText(String.valueOf(quantity));
            }
        });

        // Cập nhật dữ liệu sản phẩm (ví dụ)
//        productName.setText("Samsung Galaxy Z Flip 5");
//        productPrice.setText("Giá: 6,150,000đ");
        // Cập nhật hình ảnh sản phẩm (nếu có)
        // productImage.setImageResource(R.drawable.img_1); // Uncomment and set your image resource

    }

    // Xử lý sự kiện khi nút back trên Toolbar được nhấn
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Xử lý khi nhấn nút back trên Toolbar
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
