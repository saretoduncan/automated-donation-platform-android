package com.moringa.automated_donation_platform_android.models;

public class Admin {
private int charityid;
private boolean approval;
private int id;
    public Admin(int charityid, boolean approval) {
        this.charityid = charityid;
        this.approval = approval;

    }

    public int getCharityid() {
        return charityid;
    }

    public void setCharityid(int charityid) {
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
