package com.example.myapplication.Models;

public class RewardModel {
    private int couponIcon;
    private String couponTitle;
    private String expiryDate;
    private String couponBody;

    public RewardModel(int couponIcon, String couponTitle, String couponBody, String expiryDate) {
        this.couponIcon = couponIcon;
        this.couponTitle = couponTitle;
        this.couponBody = couponBody;
        this.expiryDate = expiryDate;
    }

    public int getCouponIcon() {
        return couponIcon;
    }

    public void setCouponIcon(int couponIcon) {
        this.couponIcon = couponIcon;
    }

    public String getCouponTitle() {
        return couponTitle;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle;
    }

    public String getCouponBody() {
        return couponBody;
    }

    public void setCouponBody(String couponBody) {
        this.couponBody = couponBody;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

}
