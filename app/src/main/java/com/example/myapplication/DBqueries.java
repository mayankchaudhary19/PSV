package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.Adapters.CategoryAdapter;
import com.example.myapplication.Adapters.HomePageAdapter;
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

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

    }


    public static void removeFromWishlist(final int index, final Context context ) {
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


    public static void loadCartList(final Context context, final Dialog dialog , final boolean loadProductData, final TextView badgeCount, final TextView cartTotalAmount){

        cartList.clear();
        firebaseFirestore.collection("Users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("UserData")
                .document("cartListSize")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                    for(long x=0;x<(long)task.getResult().get("cartListSize");x++){
                        cartList.add(task.getResult().get("productId"+x).toString());

                        if(DBqueries.cartList.contains(ProductDetailsActivity.productId)){
                            ProductDetailsActivity.ALREADY_ADDED_TO_CART = true;
                        }
                        else{
                            ProductDetailsActivity.ALREADY_ADDED_TO_CART = false;
                        }

                        if(loadProductData) {
                            cartItemModelList.clear();
                            final String productId = task.getResult().get("productId" + x).toString();
                            firebaseFirestore.collection("PRODUCTS").document(productId)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        int index =0;
                                        if (cartList.size() >= 2){
                                            index = cartList.size() - 2;
                                        }
                                        cartItemModelList.add(index, new MyCartItemModel(
                                                productId,
                                                MyCartItemModel.CART_ITEM,task.getResult().get("productImage1").toString(),
                                                task.getResult().get("productTitle").toString(),
                                                (long) task.getResult().get("freeCoupens"),
                                                task.getResult().get("product_price").toString(),
                                                task.getResult().get("cutted_price").toString(),
                                                (long)1,
                                                (long)0,
                                                (long)0,

                                                (boolean) task.getResult().get("in_stock")));

                                        if (cartList.size() == 1){
                                            cartItemModelList.add(new MyCartItemModel(MyCartItemModel.TOTAL_AMOUNT));
                                            LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
                                            parent.setVisibility(View.VISIBLE);
                                        }
                                        if (cartList.size() == 0 ){
                                            cartItemModelList.clear();
                                        }
                                        MyCartFragment.cartAdapter.notifyDataSetChanged();

                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                    if (cartList.size() != 0){
                        badgeCount.setVisibility(View.VISIBLE);
                    }
                    else{
                        badgeCount.setVisibility(View.INVISIBLE);
                    }
                    if( DBqueries.cartList.size() < 99) {
                        badgeCount.setText(String.valueOf(DBqueries.cartList.size()));
                    }
                    else{
                        badgeCount.setText("99");
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


//    public static void removeFromCart(final int index, final Context context, final TextView cartTotalAmount){
//
//        final String removedProductId = cartList.get(index);
//        cartList.remove(index);
//        Map<String,Object> updateCartList = new HashMap<>();
//
//        for(int x=0; x<cartList.size();x++){
//            updateCartList.put("product_ID_"+x,cartList.get(x));
//        }
//        updateCartList.put("list_size",(long) cartList.size());
//
//        firebaseFirestore.collection("USERS")
//                .document(FirebaseAuth.getInstance().getUid())
//                .collection("USER_DATA")
//                .document("MY_CART")
//                .set(updateCartList).addOnCompleteListener(new OnCompleteListener<Void>() {// set krne se new document create hota h.......
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//
//                    if(cartItemModelList.size() !=0 ){
//                        cartItemModelList.remove(index);
//                        MyCartFragment.cartAdapter.notifyDataSetChanged();
//                    }
//                    if (cartList.size() == 0 ){
//                        LinearLayout parent = (LinearLayout) cartTotalAmount.getParent().getParent();
//                        parent.setVisibility(View.GONE);
//                        cartItemModelList.clear();
//                    }
//                    Toast.makeText(context, "removed successfully!!", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    cartList.add(index,removedProductId);
//                    String error = task.getException().getMessage();
//                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
//                }
////                if (ProductDetailsActivity.addToWishListBtn != null) {
////                    ProductDetailsActivity.addToWishListBtn.setEnabled(true);
////                }
//
//                ProductDetailsActivity.running_cart_query = false;
//            }
//        });
//
//    }


    public static void clearData(){
        categoryModelList.clear();
        lists.clear();
        loadCategoriesNames.clear();
        wishList.clear();
        wishlistModelList.clear();
        myRatedIds.clear();
        myRating.clear();

    }
}