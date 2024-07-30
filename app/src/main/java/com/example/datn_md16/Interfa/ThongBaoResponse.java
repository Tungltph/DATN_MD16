package com.example.datn_md16.Interfa;

import com.example.datn_md16.DTO.ThongBaoDTO;

import java.util.List;

public class ThongBaoResponse {
    private String msg;
    private List<ThongBaoDTO> data;

    // Getters và setters
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ThongBaoDTO> getData() {
        return data;
    }

    public void setData(List<ThongBaoDTO> data) {
        this.data = data;
    }
}
