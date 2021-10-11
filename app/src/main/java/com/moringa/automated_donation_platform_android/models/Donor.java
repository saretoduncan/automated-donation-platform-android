package com.moringa.automated_donation_platform_android.models;

public class Donor {
    private String name;
    private Integer amount;
    private int image;

    public Donor(String name, Integer amount, int image) {
        this.name = name;
        this.amount = amount;
        this.image = image;
    }

    public Donor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
