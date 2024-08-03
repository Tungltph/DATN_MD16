package com.example.datn_md16.DTO;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AccountResponse {
    @SerializedName("status")
    public int status;

    @SerializedName("msg")
    public String msg;

    @SerializedName("data")
    public List<com.example.datn_md16.DTO.AccountResponse.Account> data;

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
    }



}