package com.example.datn_md16.DTO;

import com.google.gson.annotations.SerializedName;

public class TimKiemDTO {
    @SerializedName("tenDienThoai")
    private String tenSanPham;

    @SerializedName("giamGia")
    private String giamGia;

    @SerializedName("giaGoc")
    private String giaGoc;

    @SerializedName("hinhAnh")
    private String hinhAnh;

    public TimKiemDTO(String tenSanPham, String giamGia, String giaGoc, String hinhAnh) {
        this.tenSanPham = tenSanPham;
        this.giamGia = giamGia;
        this.giaGoc = giaGoc;
        this.hinhAnh = hinhAnh;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(String giamGia) {
        this.giamGia = giamGia;
    }

    public String getGiaGoc() {
        return giaGoc;
    }

    public void setGiaGoc(String giaGoc) {
        this.giaGoc = giaGoc;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
