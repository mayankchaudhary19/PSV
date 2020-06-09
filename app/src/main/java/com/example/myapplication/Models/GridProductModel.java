package com.example.myapplication.Models;

public class GridProductModel {
    private String productId;
    private String productImage;
    private String productTitle;
    private String productSubTitle;
    private String productPrice;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductSubTitle() {
        return productSubTitle;
    }

    public void setProductSubTitle(String productSubTitle) {
        this.productSubTitle = productSubTitle;
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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public GridProductModel(String productId,String productImage, String productTitle, String productSubTitle, String productPrice) {
        this.productId=productId;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productSubTitle=productSubTitle;
        this.productPrice = productPrice;
    }
}
