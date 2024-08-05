package com.example.datn_md16.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.datn_md16.Fragment.ChoXacNhanFragment;
import com.example.datn_md16.Fragment.DaGiaoHangFragment;
import com.example.datn_md16.Fragment.DaHuyFragment;
import com.example.datn_md16.Fragment.DangGiaoHangFragment;
import com.example.datn_md16.Fragment.DangXuLyFragment;


public class Frag_Adapter extends FragmentStateAdapter {
    int soLuongTab = 5;
    ChoXacNhanFragment choXacNhanFragment;
    DaGiaoHangFragment daGiaoHangFragment;
    DaHuyFragment daHuyFragment;
    DangGiaoHangFragment dangGiaoHangFragment;
    DangXuLyFragment dangXuLyFragment;
    public Frag_Adapter(@NonNull Fragment fragment) {
        super(fragment);
        choXacNhanFragment = new ChoXacNhanFragment();
        daHuyFragment = new DaHuyFragment();
        daGiaoHangFragment = new DaGiaoHangFragment();
        dangGiaoHangFragment = new DangGiaoHangFragment();
        dangXuLyFragment = new DangXuLyFragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch ( position ){
            case 0: return choXacNhanFragment;
            case 1 : return dangXuLyFragment;
            case 2 : return dangGiaoHangFragment;
            case 3 : return daGiaoHangFragment;
            case 4 : return daHuyFragment;
            default:return choXacNhanFragment;
        }


    }

    @Override
    public int getItemCount() {
        return soLuongTab;
    }
}
