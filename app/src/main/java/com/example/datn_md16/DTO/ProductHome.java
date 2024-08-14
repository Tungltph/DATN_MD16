package com.example.datn_md16.DTO;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProductHome implements Parcelable {
    private String _id;
    private String tenDienThoai; // Product name
    private String camera;
    private String cameraTruoc;
    private String kichThuoc;
    private String cPU;
    private String ram;
    private String sim;
    private String pin;
    private String heDieuHanh;
    private String namSanXuat;
    private String congNgheManHinh;
    private String moTaThem;
    private String hinhAnh; // Image URL
    private String doPhanGiai;
    private String giaGoc; // Original price
    private String giamGia; // Discount price
    private boolean trangThai;
    private List<MauSchema> mauSchema;
    private String idHangSX;
    private int __v;
    private float rating; // Rating

    // Constructor, getters, and setters


    public ProductHome() {
    }

    public ProductHome(String _id, String tenDienThoai, String camera, String cameraTruoc, String kichThuoc,
                       String cPU, String ram, String sim, String pin, String heDieuHanh, String namSanXuat,
                       String congNgheManHinh, String moTaThem, String hinhAnh, String doPhanGiai, String giaGoc,
                       String giamGia, boolean trangThai, List<MauSchema> mauSchema, String idHangSX, int __v, float rating) {
        this._id = _id;
        this.tenDienThoai = tenDienThoai;
        this.camera = camera;
        this.cameraTruoc = cameraTruoc;
        this.kichThuoc = kichThuoc;
        this.cPU = cPU;
        this.ram = ram;
        this.sim = sim;
        this.pin = pin;
        this.heDieuHanh = heDieuHanh;
        this.namSanXuat = namSanXuat;
        this.congNgheManHinh = congNgheManHinh;
        this.moTaThem = moTaThem;
        this.hinhAnh = hinhAnh;
        this.doPhanGiai = doPhanGiai;
        this.giaGoc = giaGoc;
        this.giamGia = giamGia;
        this.trangThai = trangThai;
        this.mauSchema = mauSchema;
        this.idHangSX = idHangSX;
        this.__v = __v;
        this.rating = rating;
    }

    protected ProductHome(Parcel in) {
        _id = in.readString();
        tenDienThoai = in.readString();
        camera = in.readString();
        cameraTruoc = in.readString();
        kichThuoc = in.readString();
        cPU = in.readString();
        ram = in.readString();
        sim = in.readString();
        pin = in.readString();
        heDieuHanh = in.readString();
        namSanXuat = in.readString();
        congNgheManHinh = in.readString();
        moTaThem = in.readString();
        hinhAnh = in.readString();
        doPhanGiai = in.readString();
        giaGoc = in.readString();
        giamGia = in.readString();
        trangThai = in.readByte() != 0;
        mauSchema = in.createTypedArrayList(MauSchema.CREATOR);
        idHangSX = in.readString();
        __v = in.readInt();
        rating = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(tenDienThoai);
        dest.writeString(camera);
        dest.writeString(cameraTruoc);
        dest.writeString(kichThuoc);
        dest.writeString(cPU);
        dest.writeString(ram);
        dest.writeString(sim);
        dest.writeString(pin);
        dest.writeString(heDieuHanh);
        dest.writeString(namSanXuat);
        dest.writeString(congNgheManHinh);
        dest.writeString(moTaThem);
        dest.writeString(hinhAnh);
        dest.writeString(doPhanGiai);
        dest.writeString(giaGoc);
        dest.writeString(giamGia);
        dest.writeByte((byte) (trangThai ? 1 : 0));
        dest.writeTypedList(mauSchema);
        dest.writeString(idHangSX);
        dest.writeInt(__v);
        dest.writeFloat(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductHome> CREATOR = new Creator<ProductHome>() {
        @Override
        public ProductHome createFromParcel(Parcel in) {
            return new ProductHome(in);
        }

        @Override
        public ProductHome[] newArray(int size) {
            return new ProductHome[size];
        }
    };

    // Getters and Setters
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenDienThoai() {
        return tenDienThoai;
    }

    public void setTenDienThoai(String tenDienThoai) {
        this.tenDienThoai = tenDienThoai;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getCameraTruoc() {
        return cameraTruoc;
    }

    public void setCameraTruoc(String cameraTruoc) {
        this.cameraTruoc = cameraTruoc;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public String getcPU() {
        return cPU;
    }

    public void setcPU(String cPU) {
        this.cPU = cPU;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getHeDieuHanh() {
        return heDieuHanh;
    }

    public void setHeDieuHanh(String heDieuHanh) {
        this.heDieuHanh = heDieuHanh;
    }

    public String getNamSanXuat() {
        return namSanXuat;
    }

    public void setNamSanXuat(String namSanXuat) {
        this.namSanXuat = namSanXuat;
    }

    public String getCongNgheManHinh() {
        return congNgheManHinh;
    }

    public void setCongNgheManHinh(String congNgheManHinh) {
        this.congNgheManHinh = congNgheManHinh;
    }

    public String getMoTaThem() {
        return moTaThem;
    }

    public void setMoTaThem(String moTaThem) {
        this.moTaThem = moTaThem;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getDoPhanGiai() {
        return doPhanGiai;
    }

    public void setDoPhanGiai(String doPhanGiai) {
        this.doPhanGiai = doPhanGiai;
    }

    public String getGiaGoc() {
        return giaGoc;
    }

    public void setGiaGoc(String giaGoc) {
        this.giaGoc = giaGoc;
    }

    public String getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(String giamGia) {
        this.giamGia = giamGia;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public List<MauSchema> getMauSchema() {
        return mauSchema;
    }

    public void setMauSchema(List<MauSchema> mauSchema) {
        this.mauSchema = mauSchema;
    }

    public String getIdHangSX() {
        return idHangSX;
    }

    public void setIdHangSX(String idHangSX) {
        this.idHangSX = idHangSX;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    // MauSchema class implementing Parcelable
    public static class MauSchema implements Parcelable {
        private String mau;
        private int soLuong;
        private int giaTien;
        private String _id;

        public MauSchema(String mau, int soLuong, int giaTien, String _id) {
            this.mau = mau;
            this.soLuong = soLuong;
            this.giaTien = giaTien;
            this._id = _id;
        }

        protected MauSchema(Parcel in) {
            mau = in.readString();
            soLuong = in.readInt();
            giaTien = in.readInt();
            _id = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mau);
            dest.writeInt(soLuong);
            dest.writeInt(giaTien);
            dest.writeString(_id);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<MauSchema> CREATOR = new Creator<MauSchema>() {
            @Override
            public MauSchema createFromParcel(Parcel in) {
                return new MauSchema(in);
            }

            @Override
            public MauSchema[] newArray(int size) {
                return new MauSchema[size];
            }
        };

        // Getters and Setters
        public String getMau() {
            return mau;
        }

        public void setMau(String mau) {
            this.mau = mau;
        }

        public int getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(int soLuong) {
            this.soLuong = soLuong;
        }

        public int getGiaTien() {
            return giaTien;
        }

        public void setGiaTien(int giaTien) {
            this.giaTien = giaTien;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }
    }
}
