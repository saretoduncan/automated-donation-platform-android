package com.moringa.automated_donation_platform_android.models;

public class Charity {
    private String description;
    private String trustDeed;
    private String image;
    private int userId;
    private int donorId;
    private int id;

    public Charity(String description, String trustDeed, String image, int userId, int donorId) {
        this.description = description;
        this.trustDeed = trustDeed;
        this.image = image;
        this.userId = userId;
        this.donorId = donorId;
    }

    public Charity(String description, String trustDeed, int userId) {
        this.description = description;
        this.trustDeed = trustDeed;
        this.image = image;
        this.userId = userId;
    }

    public Charity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTrustDeed() {
        return trustDeed;
    }

    public void setTrustDeed(String trustDeed) {
        this.trustDeed = trustDeed;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDonorId() {
        return donorId;
    }

    public void setDonorId(int donorId) {
        this.donorId = donorId;
    }

}
