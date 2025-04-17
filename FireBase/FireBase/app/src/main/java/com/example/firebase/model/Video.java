package com.example.firebase.model;

public class Video {
    private String urlVideo;
    private int quantityLike;
    private int quantityDislike;

    // THÊM: Dữ liệu phụ để gắn vào mỗi video từ người dùng
    private String email;
    private String avatar;

    public Video() {
        // Firebase yêu cầu constructor rỗng
    }

    public Video(String urlVideo, int quantityLike, int quantityDislike) {
        this.urlVideo = urlVideo;
        this.quantityLike = quantityLike;
        this.quantityDislike = quantityDislike;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public int getQuantityLike() {
        return quantityLike;
    }

    public void setQuantityLike(int quantityLike) {
        this.quantityLike = quantityLike;
    }

    public int getQuantityDislike() {
        return quantityDislike;
    }

    public void setQuantityDislike(int quantityDislike) {
        this.quantityDislike = quantityDislike;
    }

    // Getter/Setter mới
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
