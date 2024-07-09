
package com.example.datn_md16.DTO;

public class GioHangDTO {
    private String id;
    private String productName;
    private String productColor;
    private String productPrice;
    private String productImage;
    private int quantity;
    private boolean isChecked;

    public GioHangDTO(String id, String productName, String productColor, String productPrice, String productImage, int quantity, boolean isChecked) {
        this.id = id;
        this.productName = productName;
        this.productColor = productColor;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.quantity = quantity;
        this.isChecked = isChecked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
    // Các getter và setter tương ứng
}

