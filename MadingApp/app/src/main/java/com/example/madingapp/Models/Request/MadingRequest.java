package com.example.madingapp.Models.Request;

public class MadingRequest {
    private String madingId;
    private int likes;
    private int dislikes;

    public String getMadingId() {
        return madingId;
    }

    public void setMadingId(String madingId) {
        this.madingId = madingId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    @Override
    public String toString() {
        return "MadingRequest{" +
                "madingId='" + madingId + '\'' +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                '}';
    }
}
