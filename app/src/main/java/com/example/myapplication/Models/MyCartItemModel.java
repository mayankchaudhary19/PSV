package com.example.myapplication.Models;


public class MyCartItemModel {

    private String productId;
    private boolean inStock;
    private String productImage;
    private String productTitle;
    private String productSubtitle;
    private String productPrice;
    private String productInitialPrice;
    private Long productQuantity;
    private String productQuantityType;
    private Long offersApplied;
    private Long couponsApplied;
    private Long freeCouponsAvailable;

    public MyCartItemModel(String productId, boolean inStock, String productImage, String productTitle, String productSubtitle, String productPrice, String productInitialPrice, Long productQuantity, Long offersApplied, Long freeCouponsAvailable) {
        this.productId = productId;
        this.inStock = inStock;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productSubtitle = productSubtitle;
        this.productPrice = productPrice;
        this.productInitialPrice = productInitialPrice;
        this.productQuantity = productQuantity;
//        this.productQuantityType = productQuantityType;
        this.offersApplied = offersApplied;
//        this.couponsApplied = couponsApplied;
        this.freeCouponsAvailable = freeCouponsAvailable;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
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

    public Long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Long getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(Long offersApplied) {
        this.offersApplied = offersApplied;
    }

    public Long getFreeCouponsAvailable() {
        return freeCouponsAvailable;
    }

    public void setFreeCouponsAvailable(Long freeCouponsAvailable) {
        this.freeCouponsAvailable = freeCouponsAvailable;
    }



}


