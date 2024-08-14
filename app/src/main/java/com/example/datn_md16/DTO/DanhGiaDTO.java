package com.example.datn_md16.DTO;

public class DanhGiaDTO {
    private String noiDung;
    private String thoiGian;
    private int diemDanhGia;
    private AccountResponse.Account idKH;  // Đối tượng Account chứa thông tin khách hàng
    private ProductHome idSP;  // Đối tượng ProductHome chứa thông tin sản phẩm

    // Constructor, getters, và setters
    public DanhGiaDTO(String noiDung, String thoiGian, int diemDanhGia, AccountResponse.Account idKH, ProductHome idSP) {
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.diemDanhGia = diemDanhGia;
        this.idKH = idKH;
        this.idSP = idSP;
    }

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

    public AccountResponse.Account getIdKH() {
        return idKH;
    }

    public void setIdKH(AccountResponse.Account idKH) {
        this.idKH = idKH;
    }

    public ProductHome getIdSP() {
        return idSP;
    }

    public void setIdSP(ProductHome idSP) {
        this.idSP = idSP;
    }
}
