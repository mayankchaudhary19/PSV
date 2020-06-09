package com.example.myapplication.Models;

import java.util.List;

public class HomePageModel {
    public static final int BANNER_SLIDER=0;
    public static final int STRIP_AD_BANNER=1;
    public static final int HORIZONTAL_PRODUCT_VIEW=2;
    public static final int GRID_PRODUCT_VIEW=3;

    private int type;
    private String backgroundColor;

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
    private String resource;

    public HomePageModel(int type, String resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }
    public String getResource() {
        return resource;
    }
    public void setResource(String resource) {
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
    private List<ViewAllWithRatingModel> viewAllProductWithRatingList;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public HomePageModel(int type, String title,String backgroundColor, List<HorizontalProductScrollModel> horizontalProductScrollModelList,List<ViewAllWithRatingModel> viewAllProductWithRatingList) {
        this.type = type;
        this.title = title;
        this.backgroundColor=backgroundColor;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
        this.viewAllProductWithRatingList=viewAllProductWithRatingList;
    }

    public List<ViewAllWithRatingModel> getViewAllProductWithRatingList() {
        return viewAllProductWithRatingList;
    }

    public void setViewAllProductWithRatingList(List<ViewAllWithRatingModel> viewAllProductWithRatingList) {
        this.viewAllProductWithRatingList = viewAllProductWithRatingList;
    }

    //horizontal

//    public HomePageModel(int type, String title, String backgroundColor, List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
//        this.type = type;
//        this.title = title;
//        this.backgroundColor=backgroundColor;
//        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
//    }
    public List<HorizontalProductScrollModel> getHorizontalProductScrollModelList() {
        return horizontalProductScrollModelList;
    }
    public void setHorizontalProductScrollModelList(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }
    //////////////// Horizontal Product layout
// i think there is no need to make two constructors for horizontal model
//todo:grid Layout will have 2 layouts on click more:
    //todo: such as gridView kind of layout and wishlist kind of layout

    //////////////// Grid Product View layout
    private String GridViewTitle;
    private List<GridProductModel> gridProductModelList;

    public HomePageModel(int type,String gridViewTitle,List<GridProductModel> gridProductModelList) {

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
