package com.example.datn_md16.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datn_md16.R;

public class activitioSlash extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mhchao);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Chuyển sang màn hình đăng nhập
                Intent intent = new Intent(activitioSlash.this, DangNhap.class);
                startActivity(intent);
                finish(); // Kết thúc SplashActivity để không quay lại được
            }
        }, SPLASH_TIME_OUT);
    }


}
