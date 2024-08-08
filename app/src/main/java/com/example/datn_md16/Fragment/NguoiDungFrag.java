package com.example.datn_md16.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.datn_md16.Activitys.Acti_DiaChi;
import com.example.datn_md16.Activitys.Acti_KhuyenMai;
import com.example.datn_md16.Activitys.Acti_doiPass;
import com.example.datn_md16.Activitys.Acti_hoSoCuaToi;
import com.example.datn_md16.Activitys.DangNhap;
import com.example.datn_md16.R;

public class NguoiDungFrag extends Fragment {

    private static final String PREFS_NAME = "user_prefs";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_ID = "user_id";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_nguoi_dung, container, false);

        TextView tvhoSo = view.findViewById(R.id.tvHoSo);
        TextView tvDoiPass = view.findViewById(R.id.tvDoiPass);
        TextView tvDiaChi = view.findViewById(R.id.tvDiaChi);
        TextView tvDsKhuyenMai = view.findViewById(R.id.tvKhuyenMai);
        TextView tvWelcome = view.findViewById(R.id.tvWelcome);
        TextView btnLogout = view.findViewById(R.id.btnLogout);



        // Lấy tên người dùng từ SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString(KEY_USER_NAME, "User");

        // Hiển thị tên người dùng
        tvWelcome.setText("Xin chào: " + userName);

        // Sự kiện chuyển màn hình
        tvhoSo.setOnClickListener(v -> startActivity(new Intent(requireContext(), Acti_hoSoCuaToi.class)));

        tvDiaChi.setOnClickListener(v -> startActivity(new Intent(requireContext(), Acti_DiaChi.class)));

        tvDsKhuyenMai.setOnClickListener(v -> startActivity(new Intent(requireContext(), Acti_KhuyenMai.class)));

        tvDoiPass.setOnClickListener(v -> startActivity(new Intent(requireContext(), Acti_doiPass.class)));

        btnLogout.setOnClickListener(v -> {
            // Tạo hộp thoại xác nhận đăng xuất
            new AlertDialog.Builder(getContext())
                    .setTitle("Xác nhận đăng xuất")
                    .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                    .setPositiveButton("Đăng xuất", (dialog, which) -> {
                        // Xóa dữ liệu đăng nhập
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(KEY_USER_ID);  // Xóa ID người dùng
                        editor.remove(KEY_USER_NAME);  // Xóa tên người dùng
                        editor.apply();

                        // Hiển thị thông báo cho người dùng
                        Toast.makeText(getContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();

                        // Chuyển hướng về màn hình đăng nhập
                        Intent intent = new Intent(getContext(), DangNhap.class);
                        startActivity(intent);
                        dialog.dismiss();  // Đóng hoạt động hiện tại
                    })
                    .setNegativeButton("Hủy", null)  // Đóng hộp thoại nếu người dùng nhấn Hủy
                    .show();
        });



        return view;
    }
}
