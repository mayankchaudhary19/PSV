package com.example.myapplication.Pojos;

public class Categories {
    private int img;
    private String title;
    public Categories(){

    }
    public Categories(int img, String title){
        this.img=img;
        this.title=title;
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
    @Override
    public String toString(){
        return "Categories{" +
                "img=" + img +
                ", title='" + title + '\''+
                '}';
    }

}
