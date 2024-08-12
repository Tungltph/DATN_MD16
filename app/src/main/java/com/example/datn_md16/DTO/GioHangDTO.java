package com.example.datn_md16.DTO;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class GioHangDTO implements Parcelable {
    private String _id;
    private String idSanPham; // Giữ nguyên là String
    private String idAccount; // Giữ nguyên là String
    private int soLuong;
    private String idMau; // Giữ nguyên là String
    private boolean isChecked; // Thêm trường này
    private ProductHome sanPham; // Thêm trường này để lưu thông tin sản phẩm
    private String selectedColorId; // Thêm trường này để lưu màu sắc được chọn
    private KhachHang khachHang;

    // Getter và Setter cho KhachHang
    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public GioHangDTO() {
    }

    // Constructor
    public GioHangDTO(String idSanPham, String idAccount, int soLuong, String idMau) {
        this.idSanPham = idSanPham;
        this.idAccount = idAccount;
        this.soLuong = soLuong;
        this.idMau = idMau;
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

    public ProductHome getSanPham() {
        return sanPham;
    }

    public void setSanPham(ProductHome sanPham) {
        this.sanPham = sanPham;
    }

    public String getSelectedColorId() {
        return selectedColorId;
    }

    public void setSelectedColorId(String selectedColorId) {
        this.selectedColorId = selectedColorId;
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
        selectedColorId = in.readString(); // Đọc trường selectedColor
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(idSanPham);
        dest.writeString(idAccount);
        dest.writeInt(soLuong);
        dest.writeString(idMau);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeParcelable((Parcelable) sanPham, flags);
        dest.writeString(selectedColorId); // Ghi trường selectedColor
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

    // Lớp nội bộ KhachHang
    public static class KhachHang {
        @SerializedName("_id")
        private String id;

        @SerializedName("tenQuyen")
        private String tenQuyen;

        @SerializedName("taiKhoan")
        private String taiKhoan;

        @SerializedName("hoTen")
        private String hoTen;

        @SerializedName("sdt")
        private String sdt;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTenQuyen() {
            return tenQuyen;
        }

        public void setTenQuyen(String tenQuyen) {
            this.tenQuyen = tenQuyen;
        }

        public String getTaiKhoan() {
            return taiKhoan;
        }

        public void setTaiKhoan(String taiKhoan) {
            this.taiKhoan = taiKhoan;
        }

        public String getHoTen() {
            return hoTen;
        }

        public void setHoTen(String hoTen) {
            this.hoTen = hoTen;
        }

        public String getSdt() {
            return sdt;
        }

        public void setSdt(String sdt) {
            this.sdt = sdt;
        }
    }
}
