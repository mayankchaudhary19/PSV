package com.example.myapplication.Models;


public class MyCartItemModel {
    public static final int CART_ITEM=0;
    public static final int TOTAL_AMOUNT=1;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /////////////////cartItem
    private int productImage;
    private String productTitle;
    private String productSubtitle;
    private String productPrice;
    private String productInitialPrice;
    private String productDiscount;
    private String  productQuantity;
    private String productQuantityType;

    public MyCartItemModel(int type, int productImage, String productTitle, String productSubtitle, String productPrice, String productInitialPrice, String productDiscount) {
        this.type = type;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productSubtitle = productSubtitle;
        this.productPrice = productPrice;
        this.productInitialPrice = productInitialPrice;
        this.productDiscount = productDiscount;
//        this.productQuantity = productQuantity;
//        this.productQuantityType = productQuantityType;
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


//    private int couponApplied;
//        this.couponApplied = couponApplied;
//        this.productQuantity = productQuantity;
//        this.productQuantityType = productQuantityType;
//, int couponApplied, String productQuantity, String productQuantityType


    /////////////////cartItem

    /////////////////cartTotalPrice

    private String totalItems;
    private String totalItemsPrice;
    private String discountItemsPrice;
    private String ShippingCharges;
    private String SubTotal;

    public MyCartItemModel(int type, String totalItems, String totalItemsPrice, String discountItemsPrice, String shippingCharges, String subTotal) {
        this.type = type;
        this.totalItems = totalItems;
        this.totalItemsPrice = totalItemsPrice;
        this.discountItemsPrice = discountItemsPrice;
        this.ShippingCharges = shippingCharges;
        this.SubTotal = subTotal;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalItemsPrice() {
        return totalItemsPrice;
    }

    public void setTotalItemsPrice(String totalItemsPrice) {
        this.totalItemsPrice = totalItemsPrice;
    }

    public String getDiscountItemsPrice() {
        return discountItemsPrice;
    }

    public void setDiscountItemsPrice(String discountItemsPrice) {
        this.discountItemsPrice = discountItemsPrice;
    }

    public String getShippingCharges() {
        return ShippingCharges;
    }

    public void setShippingCharges(String shippingCharges) {
        ShippingCharges = shippingCharges;
    }

    public String getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(String subTotal) {
        SubTotal = subTotal;
    }
    /////////////////cartTotalPrice



}


