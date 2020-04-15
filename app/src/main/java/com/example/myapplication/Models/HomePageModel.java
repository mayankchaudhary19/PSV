package com.example.myapplication.Models;

import java.util.List;

public class HomePageModel {
    public static final int BANNER_SLIDER=0;
    public static final int STRIP_AD_BANNER=1;
    public static final int HORIZONTAL_PRODUCT_VIEW=2;
    public static final int GRID_PRODUCT_VIEW=3;


    private int type;

    //////////////// Banner Slider
    private List<SliderModel> sliderModelList;

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }
    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }
    //////////////// Banner Slider


    //////////////// Strip Ad Banner
    private int resource;
    private String backgroundColor;

    public HomePageModel(int type, int resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }
    public int getResource() {
        return resource;
    }
    public void setResource(int resource) {
        this.resource = resource;
    }
    public String getBackgroundColor() {
        return backgroundColor;
    }
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    //////////////// Strip Ad Banner

    //////////////// Horizontal Product layout
    private String title;
    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<HorizontalProductScrollModel> getHorizontalProductScrollModelList() {
        return horizontalProductScrollModelList;
    }
    public void setHorizontalProductScrollModelList(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }
    public HomePageModel(int type, String title, List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.type = type;
        this.title = title;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }
    //////////////// Horizontal Product layout



    //////////////// Grid Product View layout
    private String GridViewTitle;
    private List<GridProductModel> gridProductModelList;

    public HomePageModel(int type, List<GridProductModel> gridProductModelList,String gridViewTitle) {

        this.type = type;
        this.gridProductModelList = gridProductModelList;
        this.GridViewTitle = gridViewTitle;
    }
    public String getGridViewTitle() {
        return GridViewTitle;
    }

    public void setGridViewTitle(String gridViewTitle) {
        GridViewTitle = gridViewTitle;
    }

    public List<GridProductModel> getGridProductModelList() {
        return gridProductModelList;
    }

    public void setGridProductModelList(List<GridProductModel> gridProductModelList) {
        this.gridProductModelList = gridProductModelList;
    }
    //////////////// Grid Product View layout


}
