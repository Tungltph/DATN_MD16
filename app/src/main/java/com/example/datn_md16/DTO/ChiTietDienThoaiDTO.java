package com.example.datn_md16.DTO;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ChiTietDienThoaiDTO {
    @SerializedName("_id")
    private String id;

    @SerializedName("tenDienThoai")
    private String tenDienThoai;

    @SerializedName("camera")
    private String camera;

    @SerializedName("cameraTruoc")
    private String cameraTruoc;

    @SerializedName("kichThuoc")
    private String kichThuoc;

    @SerializedName("cPU")
    private String cPU;

    @SerializedName("ram")
    private String ram;

    @SerializedName("sim")
    private String sim;

    @SerializedName("pin")
    private String pin;

    @SerializedName("heDieuHanh")
    private String heDieuHanh;

    @SerializedName("namSanXuat")
    private String namSanXuat;

    @SerializedName("congNgheManHinh")
    private String congNgheManHinh;

    @SerializedName("moTaThem")
    private String moTaThem;

    @SerializedName("hinhAnh")
    private String hinhAnh;

    @SerializedName("doPhanGiai")
    private String doPhanGiai;

    @SerializedName("giaGoc")
    private String giaGoc;

    @SerializedName("giamGia")
    private String giamGia;

    @SerializedName("mauSchema")
    private List<Mau> mauSchema;

    @SerializedName("idHangSX")
    private HangSX idHangSX;

    // Getter và Setter cho các trường
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

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getCameraTruoc() {
        return cameraTruoc;
    }

    public void setCameraTruoc(String cameraTruoc) {
        this.cameraTruoc = cameraTruoc;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public String getcPU() {
        return cPU;
    }

    public void setcPU(String cPU) {
        this.cPU = cPU;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getHeDieuHanh() {
        return heDieuHanh;
    }

    public void setHeDieuHanh(String heDieuHanh) {
        this.heDieuHanh = heDieuHanh;
    }

    public String getNamSanXuat() {
        return namSanXuat;
    }

    public void setNamSanXuat(String namSanXuat) {
        this.namSanXuat = namSanXuat;
    }

    public String getCongNgheManHinh() {
        return congNgheManHinh;
    }

    public void setCongNgheManHinh(String congNgheManHinh) {
        this.congNgheManHinh = congNgheManHinh;
    }

    public String getMoTaThem() {
        return moTaThem;
    }

    public void setMoTaThem(String moTaThem) {
        this.moTaThem = moTaThem;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getDoPhanGiai() {
        return doPhanGiai;
    }

    public void setDoPhanGiai(String doPhanGiai) {
        this.doPhanGiai = doPhanGiai;
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


    public List<Mau> getMauSchema() {
        return mauSchema;
    }

    public void setMauSchema(List<Mau> mauSchema) {
        this.mauSchema = mauSchema;
    }

    public HangSX getIdHangSX() {
        return idHangSX;
    }

    public void setIdHangSX(HangSX idHangSX) {
        this.idHangSX = idHangSX;
    }

    public static class Mau {
        @SerializedName("mau")
        private String mau;

        @SerializedName("soLuong")
        private int soLuong;

        @SerializedName("giaTien")
        private double giaTien;

        // Getter và Setter cho các trường
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

    public static class HangSX {
        @SerializedName("_id")
        private String id;

        @SerializedName("tenHang")
        private String tenHang;

        // Getter và Setter cho các trường
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTenHang() {
            return tenHang;
        }

        public void setTenHang(String tenHang) {
            this.tenHang = tenHang;
        }
    }
}
