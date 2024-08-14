package com.example.datn_md16.DTO;

import com.google.gson.annotations.SerializedName;

public class DanhGiaDTO {

    @SerializedName("noiDung")
    private String noiDung;

    @SerializedName("thoiGian")
    private String thoiGian;

    @SerializedName("diemDanhGia")
    private int diemDanhGia;

    @SerializedName("idKH")
    private String idKH;

    @SerializedName("idSP")
    private String idSP;

    public DanhGiaDTO(String noiDung, String thoiGian, int diemDanhGia, String idKH, String idSP) {
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.diemDanhGia = diemDanhGia;
        this.idKH = idKH;
        this.idSP = idSP;
    }

    // Getters and Setters

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public int getDiemDanhGia() {
        return diemDanhGia;
    }

    public void setDiemDanhGia(int diemDanhGia) {
        this.diemDanhGia = diemDanhGia;
    }

    public String getIdKH() {
        return idKH;
    }

    public void setIdKH(String idKH) {
        this.idKH = idKH;
    }

    public String getIdSP() {
        return idSP;
    }

    public void setIdSP(String idSP) {
        this.idSP = idSP;
    }
}
