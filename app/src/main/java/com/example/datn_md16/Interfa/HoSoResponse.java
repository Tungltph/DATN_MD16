package com.example.datn_md16.Interfa;

import com.example.datn_md16.DTO.HoSoDTO;

public class HoSoResponse {
    private int status;
    private String msg;
    private HoSoDTO data; // Thay đổi tên trường tùy theo cấu trúc JSON

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HoSoDTO getData() {
        return data;
    }

    public void setData(HoSoDTO data) {
        this.data = data;
    }
}
