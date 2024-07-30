package com.example.datn_md16.DTO;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DonHangDTO {
    @SerializedName("status")
    private int status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private List<DonHang> data;

    // Getters and Setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DonHang> getData() {
        return data;
    }

    public void setData(List<DonHang> data) {
        this.data = data;
    }

    public static class DonHang {
        @SerializedName("_id")
        private String id;

        @SerializedName("idSP")
        private List<SanPham> sanPhamList;

        @SerializedName("soLuong")
        private int soLuong;

        @SerializedName("tongTien")
        private double tongTien;  // Changed to double

        @SerializedName("trangThaiThanhToan")
        private boolean trangThaiThanhToan;

        @SerializedName("ghiChu")
        private String ghiChu;

        @SerializedName("idKH")
        private KhachHang khachHang;

        @SerializedName("ngayDatHang")
        private String ngayDatHang;

        @SerializedName("ngayNhanHang")
        private String ngayNhanHang;

        @SerializedName("diaChiGiaoHang")
        private String diaChiGiaoHang;

        @SerializedName("trangThaiDonHang")
        private String trangThaiDonHang;

        @SerializedName("phuongThucThanhToan")
        private String phuongThucThanhToan;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<SanPham> getSanPhamList() {
            return sanPhamList;
        }

        public void setSanPhamList(List<SanPham> sanPhamList) {
            this.sanPhamList = sanPhamList;
        }

        public int getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(int soLuong) {
            this.soLuong = soLuong;
        }

        public double getTongTien() {
            return tongTien;
        }

        public void setTongTien(double tongTien) {
            this.tongTien = tongTien;
        }

        public boolean isTrangThaiThanhToan() {
            return trangThaiThanhToan;
        }

        public void setTrangThaiThanhToan(boolean trangThaiThanhToan) {
            this.trangThaiThanhToan = trangThaiThanhToan;
        }

        public String getGhiChu() {
            return ghiChu;
        }

        public void setGhiChu(String ghiChu) {
            this.ghiChu = ghiChu;
        }

        public KhachHang getKhachHang() {
            return khachHang;
        }

        public void setKhachHang(KhachHang khachHang) {
            this.khachHang = khachHang;
        }

        public String getNgayDatHang() {
            return ngayDatHang;
        }

        public void setNgayDatHang(String ngayDatHang) {
            this.ngayDatHang = ngayDatHang;
        }

        public String getNgayNhanHang() {
            return ngayNhanHang;
        }

        public void setNgayNhanHang(String ngayNhanHang) {
            this.ngayNhanHang = ngayNhanHang;
        }

        public String getDiaChiGiaoHang() {
            return diaChiGiaoHang;
        }

        public void setDiaChiGiaoHang(String diaChiGiaoHang) {
            this.diaChiGiaoHang = diaChiGiaoHang;
        }

        public String getTrangThaiDonHang() {
            return trangThaiDonHang;
        }

        public void setTrangThaiDonHang(String trangThaiDonHang) {
            this.trangThaiDonHang = trangThaiDonHang;
        }

        public String getPhuongThucThanhToan() {
            return phuongThucThanhToan;
        }

        public void setPhuongThucThanhToan(String phuongThucThanhToan) {
            this.phuongThucThanhToan = phuongThucThanhToan;
        }
    }

    public static class SanPham {
        @SerializedName("_id")
        private String id;

        @SerializedName("tenDienThoai")
        private String tenDienThoai;

        @SerializedName("hinhAnh")
        private String hinhAnh;

        @SerializedName("mauSchema")
        private List<MauSchema> mauSchema;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTenDienThoai() {
            return tenDienThoai;
        }

        public void setTenDienThoai(String tenDienThoai) {
            this.tenDienThoai = tenDienThoai;
        }

        public String getHinhAnh() {
            return hinhAnh;
        }

        public void setHinhAnh(String hinhAnh) {
            this.hinhAnh = hinhAnh;
        }

        public List<MauSchema> getMauSchema() {
            return mauSchema;
        }

        public void setMauSchema(List<MauSchema> mauSchema) {
            this.mauSchema = mauSchema;
        }
    }

    public static class MauSchema {
        @SerializedName("mau")
        private String mau;

        @SerializedName("soLuong")
        private int soLuong;

        @SerializedName("giaTien")
        private double giaTien;  // Changed to double

        // Getters and Setters
        public String getMau() {
            return mau;
        }

        public void setMau(String mau) {
            this.mau = mau;
        }

        public int getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(int soLuong) {
            this.soLuong = soLuong;
        }

        public double getGiaTien() {
            return giaTien;
        }

        public void setGiaTien(double giaTien) {
            this.giaTien = giaTien;
        }
    }

    public static class KhachHang {
        @SerializedName("_id")
        private String id;

        @SerializedName("tenQuyen")
        private String tenQuyen; // Ensure this matches the actual data if present

        @SerializedName("taiKhoan")
        private String taiKhoan;

        @SerializedName("hoTen")
        private String hoTen; // Ensure this matches the actual data if present

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTenQuyen() {
            return tenQuyen;
        }

        public void setTenQuyen(String tenQuyen) {
            this.tenQuyen = tenQuyen;
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
    }
}
