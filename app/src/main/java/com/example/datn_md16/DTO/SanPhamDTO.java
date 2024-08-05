package com.example.datn_md16.DTO;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class SanPhamDTO implements Parcelable {
    private String _id;
    private String tenDienThoai;
    private String hinhAnh;
    private List<MauSchemaDTO> mauSchema;

    // Constructor
    public SanPhamDTO() {
    }

    // Getter và Setter

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getTenSanPham() {
        return tenDienThoai;
    }

    public void setTenSanPham(String tenDienThoai) {
        this.tenDienThoai = tenDienThoai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public List<MauSchemaDTO> getMauSchema() {
        return mauSchema;
    }

    public void setMauSchema(List<MauSchemaDTO> mauSchema) {
        this.mauSchema = mauSchema;
    }

    // Parcelable implementation
    protected SanPhamDTO(Parcel in) {
        _id = in.readString();
        tenDienThoai = in.readString();
        hinhAnh = in.readString();
        mauSchema = in.createTypedArrayList(MauSchemaDTO.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(tenDienThoai);
        dest.writeString(hinhAnh);
        dest.writeTypedList(mauSchema);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SanPhamDTO> CREATOR = new Creator<SanPhamDTO>() {
        @Override
        public SanPhamDTO createFromParcel(Parcel in) {
            return new SanPhamDTO(in);
        }

        @Override
        public SanPhamDTO[] newArray(int size) {
            return new SanPhamDTO[size];
        }
    };

    // Inner class MauSchemaDTO
    public static class MauSchemaDTO implements Parcelable {
        private String id;
        private String mau;
        private int giaTien;

        // Constructor
        public MauSchemaDTO() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        // Getter và Setter

        public String getMau() {
            return mau;
        }

        public void setMau(String mau) {
            this.mau = mau;
        }

        public int getGiaTien() {
            return giaTien;
        }

        public void setGiaTien(int giaTien) {
            this.giaTien = giaTien;
        }

        // Parcelable implementation
        protected MauSchemaDTO(Parcel in) {
            mau = in.readString();
            giaTien = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mau);
            dest.writeInt(giaTien);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<MauSchemaDTO> CREATOR = new Creator<MauSchemaDTO>() {
            @Override
            public MauSchemaDTO createFromParcel(Parcel in) {
                return new MauSchemaDTO(in);
            }

            @Override
            public MauSchemaDTO[] newArray(int size) {
                return new MauSchemaDTO[size];
            }
        };
    }
}


