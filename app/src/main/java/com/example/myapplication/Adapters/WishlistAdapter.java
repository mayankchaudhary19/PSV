package com.example.myapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.Models.WishlistModel;
import com.example.myapplication.MyCartActivity;
import com.example.myapplication.ProductDetailsActivity;
import com.example.myapplication.R;
import com.example.myapplication.WishlistActivity;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    private List<WishlistModel> wishlistModelList ;
    private Boolean wishlist;
    private Boolean cartWishlist;
    private Context context;
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
        int resource=wishlistModelList.get(position).getProductImage();
        String title=wishlistModelList.get(position).getProductTitle();
        String subtitle=wishlistModelList.get(position).getProductSubtitle();
        String price=wishlistModelList.get(position).getProductPrice();
        String initialPrice=wishlistModelList.get(position).getProductInitialPrice();
        String discount=wishlistModelList.get(position).getProductDiscount();
        holder.setData(resource,title,subtitle,price,initialPrice,discount);
    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle,productSubtitle,productPrice,productInitialPrice,productDiscount,moveToCart_tv;
        private ImageView deleteFromWishlistBtn;
        private View wishlistDivider;
        private LinearLayout moveToCarBtn;
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
        private void setData(int resource, String title, String subtitle, String price, String initialPrice,String discount){
            productImage.setImageResource(resource);
            productTitle.setText(title);
            productSubtitle.setText(subtitle);
            productPrice.setText(price);
            productInitialPrice.setText(initialPrice);
            productDiscount.setText(discount);

            if(wishlist){
                deleteFromWishlistBtn.setImageResource(R.drawable.ic_cancel);
                wishlistDivider.setVisibility(View.VISIBLE);
                moveToCarBtn.setVisibility(View.VISIBLE);
                deleteFromWishlistBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(itemView.getContext(), "delete", Toast.LENGTH_SHORT).show();
                    }
                });


            }else if(cartWishlist){
                deleteFromWishlistBtn.setVisibility(View.GONE);
                productImage.requestLayout();
                productImage.getLayoutParams().height = 315;
                productImage.getLayoutParams().width = 330;
                productPrice.setTextSize(14);
                moveToCart_tv.setText("Add To Cart");
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
            moveToCarBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Moved to Cart", Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent=new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    //                    itemView.getContext().startActivity(productDetailsIntent);
                    Activity activity = (Activity) itemView.getContext();
                    activity.startActivity(productDetailsIntent);
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });

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
