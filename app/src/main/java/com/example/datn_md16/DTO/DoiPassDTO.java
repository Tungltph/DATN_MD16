package com.example.datn_md16.DTO;

import com.google.gson.annotations.SerializedName;

public class DoiPassDTO {
    @SerializedName("currentPassword")  // Ensure this matches the key in the JSON
    private String oldPassword;

    @SerializedName("newPassword")  // Ensure this matches the key in the JSON
    private String newPassword;

    // Constructor
    public DoiPassDTO(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    // Getters and Setters
    public String getOldPassword() { return oldPassword; }
    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}



