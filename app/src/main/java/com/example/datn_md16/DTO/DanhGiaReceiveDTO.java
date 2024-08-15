package com.example.datn_md16.DTO;

public class DanhGiaReceiveDTO {
    private String noiDung;
    private String thoiGian;
    private int diemDanhGia;
    private KhachHangDTO idKH; // Chứa đối tượng chi tiết
    private SanPhamDTO idSP;   // Chứa đối tượng chi tiết

    public DanhGiaReceiveDTO(String noiDung, String thoiGian, int diemDanhGia, KhachHangDTO idKH, SanPhamDTO idSP) {
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

    public KhachHangDTO getIdKH() { return idKH; }
    public void setIdKH(KhachHangDTO idKH) { this.idKH = idKH; }

    public SanPhamDTO getIdSP() { return idSP; }
    public void setIdSP(SanPhamDTO idSP) { this.idSP = idSP; }

    public static class KhachHangDTO {
        private String hoTen;
        private String sdt;

        // Getter and Setter
        public String getHoTen() { return hoTen; }
        public void setHoTen(String hoTen) { this.hoTen = hoTen; }

        public String getSdt() { return sdt; }
        public void setSdt(String sdt) { this.sdt = sdt; }
    }

    public static class SanPhamDTO {
        private String tenDienThoai;
        private String hinhAnh;

        // Getter and Setter
        public String getTenDienThoai() { return tenDienThoai; }
        public void setTenDienThoai(String tenDienThoai) { this.tenDienThoai = tenDienThoai; }

        public String getHinhAnh() { return hinhAnh; }
        public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
    }
}
