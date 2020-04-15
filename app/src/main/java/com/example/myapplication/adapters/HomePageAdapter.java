package com.example.myapplication.adapters;

import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Models.GridProductModel;
import com.example.myapplication.Models.HomePageModel;
import com.example.myapplication.Models.HorizontalProductScrollModel;
import com.example.myapplication.Models.SliderModel;
import com.example.myapplication.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {
    private List<HomePageModel> homePageModelList;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()){
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_AD_BANNER;
            case 2:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch(viewType){
            case HomePageModel.BANNER_SLIDER:
                View bannerSliderView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sliding_banner_layout,viewGroup,false );
                return new BannerSliderViewHolder(bannerSliderView);

            case HomePageModel.STRIP_AD_BANNER:
                View stripAdView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.strip_ad_layout,viewGroup,false );
                return new StripAdBannerViewHolder(stripAdView);
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalProductView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_layout,viewGroup,false );
                return new HorizontalProductViewHolder(horizontalProductView);
            case HomePageModel.GRID_PRODUCT_VIEW:
                View gridProductView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_product_layout,viewGroup,false );
                return new GridViewProductViewHolder(gridProductView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (homePageModelList.get(position).getType()){
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList=homePageModelList.get(position).getSliderModelList();
                ((BannerSliderViewHolder)viewHolder).setBannerSliderViewPager(sliderModelList);
                break;
            case HomePageModel.STRIP_AD_BANNER:
                int resource = homePageModelList.get(position).getResource();
                String color= homePageModelList.get(position).getBackgroundColor();
                ((StripAdBannerViewHolder)viewHolder).setStripAd(resource,color);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String title = homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList= homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((HorizontalProductViewHolder)viewHolder).setHorizontalProductLayout(horizontalProductScrollModelList,title);
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridViewTitle = homePageModelList.get(position).getGridViewTitle();
                List<GridProductModel> gridProductModelList= homePageModelList.get(position).getGridProductModelList();
                ((GridViewProductViewHolder)viewHolder).setGridViewLayout(gridProductModelList,gridViewTitle);
                break;

            default:
                return;
        }


    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public class BannerSliderViewHolder extends RecyclerView.ViewHolder{

        private ViewPager bannerSliderViewPager;
        private int currentPage=2;
        private Timer timer;
        final private long DELAY_TIME=3000;
        final private long PERIOD_TIME=3000;
        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager=itemView.findViewById(R.id.banner_slider_viewPager);
        }
        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList){


            SliderAdapter sliderAdapter = new SliderAdapter (sliderModelList);
            bannerSliderViewPager.setAdapter(sliderAdapter);
            bannerSliderViewPager.setClipToPadding(false);
            bannerSliderViewPager.setPageMargin(20);
            bannerSliderViewPager.setCurrentItem(currentPage);


            ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage=position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE){
                        pageLooper( sliderModelList);
                    }
                }
            };
            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
            startBannerSliderShow(sliderModelList);


            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLooper(sliderModelList);
                    stopBannerSliderShow();
                    if(event.getAction()==MotionEvent.ACTION_UP){
                        startBannerSliderShow(sliderModelList);
                    }
                    return false;
                }
            });

        }
        private void pageLooper(List<SliderModel> sliderModelList){
            if (currentPage== sliderModelList.size()-2){
                currentPage=2;
                bannerSliderViewPager.setCurrentItem(currentPage,false);
            }
            if (currentPage== 1){
                currentPage=sliderModelList.size()-3 ;
                bannerSliderViewPager.setCurrentItem(currentPage,false);
            }

        }
        private void startBannerSliderShow(final List<SliderModel> sliderModelList){
            final Handler handler =new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if(currentPage>=sliderModelList.size()){
                        currentPage=1;
                    }
                    bannerSliderViewPager.setCurrentItem(currentPage++,true);
                }
            };
            timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            },DELAY_TIME,PERIOD_TIME);
        }
        private void stopBannerSliderShow(){
            timer.cancel();
        }
    }

    public class StripAdBannerViewHolder extends RecyclerView.ViewHolder{
        private ImageView stripAdImage;
        private ConstraintLayout stripAdContainer;
        public StripAdBannerViewHolder(@NonNull View itemView) {
            super(itemView);
            stripAdImage = itemView.findViewById(R.id.strip_ad_image);
            stripAdContainer = itemView.findViewById(R.id.strip_ad_container);
        }
        private void setStripAd(int resource,String color){
            stripAdImage.setImageResource(resource);
            stripAdImage.setBackgroundColor(Color.parseColor(color));

        }
    }

    public class HorizontalProductViewHolder extends RecyclerView.ViewHolder{
        private TextView horizontalLayoutTitle;
        private TextView horizontalViewAllBtn;
        private RecyclerView horizontalRecyclerView;
        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalLayoutTitle=itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalViewAllBtn=itemView.findViewById(R.id.horizontal_scroll_layout_viewAll_btn);
            horizontalRecyclerView=itemView.findViewById(R.id.horizontal_scroll_layout_recyclerView);
        }
        private  void setHorizontalProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList,String title){
            horizontalLayoutTitle.setText(title);
            if (horizontalProductScrollModelList.size() > 8 ){
                horizontalViewAllBtn.setVisibility(View.VISIBLE);

            }else{
                horizontalViewAllBtn.setVisibility(View.INVISIBLE);
            }

            HorizontalProductScrollAdapter horizontalProductScrollAdapter=new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            LinearLayoutManager linearLayoutManager= new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayoutManager);

            horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }

    }

    public class GridViewProductViewHolder extends RecyclerView.ViewHolder {

        private TextView gridLayoutTitle;
        private TextView gridViewAllBtn;
        private GridView gridLayoutGridView;

        public GridViewProductViewHolder(@NonNull View itemView) {
            super(itemView);
            gridLayoutTitle = itemView.findViewById(R.id.grid_layout_title);
            gridViewAllBtn = itemView.findViewById(R.id.grid_layout_view_all_txt);
            gridLayoutGridView = itemView.findViewById(R.id.grid_product_layout_gridView);
        }

        private void setGridViewLayout(List<GridProductModel> gridProductModelList, String gridViewTitle) {
            gridLayoutTitle.setText(gridViewTitle);
            gridLayoutGridView.setAdapter(new GridProductLayoutAdapter(gridProductModelList));
        }
    }
}
