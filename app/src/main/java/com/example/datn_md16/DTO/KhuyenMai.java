package com.example.datn_md16.DTO;

import com.google.gson.annotations.SerializedName;

public class KhuyenMai {
    @SerializedName("_id")
    private String id;

    @SerializedName("ten")
    private String ten;

    @SerializedName("ngayBatDau")
    private String ngayBatDau;

    @SerializedName("ngayKetThuc")
    private String ngayKetThuc;

    @SerializedName("soLuong")
    private int soLuong;

    @SerializedName("giaKhoiDiem")
    private double giaKhoiDiem;

    @SerializedName("soLanApDung")
    private int soLanApDung;

    @SerializedName("trangThai")
    private boolean trangThai;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaKhoiDiem() {
        return giaKhoiDiem;
    }

    public void setGiaKhoiDiem(double giaKhoiDiem) {
        this.giaKhoiDiem = giaKhoiDiem;
    }

    public int getSoLanApDung() {
        return soLanApDung;
    }

    public void setSoLanApDung(int soLanApDung) {
        this.soLanApDung = soLanApDung;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
