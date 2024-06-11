package com.example.datn_md16.DTO;

public class Product_TimKiem {
    private int imageRes;
    private String name;
    private String price;

    public Product_TimKiem(int imageRes, String name, String price) {
        this.imageRes = imageRes;
        this.name = name;
        this.price = price;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}

