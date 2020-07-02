package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Adapters.CategoryAdapter;
import com.example.myapplication.Adapters.HomePageAdapter;
import com.example.myapplication.Adapters.MyCartAdapter;
import com.example.myapplication.Fragments.AddNewAddressFragment;
import com.example.myapplication.Models.AddressModel;
import com.example.myapplication.Models.CategoryModel;
import com.example.myapplication.Models.GridProductModel;
import com.example.myapplication.Models.HomePageModel;
import com.example.myapplication.Models.HorizontalProductScrollModel;
import com.example.myapplication.Models.MyCartItemModel;
import com.example.myapplication.Models.SliderModel;
import com.example.myapplication.Models.ViewAllWithRatingModel;
import com.example.myapplication.Models.WishlistModel;
import com.example.myapplication.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.myapplication.ProductDetailsActivity.productId;
import static com.example.myapplication.ProductDetailsActivity.setRating;
import static com.example.myapplication.ProductDetailsActivity.wishlist_btn;

public class DBqueries {


//    public static long noOfProducts;

    public static FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList=new ArrayList<>();
//    public static List<HomePageModel> homePageModelList =new ArrayList<>();

    public static List<List<HomePageModel>> lists =new ArrayList<>();
    public static List<String> loadCategoriesNames=new ArrayList<>();

    public static List<String> wishList=new ArrayList<>();
    public static List<WishlistModel> wishlistModelList=new ArrayList<>();

    public static List<String> myRatedIds =new ArrayList<>();
    public static List<Long> myRating =new ArrayList<>();

    public static List<String> cartList = new ArrayList<>();
    public static List<MyCartItemModel> cartItemModelList = new ArrayList<>();

    public static int selectedAddress = -1;
    public static List<AddressModel> addressesModelList = new ArrayList<>();



    public static void loadCategories(final RecyclerView categoryRecyclerView, final Context context){
        categoryModelList.clear();
        firebaseFirestore.collection("Categories").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(),documentSnapshot.get("categoryName").toString()));
                            }
                            CategoryAdapter categoryAdapter=new CategoryAdapter(categoryModelList);
                            categoryRecyclerView.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();


                        }else{
                            String error= task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public static void loadFragmentRecyclerData(final RecyclerView homePageRecyclerView, final Context context, final int index, final String categoryName){

        firebaseFirestore.collection("Categories").document(categoryName)
                .collection("TopDeals").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                if ((long)documentSnapshot.get("viewType")==0){
                                    List<SliderModel> sliderModelList=new ArrayList<>();
                                    long no_of_banners=(long)documentSnapshot.get("noOfBanners");
                                    for (long x=1;x<no_of_banners+1;x++){
                                        sliderModelList.add(new SliderModel(documentSnapshot.get("banner"+x).toString(),documentSnapshot.get("banner"+x+"Background").toString()));
                                    }
                                    lists.get(index).add(new HomePageModel(0,sliderModelList));

                                }else if ((long)documentSnapshot.get("viewType")==1){
                                    lists.get(index).add(new HomePageModel(1,documentSnapshot.get("stripAdBanner").toString(),documentSnapshot.get("backgroundColor").toString()));

                                }else if ((long)documentSnapshot.get("viewType")==2){

//                                    List<WishlistModel>
                                    List<ViewAllWithRatingModel> viewAllProductListWithRatingList=new ArrayList<>();

                                    List<HorizontalProductScrollModel> horizontalProductScrollModelList=new ArrayList<>();

                                    long no_of_products=(long)documentSnapshot.get("noOfProducts");
                                    for (long x=1;x<no_of_products+1;x++){
                                        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("productId"+x).toString(),
                                                documentSnapshot.get("productImage"+x).toString(),documentSnapshot.get("productTitle"+x).toString(),
                                                documentSnapshot.get("productSubtitle"+x).toString(),documentSnapshot.get("productPrice"+x).toString(),
                                                documentSnapshot.get("productInitialPrice"+x).toString()));

                                        viewAllProductListWithRatingList.add(new ViewAllWithRatingModel(
                                                documentSnapshot.get("productImage"+x).toString(),
                                                (long)documentSnapshot.get("freeCoupons"+x),
                                                (long)documentSnapshot.get("totalRatings"+x),
                                                documentSnapshot.get("productTitle"+x).toString(),
                                                documentSnapshot.get("productSubtitle"+x).toString(),
                                                documentSnapshot.get("productPrice"+x).toString(),
                                                documentSnapshot.get("productInitialPrice"+x).toString(),
                                                documentSnapshot.get("averageRating"+x).toString()));

                                    }
                                    lists.get(index).add(new HomePageModel(2,documentSnapshot.get("layoutTitle").toString(),documentSnapshot.get("layoutBackground").toString(),horizontalProductScrollModelList,viewAllProductListWithRatingList));

//                                    homePageModelList.add(new HomePageModel(2,documentSnapshot.get("layoutTitle").toString(),documentSnapshot.get("layoutBackground").toString(),horizontalProductScrollModelList));

                                }else if ((long)documentSnapshot.get("viewType")==3){
                                    List<GridProductModel> gridLayoutModelList=new ArrayList<>();
                                    long no_of_products=(long)documentSnapshot.get("noOfProducts");
                                    for (long x=1;x<no_of_products+1;x++){
                                        gridLayoutModelList.add(new GridProductModel(documentSnapshot.get("productId"+x).toString(),
                                                documentSnapshot.get("productImage"+x).toString(),documentSnapshot.get("productTitle"+x).toString(),
                                                documentSnapshot.get("productSubtitle"+x).toString(),documentSnapshot.get("productPrice"+x).toString()));
                                    }
                                     lists.get(index).add(new HomePageModel(3,documentSnapshot.get("layoutTitle").toString(),gridLayoutModelList));


                                }
                            }
                            HomePageAdapter homePageAdapter= new HomePageAdapter(lists.get(index));
                            homePageRecyclerView.setAdapter(homePageAdapter);
                            homePageAdapter.notifyDataSetChanged();
                            HomeFragment.swipeRefreshLayout.setRefreshing(false);

                        }else{
                            String error =task.getException().getMessage().toString();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public static void loadWishlist(final Context context, final Dialog dialog,final boolean  loadProductData){
        wishList.clear();
        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid())
                .collection("UserData").document("Wishlist")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    wishList.clear();
                    for (long x = 0; x < (long) task.getResult().get("wishlistSize"); x++) {
                        wishList.add(task.getResult().get("productId" + x).toString());
                        if (DBqueries.wishList.contains(ProductDetailsActivity.productId)) {
                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = true;
                            if (ProductDetailsActivity.wishlist_btn != null)
                                ProductDetailsActivity.wishlist_btn.setImageTintList(context.getResources().getColorStateList(R.color.lightOrange, null));

                        } else {
//                            Toast.makeText(context, "tdt              "+DBqueries.wishList, Toast.LENGTH_SHORT).show();
                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                            if (ProductDetailsActivity.wishlist_btn != null) {
                                ProductDetailsActivity.wishlist_btn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#14000000")));
                            }
                        }

                        if (loadProductData) {
                            wishlistModelList.clear();
                            final String productId = task.getResult().get("productId" + x).toString();
                            firebaseFirestore.collection("PRODUCTS").document(productId)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        wishlistModelList.add(new WishlistModel(
                                                productId,
                                                task.getResult().get("productImage1").toString(),
                                                task.getResult().get("productTitle").toString(),
                                                task.getResult().get("productSubtitle").toString(),
                                                task.getResult().get("productPrice").toString(),
                                                task.getResult().get("productInitialPrice").toString()));

                                        WishlistActivity.wishlistAdapter.notifyDataSetChanged();
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                    if (loadProductData) {
                        if (wishList.size() != 0) {
                            Long no_of_items=task.getResult().getLong("wishlistSize");
                            WishlistActivity.wishlistTitle.setText("Wishlist ("+no_of_items+")");
                        } else {
                            WishlistActivity.wishlistTitle.setText("Wishlist"); }
                    }


                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

    }


    public static void removeFromWishlist(final int index, final Context context ) {

        final String removedProductId = wishList.get(index);
        wishList.remove(index);

        Map<String, Object> updateWishlist = new HashMap<>();
        for (int x = 0; x < wishList.size(); x++) {
            updateWishlist.put("productId" + x, wishList.get(x));
        }
        updateWishlist.put("wishlistSize", (long) wishList.size());
        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid())
                .collection("UserData").document("Wishlist")
                .set(updateWishlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (wishlistModelList.size()!=0){
                        wishlistModelList.remove(index);
                        WishlistActivity.wishlistAdapter.notifyDataSetChanged();
                    }
//                    if (wishlistModelList.size()==0){
//                        wishList.clear();
////                        wishlistModelList.clear();
//                    }
                    ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST =false;

                    LayoutInflater inflater = LayoutInflater.from(context);
                    View layout = inflater.inflate(R.layout.custom_toast, null);
                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText("Removed Successfully");
                    text.setPadding(8,4,8,7);
                    Toast toast = new Toast(context);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.setGravity(Gravity.BOTTOM, 0, 190);
                    toast.show();

                } else {
                    if (ProductDetailsActivity.wishlist_btn!=null) {
                        ProductDetailsActivity.wishlist_btn.setImageTintList(context.getResources().getColorStateList(R.color.lightOrange, null));
                        }
                    wishList.add(index,removedProductId);

                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
//                if (ProductDetailsActivity.wishlist_btn!=null)
                    //ishlist_btn.setEnabled(true);
                    ProductDetailsActivity.running_wishlist_query=false;
            }
        });
    }


    public static void loadRatingList(final Context context){
        myRatedIds.clear();
        myRating.clear();


////// error in if statement
        if(!ProductDetailsActivity.running_rated_query) {
            ProductDetailsActivity.running_rated_query = true;
            firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid())
                .collection("UserData").document("Ratings")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    for (long x =0; x<(long)task.getResult().get("ratingListSize"); x++){
                        myRatedIds.add(task.getResult().get("productId"+x).toString());
                        myRating.add((long)task.getResult().get("rating"+x));
                        if (task.getResult().get("productId"+x).toString().equals(ProductDetailsActivity.productId)){

                            ProductDetailsActivity.initialRating = (Integer.parseInt(String.valueOf((long) task.getResult().get("rating" + x)))) - 1;

//                            ProductDetailsActivity.setRating( ProductDetailsActivity.initialRating);

                            if (ProductDetailsActivity.rateNowContainer != null) {
                                ProductDetailsActivity.setRating(ProductDetailsActivity.initialRating);
                            }
                        }
                    }
                }else{
//                    ProductDetailsActivity.running_rated_query = false;
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                ProductDetailsActivity.running_rated_query = false;

            }
        });
        }
    }


    public static void loadCartList(final Context context, final Dialog dialog , final boolean loadProductData){

        cartList.clear();
        firebaseFirestore.collection("Users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("UserData")
                .document("Cart")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {

                    for (long x = 0; x < (long) task.getResult().get("cartListSize"); x++) {
                        cartList.add(task.getResult().get("productId"+ x).toString());

                        if (DBqueries.cartList.contains(ProductDetailsActivity.productId)) {
                            ProductDetailsActivity.ALREADY_ADDED_TO_CART = true;
                        } else {
                            ProductDetailsActivity.ALREADY_ADDED_TO_CART = false;
                        }

                        if (loadProductData) {
                            cartItemModelList.clear();
                            final String productId = task.getResult().get("productId"+ x).toString();
                            firebaseFirestore.collection("PRODUCTS").document(productId)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
//                                        int index = 0;
//                                        if (cartList.size() >= 2) {
//                                            index = cartList.size() - 2;
//                                        }
                                        cartItemModelList.add(new MyCartItemModel(productId,
                                                (boolean) task.getResult().get("inStock"),
                                                task.getResult().get("productImage1").toString(),
                                                task.getResult().get("productTitle").toString(),
                                                task.getResult().get("productSubtitle").toString(),
                                                task.getResult().get("productPrice").toString(),
                                                task.getResult().get("productInitialPrice").toString(),
                                                (long) 1,    //productQuantity
                                                (long) 0,   //offerApplied
                                                (long) task.getResult().get("freeCoupons")));

//                                        if (cartList.size() == 1) {
//                                            cartItemModelList.add(new MyCartItemModel(MyCartItemModel.TOTAL_AMOUNT));

//                                            LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
//                                            parent.setVisibility(View.VISIBLE);
//                                        }
                                        if (cartList.size() == 0) {
                                            cartItemModelList.clear();
                                        }
                                        MyCartActivity.cartAdapter.notifyDataSetChanged();

                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                    if (loadProductData) {
                        if (cartList.size() > 0) {

                            new CountDownTimer(500, 500) {

                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {
                                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                                    MyCartActivity.priceDetailsLL.startAnimation(animation);


                                    int totalItemPrice = 0;
                                    for (int x = 0; x<MyCartAdapter.myCartItemModelList.size(); x++){

                                        if (MyCartAdapter.myCartItemModelList.get(x).isInStock()){
                                            totalItemPrice = totalItemPrice + Integer.parseInt(MyCartAdapter.myCartItemModelList.get(x).getProductPrice());
                                        }
                                    }
                                    if (totalItemPrice==0) {
                                        MyCartActivity.continueBtnLL.setVisibility(View.GONE);
                                        MyCartActivity.priceDetailsLL.setVisibility(View.GONE);
                                    } else {
                                        MyCartActivity.continueBtnLL.setVisibility(View.VISIBLE);
                                        MyCartActivity.priceDetailsLL.setVisibility(View.VISIBLE);
                                    }
                                }
                            }.start();


                            MyCartActivity.cartTitle.setText("Cart (" + task.getResult().getLong("cartListSize") + ")");
                        } else {
//                            MyCartActivity.cartTitle.setText("Cart");
                            MyCartActivity.continueBtnLL.setVisibility(View.GONE);
                            MyCartActivity.priceDetailsLL.setVisibility(View.GONE);
                        }
                    }
                }
                else{
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }


    public static void removeFromCart(final int index, final Context context){
        final String removedProductId = cartList.get(index);
        cartList.remove(index);

        Map<String,Object> updateCartList = new HashMap<>();

        for(int x=0; x<cartList.size();x++){
            updateCartList.put("productId"+x,cartList.get(x));
        }
        updateCartList.put("cartListSize",(long) cartList.size());

        firebaseFirestore.collection("Users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("UserData")
                .document("Cart")
                .set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {// set krne se new document create hota h.......
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    if(cartItemModelList.size() !=0 ){
                        cartItemModelList.remove(index);
                        MyCartActivity.cartAdapter.notifyDataSetChanged();

                        int totalItems = 0;
                        int totalItemPrice = 0;
                        int discountItemsPrice=0;
                        int discountInitialItemsPrice=0;
                        long couponAvailable=0;

                        for (int x = 0; x<MyCartAdapter.myCartItemModelList.size(); x++){

                            if ( MyCartAdapter.myCartItemModelList.get(x).isInStock()){
                                totalItems++;
                                totalItemPrice = totalItemPrice + Integer.parseInt(MyCartAdapter.myCartItemModelList.get(x).getProductPrice());
                                discountInitialItemsPrice=discountInitialItemsPrice+Integer.parseInt(MyCartAdapter.myCartItemModelList.get(x).getProductInitialPrice());
                                discountItemsPrice=discountInitialItemsPrice-totalItemPrice;
                                couponAvailable=couponAvailable+Long.parseLong(String.valueOf(MyCartAdapter.myCartItemModelList.get(x).getFreeCouponsAvailable()));
                            }
                        }


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

                        if (totalItemPrice==0){
                            MyCartActivity.continueBtnLL.setVisibility(View.GONE);
                            MyCartActivity.priceDetailsLL.setVisibility(View.GONE);
                        }else{
                            MyCartActivity.continueBtnLL.setVisibility(View.VISIBLE);
                            MyCartActivity.priceDetailsLL.setVisibility(View.VISIBLE);
                        }
                    }

                    if (cartList.size() == 0 ){
                        cartItemModelList.clear();
                        MyCartActivity.continueBtnLL.setVisibility(View.GONE);
                        MyCartActivity.priceDetailsLL.setVisibility(View.GONE);
                    }
                    Toast.makeText(context, "Removed Successfully!", Toast.LENGTH_SHORT).show();
                }
                else{
                    cartList.add(index,removedProductId);
                    MyCartActivity.continueBtnLL.setVisibility(View.VISIBLE);
                    MyCartActivity.priceDetailsLL.setVisibility(View.VISIBLE);
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }

                ProductDetailsActivity.running_cart_query = false;
            }
        });

    }


    public static void loadAddress(final Context context, final Dialog loadingDialog){

        addressesModelList.clear();
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
                                bundle.putString("params", MyCartActivity.totalAmount.getText().toString());
                                bottomSheet.setArguments(bundle);
                                bottomSheet.show(((MyAddressActivity)context).getSupportFragmentManager(),"TAG");

                                // matlab user ke pass agar koi address nhi h to list size zero hoga
//                                deliveryIntent = new Intent(context,AddAddressActivity.class);
                                // to agar size zero h to addressactivity pr bhej dena
//                                deliveryIntent.putExtra("INTENT","deliveryIntent");
                            }
                            else{
                                for (long x = 1; x< (long) task.getResult().get("addressListSize")+1; x++){

                                    addressesModelList.add(new AddressModel(task.getResult().get("fullName"+x).toString(),
                                            task.getResult().get("contactNo"+x).toString(),
                                            task.getResult().get("addressLineOne"+x).toString(),
                                            task.getResult().get("addressLineTwo"+x).toString(),
                                            task.getResult().get("state"+x).toString(),
                                            task.getResult().get("pincode"+x).toString(),
                                            (String) task.getResult().get("addressType"+x),
                                            (boolean) task.getResult().get("selectedAddress"+x)
                                            ));

                                    if ((boolean) task.getResult().get("selectedAddress"+x)){
                                        selectedAddress = Integer.parseInt(String.valueOf(x-1));
                                    }
                                }
//                                deliveryIntent = new Intent(context,DeliveryActivity.class);

                            }
//                            context.startActivity(deliveryIntent);
                        }
                        else{
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.dismiss();
                    }
                });
    }



    public static void clearData(){
        categoryModelList.clear();
        lists.clear();
        loadCategoriesNames.clear();
        wishList.clear();
        wishlistModelList.clear();
        myRatedIds.clear();
        myRating.clear();
        cartList.clear();
        cartItemModelList.clear();

    }
}