package com.moringa.automated_donation_platform_android.models;

public class Admin {
private String charityid;
private boolean approval;
private int id;
    public Admin(String charityid) {
        this.charityid = charityid;


    }

    public String getCharityid() {
        return charityid;
    }

    public void setCharityid(String charityid) {
        this.charityid = charityid;
    }

    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
