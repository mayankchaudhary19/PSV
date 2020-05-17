package com.example.myapplication.Models;

public class GridProductModel {
    private int productImage;
    private String productTitle;
    private String productSubTitle;
    private String productPrice;

    public String getProductSubTitle() {
        return productSubTitle;
    }

    public void setProductSubTitle(String productSubTitle) {
        this.productSubTitle = productSubTitle;
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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public GridProductModel(int productImage, String productTitle, String productSubTitle, String productPrice) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productSubTitle=productSubTitle;
        this.productPrice = productPrice;
    }
}
