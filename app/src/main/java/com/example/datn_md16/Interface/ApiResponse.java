package com.example.datn_md16.Interface;

import com.example.datn_md16.DTO.DiaChiDTO;
import java.util.List;

public class ApiResponse {
    private int status;
    private String msg;
    private List<DiaChiDTO> data;

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

    public List<DiaChiDTO> getData() {
        return data;
    }

    public void setData(List<DiaChiDTO> data) {
        this.data = data;
    }
}
