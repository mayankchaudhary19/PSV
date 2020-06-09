package com.example.myapplication.Models;

public class HorizontalProductScrollModel {
    private String productImage;
    private String productTitle;
    private String productDescription;
    private String productPrice;
    private String productInitialPrice;
    private String productId;
//    private String backgroundColor;




    public HorizontalProductScrollModel(String productId, String productImage, String productTitle, String productDescription, String productPrice, String productInitialPrice) {
        this.productId = productId;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productInitialPrice = productInitialPrice;
    }
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
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
}
