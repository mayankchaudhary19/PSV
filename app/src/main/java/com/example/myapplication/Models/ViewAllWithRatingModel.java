package com.example.myapplication.Models;

public class ViewAllWithRatingModel {
    private String productImage;
    private long freeCoupons, no_of_ratings;
    private String productTitle, productSubtitle, productPrice, productInitialPrice;
    private String rating;

    public ViewAllWithRatingModel(String productImage, long freeCoupons, long no_of_ratings, String productTitle, String productSubtitle, String productPrice, String productInitialPrice, String rating) {
        this.productImage = productImage;
        this.freeCoupons = freeCoupons;
        this.no_of_ratings = no_of_ratings;
        this.productTitle = productTitle;
        this.productSubtitle = productSubtitle;
        this.productPrice = productPrice;
        this.productInitialPrice = productInitialPrice;
        this.rating = rating;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public long getFreeCoupons() {
        return freeCoupons;
    }

    public void setFreeCoupons(long freeCoupons) {
        this.freeCoupons = freeCoupons;
    }

    public long getNo_of_ratings() {
        return no_of_ratings;
    }

    public void setNo_of_ratings(long no_of_ratings) {
        this.no_of_ratings = no_of_ratings;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductSubtitle() {
        return productSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductInitialPrice() {
        return productInitialPrice;
    }

    public void setProductInitialPrice(String productInitialPrice) {
        this.productInitialPrice = productInitialPrice;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}