package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapters.ProductDescriptionAdapter;
import com.example.myapplication.Adapters.ProductImagesAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

 public class ProductDetailsActivity extends AppCompatActivity {
    private ViewPager productImagesViewPager;
    private TabLayout viewPagerIndicator;

    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;

    //////////////////// rating layout
    private LinearLayout rateNowContainer;
    private TextView ratingText;
    //////////////////// rating layout

    private boolean ALREADY_ADDED_TO_WISHLIST=false;
    private ImageView wishlist_btn;

     //////////////////// buy Now layout
     private LinearLayout shipping_details_layout,shipping_details_layout_background,address_container;
     private TextView butNowBtn;
     private int count=0;
     //////////////////// buy Now layout

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.black_overlay2), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);



        productImagesViewPager=findViewById(R.id.product_images_view_pager);
        viewPagerIndicator=findViewById(R.id.viewPager_indicator);
        wishlist_btn=findViewById(R.id.wishlist_btn);

        productDetailsViewPager=findViewById(R.id.product_details_viewPager);
        productDetailsTabLayout=findViewById(R.id.product_detalis_tabLayout);

        List<Integer> productImages =new ArrayList<>();
        productImages.add(R.drawable.sampleproductone);
        productImages.add(R.drawable.sampleproducttwo);
        productImages.add(R.drawable.unknown1);
        productImages.add(R.drawable.sampleproducttwo);
        productImages.add(R.drawable.sampleproductthree);
        ProductImagesAdapter productImagesAdapter =new ProductImagesAdapter(productImages);
        productImagesViewPager.setAdapter(productImagesAdapter);

        viewPagerIndicator.setupWithViewPager(productImagesViewPager,true);
        for(int i=0; i < viewPagerIndicator.getTabCount() - 1; i++) {
            View tab = ((ViewGroup) viewPagerIndicator.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 10, 0);
            tab.requestLayout();
        }



        wishlist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ALREADY_ADDED_TO_WISHLIST){
                    ALREADY_ADDED_TO_WISHLIST =false;
                    wishlist_btn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#14000000")));
//                    Toast.makeText(ProductDetailsActivity.this, "Removed from Wishlist", Toast.LENGTH_SHORT).show();
                }else{
                    ALREADY_ADDED_TO_WISHLIST =true;
                    wishlist_btn.setImageTintList(getResources().getColorStateList(R.color.lightOrange,null));
//                    Toast toast = Toast.makeText(ProductDetailsActivity.this, "Added to Wishlist", Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.BOTTOM, 0, 0);
//                    toast.show();


                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));
                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText("Added to Wishlist");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.setGravity(Gravity.BOTTOM, 0, 180);
                    toast.show();
                }

            }
        });

        productDetailsViewPager.setAdapter(new ProductDescriptionAdapter(getSupportFragmentManager() ,productDetailsTabLayout.getTabCount()));
        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //////////////////// rating layout
        rateNowContainer=findViewById(R.id.rate_now_container);
        ratingText=findViewById(R.id.rating_text);
        for(int x=0; x<rateNowContainer.getChildCount() ;x++){
            final int starPosition =x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(starPosition);
                }
            });
        }
        //////////////////// rating layout


        //////////////////// buy Now layout


        butNowBtn= findViewById(R.id.buy_now_text_btn);
        shipping_details_layout=findViewById(R.id.layout_shipping_address);
        shipping_details_layout_background=findViewById(R.id.layout_shipping_background);
        address_container=findViewById(R.id.address_container);

        shipping_details_layout.setVisibility(View.INVISIBLE);
        shipping_details_layout_background.setVisibility(View.INVISIBLE);
//        isUp = false;
         butNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count == 1) {
                    Animation anim_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                    shipping_details_layout_background.startAnimation(anim_in);
                    shipping_details_layout_background.setVisibility(View.VISIBLE);
                    slideUp(shipping_details_layout);

                }
                else{
                    count=0;
                    shipping_details_layout.setVisibility(View.INVISIBLE);
                    shipping_details_layout_background.setVisibility(View.INVISIBLE);
                    Intent intent=new Intent(ProductDetailsActivity.this,MainActivity.class);
                    startActivity(intent);
                }

//                onSlideViewButtonClick(shipping_details_layout);

            }
        });

        address_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                shipping_details_layout.setVisibility(View.INVISIBLE);
                shipping_details_layout_background.setVisibility(View.INVISIBLE);
                Intent intent=new Intent(ProductDetailsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        shipping_details_layout_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                Animation anim_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                shipping_details_layout_background.startAnimation(anim_out);
                shipping_details_layout_background.setVisibility(View.INVISIBLE);
                slideDown(shipping_details_layout);
            }
        });
        //////////////////// buy Now layout


    }

    private void setRating(int starPosition) {
        for (int x = 0;x < rateNowContainer.getChildCount(); x++){
            ImageView starBtn =(ImageView) rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#33000000")));
            if (x <= starPosition) {
                if(starPosition<=4){
                    starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF91E35E")));
                    if(starPosition==4){
                        ratingText.setText("Amazing");
                    }
                    if(starPosition==3){
                        ratingText.setText("Good");
                    }
                }if (starPosition<=2){
                    starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFC400")));
                        ratingText.setText("Satisfied");
                }
                if (starPosition<=1){
                    starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFDD2C00")));
                    if(starPosition==1){
                        ratingText.setText("Fair");
                    }
                    if(starPosition==0){
                        ratingText.setText("Poor");
                    }
                }
            }
        }
    }


     public void slideUp(View view){
         view.setVisibility(View.VISIBLE);
         TranslateAnimation animate = new TranslateAnimation(
                 0,                 // fromXDelta
                 0,                 // toXDelta
                 view.getHeight(),  // fromYDelta
                 0);                // toYDelta
         animate.setDuration(500);
         animate.setFillAfter(true);
         view.startAnimation(animate);
     }

     // slide the view from its current position to below itself
     public void slideDown(View view){
         TranslateAnimation animate = new TranslateAnimation(
                 0,                 // fromXDelta
                 0,                 // toXDelta
                 0,                 // fromYDelta
                 view.getHeight()); // toYDelta
         animate.setDuration(500);
         animate.setFillAfter(true);
         view.startAnimation(animate);
     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.without_cotification_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.main_search) {
            return true;
        }
        if (id == R.id.main_wishlist) {
            Intent wishlistIntent=new Intent(ProductDetailsActivity.this,WishlistActivity.class);
            startActivity(wishlistIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }
        if (id == R.id.main_shopping_cart) {
            Intent cartIntent=new Intent(ProductDetailsActivity.this,MyCartActivity.class);
            startActivity(cartIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }
        else if (id == android.R.id.home) {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
