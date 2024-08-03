package com.example.datn_md16.DTO;

import android.os.Parcel;
import android.os.Parcelable;

public class GioHangDTO implements Parcelable {
    private String _id;
    private String idSanPham; // Giữ nguyên là String
    private String idAccount; // Giữ nguyên là String
    private int soLuong;
    private String idMau; // Giữ nguyên là String
    private boolean isChecked; // Thêm trường này
    private SanPhamDTO sanPham; // Thêm trường này để lưu thông tin sản phẩm
    private String selectedColor; // Thêm trường này để lưu màu sắc được chọn

    // Constructor
    public GioHangDTO() {
    }

    // Getter và Setter

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(String idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(String idAccount) {
        this.idAccount = idAccount;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getIdMau() {
        return idMau;
    }

    public void setIdMau(String idMau) {
        this.idMau = idMau;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public SanPhamDTO getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPhamDTO sanPham) {
        this.sanPham = sanPham;
    }

    public String getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    // Parcelable implementation
    protected GioHangDTO(Parcel in) {
        _id = in.readString();
        idSanPham = in.readString();
        idAccount = in.readString();
        soLuong = in.readInt();
        idMau = in.readString();
        isChecked = in.readByte() != 0;
        sanPham = in.readParcelable(SanPhamDTO.class.getClassLoader());
        selectedColor = in.readString(); // Đọc trường selectedColor
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(idSanPham);
        dest.writeString(idAccount);
        dest.writeInt(soLuong);
        dest.writeString(idMau);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeParcelable(sanPham, flags);
        dest.writeString(selectedColor); // Ghi trường selectedColor
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GioHangDTO> CREATOR = new Creator<GioHangDTO>() {
        @Override
        public GioHangDTO createFromParcel(Parcel in) {
            return new GioHangDTO(in);
        }

        @Override
        public GioHangDTO[] newArray(int size) {
            return new GioHangDTO[size];
        }
    };
}
