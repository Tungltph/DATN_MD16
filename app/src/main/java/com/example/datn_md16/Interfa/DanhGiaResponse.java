package com.example.datn_md16.Interfa;

import com.example.datn_md16.DTO.DanhGiaDTO;

import java.util.List;

public class DanhGiaResponse {
    private int status;
    private String msg;
    private List<DanhGiaDTO> data;

    // Getters and Setters
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

    public List<DanhGiaDTO> getData() {
        return data;
    }

    public void setData(List<DanhGiaDTO> data) {
        this.data = data;
    }
}
