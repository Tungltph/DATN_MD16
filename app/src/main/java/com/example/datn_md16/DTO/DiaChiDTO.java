package com.example.datn_md16.DTO;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class DiaChiDTO implements Parcelable {
    @SerializedName("_id")
    private String id;

    @SerializedName("ten")
    private String ten;

    @SerializedName("sdt")
    private String sdt;

    @SerializedName("diaChi")
    private String diaChi;

    private boolean isSelected; // Thêm thuộc tính này

    public DiaChiDTO() {
    }

    // Constructor với các tham số
    public DiaChiDTO(String id, String ten, String sdt, String diaChi) {
        this.id = id;
        this.ten = ten;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

    public DiaChiDTO(String ten, String sdt, String diaChi) {
        this.ten = ten;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

    // Constructor từ Parcel
    protected DiaChiDTO(Parcel in) {
        id = in.readString();
        ten = in.readString();
        sdt = in.readString();
        diaChi = in.readString();
    }

    // Phương thức CREATOR
    public static final Creator<DiaChiDTO> CREATOR = new Creator<DiaChiDTO>() {
        @Override
        public DiaChiDTO createFromParcel(Parcel in) {
            return new DiaChiDTO(in);
        }

        @Override
        public DiaChiDTO[] newArray(int size) {
            return new DiaChiDTO[size];
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
        dest.writeString(sdt);
        dest.writeString(diaChi);
    }

    // Getter và Setter
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "DiaChiDTO{" +
                "id='" + id + '\'' +
                ", ten='" + ten + '\'' +
                ", sdt='" + sdt + '\'' +
                ", diaChi='" + diaChi + '\'' +
                '}';
    }
}
