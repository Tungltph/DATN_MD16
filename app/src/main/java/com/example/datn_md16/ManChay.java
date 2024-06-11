package com.example.datn_md16;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.datn_md16.Fragment.HoaDonFrag;
import com.example.datn_md16.Fragment.HomeFrag;
import com.example.datn_md16.Fragment.NguoiDungFrag;
import com.example.datn_md16.Fragment.ThongBaoFrag;
import com.example.datn_md16.Fragment.YeuThichFrag;
import com.example.datn_md16.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ManChay extends AppCompatActivity {
    Toolbar mtoolbar001;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_chay);

        mtoolbar001 = findViewById(R.id.mtoolbar001);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        setSupportActionBar(mtoolbar001);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                handleNavigation(item.getItemId());
                return true;
            }
        });

        if (savedInstanceState == null) {
            handleNavigation(R.id.bottom_navigation);
            mtoolbar001.setTitle("Màn chính");
        }
    }

    private void handleNavigation(int itemId) {
        Fragment fr;
        String title = "";

        if (itemId == R.id.nav_home) {
            fr = new HomeFrag();
            title = "Màn chính";
        } else if (itemId == R.id.nav_hoaDon) {
            fr = new HoaDonFrag();
            title = "Hóa đơn";
        } else if (itemId == R.id.nav_thongBao) {
            fr = new ThongBaoFrag();
            title = "Thông báo";
        } else if (itemId == R.id.nav_yeuThich) {
            fr = new YeuThichFrag();
            title = "Yêu thích";
        } else if (itemId == R.id.nav_nguoiDung) {
            fr = new NguoiDungFrag();
            title = "Người Dùng";
        } else {
            fr = new HomeFrag();
            title = "Màn chính";
        }

        mtoolbar001.setTitle(title);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_container001, fr)
                .commit();
    }
}
