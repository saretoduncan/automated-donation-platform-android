package com.moringa.automated_donation_platform_android.models;

public class Donor {
    private String name;
    private String amount;
    private String image;

    public Donor(String name, String amount, String image) {
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
