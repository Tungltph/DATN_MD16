package com.example.datn_md16.DTO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TimKiemDTO {
    @SerializedName("tenDienThoai")
    private String tenSanPham;

    @SerializedName("giamGia")
    private String giamGia;

    @SerializedName("giaGoc")
    private String giaGoc;

    @SerializedName("hinhAnh")
    private String hinhAnh;

    private List<MauSchema> mauSchema;

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


    @SerializedName("doPhanGiai")
    private String doPhanGiai;



    public TimKiemDTO(String tenSanPham, String giamGia, String giaGoc, String hinhAnh) {
        this.tenSanPham = tenSanPham;
        this.giamGia = giamGia;
        this.giaGoc = giaGoc;
        this.hinhAnh = hinhAnh;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
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

    public String getDoPhanGiai() {
        return doPhanGiai;
    }

    public void setDoPhanGiai(String doPhanGiai) {
        this.doPhanGiai = doPhanGiai;
    }

    public String getCameraTruoc() {
        return cameraTruoc;
    }

    public void setCameraTruoc(String cameraTruoc) {
        this.cameraTruoc = cameraTruoc;
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

    public List<MauSchema> getMauSchema() {
        return mauSchema;
    }



    public static class MauSchema implements Serializable {
        private String mau;
        private int soLuong;
        private int giaTien;
        private String _id;

        public MauSchema(String mau, int soLuong, int giaTien, String _id) {
            this.mau = mau;
            this.soLuong = soLuong;
            this.giaTien = giaTien;
            this._id = _id;
        }



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

        public int getGiaTien() {
            return giaTien;
        }

        public void setGiaTien(int giaTien) {
            this.giaTien = giaTien;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }
    }
}
