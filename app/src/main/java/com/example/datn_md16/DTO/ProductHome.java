package com.example.datn_md16.DTO;

public class ProductHome {
    private int imageRes; // Resource ID của hình ảnh sản phẩm
    private String name; // Tên sản phẩm
    private String price; // Giá sản phẩm
    private float rating; // Đánh giá sản phẩm

    public ProductHome(int imageRes, String name, String price, float rating) {
        this.imageRes = imageRes;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
