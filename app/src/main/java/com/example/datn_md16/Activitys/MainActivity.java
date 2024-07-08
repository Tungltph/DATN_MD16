package com.example.datn_md16.Activitys;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.datn_md16.Fragment.HoaDonFrag;
import com.example.datn_md16.Fragment.HomeFrag;
import com.example.datn_md16.Fragment.NguoiDungFrag;
import com.example.datn_md16.Fragment.ThongBaoFrag;
import com.example.datn_md16.Fragment.YeuThichFrag;
import com.example.datn_md16.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    selectedFragment = new HomeFrag();
                } else if (itemId == R.id.nav_hoaDon) {
                    selectedFragment = new HoaDonFrag();
                } else if (itemId == R.id.nav_thongBao) {
                    selectedFragment = new ThongBaoFrag();
                } else if (itemId == R.id.nav_yeuThich) {
                    selectedFragment = new YeuThichFrag();
                } else if (itemId == R.id.nav_nguoiDung) {
                    selectedFragment = new NguoiDungFrag();
                }

                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                    return true;
                }
                return false;
            }
        });

        // Load the default fragment
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}



