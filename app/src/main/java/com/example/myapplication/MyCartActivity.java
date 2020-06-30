package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.example.myapplication.Fragments.AddNewAddressFragment;
import com.example.myapplication.Fragments.DeliveryFragment;
import com.example.myapplication.Models.MyCartItemModel;
import com.example.myapplication.Adapters.MyCartAdapter;
import com.example.myapplication.Models.WishlistModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MyCartActivity extends AppCompatActivity  {

    public static TextView cartTitle;
    public static LinearLayout priceDetailsLL,continueBtnLL;
    private Dialog loadingDialog;
    public static FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();


    private RecyclerView cartItemRecyclerView ,wishlistCartRecylerView;
    public static MyCartAdapter cartAdapter;
    private int no_of_items;
    private TextView cartContinueBtn;

    public static TextView totalAmount, totalItems,totalItemsPrice,totalItemsDiscount,couponDiscountTxt,totalCouponDiscount,shippingCharges,subTotal;

    private LinearLayout shipping_details_layout,shipping_details_layout_background,address_container;


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
    Context  context;



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

        //////loadingDialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //////loadingDialog

        cartItemRecyclerView=findViewById(R.id.cart_items_recyclerView);
        cartContinueBtn = findViewById(R.id.cart_continue_btn);
        totalAmount =findViewById(R.id.cart_total_amount);

        priceDetailsLL=findViewById(R.id.PriceDetailsLL);
        continueBtnLL=findViewById(R.id.continueLL);

        totalItems=findViewById(R.id.total_items);
        totalItemsPrice=findViewById(R.id.total_items_price);
        totalItemsDiscount=findViewById(R.id.saved_or_discount_price);
        couponDiscountTxt=findViewById(R.id.couponDiscountText);
        totalCouponDiscount=findViewById(R.id.coupon_discount_price);
        shippingCharges=findViewById(R.id.shipping_price);
        subTotal=findViewById(R.id.total_or_subTotal_price);
//        totalItemsTxtActivity=findViewById(R.id.totalItemsTxtActivity);


//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
//        priceDetailsLL.setAnimation(animation);




        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);



        cartAdapter =new MyCartAdapter(DBqueries.cartItemModelList,getApplicationContext());
        cartItemRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();



//        int totalItemPrice = 0;
//        for (int x = 0; x<MyCartAdapter.myCartItemModelList.size(); x++){
//
//            if (MyCartAdapter.myCartItemModelList.get(x).isInStock()){
//                totalItemPrice = totalItemPrice + Integer.parseInt(MyCartAdapter.myCartItemModelList.get(x).getProductPrice());
//            }
//        }
//        MyCartActivity.totalAmount.setText("₹"+totalItemPrice);

//        Toast.makeText(this, totalItemPrice, Toast.LENGTH_SHORT).show();





















////////wishListDialog
        wishlistCartRecylerView=findViewById(R.id.wishlistCartRecyclerView);
        LinearLayoutManager layoutManager2=new LinearLayoutManager(this);
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        wishlistCartRecylerView.setLayoutManager(layoutManager2);
        List<WishlistModel> wishlistModelList =new ArrayList<>();

//        wishlistModelList.add(new WishlistModel(R.drawable.unknown1,"200ml Jug","Easy to handle grip, Pink color","₹1600/pc","₹1800","(10% OFF)"));
//        wishlistModelList.add(new WishlistModel(R.drawable.unknown3,"200ml Jug","Easy to handle grip, Pink color","₹170","₹160","₹10"));

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

////////wishListDialog

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

//        cartContinueBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText(MyCartActivity.this, "vdgv", Toast.LENGTH_SHORT).show();
//                count++;
//                if(newUser){
//                    if(count==1){
//                        newAdd.setVisibility(View.VISIBLE);
//                        new_shipping_details_layout_background.startAnimation(anim_in);
//                        new_shipping_details_layout_background.setVisibility(View.VISIBLE);
//                        slideUp(new_shipping_details_layout);
//                        new_shipping_details_layout.startAnimation(bounce);
//
//                    }
//                    else if(count==2){
//                        newUser=false;
//                        newAdd.setVisibility(View.GONE);
//                        new_shipping_details_layout.setVisibility(View.GONE);
//                        new_shipping_details_layout_background.setVisibility(View.GONE);
////                        Toast.makeText(MyCartActivity.this, "nkn", Toast.LENGTH_SHORT).show();
//                        slideUp(shipping_details_layout);
//                        shipping_details_layout_background.startAnimation(anim_in);
//                        shipping_details_layout_background.setVisibility(View.VISIBLE);
//                        shipping_details_layout.setVisibility(View.VISIBLE);
//
//                    }else{
//                        count=0;
//                        newAdd.setVisibility(View.INVISIBLE);
//                        new_shipping_details_layout.setVisibility(View.INVISIBLE);
//                        new_shipping_details_layout_background.setVisibility(View.INVISIBLE);
//                        shipping_details_layout.setVisibility(View.INVISIBLE);
//                        shipping_details_layout_background.setVisibility(View.INVISIBLE);
//                        Intent intent=new Intent(MyCartActivity.this,AddAddressActivity.class);
//                        startActivity(intent);
//                    }
//
//                }else{
//                    if(count==1){
//                        slideUp(shipping_details_layout);
//                        shipping_details_layout_background.startAnimation(anim_in);
//                        shipping_details_layout_background.setVisibility(View.VISIBLE);
//                        shipping_details_layout.setVisibility(View.VISIBLE);
//
//                    }else {
//                        count=0;
////                        Toast.makeText(MyCartActivity.this, "b c", Toast.LENGTH_SHORT).show();
//                        shipping_details_layout.setVisibility(View.INVISIBLE);
//                        shipping_details_layout_background.setVisibility(View.INVISIBLE);
//                        Intent intent=new Intent(MyCartActivity.this,AddAddressActivity.class);
//                        startActivity(intent);
//                    }
//                }
////                if (count == 1) {
////                    if (!newUser){
////                        shipping_details_layout_background.startAnimation(anim_in);
////                        shipping_details_layout_background.setVisibility(View.VISIBLE);
////                        slideUp(shipping_details_layout);
////
////                    }
////                    else{
////                        new_shipping_details_layout_background.startAnimation(anim_in);
////                        new_shipping_details_layout_background.setVisibility(View.VISIBLE);
////                        slideUp(new_shipping_details_layout);
////                        Animation bounce = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
////                        new_shipping_details_layout.startAnimation(bounce);
////
////                    }
////
////                }
////
////                else{
////                    new_shipping_details_layout.setVisibility(View.GONE);
////                    new_shipping_details_layout_background.setVisibility(View.GONE);
////                    shipping_details_layout.setVisibility(View.GONE);
////                    shipping_details_layout_background.setVisibility(View.GONE);
////                    Intent intent=new Intent(MyCartActivity.this,AddAddressActivity.class);
////                    startActivity(intent);
////                    count=0;
////
////
////                }
////
//////                onSlideViewButtonClick(shipping_details_layout);
////
//
//            }
//        });

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


    public Context getContext() {
        return context;
    }

    @Override
    protected void onStart() {
        super.onStart();
        no_of_items= DBqueries.cartList.size();
        cartTitle.setText("Cart ("+no_of_items+")");

        if(DBqueries.cartItemModelList.size() == 0){
            DBqueries.cartList.clear();
            DBqueries.loadCartList(this,loadingDialog,true);
        }
        else{
            MyCartActivity.continueBtnLL.setVisibility(View.VISIBLE);
            MyCartActivity.priceDetailsLL.setVisibility(View.VISIBLE);
            loadingDialog.dismiss();
        }

//
//        if (no_of_items>0){
//            continueBtnLL.setVisibility(View.VISIBLE);
//            priceDetailsLL.setVisibility(View.VISIBLE);
//        } else  {
//            continueBtnLL.setVisibility(View.GONE);
//            priceDetailsLL.setVisibility(View.GONE);
//        }

        int totalItems = 0;
        int totalItemPrice = 0;
        int discountItemsPrice=0;
        int discountInitialItemsPrice=0;
        long couponAvailable=0;

        for (int x = 0; x<MyCartAdapter.myCartItemModelList.size(); x++){

            if (MyCartAdapter.myCartItemModelList.get(x).isInStock()){
                totalItems++;
                totalItemPrice = totalItemPrice + Integer.parseInt(MyCartAdapter.myCartItemModelList.get(x).getProductPrice());
                discountInitialItemsPrice=discountInitialItemsPrice+Integer.parseInt(MyCartAdapter.myCartItemModelList.get(x).getProductInitialPrice());
                discountItemsPrice=discountInitialItemsPrice-totalItemPrice;
                couponAvailable=couponAvailable+Long.parseLong(String.valueOf(MyCartAdapter.myCartItemModelList.get(x).getFreeCouponsAvailable()));
            }
        }

//
//        Bundle bundle = new Bundle();
//        bundle.putString("params", "₹"+totalItemPrice);
//// set MyFragment Arguments
//        AddNewAddressFragment myObj = new AddNewAddressFragment();
//        myObj.setArguments(bundle);

        if (totalItems==1){
            MyCartActivity.totalItems.setText("Price ( "+totalItems+" Item )");
        }else{
            MyCartActivity.totalItems.setText("Price ( "+totalItems+" Items )");
        }
        MyCartActivity.totalItemsPrice.setText("₹"+discountInitialItemsPrice);
        MyCartActivity.totalItemsDiscount.setText("₹"+discountItemsPrice);
        //todo: amount for coupon
        if (couponAvailable!=0){
            MyCartActivity.totalCouponDiscount.setText("Apply Coupon");
            MyCartActivity.couponDiscountTxt .setVisibility(View.VISIBLE);
            MyCartActivity.totalCouponDiscount.setVisibility(View.VISIBLE);
        }else{
            MyCartActivity.totalCouponDiscount.setText("");
            MyCartActivity.couponDiscountTxt .setVisibility(View.GONE);
            MyCartActivity.totalCouponDiscount.setVisibility(View.GONE);
        }
        //todo: amount for delivery
        if (totalItemPrice > 20000){
            MyCartActivity.shippingCharges.setText("Free");
        }
        else{
            MyCartActivity.shippingCharges.setText("Extra*");
        }
        MyCartActivity.subTotal.setText("₹"+totalItemPrice);
        MyCartActivity.totalAmount.setText("₹"+totalItemPrice);

        Toast.makeText(this, totalAmount.getText(), Toast.LENGTH_SHORT).show();

        if (totalItemPrice==0){
            priceDetailsLL.setVisibility(View.GONE);
            continueBtnLL.setVisibility(View.GONE);
        }else {
            priceDetailsLL.setVisibility(View.VISIBLE);
            continueBtnLL.setVisibility(View.VISIBLE);

        }


        cartContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Users")
                        .document(FirebaseAuth.getInstance().getUid())
                        .collection("UserData")
                        .document("Addresses")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()){
                                    if((long) task.getResult().get("addressListSize") == 0){
                                        AddNewAddressFragment bottomSheet = new AddNewAddressFragment();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("params", totalAmount.getText().toString());
                                        bottomSheet.setArguments(bundle);
                                        bottomSheet.show(getSupportFragmentManager(),"TAG");
                                    }
                                    else {
                                        Intent intent =new Intent(getApplicationContext(),OrderSummaryActivity.class);
                                        startActivity(intent);
                                    }
                                }
                                 else{
                                    String error = task.getException().getMessage();
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


//                if (!openSelectedAdd){
//                    if (DBqueries.addressesModelList.size() == 0) {
//    //                    DBqueries.loadAddress(getContext(), loadingDialog);
//                        AddNewAddressFragment bottomSheet = new AddNewAddressFragment();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("params", totalAmount.getText().toString());
//                        bottomSheet.setArguments(bundle);
//                        bottomSheet.show(getSupportFragmentManager(),"TAG");
//                    }
////                    else{
////                    }
//                }
            }

        });

//        if  (openSelectedAdd){
//            DeliveryFragment bottomSheet2 = new DeliveryFragment();
//            bottomSheet2.show(getSupportFragmentManager(),"TAG");
//        }
//                    AddNewAddressFragment bottomSheet = new AddNewAddressFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("params", totalAmount.getText().toString());
//                    bottomSheet.setArguments(bundle);
//                    bottomSheet.show(getSupportFragmentManager(),"TAG");

//                for (int x =0 ;x<DBqueries.cartItemModelList.size(); x++){
//
//                    MyCartItemModel cartItemModel = DBqueries.cartItemModelList.get(x);
//                    if(cartItemModel.isInStock()){
////                        OrderSummaryActivity.cartItemModelList.add(cartItemModel);
//                    }
//                }


//        MyCartActivity.totalAmount.setText("₹"+totalItemPrice);
//        if (totalItems==1){
//            MyCartActivity.totalItemsTxtActivity.setText("Price ( "+totalItemPrice+" Item )");
//        }else{
//            MyCartActivity.totalItemsTxtActivity.setText("Price ( "+totalItemPrice+" Items )");
//        }


    }


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