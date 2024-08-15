package com.example.datn_md16.DTO;

public class DanhGiaSendDTO {
    private String noiDung;
    private String thoiGian;
    private int diemDanhGia;
    private String idKH; // Chỉ chứa ID dưới dạng String
    private String idSP; // Chỉ chứa ID dưới dạng String

    public DanhGiaSendDTO(String noiDung, String thoiGian, int diemDanhGia, String idKH, String idSP) {
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.diemDanhGia = diemDanhGia;
        this.idKH = idKH;
        this.idSP = idSP;
    }

    // Getter and Setter
    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }

    public String getThoiGian() { return thoiGian; }
    public void setThoiGian(String thoiGian) { this.thoiGian = thoiGian; }

    public int getDiemDanhGia() { return diemDanhGia; }
    public void setDiemDanhGia(int diemDanhGia) { this.diemDanhGia = diemDanhGia; }

    public String getIdKH() { return idKH; }
    public void setIdKH(String idKH) { this.idKH = idKH; }

    public String getIdSP() { return idSP; }
    public void setIdSP(String idSP) { this.idSP = idSP; }
}
