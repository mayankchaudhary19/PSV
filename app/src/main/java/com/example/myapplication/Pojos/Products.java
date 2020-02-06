package com.example.myapplication.Pojos;

public class Products {

    private int img;
    private String title,subtitle,price,initialPrice;
    public Products(){

    }
    public Products(int img, String title, String subtitle, String price, String initialPrice){
        this.img=img;
        this.title=title;
        this.subtitle=subtitle;
        this.price=price;
        this.initialPrice=initialPrice;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(String initialPrice) {
        this.initialPrice = initialPrice;
    }

    @Override
    public String toString(){
        return "Products{" +
                "img=" + img +
                ", title='" + title + '\''+
                ", subtitle='" + subtitle + '\''+
                ", price'" + price+ '\''+
                ", initialPrice='" + initialPrice + '\''+
                '}';
    }

}
