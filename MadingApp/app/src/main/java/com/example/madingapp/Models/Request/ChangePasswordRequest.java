package com.example.madingapp.Models.Request;

public class ChangePasswordRequest {
    private String userId;
    private String oldPassword;
    private String newPassWord;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }

    @Override
    public String toString() {
        return "ChangePasswordRequest{" +
                "userId='" + userId + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassWord='" + newPassWord + '\'' +
                '}';
    }
}
