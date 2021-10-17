package com.moringa.automated_donation_platform_android.models;

public class Beneficiary {
    private String name;
    private String testimony;
    private String image;
    private int id;
    private int charityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCharityId() {
        return charityId;
    }

    public void setCharityId(int charityId) {
        this.charityId = charityId;
    }

    public Beneficiary(String name, String testimony, String image) {
        this.name = name;
        this.testimony = testimony;
        this.image = image;
    }

    public Beneficiary() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTestimony() {
        return testimony;
    }

    public void setTestimony(String testimony) {
        this.testimony = testimony;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
