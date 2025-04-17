package com.example.firebase.model;

import java.util.List;

public class User {
    private String email;
    private String password;
    private String avatar;
    private List<Video> videos;

    public User() {
        // Firebase yêu cầu constructor rỗng
    }

    public User(String email, String password, String avatar, List<Video> videos) {
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.videos = videos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
