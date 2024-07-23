package com.example.datn_md16.DTO;

import java.util.List;

public class SanPhamDTO {
    private String _id;
    private String tenDienThoai; // Cập nhật theo JSON bạn cung cấp
    private String hinhAnh;
    private List<MauSchemaDTO> mauSchema; // Trường để chứa giá

    // Getter và Setter
    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getTenSanPham() {
        return tenDienThoai;
    }

    public void setTenSanPham(String tenDienThoai) {
        this.tenDienThoai = tenDienThoai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public List<MauSchemaDTO> getMauSchema() {
        return mauSchema;
    }

    public void setMauSchema(List<MauSchemaDTO> mauSchema) {
        this.mauSchema = mauSchema;
    }

    public static class MauSchemaDTO {
        private String mau;
        private int giaTien; // Cập nhật theo JSON

        public String getMau() {
            return mau;
        }

        public void setMau(String mau) {
            this.mau = mau;
        }

        public int getGiaTien() {
            return giaTien;
        }

        public void setGiaTien(int giaTien) {
            this.giaTien = giaTien;
        }
    }
}

