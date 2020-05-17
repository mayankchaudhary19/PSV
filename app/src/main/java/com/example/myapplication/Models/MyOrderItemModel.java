package com.example.myapplication.Models;

public class MyOrderItemModel {
    private int productImage;
    private int rating;
    private String productTitle;
    private String productSubtitle;
    private String quantity;
    private String deliveryStatus;

    public MyOrderItemModel(int productImage, String productTitle, String productSubtitle, String quantity, String deliveryStatus, int rating) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productSubtitle = productSubtitle;
        this.quantity=quantity;
        this.deliveryStatus = deliveryStatus;
        this.rating=rating;

    }
    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }


}
