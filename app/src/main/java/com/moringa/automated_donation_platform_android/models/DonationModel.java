package com.moringa.automated_donation_platform_android.models;

import java.security.Timestamp;

public class DonationModel {
    private int userid;
    private int charityid;
    private boolean anonymity;
    private String frequency;
    private Timestamp reminderdate;
    private String paymentmode;
    private int id;
    public DonationModel(int userid, int charityid, boolean anonymity, String frequency, Timestamp reminderdate, String paymentmode) {
        this.userid = userid;
        this.charityid = charityid;
        this.anonymity = anonymity;
        this.frequency = frequency;
        this.reminderdate = reminderdate;
        this.paymentmode = paymentmode;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getCharityid() {
        return charityid;
    }

    public void setCharityid(int charityid) {
        this.charityid = charityid;
    }

    public boolean isAnonymity() {
        return anonymity;
    }

    public void setAnonymity(boolean anonymity) {
        this.anonymity = anonymity;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Timestamp getReminderdate() {
        return reminderdate;
    }

    public void setReminderdate(Timestamp reminderdate) {
        this.reminderdate = reminderdate;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
