package com.example.datn_md16.Interfa;

import com.example.datn_md16.DTO.SanPhamYeuThichDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SanPhamYeuThichResponse {
    private List<SanPhamYeuThichDTO> data;

    public List<SanPhamYeuThichDTO> getData() {
        return data;
    }

    public void setData(List<SanPhamYeuThichDTO> data) {
        this.data = data;
    }
}


