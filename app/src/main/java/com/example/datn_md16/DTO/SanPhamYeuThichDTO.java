package com.example.datn_md16.DTO;

import com.google.gson.annotations.SerializedName;

public class SanPhamYeuThichDTO {
    private String _id;
    private String id_sanPham; // Giữ nguyên là String
    private String id_user; // Giữ nguyên là String
    private ProductHome sanPham; // Thêm trường này để lưu thông tin sản phẩm

    public SanPhamYeuThichDTO(String id_sanPham, String id_user) {
        this.id_sanPham = id_sanPham;
        this.id_user = id_user;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_sanPham() {
        return id_sanPham;
    }

    public void setId_sanPham(String id_sanPham) {
        this.id_sanPham = id_sanPham;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public ProductHome getSanPham() {
        return sanPham;
    }

    public void setSanPham(ProductHome sanPham) {
        this.sanPham = sanPham;
    }
}

