package com.example.datn_md16.DTO;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DiaChiDTO implements Parcelable, Serializable {
    @SerializedName("_id")
    private String id;

    @SerializedName("ten")
    private String ten;

    @SerializedName("sdt")
    private String sdt;

    @SerializedName("diaChi")
    private String diaChi;

    @SerializedName("idAccount")
    private String idAccount;

    private boolean isSelected; // Thêm thuộc tính này

    public DiaChiDTO() {
    }

    public DiaChiDTO(String name, String phoneNumber, String address, String idAccount) {
        this.ten = name;
        this.sdt = phoneNumber;
        this.diaChi = address;
        this.idAccount = idAccount;

    }

    // Constructor với các tham số


    public DiaChiDTO(String ten, String sdt, String diaChi, String idAccount, boolean isSelected) {
        this.ten = ten;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.idAccount = idAccount;
        this.isSelected = isSelected;
    }

    public DiaChiDTO(String id, String ten, String sdt, String diaChi, String idAccount, boolean isSelected) {
        this.id = id;
        this.ten = ten;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.idAccount = idAccount;
        this.isSelected = isSelected;
    }



    // Constructor từ Parcel
    protected DiaChiDTO(Parcel in) {
        id = in.readString();
        ten = in.readString();
        sdt = in.readString();
        diaChi = in.readString();
        idAccount = in.readString();
        isSelected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(ten);
        dest.writeString(sdt);
        dest.writeString(diaChi);
        dest.writeString(idAccount);
        dest.writeByte((byte) (isSelected ? 1 : 0));
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

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
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
