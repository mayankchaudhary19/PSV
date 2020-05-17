package com.example.myapplication.Models;

public class ViewAllWithRatingModel {
    private int productImage;
    private String productTitle,productSubtitle,productPrice,productInitialPrice,productDiscount,no_of_ratings;
    private String rating;

    public ViewAllWithRatingModel(int productImage, String productTitle, String productSubtitle, String productPrice, String productInitialPrice, String productDiscount, String rating, String no_of_ratings) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productSubtitle = productSubtitle;
        this.productPrice = productPrice;
        this.productInitialPrice = productInitialPrice;
        this.productDiscount = productDiscount;
        this.rating = rating;
        this.no_of_ratings = no_of_ratings;

    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
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

    public String getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(String productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getNo_of_ratings() {
        return no_of_ratings;
    }

    public void setNo_of_ratings(String no_of_ratings) {
        this.no_of_ratings = no_of_ratings;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String  rating) {
        this.rating = rating;
    }
}
