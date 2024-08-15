package com.example.datn_md16.Interfa;

import com.example.datn_md16.DTO.DanhGiaReceiveDTO;

import java.util.List;

public class DanhGiaResponse {
    private int status;
    private String msg;
    private List<DanhGiaReceiveDTO> data;

    // Getter and Setter
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }

    public List<DanhGiaReceiveDTO> getData() { return data; }
    public void setData(List<DanhGiaReceiveDTO> data) { this.data = data; }
}

