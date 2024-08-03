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
    private double giaKhoiDiem;

    @SerializedName("soLanApDung")
    private int soLanApDung;

    @SerializedName("trangThai")
    private boolean trangThai;

    public KhuyenMai() {
    }

    protected KhuyenMai(Parcel in) {
        id = in.readString();
        ten = in.readString();
        ngayBatDau = in.readString();
        ngayKetThuc = in.readString();
        soLuong = in.readInt();
        giaKhoiDiem = in.readDouble();
        soLanApDung = in.readInt();
        trangThai = in.readByte() != 0;
    }

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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(ten);
        dest.writeString(ngayBatDau);
        dest.writeString(ngayKetThuc);
        dest.writeInt(soLuong);
        dest.writeDouble(giaKhoiDiem);
        dest.writeInt(soLanApDung);
        dest.writeByte((byte) (trangThai ? 1 : 0));
    }

    // Getters and setters
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

    public double getGiaKhoiDiem() {
        return giaKhoiDiem;
    }

    public void setGiaKhoiDiem(double giaKhoiDiem) {
        this.giaKhoiDiem = giaKhoiDiem;
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
