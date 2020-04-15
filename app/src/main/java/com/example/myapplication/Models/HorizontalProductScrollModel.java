package com.example.myapplication.Models;

public class HorizontalProductScrollModel {
    private int productImage;
    private String productTitle;
    private String productDescription;
    private String productPrice;
    private String productInitialPrice;

    public HorizontalProductScrollModel(int productImage, String productTitle, String productDescription, String productPrice, String productInitialPrice) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productInitialPrice = productInitialPrice;
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
