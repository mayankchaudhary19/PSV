package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapters.ProductDescriptionAdapter;
import com.example.myapplication.Adapters.ProductImagesAdapter;
import com.example.myapplication.Adapters.RewardAdapter;
import com.example.myapplication.Models.MyCartItemModel;
import com.example.myapplication.Models.ProductSpecificationModel;
import com.example.myapplication.Models.RewardModel;
import com.example.myapplication.Models.WishlistModel;
import com.example.myapplication.UserSession.LoginFragment;
import com.example.myapplication.UserSession.RegisterFragment;
import com.example.myapplication.UserSession.UserSessionActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import static com.example.myapplication.DBqueries.loadWishlist;
import static com.example.myapplication.UserSession.UserSessionActivity.registerFrag;


public class ProductDetailsActivity extends AppCompatActivity {

    public static boolean running_wishlist_query=false;
    public static boolean running_rated_query = false;
    public static boolean running_cart_query = false;
    private ViewPager productImagesViewPager;
    private TabLayout viewPagerIndicator;

    private FirebaseUser currentUser;

    private TextView productTitle;
    private TextView productSubtitle;
    private TextView productCategory;
    private TextView productSubcategory1;
    private TextView productSubcategory2;
    private TextView productSubcategory3;
    private TextView averageRatingMiniView;
    private TextView productPrice;
    private TextView productInitialPrice;

    private TextView rewardTitle;
    private TextView rewardBody;
    private ImageView rewardIcon;

    //////////////////// product Description
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;
    private ConstraintLayout productDescriptionTabContainer;
    private ConstraintLayout productDetailsOnlyContainer;
    private TextView productDetailsOnlyDesc;
    private TextView productDetailsOnlyCareDesc;
    private List<ProductSpecificationModel> productSpecificationModelList=new ArrayList<>();
    private String productDesc;
    private String productMaterialCareDesc;
    //////////////////// product Description


     //////////////////// rating layout
    public static int initialRating;
    public static LinearLayout rateNowContainer;
    public static TextView ratingText;
    private TextView totalRatings;
    private TextView totalRatingsFigure;
//    private long totalRatingValue;
    private TextView averageProductRating;
    private LinearLayout ratingsNoContainer;
    private LinearLayout ratingsProgressBarContainer;

    //////////////////// rating layout

    public static boolean ALREADY_ADDED_TO_WISHLIST=false;
    public static  ImageView wishlist_btn;

    public static boolean ALREADY_ADDED_TO_CART=false;


    //////////////////// buy Now layout
     private LinearLayout shipping_details_layout,shipping_details_layout_background,address_container;
     private TextView butNowBtn,addtoCartBtn;
     private int count=0;
     //////////////////// buy Now layout

     private FirebaseFirestore firebaseFirestore;
     private DocumentSnapshot documentSnapshot;
     public static String productId;
//     private Boolean wishlistToPD;



    //////////////////// coupon dialog
     private Button coupon_redeemBtn;
     private ConstraintLayout coupon_redemption_layout;
     public static TextView couponTile,couponExpiryDate,couponBody;
     public static ImageView couponIcon;
     private static RecyclerView couponRecyclerView;
     private static LinearLayout selectedCoupon;
     //////////////////// coupon dialog

     private Dialog signInDialog;
     private TextView registerLoginDialogText;
     private Dialog loadingDialog;




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

        initialRating = -1; // initial rating isliye 0 set nhi kiya kyuki fir zero matlab 1 star ho jayega

        ratingText=findViewById(R.id.rating_text);

        coupon_redeemBtn=findViewById(R.id.coupon_redemptionbutton);
        coupon_redemption_layout=findViewById(R.id.coupon_redemption_layout);

        addtoCartBtn=findViewById(R.id.add_to_cart_btn);
        butNowBtn= findViewById(R.id.buy_now_text_btn);
        shipping_details_layout=findViewById(R.id.layout_shipping_address);
        shipping_details_layout_background=findViewById(R.id.layout_shipping_background);
        address_container=findViewById(R.id.address_container);

        productImagesViewPager=findViewById(R.id.product_images_view_pager);
        viewPagerIndicator=findViewById(R.id.viewPager_indicator);
        wishlist_btn=findViewById(R.id.wishlist_btn);

         productTitle=findViewById(R.id.product_title);
         productSubtitle=findViewById(R.id.product_subtitle);
         productCategory=findViewById(R.id.product_category);
         productSubcategory1=findViewById(R.id.product_sub_category_one);
         productSubcategory2=findViewById(R.id.product_sub_category_two);
         productSubcategory3=findViewById(R.id.product_sub_category_three);
         averageRatingMiniView=findViewById(R.id.tv_product_rating_miniView);
         productPrice=findViewById(R.id.product_price);
         productInitialPrice=findViewById(R.id.product_initial_price);
         rewardTitle=findViewById(R.id.reward_title);
         rewardBody=findViewById(R.id.reward_body);
         rewardIcon=findViewById(R.id.reward_icon);

         productDetailsViewPager=findViewById(R.id.product_details_viewPager);
         productDetailsTabLayout=findViewById(R.id.product_detalis_tabLayout);
         productDescriptionTabContainer=findViewById(R.id.productDescriptionTabLayout);
         productDetailsOnlyContainer=findViewById(R.id.product_details_container);
         productDetailsOnlyDesc=findViewById(R.id.tv_product_desc);
         productDetailsOnlyCareDesc=findViewById(R.id.tv_product_material_care);

         totalRatings=findViewById(R.id.total_ratings);
         ratingsNoContainer=findViewById(R.id.ratings_number_container);
         ratingsProgressBarContainer=findViewById(R.id.ratings_progress_bar_container);
         averageProductRating=findViewById(R.id.average_product_rating);
         totalRatingsFigure=findViewById(R.id.totalRatingFigure);


         ////LoadingDialog
         loadingDialog=new Dialog(ProductDetailsActivity.this);
         loadingDialog.setContentView(R.layout.loading_progress_dialog);
         loadingDialog.setCancelable(false);
         loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
         loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         loadingDialog.show();
         ////LoadingDialog


        firebaseFirestore=FirebaseFirestore.getInstance();
        currentUser= FirebaseAuth.getInstance().getCurrentUser() ;

        final List<String> productImages =new ArrayList<>();

        productId=getIntent().getStringExtra("productId");

        firebaseFirestore.collection("PRODUCTS").document(productId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    documentSnapshot = task.getResult();
                    for (long x = 1; x < (long) documentSnapshot.get("noOfProductImages") + 1; x++) {
                        productImages.add(documentSnapshot.get("productImage" + x).toString());
                    }
                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                    productImagesViewPager.setAdapter(productImagesAdapter);

                    productTitle.setText(documentSnapshot.get("productTitle").toString());
                    productSubtitle.setText(documentSnapshot.get("productSubtitle").toString());

                    String productCategoryStr, productSubcategory1Str, productSubcategory2Str, productSubcategory3Str;
                    productCategoryStr = documentSnapshot.get("productCategory").toString();
                    productSubcategory1Str = documentSnapshot.get("productSubcategory1").toString();
                    productSubcategory2Str = documentSnapshot.get("productSubcategory2").toString();
                    productSubcategory3Str = documentSnapshot.get("productSubcategory3").toString();

                    if (productCategoryStr.isEmpty() || productCategoryStr.equals(" ")) {
                        productCategory.setVisibility(View.GONE);

                    } else productCategory.setText(productCategoryStr);

                    if (productSubcategory1Str.isEmpty() || productSubcategory1Str.equals(" ")) {
                        productSubcategory1.setVisibility(View.GONE);

                    } else productSubcategory1.setText(productSubcategory1Str);

                    if (productSubcategory2Str.isEmpty() || productSubcategory2Str.equals(" ")) {
                        productSubcategory2.setVisibility(View.GONE);

                    } else productSubcategory2.setText(productSubcategory2Str);

                    if (productSubcategory3Str.isEmpty() || productSubcategory3Str.equals(" ")) {
                        productSubcategory3.setVisibility(View.GONE);

                    } else productSubcategory3.setText(productSubcategory3Str);

                    averageRatingMiniView.setText(documentSnapshot.get("productRating").toString());

                    productPrice.setText("₹" + documentSnapshot.get("productPrice").toString() + "/pc");
                    productInitialPrice.setText("₹" + documentSnapshot.get("productInitialPrice").toString());

                    if ((boolean) documentSnapshot.get("couponOtherInfo")) {
                        rewardIcon.setVisibility(View.GONE);
                        rewardTitle.setText(documentSnapshot.get("couponTitle").toString());
                        rewardBody.setText(documentSnapshot.get("couponBody").toString());
                    } else {
                        rewardIcon.setVisibility(View.VISIBLE);
                        if ((long) documentSnapshot.get("freeCoupons") == 1) {
                            rewardTitle.setText(documentSnapshot.get("couponTitle").toString());
                        } else {
                            rewardTitle.setText((long) documentSnapshot.get("freeCoupons") + " Coupons Available");
                        }
                        rewardBody.setText(documentSnapshot.get("couponBody").toString());
                    }

                    if ((boolean) documentSnapshot.get("useTabLayout")) {
                        productDescriptionTabContainer.setVisibility(View.VISIBLE);
                        productDetailsOnlyContainer.setVisibility(View.GONE);
                        productDesc = documentSnapshot.get("productDescriptionBody").toString();
                        productMaterialCareDesc = documentSnapshot.get("productMaterialCareDescription").toString();

//                        ProductDescriptionFragment.productDescriptionData=documentSnapshot.get("productDescriptionBody").toString();
//                        ProductDescriptionFragment.productCareDescriptionData=documentSnapshot.get("productMaterialCareDescription").toString();
//                        ProductSpecificationFragment.productSpecificationModelList=new ArrayList<>();

                        for (long x = 1; x < (long) documentSnapshot.get("totalSpecificationTitles") + 1; x++) {
                            productSpecificationModelList.add(new ProductSpecificationModel(0, documentSnapshot.get("specTitle" + x).toString()));
                            for (long y = 1; y < (long) documentSnapshot.get("specTitle" + x + "TotalFields") + 1; y++) {
                                productSpecificationModelList.add(new ProductSpecificationModel(1, documentSnapshot.get("specTitle" + x + "FieldTitle" + y).toString(), documentSnapshot.get("specTitle" + x + "FieldTitle" + y + "Value").toString()));
                            }
                        }
                    } else {
                        productDescriptionTabContainer.setVisibility(View.GONE);
                        productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                        productDetailsOnlyDesc.setText(documentSnapshot.get("productDescriptionBody").toString());
                        productDetailsOnlyCareDesc.setText(documentSnapshot.get("productMaterialCareDescription").toString());
                    }

                    totalRatings.setText((long) documentSnapshot.get("totalRatings") + " Ratings");

                    int maxProgress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("totalRatings")));

                    for (int x = 0; x < 5; x++) {
                        TextView rating = (TextView) ratingsNoContainer.getChildAt(x);
                        rating.setText(String.valueOf((long) documentSnapshot.get((5 - x) + "Star")));

                        ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                        progressBar.setMax(maxProgress);

                        progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get((5 - x) + "Star"))));
                    }

                    averageProductRating.setText(documentSnapshot.get("productRating").toString());
                    totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("totalRatings")));

                    productDetailsViewPager.setAdapter(new ProductDescriptionAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(), productDesc, productMaterialCareDesc, productSpecificationModelList));


                    if (currentUser != null) {
                        if (DBqueries.myRating.size() == 0) {
                            DBqueries.loadRatingList(ProductDetailsActivity.this);
                        }
                        if (DBqueries.cartList.size() == 0) {
                            DBqueries.loadCartList(ProductDetailsActivity.this, loadingDialog, false);
                        }
                        if (DBqueries.wishList.size() == 0) {
                            DBqueries.loadWishlist(ProductDetailsActivity.this, loadingDialog, false);
                        } else {
                            loadingDialog.dismiss();
                        }
                    } else {
                        loadingDialog.dismiss();
                    }

                    if (DBqueries.myRatedIds.contains(productId)) {
                        int index = DBqueries.myRatedIds.indexOf(productId);
                        initialRating = Integer.parseInt(String.valueOf(DBqueries.myRating.get(index))) - 1;
                        setRating(initialRating);
                    }
//                    if (currentUser != null) {
                        if (DBqueries.wishList.contains(productId)) {
                            ProductDetailsActivity.wishlist_btn.setImageTintList(getResources().getColorStateList(R.color.lightOrange, null));
                            ALREADY_ADDED_TO_WISHLIST = true;
                        } else {
                            wishlist_btn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#14000000")));
                            ALREADY_ADDED_TO_WISHLIST = false;

                        }
//                    }

                    if (DBqueries.cartList.contains(productId)) {
                        ALREADY_ADDED_TO_CART = true;
                    } else {
                        ALREADY_ADDED_TO_CART = false;
                    }


                    //////////////////// add to cart btn
                    if ((boolean) documentSnapshot.get("inStock")){
                        addtoCartBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (currentUser==null){
                                    registerLoginDialogText.setText("Please login to add products in your cart!");
                                    signInDialog.show();
                                }
                                else {
                                    if (!running_cart_query) {
                                        running_cart_query = true;
                                        if (ALREADY_ADDED_TO_CART) {
                                            running_cart_query = false;
                                            Toast.makeText(ProductDetailsActivity.this, "Already Added To Cart!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Map<String, Object> addProduct = new HashMap<>();
                                            addProduct.put("productId" + String.valueOf(DBqueries.cartList.size()), productId);
                                            addProduct.put("cartListSize", (long) DBqueries.cartList.size() + 1);

                                            firebaseFirestore.collection("Users").document(currentUser.getUid()).collection("UserData").document("Cart")
                                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        if (DBqueries.cartItemModelList.size() != 0) {
                                                            DBqueries.cartItemModelList.add(new MyCartItemModel(productId,
                                                                    (boolean) documentSnapshot.get("inStock"),
                                                                    documentSnapshot.get("productImage1").toString(),
                                                                    documentSnapshot.get("productTitle").toString(),
                                                                    documentSnapshot.get("productSubtitle").toString(),
                                                                    documentSnapshot.get("productPrice").toString(),
                                                                    documentSnapshot.get("productInitialPrice").toString(),
                                                                    (long)1,    //productQuantity
                                                                    (long)0,   //offerApplied
                                                                    (long) documentSnapshot.get("freeCoupons")));
                                                        }

                                                        ALREADY_ADDED_TO_CART = true;
                                                        DBqueries.cartList.add(productId);
//                                                        if ((boolean) documentSnapshot.get("inStock")){
//                                                            LinearLayout parent = (LinearLayout) MyCartActivity.totalAmount.getParent().getParent();
//                                                            parent.setVisibility(View.VISIBLE);
//                                                        }
                                                        Toast.makeText(ProductDetailsActivity.this, "Added To Cart!", Toast.LENGTH_SHORT).show();

//                                                        invalidateOptionsMenu();    // why used this? this is for badge icon of cart
                                                        running_cart_query = false;
                                                    }

                                                    else {
                                                        //addToWishListBtn.setEnabled(true);
                                                        running_cart_query = false;
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                        }
                                    }
                                }

                            }
                        });
                    }
                    //////////////////// add to cart btn
                    else{
                        String boldText = "Sorry\n";
                        String normalText = "Product Is Unavailable";
                        SpannableString str = new SpannableString(boldText + normalText);
                        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        str.setSpan(new RelativeSizeSpan(1.3f), 0,6, 0); // set size
                        str.setSpan(new RelativeSizeSpan(0.9f), 7,28 , 0);// set color
                        addtoCartBtn.setText(str);
                        addtoCartBtn.setAllCaps(false);
                        addtoCartBtn.setGravity(Gravity.START);
                        addtoCartBtn.setTypeface(null,Typeface.NORMAL);
                        addtoCartBtn.setPadding(12,0,50,0);
                        addtoCartBtn.setBackground(null);
                        TextView outOfStock = (TextView) butNowBtn;
                        outOfStock.setText("Out of Stock");
                        outOfStock.setWidth(300);
                        outOfStock.setTextColor(getResources().getColor(R.color.white));

                        outOfStock.setCompoundDrawables(null,null,null,null);
                    }


                }else{
                    loadingDialog.dismiss();
                    String error= task.getException().getMessage();
                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                }

            }
        });

//        productImages.add(R.drawable.sampleproductone);
//        productImages.add(R.drawable.sampleproducttwo);
//        productImages.add(R.drawable.unknown1);
//        productImages.add(R.drawable.sampleproducttwo);
//        productImages.add(R.drawable.sampleproductthree);

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
                if (currentUser==null){
                    registerLoginDialogText.setText("Please login to add products in your wishlist!");
                    signInDialog.show();
                }
                else {
//                    wishlist_btn.setEnabled(false);
                    if(!running_wishlist_query){
                        running_wishlist_query=true;
                        if (ALREADY_ADDED_TO_WISHLIST){
                            int index = DBqueries.wishList.indexOf(productId);
                            DBqueries.removeFromWishlist(index,ProductDetailsActivity.this);
                            ProductDetailsActivity.wishlist_btn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#14000000")));
                            ALREADY_ADDED_TO_WISHLIST=false;
                        }else{
//                            wishlist_btn.setImageTintList(getResources().getColorStateList(R.color.lightOrange,null));
                            Map<String,Object> productIdMap=new HashMap<>();
                            productIdMap.put("productId"+ String.valueOf(DBqueries.wishList.size()),productId);
                            productIdMap.put("wishlistSize",(long) (DBqueries.wishList.size()+1));

                            firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid())
                                    .collection("UserData").document("Wishlist")
                                    .update(productIdMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){

//                                        Map<String ,Object> updateProductIdMap=new HashMap<>();
//                                        updateProductIdMap.put("wishlistSize",(long) (DBqueries.wishList.size()+1));
//                                        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid())
//                                            .collection("UserData").document("Wishlist")
//                                            .update(updateProductIdMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if (task.isSuccessful()){
                                                if (DBqueries.wishlistModelList.size()!=0){
                                                    DBqueries.wishlistModelList.add(new WishlistModel(
                                                            productId,
                                                            documentSnapshot.get("productImage1").toString(),
                                                            documentSnapshot.get("productTitle").toString(),
                                                            documentSnapshot.get("productSubtitle").toString(),
                                                            documentSnapshot.get("productPrice").toString(),
                                                            documentSnapshot.get("productInitialPrice").toString()));
                                                }
                                                ProductDetailsActivity.wishlist_btn.setImageTintList(getResources().getColorStateList(R.color.lightOrange,null));
                                                ALREADY_ADDED_TO_WISHLIST =true;
                                                DBqueries.wishList.add(productId);
                                                LayoutInflater inflater = getLayoutInflater();
                                                View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));
                                                TextView text = (TextView) layout.findViewById(R.id.text);
                                                text.setText("Added to Wishlist");
                                                text.setPadding(8,4,8,7);
                                                Toast toast = new Toast(getApplicationContext());
                                                toast.setDuration(Toast.LENGTH_SHORT);
                                                toast.setView(layout);
                                                toast.setGravity(Gravity.BOTTOM, 0, 190);
                                                toast.show();

//                                            }else {
//
//                                                wishlist_btn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#14000000")));
//                                                String error=task.getException().getMessage();
//                                                Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
//                                            }
//                                            running_wishlist_query=false;
//                                                wishlist_btn.setEnabled(true);
//                                            }
//                                        });
                                    }else {
                                        wishlist_btn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#14000000")));
//                                        wishlist_btn.setEnabled(true);
//                                        running_wishlist_query=false;
                                        String error=task.getException().getMessage();
                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                }
                                    running_wishlist_query=false;

                                }
                        });
                    }
                }
              }
            }
        });

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

         for(int x=0; x<rateNowContainer.getChildCount() ;x++){
            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser==null){
                        registerLoginDialogText.setText("Please login to rate products!");
                        signInDialog.show();
                    } else {
                        if (starPosition != initialRating) {
                            if (!running_rated_query) {
                                running_rated_query = true;
                                setRating(starPosition);
                                Map<String, Object> updateRating = new HashMap<>();
                                if (DBqueries.myRatedIds.contains(productId)) {
                                    TextView oldRating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                    TextView finalRating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);

                                    updateRating.put(initialRating + 1 + "Star", Long.parseLong(oldRating.getText().toString()) - 1);

                                    updateRating.put(starPosition + 1 + "Star", Long.parseLong(finalRating.getText().toString()) + 1);

                                    updateRating.put("productRating", calculateAverageRating((long) starPosition - initialRating, true));

//                                    Toast.makeText(ProductDetailsActivity.this, "updated", Toast.LENGTH_SHORT).show();

                                } else { //when user has not given any rating to the product
                                    updateRating.put((starPosition + 1) + "Star", (long) documentSnapshot.get(starPosition + 1 + "Star") + 1);
                                    updateRating.put("productRating", calculateAverageRating((long) starPosition + 1, false));
                                    updateRating.put("totalRatings", (long) documentSnapshot.get("totalRatings") + 1);
                                }


                                firebaseFirestore.collection("PRODUCTS")
                                        .document(productId)
                                        .update(updateRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Map<String, Object> myRating = new HashMap<>();

                                            if (DBqueries.myRatedIds.contains(productId)) {
                                                myRating.put("rating" + DBqueries.myRatedIds.indexOf(productId), (long) starPosition + 1);
                                            } else {   //rating 1st time
                                                myRating.put("ratingListSize", (long) DBqueries.myRatedIds.size() + 1);
                                                myRating.put("productId" + DBqueries.myRatedIds.size(), productId);
                                                myRating.put("rating" + DBqueries.myRatedIds.size(), (long) (starPosition + 1));
                                            }
                                            firebaseFirestore.collection("Users")
                                                    .document(currentUser.getUid())
                                                    .collection("UserData")
                                                    .document("Ratings")
                                                    .update(myRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {

                                                        if (DBqueries.myRatedIds.contains(productId)) {
                                                            DBqueries.myRating.set(DBqueries.myRatedIds.indexOf(productId), (long) starPosition + 1);
                                                            TextView oldRating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                                            TextView finalRating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);
                                                            finalRating.setText(String.valueOf(Integer.parseInt(finalRating.getText().toString()) + 1));
                                                            oldRating.setText(String.valueOf(Integer.parseInt(oldRating.getText().toString()) - 1));
                                                        } else {
                                                            DBqueries.myRatedIds.add(productId);
                                                            DBqueries.myRating.add((long) (starPosition + 1));
                                                            TextView rating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);
                                                            rating.setText(String.valueOf(Integer.parseInt(rating.getText().toString()) + 1));
//                                                            averageRatingMiniView.setText( + ((long) documentSnapshot.get("totalRatings") + 1) + " Ratings");
                                                            totalRatings.setText((long) documentSnapshot.get("totalRatings") + 1 + " Ratings");
                                                            totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("totalRatings") + 1));
                                                            Toast.makeText(ProductDetailsActivity.this, "Thanks for your feedback!", Toast.LENGTH_SHORT).show();
                                                        }


                                                        for (int x = 0; x < 5; x++) {
                                                            TextView ratingFigures = (TextView) ratingsNoContainer.getChildAt(x);

                                                            ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);
                                                            int maxProgress = Integer.parseInt(totalRatingsFigure.getText().toString());

                                                            progressBar.setMax(maxProgress);

                                                            progressBar.setProgress(Integer.parseInt(ratingFigures.getText().toString()));
                                                        }


                                                        initialRating = starPosition;
                                                        averageProductRating.setText(calculateAverageRating(0, true));
                                                        averageRatingMiniView.setText(calculateAverageRating(0, true));

//                                                        if (DBqueries.wishList.contains(productId) && DBqueries.wishlistModelList.size() != 0) {
//
//                                                            int index = DBqueries.wishList.indexOf(productId);
//                                                            DBqueries.wishlistModelList.get(index).setRating(averageProductRating.getText().toString());
////                                                            DBqueries.wishlistModelList.get(index).setTotalRatings(Long.parseLong(totalRatingsFigure.getText().toString()));
//
//                                                        }

                                                    } else {
                                                        setRating(initialRating);// if any error occurs set rating as previous rating
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                                    }
                                                    running_rated_query = false;
                                                }
                                            });
                                        } else {
                                            running_rated_query = false;
                                            setRating(initialRating);
                                            String error = task.getException().getMessage();
                                            Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }


                    }
                }
            });
        }
        //////////////////// rating layout




         //////////////////// buy Now layout

        shipping_details_layout.setVisibility(View.INVISIBLE);
        shipping_details_layout_background.setVisibility(View.INVISIBLE);
//        isUp = false;
         butNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser==null){
                    registerLoginDialogText.setText("Sorry, we are unable to place your order!");
                    signInDialog.show();
                }
                else {

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

         ////////////couponDIALOG

         final Dialog checkCouponPriceDialog =new Dialog(ProductDetailsActivity.this);
         checkCouponPriceDialog.setContentView(R.layout.coupon_redem_dialog);
         checkCouponPriceDialog.setCancelable(true);
         checkCouponPriceDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
         checkCouponPriceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

         ImageView toggleRecyclerViewBtn=checkCouponPriceDialog.findViewById(R.id.toogle_recyclerview);
         couponRecyclerView=checkCouponPriceDialog.findViewById(R.id.couponsRecyclerView);
         selectedCoupon=checkCouponPriceDialog.findViewById(R.id.selectedCoupon);

         couponBody=checkCouponPriceDialog.findViewById(R.id.couponBody);
         couponExpiryDate=checkCouponPriceDialog.findViewById(R.id.couponValidityDate);
         couponTile=checkCouponPriceDialog.findViewById(R.id.couponTitle);
         couponIcon=checkCouponPriceDialog.findViewById(R.id.couponIcon);

         TextView apply_coupon_at_checkout=checkCouponPriceDialog.findViewById(R.id.apply_coupon_at_checkout);
         TextView originalPrice=checkCouponPriceDialog.findViewById(R.id.originalPrice);
         TextView newDiscpuntedPrice=checkCouponPriceDialog.findViewById(R.id.discounted_new_price);

         apply_coupon_at_checkout.setSelected(true);

         LinearLayoutManager layoutManager =new LinearLayoutManager(ProductDetailsActivity.this);
         layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

         couponRecyclerView.setLayoutManager(layoutManager);
         List<RewardModel> rewardModelList =new ArrayList<>();
         rewardModelList.add(new RewardModel(R.drawable.ic_tag,"Discount","Get 10% OFF on any product above Rs.5000/- and get additional Rs.100/- discount on next purchase","Till 27th May,2020"));
         rewardModelList.add(new RewardModel(R.drawable.ic_tag,"New Deals","Get 20% OFF on any product above Rs.20000/-","Till 30th May,2020"));
         rewardModelList.add(new RewardModel(R.drawable.ic_new,"Sale","Buy 50 pieces get 2 free ","Till 15th June,2020"));

         rewardModelList.add(new RewardModel(R.drawable.ic_tag,"Discount","Get 10% OFF on any product above Rs.5000/- and get additional Rs.100/- discount on next purchase","Till 27th May,2020"));
         rewardModelList.add(new RewardModel(R.drawable.ic_tag,"New Deals","Get 20% OFF on any product above Rs.20000/-","Till 30th May,2020"));
         rewardModelList.add(new RewardModel(R.drawable.ic_new,"Sale","Buy 50 pieces get 2 free ","Till 15th June,2020"));

         RewardAdapter rewardAdapter =new RewardAdapter(rewardModelList,true);
         couponRecyclerView.setAdapter(rewardAdapter);
         rewardAdapter.notifyDataSetChanged();

         toggleRecyclerViewBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showDialogRecyclerView();
             }
         });

         ////////////couponDIALOG



         //////////////////// coupon redemption btn
         coupon_redeemBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 checkCouponPriceDialog.show();
             }
         });






//////signIn Dialog
             signInDialog=new Dialog(ProductDetailsActivity.this);
             signInDialog.setContentView(R.layout.register_or_login_dialog);
             signInDialog.setCancelable(true);
             signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
             signInDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
             registerLoginDialogText= signInDialog.findViewById(R.id.registerLoginDilogText);
             TextView registerDialogBtn=signInDialog.findViewById(R.id.registerDialogButton);
             TextView loginDialogBtn=signInDialog.findViewById(R.id.loginDialogButton);
//                LinearLayout linearLayoutRegisterOrLogin=signInDialog.findViewById(R.id.linearLayoutRegisterOrLogin);
             final Intent registerIntent=new Intent(ProductDetailsActivity.this, UserSessionActivity.class);

//                linearLayoutRegisterOrLogin.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        signInDialog.dismiss();
//                        startActivity(registerIntent);
////                        finishAffinity();
//                    }
//                });
             registerDialogBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     RegisterFragment.disableCloseBtn=true;
                     LoginFragment.disableCloseBtn=true;
                     signInDialog.dismiss();
                     registerFrag=true;
                     startActivity(registerIntent);
                     overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                 }
             });
             loginDialogBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     RegisterFragment.disableCloseBtn=true;
                     LoginFragment.disableCloseBtn=true;
                     signInDialog.dismiss();
                     registerFrag=false;
                     startActivity(registerIntent);
                     overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                 }
             });

         //////signIn Dialog


     }


    @Override
    protected void onStart() {
        super.onStart();
        currentUser= FirebaseAuth.getInstance().getCurrentUser() ;

//        wishlist_btn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#14000000")));

        if (currentUser==null){
            coupon_redemption_layout.setVisibility(View.GONE);
        }
        else
            coupon_redemption_layout.setVisibility(View.VISIBLE);

        if (currentUser!=null){
            if (DBqueries.myRating.size() == 0) {
                DBqueries.loadRatingList(ProductDetailsActivity.this);
            }
            if (DBqueries.cartList.size() == 0) {
                DBqueries.loadCartList(ProductDetailsActivity.this, loadingDialog, false);
            }
            if (DBqueries.wishList.size()==0){
                DBqueries.loadWishlist(ProductDetailsActivity.this,loadingDialog,false);
            }else{
                loadingDialog.dismiss();
            }
        }else {
            loadingDialog.dismiss();
        }
        if (DBqueries.myRatedIds.contains(productId)) {
            int index = DBqueries.myRatedIds.indexOf(productId);
            initialRating = Integer.parseInt(String.valueOf(DBqueries.myRating.get(index))) - 1;
            setRating(initialRating);
        }

        if (DBqueries.wishList.contains(productId)){
            wishlist_btn.setImageTintList(getResources().getColorStateList(R.color.lightOrange,null));
            ALREADY_ADDED_TO_WISHLIST=true;
        }else {
            wishlist_btn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#14000000")));
            ALREADY_ADDED_TO_WISHLIST = false;
        }

        if (DBqueries.cartList.contains(productId)) {
            ALREADY_ADDED_TO_CART = true;
        } else {
            ALREADY_ADDED_TO_CART = false;
        }


    }


    public static void showDialogRecyclerView(){
        if (couponRecyclerView.getVisibility()==View.GONE){
            couponRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupon.setVisibility(View.GONE);
        }
        else{
            couponRecyclerView.setVisibility(View.GONE);
            selectedCoupon.setVisibility(View.VISIBLE);}

    }


    public static void setRating(int starPosition) {
        for (int x = 0;x < rateNowContainer.getChildCount(); x++){
            ImageView starBtn =(ImageView) rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#33000000")));
            if (x <= starPosition) {
//                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));

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
    private String calculateAverageRating(long currentUserRating, boolean update) {
        Double totalStars = Double.valueOf(0);
        for (int x = 1; x < 6; x++) {
            TextView ratingNo = (TextView) ratingsNoContainer.getChildAt(5 - x); // multiply by x for no. of users who rated the product
            totalStars = totalStars + (Long.parseLong(ratingNo.getText().toString()) * x);
            // 1 star multiply by no.of users, 2 stars multiply by it's users and so on
        }
        totalStars = totalStars + currentUserRating;
        if (update) {
            return String.valueOf(totalStars / Long.parseLong(totalRatingsFigure.getText().toString())).substring(0, 3);//for already rated product
        } else {
            return String.valueOf(totalStars / (Long.parseLong(totalRatingsFigure.getText().toString()) + 1)).substring(0, 3);//for newly rated product
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
        getMenuInflater().inflate(R.menu.without_notification_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.main_search) {
            return true;
        }
        if (id == R.id.main_wishlist) {
            if (currentUser==null) {
                registerLoginDialogText.setText("Just few steps away from your wishlist!");
                signInDialog.show();
            }else{
            Intent wishlistIntent=new Intent(ProductDetailsActivity.this,WishlistActivity.class);
            wishlistIntent.putExtra("currentProductId",productId);
            wishlistIntent.putExtra("PDtoW",true);
            startActivity(wishlistIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
            }
        }
        if (id == R.id.main_shopping_cart) {
            if (currentUser==null) {
                registerLoginDialogText.setText("Just few steps away from your cart!");
                signInDialog.show();
            }else{
            Intent cartIntent=new Intent(ProductDetailsActivity.this,MyCartActivity.class);
            startActivity(cartIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
            }
        }
        else if (id == android.R.id.home) {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
