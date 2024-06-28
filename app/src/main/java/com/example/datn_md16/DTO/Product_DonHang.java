package com.example.datn_md16.DTO;

public class Product_DonHang {
    private String name;
    private String color;
    private String price;
    private String soluong;
    private int imageResId;
    private String status; // Thêm trường status

    public Product_DonHang(String name, String color, String price, String soluong, int imageResId, String status) {
        this.name = name;
        this.color = color;
        this.price = price;
        this.soluong = soluong;
        this.imageResId = imageResId;
        this.status = status;
    }

    // Các phương thức getter và setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
