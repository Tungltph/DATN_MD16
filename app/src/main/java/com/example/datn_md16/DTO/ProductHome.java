package com.example.datn_md16.DTO;

public class ProductHome {
    private String tenDienThoai; // Product name
    private String giaGoc; // Original price
    private String giamGia; // Discount price
    private String hinhAnh; // Image URL
    private float rating; // Rating

    public ProductHome(String tenDienThoai, String giaGoc, String giamGia, String hinhAnh, float rating) {
        this.tenDienThoai = tenDienThoai;
        this.giaGoc = giaGoc;
        this.giamGia = giamGia;
        this.hinhAnh = hinhAnh;
        this.rating = rating;
    }

    public String getTenDienThoai() {
        return tenDienThoai;
    }

    public void setTenDienThoai(String tenDienThoai) {
        this.tenDienThoai = tenDienThoai;
    }

    public String getGiaGoc() {
        return giaGoc;
    }

    public void setGiaGoc(String giaGoc) {
        this.giaGoc = giaGoc;
    }

    public String getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(String giamGia) {
        this.giamGia = giamGia;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
