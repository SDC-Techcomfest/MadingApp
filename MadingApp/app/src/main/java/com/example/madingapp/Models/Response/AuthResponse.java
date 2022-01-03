package com.example.madingapp.Models.Response;

public class AuthResponse {

    private int code;
    private boolean status;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "code=" + code +
                ", status=" + status +
                ", data='" + data + '\'' +
                '}';
    }
}
