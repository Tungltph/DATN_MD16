package com.example.datn_md16.DTO;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class KhuyenMai implements Parcelable {
    @SerializedName("_id")
    private String id;

    @SerializedName("ten")
    private String ten;

    @SerializedName("ngayBatDau")
    private String ngayBatDau;

    @SerializedName("ngayKetThuc")
    private String ngayKetThuc;

    @SerializedName("soLuong")
    private int soLuong;

    @SerializedName("giaKhoiDiem")
    private int giaKhoiDiem;

    @SerializedName("giaToiDa")
    private int giaToiDa;

    @SerializedName("giaKhuyenMaiToiDa")
    private int giaKhuyenMaiToiDa;

    @SerializedName("phanTramGiamGia")
    private int phanTramGiamGia;

    @SerializedName("soLanApDung")
    private int soLanApDung;

    @SerializedName("trangThai")
    private boolean trangThai;

    // Constructor
    public KhuyenMai(String id, String ten, String ngayBatDau, String ngayKetThuc, int soLuong, int giaKhoiDiem, int giaToiDa, int giaKhuyenMaiToiDa, int phanTramGiamGia, int soLanApDung, boolean trangThai) {
        this.id = id;
        this.ten = ten;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.soLuong = soLuong;
        this.giaKhoiDiem = giaKhoiDiem;
        this.giaToiDa = giaToiDa;
        this.giaKhuyenMaiToiDa = giaKhuyenMaiToiDa;
        this.phanTramGiamGia = phanTramGiamGia;
        this.soLanApDung = soLanApDung;
        this.trangThai = trangThai;
    }

    // Constructor để tạo đối tượng từ Parcel
    protected KhuyenMai(Parcel in) {
        id = in.readString();
        ten = in.readString();
        ngayBatDau = in.readString();
        ngayKetThuc = in.readString();
        soLuong = in.readInt();
        giaKhoiDiem = in.readInt();
        giaToiDa = in.readInt();
        giaKhuyenMaiToiDa = in.readInt();
        phanTramGiamGia = in.readInt();
        soLanApDung = in.readInt();
        trangThai = in.readByte() != 0;
    }

    // Viết đối tượng vào Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(ten);
        dest.writeString(ngayBatDau);
        dest.writeString(ngayKetThuc);
        dest.writeInt(soLuong);
        dest.writeInt(giaKhoiDiem);
        dest.writeInt(giaToiDa);
        dest.writeInt(giaKhuyenMaiToiDa);
        dest.writeInt(phanTramGiamGia);
        dest.writeInt(soLanApDung);
        dest.writeByte((byte) (trangThai ? 1 : 0));
    }

    // Tạo đối tượng KhuyenMai từ Parcel
    public static final Creator<KhuyenMai> CREATOR = new Creator<KhuyenMai>() {
        @Override
        public KhuyenMai createFromParcel(Parcel in) {
            return new KhuyenMai(in);
        }

        @Override
        public KhuyenMai[] newArray(int size) {
            return new KhuyenMai[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaKhoiDiem() {
        return giaKhoiDiem;
    }

    public void setGiaKhoiDiem(int giaKhoiDiem) {
        this.giaKhoiDiem = giaKhoiDiem;
    }

    public int getGiaToiDa() {
        return giaToiDa;
    }

    public void setGiaToiDa(int giaToiDa) {
        this.giaToiDa = giaToiDa;
    }

    public int getGiaKhuyenMaiToiDa() {
        return giaKhuyenMaiToiDa;
    }

    public void setGiaKhuyenMaiToiDa(int giaKhuyenMaiToiDa) {
        this.giaKhuyenMaiToiDa = giaKhuyenMaiToiDa;
    }

    public int getPhanTramGiamGia() {
        return phanTramGiamGia;
    }

    public void setPhanTramGiamGia(int phanTramGiamGia) {
        this.phanTramGiamGia = phanTramGiamGia;
    }

    public int getSoLanApDung() {
        return soLanApDung;
    }

    public void setSoLanApDung(int soLanApDung) {
        this.soLanApDung = soLanApDung;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
