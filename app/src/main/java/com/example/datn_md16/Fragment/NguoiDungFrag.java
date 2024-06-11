package com.example.datn_md16.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.datn_md16.Activitys.Acti_DiaChi;
import com.example.datn_md16.Activitys.Acti_KhuyenMai;
import com.example.datn_md16.Activitys.Acti_doiPass;
import com.example.datn_md16.Activitys.Acti_hoSoCuaToi;
import com.example.datn_md16.R;

public class NguoiDungFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_nguoi_dung, container, false);

        TextView tvhoSo = view.findViewById(R.id.tvHoSo);
        TextView tvDoiPass = view.findViewById(R.id.tvDoiPass);
        TextView tvDiaChi = view.findViewById(R.id.tvDiaChi);
        TextView tvDsKhuyenMai = view.findViewById(R.id.tvKhuyenMai);


        // chuyển màn hồ sơ của tôi
        tvhoSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), Acti_hoSoCuaToi.class));
            }
        });

        // chuyển màn địa chỉ
        tvDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), Acti_DiaChi.class));
            }
        });

        //chuyển màn khuyến mại
        tvDsKhuyenMai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), Acti_KhuyenMai.class));
            }
        });

        //chuyển màn đổi pass
        tvDoiPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), Acti_doiPass.class));
            }
        });

        return view;
    }
}

