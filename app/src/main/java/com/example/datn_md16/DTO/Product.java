package com.example.datn_md16.DTO;

public class Product {
    private int image;
    private String name;
    private float rating;
    private boolean isFavorite;

    public Product(int image, String name, float rating) {
        this.image = image;
        this.name = name;
        this.rating = rating;
        this.isFavorite = false; // Khởi tạo trạng thái mặc định
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
