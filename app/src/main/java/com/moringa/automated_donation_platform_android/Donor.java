package com.moringa.automated_donation_platform_android;

public class Donor {
    private String name;
    private Integer amount;
    private String image;

    public Donor(String name, Integer amount, String image) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
