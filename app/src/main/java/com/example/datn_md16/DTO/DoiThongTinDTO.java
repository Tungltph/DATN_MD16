package com.example.datn_md16.DTO;

import com.google.gson.annotations.SerializedName;

public class DoiThongTinDTO {
    @SerializedName("hoTen")  // Ensure this matches the key in the JSON
    private String hoTen;

    @SerializedName("sdt")  // Ensure this matches the key in the JSON
    private String sdt;

    public DoiThongTinDTO(String hoTen, String sdt) {
        this.hoTen = hoTen;
        this.sdt = sdt;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
