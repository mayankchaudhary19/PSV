package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapters.WishlistAdapter;
import com.example.myapplication.Models.MyCartItemModel;
import com.example.myapplication.Adapters.MyCartAdapter;
import com.example.myapplication.Models.WishlistModel;

import java.util.ArrayList;
import java.util.List;

public class MyCartActivity extends AppCompatActivity  {
    private TextView cartTitle;
    private RecyclerView cartItemRecyclerView ,wishlistCartRecylerView;
    ;
    private int no_of_items;
    private LinearLayout shipping_details_layout,shipping_details_layout_background,address_container;
    private TextView cartContinueBtn;

    private LinearLayout new_shipping_details_layout,new_shipping_details_layout_background;
//    private ConstraintLayout new_shipping_details_layout
    private Button newAddressSaveBtn,changeOrAddAddressBtn;
    private boolean newUser=true;
    private int count=0;
    private ScrollView newAdd;
    public static final int SELECT_ADDRESS=0;
    private LinearLayout wishlsitCartBackground;
    private ConstraintLayout wishlistCartLayout;
    private ConstraintLayout activityMyCartLayout;
    private static boolean isSpinnerClosed;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        cartTitle= findViewById(R.id.cart_activity_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.black_overlay2), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        cartItemRecyclerView=findViewById(R.id.cart_items_recyclerView);

//        cartItemRecyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MyCartActivity.this, "jjbj", Toast.LENGTH_SHORT).show();
//            }
//        });



        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);
        List<MyCartItemModel> myCartItemModelList =new ArrayList<>();
        myCartItemModelList.add(new MyCartItemModel(0,R.drawable.sampleproductone,"Round container [small height] with anti water leakage lid ","Transparent color, with dotted texture","₹150","₹160","[ 10% OFF ]"));
        myCartItemModelList.add(new MyCartItemModel(0,R.drawable.sampleproductone,"Square Bowl [small size]","Transparent color, with dotted texture","₹170","₹160","₹10"));
        myCartItemModelList.add(new MyCartItemModel(0,R.drawable.sampleproductone,"Square Bowl [small size]","Transparent color, with dotted texture","₹180","₹160","₹10"));
        myCartItemModelList.add(new MyCartItemModel(1,"Price ( 3 item )","₹380","₹130","Extra Charges*","250"));

        MyCartAdapter myCartAdapter =new MyCartAdapter(myCartItemModelList,getApplicationContext());
        no_of_items= myCartItemModelList.size()-1;
        cartTitle.setText("Cart ("+no_of_items+")");

        cartItemRecyclerView.setAdapter(myCartAdapter);
        myCartAdapter.notifyDataSetChanged();

        wishlistCartRecylerView=findViewById(R.id.wishlistCartRecyclerView);
        LinearLayoutManager layoutManager2=new LinearLayoutManager(this);
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        wishlistCartRecylerView.setLayoutManager(layoutManager2);
        List<WishlistModel> wishlistModelList =new ArrayList<>();

        wishlistModelList.add(new WishlistModel(R.drawable.unknown1,"200ml Jug","Easy to handle grip, Pink color","₹1600/pc","₹1800","(10% OFF)"));
        wishlistModelList.add(new WishlistModel(R.drawable.unknown3,"200ml Jug","Easy to handle grip, Pink color","₹170","₹160","₹10"));
        wishlistModelList.add(new WishlistModel(R.drawable.unknown2,"200ml Jug","Easy to handle grip, Pink color","₹170","₹160","₹10"));
        wishlistModelList.add(new WishlistModel(R.drawable.unknown4,"200ml Jug","Easy to handle grip, Pink color","₹170","₹160","₹10"));
        wishlistModelList.add(new WishlistModel(R.drawable.unknown5,"200ml Jug","Easy to handle grip, Pink color","₹170","₹160","₹10"));
        wishlistModelList.add(new WishlistModel(R.drawable.unknown1,"200ml Jug","Easy to handle grip, Pink color","₹170","₹160","₹10"));
        wishlistModelList.add(new WishlistModel(R.drawable.unknown4,"200ml Jug","Easy to handle grip, Pink color","₹170","₹160","₹10"));
        wishlistModelList.add(new WishlistModel(R.drawable.unknown2,"200ml Jug","Easy to handle grip, Pink color","₹170","₹160","₹10"));

        WishlistAdapter wishlistAdapter =new WishlistAdapter(wishlistModelList,getApplicationContext(),false,true);

        wishlistCartRecylerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();

//        activityMyCartLayout=findViewById(R.id.activity_my_cartLayout);
//        activityMyCartLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MyCartActivity.this, "kdenn", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        cartItemRecyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MyCartActivity.this, "hushs", Toast.LENGTH_SHORT).show();
//            }
//        });


        cartContinueBtn= findViewById(R.id.cart_continue_btn);
        shipping_details_layout=findViewById(R.id.layout_shipping_address);

        shipping_details_layout_background=findViewById(R.id.layout_shipping_background);
        address_container=findViewById(R.id.address_container);

        wishlistCartLayout=findViewById(R.id.wishliatCartLayout);
        wishlsitCartBackground=findViewById(R.id.wishlistCartBackground);



        ///////////new Address
        newAddressSaveBtn= findViewById(R.id.save_new_address);
        new_shipping_details_layout=findViewById(R.id.layout_new_shipping_address);
        new_shipping_details_layout_background=findViewById(R.id.layout_new_shipping_background);

        newAdd=findViewById(R.id.newAdd_scroll);

        new_shipping_details_layout.setVisibility(View.INVISIBLE);
        new_shipping_details_layout_background.setVisibility(View.INVISIBLE);

        shipping_details_layout.setVisibility(View.INVISIBLE);
        shipping_details_layout_background.setVisibility(View.INVISIBLE);

        wishlsitCartBackground.setVisibility(View.INVISIBLE);
        wishlistCartLayout.setVisibility(View.INVISIBLE);

        newAdd.setVisibility(View.INVISIBLE);


        changeOrAddAddressBtn=findViewById(R.id.change_add_address);
        changeOrAddAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUser=false;
                count=0;
                shipping_details_layout.setVisibility(View.INVISIBLE);
                shipping_details_layout_background.setVisibility(View.INVISIBLE);
                Intent intent =new Intent(MyCartActivity.this,MyAddressActivity.class);
                intent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(intent);

            }
        });

//        new_shipping_details_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MyCartActivity.this, "vjgv", Toast.LENGTH_SHORT).show();
//            }
//        });

//        isUp = false;
        final Animation anim_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        final Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
        final Animation anim_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);

        cartContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MyCartActivity.this, "vdgv", Toast.LENGTH_SHORT).show();
                count++;
                if(newUser){
                    if(count==1){
                        newAdd.setVisibility(View.VISIBLE);
                        new_shipping_details_layout_background.startAnimation(anim_in);
                        new_shipping_details_layout_background.setVisibility(View.VISIBLE);
                        slideUp(new_shipping_details_layout);
                        new_shipping_details_layout.startAnimation(bounce);

                    }
                    else if(count==2){
                        newUser=false;
                        newAdd.setVisibility(View.GONE);
                        new_shipping_details_layout.setVisibility(View.GONE);
                        new_shipping_details_layout_background.setVisibility(View.GONE);
//                        Toast.makeText(MyCartActivity.this, "nkn", Toast.LENGTH_SHORT).show();
                        slideUp(shipping_details_layout);
                        shipping_details_layout_background.startAnimation(anim_in);
                        shipping_details_layout_background.setVisibility(View.VISIBLE);
                        shipping_details_layout.setVisibility(View.VISIBLE);

                    }else{
                        count=0;
                        newAdd.setVisibility(View.INVISIBLE);
                        new_shipping_details_layout.setVisibility(View.INVISIBLE);
                        new_shipping_details_layout_background.setVisibility(View.INVISIBLE);
                        shipping_details_layout.setVisibility(View.INVISIBLE);
                        shipping_details_layout_background.setVisibility(View.INVISIBLE);
                        Intent intent=new Intent(MyCartActivity.this,AddAddressActivity.class);
                        startActivity(intent);
                    }

                }else{
                    if(count==1){
                        slideUp(shipping_details_layout);
                        shipping_details_layout_background.startAnimation(anim_in);
                        shipping_details_layout_background.setVisibility(View.VISIBLE);
                        shipping_details_layout.setVisibility(View.VISIBLE);

                    }else {
                        count=0;
//                        Toast.makeText(MyCartActivity.this, "b c", Toast.LENGTH_SHORT).show();
                        shipping_details_layout.setVisibility(View.INVISIBLE);
                        shipping_details_layout_background.setVisibility(View.INVISIBLE);
                        Intent intent=new Intent(MyCartActivity.this,AddAddressActivity.class);
                        startActivity(intent);
                    }
                }
//                if (count == 1) {
//                    if (!newUser){
//                        shipping_details_layout_background.startAnimation(anim_in);
//                        shipping_details_layout_background.setVisibility(View.VISIBLE);
//                        slideUp(shipping_details_layout);
//
//                    }
//                    else{
//                        new_shipping_details_layout_background.startAnimation(anim_in);
//                        new_shipping_details_layout_background.setVisibility(View.VISIBLE);
//                        slideUp(new_shipping_details_layout);
//                        Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
//                        new_shipping_details_layout.startAnimation(bounce);
//
//                    }
//
//                }
//
//                else{
//                    new_shipping_details_layout.setVisibility(View.GONE);
//                    new_shipping_details_layout_background.setVisibility(View.GONE);
//                    shipping_details_layout.setVisibility(View.GONE);
//                    shipping_details_layout_background.setVisibility(View.GONE);
//                    Intent intent=new Intent(MyCartActivity.this,AddAddressActivity.class);
//                    startActivity(intent);
//                    count=0;
//
//
//                }
//
////                onSlideViewButtonClick(shipping_details_layout);
//

            }
        });

        address_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                shipping_details_layout.setVisibility(View.GONE);
                shipping_details_layout_background.setVisibility(View.GONE);
                Intent intent=new Intent(MyCartActivity.this,AddAddressActivity.class);
                startActivity(intent);
            }
        });
        shipping_details_layout_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!newUser)
                   count=0;
                shipping_details_layout_background.startAnimation(anim_out);
                shipping_details_layout_background.setVisibility(View.INVISIBLE);
                slideDown(shipping_details_layout);
                shipping_details_layout.setVisibility(View.INVISIBLE);

            }
        });


        new_shipping_details_layout_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                newUser=true;
                newAdd.setVisibility(View.INVISIBLE);
                new_shipping_details_layout_background.startAnimation(anim_out);
                new_shipping_details_layout_background.setVisibility(View.INVISIBLE);
                newAdd.fullScroll(ScrollView.FOCUS_UP);
                slideDown(new_shipping_details_layout);
                new_shipping_details_layout.setVisibility(View.INVISIBLE);

            }
        });
        newAddressSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=0;
                newUser=false;
                newAdd.setVisibility(View.INVISIBLE);
                new_shipping_details_layout.setVisibility(View.GONE);
                new_shipping_details_layout_background.setVisibility(View.GONE);

                shipping_details_layout.setVisibility(View.VISIBLE);
                slideUp(shipping_details_layout);
//                Animation anim_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                shipping_details_layout_background.startAnimation(anim_in);
                shipping_details_layout_background.setVisibility(View.VISIBLE);

//                Intent intent=new Intent(MyCartActivity.this,AddAddressActivity.class);
//                startActivity(intent);

            }
        });

//////////////WishlistCartLayout

        wishlsitCartBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideDown2(wishlistCartLayout);
                wishlsitCartBackground.startAnimation(anim_out);
                wishlsitCartBackground.setVisibility(View.INVISIBLE);
                wishlistCartLayout.setVisibility(View.INVISIBLE);
            }
        });
//
//        wishlistCartLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });



    }

//    @Override
//    public void onClick(final boolean value) {
//            cartItemRecyclerView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(MyCartActivity.this, "hhb", Toast.LENGTH_SHORT).show();
//                    if (value==true){
//                        Toast.makeText(MyCartActivity.this, "e ge", Toast.LENGTH_SHORT).show();
//                         isSpinnerClosed=true;
//
//                    }
//                }
//            });

//    }


    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(600);
//        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    public void slideUp2(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(490);
//        animate.setFillAfter(true);
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
//        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
    public void slideDown2(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(400);
//        animate.setFillAfter(true);
        view.startAnimation(animate);
    }
//    public void onSlideViewButtonClick(View view) {
//        Animation anim_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
//        Animation anim_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
//
////        if (isUp) {
////            slideDown(shipping_details_layout);
////            shipping_details_layout_background.startAnimation(anim_out);
////            shipping_details_layout_background.setVisibility(View.INVISIBLE);
//////            cartContinueBtn.setText("Slide up");
////        } else {
//            shipping_details_layout_background.startAnimation(anim_in);
//            shipping_details_layout_background.setVisibility(View.VISIBLE);
//            slideUp(shipping_details_layout);
////            cartContinueBtn.setText("Slide down");
////        }
////        isUp = !isUp;
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.main_search) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }
        if (id == R.id.main_wishlist) {
            slideUp2(wishlistCartLayout);
            final Animation anim_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
            wishlsitCartBackground.startAnimation(anim_in);
            wishlsitCartBackground.setVisibility(View.VISIBLE);
            wishlistCartLayout.setVisibility(View.VISIBLE);



//            Intent wishlistIntent=new Intent(MyCartActivity.this,WishlistActivity.class);
//            startActivity(wishlistIntent);
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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