package com.moringa.automated_donation_platform_android.models;

public class Beneficiary {
    private String name;
    private String testimonial;
    private int image;

    public Beneficiary(String name, String testimonial, int image) {
        this.name = name;
        this.testimonial = testimonial;
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

    public String getTestimonial() {
        return testimonial;
    }

    public void setTestimonial(String testimonial) {
        this.testimonial = testimonial;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
