package com.example.myapplication.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.DBqueries;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Models.MyCartItemModel;
import com.example.myapplication.Models.WishlistModel;
import com.example.myapplication.MyCartActivity;
import com.example.myapplication.ProductDetailsActivity;
import com.example.myapplication.R;
import com.example.myapplication.WishlistActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    private List<WishlistModel> wishlistModelList ;
    private Boolean wishlist;
    private Boolean cartWishlist;
    private Context context;
    private int  lastPosition=-1;

    public WishlistAdapter(List<WishlistModel> wishlistModelList,Context context,Boolean wishlist,Boolean cartWishlist) {
        this.wishlistModelList = wishlistModelList;
        this.wishlist=wishlist;
        this.context=context;
        this.cartWishlist=cartWishlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String productId=wishlistModelList.get(position).getProductId();
        String resource=wishlistModelList.get(position).getProductImage();
        String title=wishlistModelList.get(position).getProductTitle();
        String subtitle=wishlistModelList.get(position).getProductSubtitle();
        String price=wishlistModelList.get(position).getProductPrice();
        String initialPrice=wishlistModelList.get(position).getProductInitialPrice();
//        String discount=wishlistModelList.get(position).getProductDiscount();
        holder.setData(productId,resource,title,subtitle,price,initialPrice,position);

       if (lastPosition <position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
        holder.itemView.setAnimation(animation);
        lastPosition=position;
    }
    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private DocumentSnapshot documentSnapshot;

        private ImageView productImage;
        private TextView productTitle,productSubtitle,productPrice,productInitialPrice,productDiscount,moveToCart_tv;
        private ImageView deleteFromWishlistBtn;
        private View wishlistDivider;
        private ConstraintLayout moveToCarBtn;
        private ConstraintLayout wishlistItemLayoutContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productTitle=itemView.findViewById(R.id.product_title);
            productSubtitle=itemView.findViewById(R.id.product_subtitle);
            productImage=itemView.findViewById(R.id.product_image);
            productPrice=itemView.findViewById(R.id.product_price);
            productInitialPrice=itemView.findViewById(R.id.product_initial_price);
            productDiscount=itemView.findViewById(R.id.product_discount);
            moveToCarBtn=itemView.findViewById(R.id.move_to_cart_button);
            deleteFromWishlistBtn=itemView.findViewById(R.id.delete_btn);
            wishlistDivider=itemView.findViewById(R.id.wishlistDivider);
            moveToCart_tv=itemView.findViewById(R.id.move_to_cart_tv);
            wishlistItemLayoutContainer=itemView.findViewById(R.id.wishlistItemLayoutContainer);


        }
        private void setData(final String productId, String resource, String title, String subtitle, final String price, String initialPrice, final int index){
//            productImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.square_placeholder)).into(productImage);
            productTitle.setText(title);
            productSubtitle.setText(subtitle);
            productPrice.setText("₹"+price+"/pc");
            productInitialPrice.setText("₹"+initialPrice);
//            productDiscount.setText(discount);
//            public int getScreenWidth() {
//                WindowManager wm = (WindowManager) ((WishlistActivity)context).getSystemService(Context.WINDOW_SERVICE);
//
//                Display display = wm.getDefaultDisplay();
//                Point size = new Point();
//                display.getSize(size);
//                return size.x;
//            }
            if (!price.isEmpty()&&!initialPrice.isEmpty()){
                int intPrice=Integer.parseInt(price);
                int intInitialPrice=Integer.parseInt(initialPrice);
                int  productDiscountValue;
                if (intInitialPrice>intPrice && !initialPrice.equals(" ") && !initialPrice.isEmpty() && !price.equals(" ") && !price.isEmpty()){
                    productDiscountValue= ((intInitialPrice-intPrice)*100)/intInitialPrice;
                    productDiscount.setText("("+productDiscountValue+"% OFF)");
                }
                else
                    productDiscount.setText(" ");
            }
            else
                productDiscount.setText(" ");

            if(wishlist){
                deleteFromWishlistBtn.setImageResource(R.drawable.ic_cancel);
                wishlistDivider.setVisibility(View.VISIBLE);
                moveToCarBtn.setVisibility(View.VISIBLE);

                deleteFromWishlistBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        deleteFromWishlistBtn.setEnabled(false);
                        if (!ProductDetailsActivity.running_wishlist_query) {

                            if (DBqueries.wishList.contains(productId)) {
                                ProductDetailsActivity.running_wishlist_query = true;
                                DBqueries.removeFromWishlist(index, itemView.getContext());
                                WishlistActivity.wishlistTitle.setText("Wishlist (" + DBqueries.wishList.size() + ")");
                            }
                            //todo:else where we can update the list id it is already removed also {if (DBqueries.wishList.contains(productId))} this just don't allow clicking if the product is not in the wishlist
                        }
//                        Toast.makeText(itemView.getContext(), "delete", Toast.LENGTH_SHORT).show();
                    }
                });


            }else if(cartWishlist){
                deleteFromWishlistBtn.setVisibility(View.GONE);
                productImage.requestLayout();
                productImage.getLayoutParams().height = 315;
                productImage.getLayoutParams().width = 330;
                productPrice.setTextSize(14);
                moveToCart_tv.setText("Add to Cart");
                wishlistItemLayoutContainer.setBackground(AppCompatResources.getDrawable(context,R.drawable.bg_horizontal_scroll_product_outline_box));
                setMargins(productPrice,0,8,0,0);
                setMargins(wishlistDivider,0,21,0,0);
                moveToCarBtn.setPadding(2,28,2,28);
                setMargins(wishlistItemLayoutContainer,0,28,0,0);
            }else{
                deleteFromWishlistBtn.setImageResource(R.drawable.ic_favorite_black_24dp);
                deleteFromWishlistBtn.setPadding(5,15,15,5);
                wishlistDivider.setVisibility(View.INVISIBLE);
                moveToCarBtn.setVisibility(View.GONE);
            }





//////////productDetailsDialog

            ConstraintLayout backgroundProductDetails;

            final ViewPager productImagesViewPagerDialog;
            final TabLayout productImageTabLayoutDialog;
            final TextView productTitleDialog;
            final TextView productSubtitleDialog;
            final TextView productPriceDialog;
            final TextView productInitialPriceDialog;
            final ReadMoreTextView productDescriptionDialog;
            final TextView productMaterialDialog;
            final TextView productSizeDialog;
            final TextView productWeightDialog;
            final TextView productCategoryDialog;
            final TextView productSubcategory1Dialog;
            final TextView productSubcategory2Dialog;
            final TextView addToCartBtnDialog;
            final ConstraintLayout sizeCL;
            final ImageView dismissProductDetailsDialog;



            FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();

            final List<String> productImagesDialog =new ArrayList<>();

            final Dialog loadingDialog;

            //////productDetailsDialog initialization
            final Dialog productDetailsDialog = new Dialog((WishlistActivity) itemView.getContext());
            productDetailsDialog.setContentView(R.layout.product_details_dialog_layout);
            productDetailsDialog.setCancelable(true);
            WindowManager.LayoutParams lp=((WishlistActivity) itemView.getContext()).getWindow().getAttributes();
            lp.dimAmount=0.85f;

            productDetailsDialog.getWindow().setAttributes(lp);
            productDetailsDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            productDetailsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            productDetailsDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            backgroundProductDetails=productDetailsDialog.findViewById(R.id.productDetailsBackground);

            //////productDetailsDialog initialization

            ////LoadingDialog
            loadingDialog=new Dialog(itemView.getContext());
            loadingDialog.setContentView(R.layout.loading_progress_dialog);
            loadingDialog.setCancelable(false);
            loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            ////LoadingDialog

            productImagesViewPagerDialog=productDetailsDialog.findViewById(R.id.product_images_view_pager);
            productImageTabLayoutDialog=productDetailsDialog.findViewById(R.id.viewPager_indicator);

            productTitleDialog=productDetailsDialog.findViewById(R.id.product_title);
            productSubtitleDialog=productDetailsDialog.findViewById(R.id.product_subtitle);
            productPriceDialog=productDetailsDialog.findViewById(R.id.product_price);
            productInitialPriceDialog=productDetailsDialog.findViewById(R.id.product_initial_price);
            productDescriptionDialog=productDetailsDialog.findViewById(R.id.tv_product_desc);

            productMaterialDialog=productDetailsDialog.findViewById(R.id.productMaterial);
            productSizeDialog=productDetailsDialog.findViewById(R.id.productSize);
            productWeightDialog=productDetailsDialog.findViewById(R.id.productWeight);

            productCategoryDialog=productDetailsDialog.findViewById(R.id.product_category);
            productSubcategory1Dialog=productDetailsDialog.findViewById(R.id.product_sub_category_one);
            productSubcategory2Dialog=productDetailsDialog.findViewById(R.id.product_sub_category_two);
            sizeCL=productDetailsDialog.findViewById(R.id.size_CL);
            dismissProductDetailsDialog=productDetailsDialog.findViewById(R.id.dismissdialog);

//            weightcategoryCL=productDetailsDialog.findViewById(R.id.weight_category_CL);


            backgroundProductDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productDetailsDialog.dismiss();

                }
            });
            dismissProductDetailsDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productDetailsDialog.dismiss();
                }
            });

            productDetailsDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    sizeCL.setVisibility(View.GONE);
                    loadingDialog.dismiss();

                }
            });

            firebaseFirestore.collection("PRODUCTS").document(productId).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                loadingDialog.show();

                                documentSnapshot = task.getResult();
                                for (long x = 1; x < (long) documentSnapshot.get("noOfProductImages") + 1; x++) {
                                    productImagesDialog.add(documentSnapshot.get("productImage" + x).toString());
                                }
                                ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImagesDialog);
                                productImagesViewPagerDialog.setAdapter(productImagesAdapter);

                                productTitleDialog.setText(documentSnapshot.get("productTitle").toString());
                                productSubtitleDialog.setText(documentSnapshot.get("productSubtitle").toString());
                                productPriceDialog.setText("₹" + documentSnapshot.get("productPrice").toString() + "/pc");
                                productInitialPriceDialog.setText("₹" + documentSnapshot.get("productInitialPrice").toString());

                                sizeCL.setVisibility(View.GONE);
                                productDescriptionDialog.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sizeCL.setVisibility(View.VISIBLE);
                                    }
                                });

                                productDescriptionDialog.setText(documentSnapshot.get("productDescriptionBody").toString());

                                String productMaterial,productHeight,productWidth,productLength,productSize,productWeight,productCategoryStr, productSubcategory1Str, productSubcategory2Str;;

                                productMaterial=documentSnapshot.get("specTitle1FieldTitle1Value").toString();
                                productLength=documentSnapshot.get("specTitle1FieldTitle2Value").toString();
                                productWidth=documentSnapshot.get("specTitle1FieldTitle3Value").toString();
                                productHeight=documentSnapshot.get("specTitle1FieldTitle4Value").toString();
                                productSize=productLength+" x "+productWidth+" x "+productHeight;
                                productWeight=documentSnapshot.get("specTitle1FieldTitle5Value").toString();
                                productCategoryStr = documentSnapshot.get("productCategory").toString();
                                productSubcategory1Str = documentSnapshot.get("productSubcategory1").toString();
                                productSubcategory2Str = documentSnapshot.get("productSubcategory2").toString();


                                if (productMaterial.isEmpty() || productMaterial.equals(" ")) {
                                    productMaterialDialog.setVisibility(View.GONE);

                                } else productMaterialDialog.setText("Material: "+ productMaterial);

                                if (productSize.isEmpty() || productSize.equals(" ")) {
                                    productSizeDialog.setVisibility(View.GONE);

                                } else productSizeDialog.setText("Size: "+productSize);

                                if (productWeight.isEmpty() || productWeight.equals(" ")) {
                                    productWeightDialog.setVisibility(View.GONE);

                                } else productWeightDialog.setText("Weight: "+productWeight);

                                if (productCategoryStr.isEmpty() || productCategoryStr.equals(" ")) {
                                    productCategoryDialog.setVisibility(View.GONE);

                                } else productCategoryDialog.setText(productCategoryStr);

                                if (productSubcategory1Str.isEmpty() || productSubcategory1Str.equals(" ")) {
                                    productSubcategory1Dialog.setVisibility(View.GONE);

                                } else productSubcategory1Dialog.setText(productSubcategory1Str);

                                if (productSubcategory2Str.isEmpty() || productSubcategory2Str.equals(" ")) {
                                    productSubcategory2Dialog.setVisibility(View.GONE);

                                } else productSubcategory2Dialog.setText(productSubcategory2Str);
////////////////////
//                                todo:
                                if (DBqueries.cartList.contains(productId)) {
                                    ProductDetailsActivity.ALREADY_ADDED_TO_CART = true;
                                } else {
                                    ProductDetailsActivity.ALREADY_ADDED_TO_CART = false;
                                }

                                //////////////////// add to cart btn
                                if ((boolean) documentSnapshot.get("inStock")){
                                    moveToCart_tv.setText("Move to Cart");
                                    moveToCart_tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_shopping_cart_3, 0, 0, 0);
                                    moveToCarBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (!ProductDetailsActivity.running_cart_query) {
                                                ProductDetailsActivity.running_cart_query = true;
                                                if (ProductDetailsActivity.ALREADY_ADDED_TO_CART) {
                                                    ProductDetailsActivity.running_cart_query = false;
                                                    Toast.makeText(context, "Already Added To Cart!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Map<String, Object> addProduct = new HashMap<>();
                                                    addProduct.put("productId" + String.valueOf(DBqueries.cartList.size()), productId);
                                                    addProduct.put("cartListSize", (long) DBqueries.cartList.size() + 1);

                                                    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
                                                    FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser() ;

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

                                                                ProductDetailsActivity.ALREADY_ADDED_TO_CART = true;
                                                                DBqueries.cartList.add(productId);
                                                                Toast.makeText(context, "Added To Cart!", Toast.LENGTH_SHORT).show();
                                                                ProductDetailsActivity.running_cart_query = false;
                                                            }

                                                            else {
                                                                ProductDetailsActivity.running_cart_query = false;
                                                                String error = task.getException().getMessage();
                                                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                                }
                                            }
                                        }
                                    });
                                }
                            //////////////////// add to cart btn
                            else{
                                    moveToCart_tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                    moveToCart_tv.setText("Out of Stock");
                            }
//////////////////////////////////move to cart end
                                loadingDialog.dismiss();


                            }else{
                                loadingDialog.dismiss();
                                String error= task.getException().getMessage();
                                Toast.makeText(itemView.getContext(), error, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

            productImageTabLayoutDialog.setupWithViewPager(productImagesViewPagerDialog,true);
            for(int i=0; i < productImageTabLayoutDialog.getTabCount() - 1; i++) {
                View tab = ((ViewGroup) productImageTabLayoutDialog.getChildAt(0)).getChildAt(i);
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
                p.setMargins(0, 0, 10, 0);
                tab.requestLayout();
            }


//////////productDetailsDialog

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productDetailsDialog.show();

//                    if(productId.equals(currentProductId)){
//                        ((WishlistActivity)itemView.getContext()).finish();
//                    }else{
//                    Intent productDetailsIntent=new Intent(itemView.getContext(), ProductDetailsActivity.class);
//                    //                    itemView.getContext().startActivity(productDetailsIntent);\
//                    productDetailsIntent.putExtra("productId",productId);
//                    productDetailsIntent.putExtra("WishlistToPD",true);
//                    Activity activity = (Activity) itemView.getContext();
//                    activity.startActivity(productDetailsIntent);
////                    ((WishlistActivity)itemView.getContext()).finish();
//                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);}
                }
            });


//////////productDetailsDialog


        }

    }

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }


}
