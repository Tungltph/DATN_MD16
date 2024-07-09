package com.example.datn_md16.DTO;

import com.google.gson.annotations.SerializedName;

public class AccountRequest {

    @SerializedName("taiKhoan")
    private String taiKhoan;

    @SerializedName("hoTen")
    private String hoTen;

    @SerializedName("matKhau")
    private String matKhau;

    @SerializedName("sdt")
    private String sdt;

    @SerializedName("tenQuyen")
    private String tenQuyen = "User"; // Đặt mặc định là "User"

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTenQuyen() {
        return tenQuyen;
    }

    // Không cần setter cho tenQuyen để giữ giá trị mặc định "User"
}
