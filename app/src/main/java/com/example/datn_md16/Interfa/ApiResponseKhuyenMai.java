package com.example.datn_md16.Interfa;

import com.example.datn_md16.DTO.KhuyenMai;

import java.util.List;

public class ApiResponseKhuyenMai {
    private int status;
    private String msg;
    private List<KhuyenMai> data;

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public List<KhuyenMai> getData() {
        return data;
    }
}