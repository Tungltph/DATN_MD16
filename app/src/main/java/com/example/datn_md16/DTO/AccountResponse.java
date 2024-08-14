package com.example.datn_md16.DTO;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AccountResponse {
    @SerializedName("status")
    public int status;

    @SerializedName("msg")
    public String msg;

    @SerializedName("data")
    public List<AccountResponse.Account> data;

    public static class Account {
        @SerializedName("_id")
        public String _id;

        @SerializedName("taiKhoan")
        public String taiKhoan;

        @SerializedName("hoTen")
        public String hoTen;

        @SerializedName("matKhau")
        public String matKhau;

        @SerializedName("sdt")
        public String sdt;

        @SerializedName("tenQuyen")
        public String tenQuyen;

        @SerializedName("__v")
        public int v;

        @SerializedName("trangThai")
        public boolean isActive;

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

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

        public void setTenQuyen(String tenQuyen) {
            this.tenQuyen = tenQuyen;
        }

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }
    }





}