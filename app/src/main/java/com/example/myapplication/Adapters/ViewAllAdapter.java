package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Models.ViewAllWithRatingModel;
import com.example.myapplication.R;
import com.example.myapplication.ViewAllActivity;

import java.util.List;
import java.util.zip.Inflater;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {

    private List<ViewAllWithRatingModel> viewAllWithRatingModelList;

    public ViewAllAdapter(List<ViewAllWithRatingModel> viewAllWithRatingModelList) {
        this.viewAllWithRatingModelList = viewAllWithRatingModelList;
    }

    @NonNull
    @Override
    public ViewAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_horizontal_recycler_view_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllAdapter.ViewHolder holder, int position) {
        String resource =viewAllWithRatingModelList.get(position).getProductImage();
        String title =viewAllWithRatingModelList.get(position).getProductTitle();
        String subtitle =viewAllWithRatingModelList.get(position).getProductSubtitle();
        String price  =viewAllWithRatingModelList.get(position).getProductPrice();
        String initialPrice  =viewAllWithRatingModelList.get(position).getProductInitialPrice();
//        String discount =viewAllWithRatingModelList.get(position).getProductDiscount();
        String rating  =viewAllWithRatingModelList.get(position).getRating();
        long noOfRating  =viewAllWithRatingModelList.get(position).getNo_of_ratings();
        long freeCoupons  =viewAllWithRatingModelList.get(position).getFreeCoupons();

        holder.setData(resource,title,subtitle,freeCoupons,price,initialPrice,rating,noOfRating);

    }

    @Override
    public int getItemCount() {
        return viewAllWithRatingModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle, productSubtitle, productPrice, productInitialPrice, productDiscount, no_of_ratings, productRating,freeCoupon;
        private View dividerInitialPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            productSubtitle = itemView.findViewById(R.id.product_subtitle);
            productPrice = itemView.findViewById(R.id.product_price);
            productInitialPrice = itemView.findViewById(R.id.product_initial_price);
            productDiscount = itemView.findViewById(R.id.product_discount);
            productRating = itemView.findViewById(R.id.tv_product_rating_miniView);
            no_of_ratings = itemView.findViewById(R.id.total_no_of_rating);
            freeCoupon= itemView.findViewById(R.id.t_v_coupon_available);
            dividerInitialPrice=itemView.findViewById(R.id.dividerInitialPrice);

        }

        private void setData(String resource, String title, String subtitle,long freeCoupons, String price,String initialPrice,String ratingOfProduct, long totalNoOfRatings) {
//            productImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.rectangular_placeholder)).into(productImage);
            productTitle.setText(title);
            productSubtitle.setText(subtitle);
            productPrice.setText("₹"+price+"/pc");
            if (!initialPrice.equals("") || !initialPrice.isEmpty()){
                productInitialPrice.setVisibility(View.VISIBLE);
                dividerInitialPrice.setVisibility(View.VISIBLE);
                productInitialPrice .setText("₹"+initialPrice);
            }

            else{
                dividerInitialPrice.setVisibility(View.GONE);
                productInitialPrice.setVisibility(View.INVISIBLE);
            }
//            productDiscount.setText(discount);
            if (!price.isEmpty()&&!initialPrice.isEmpty()){
            int intPrice=Integer.parseInt(price);
            int intInitialPrice=Integer.parseInt(initialPrice);
            int productDiscountValue;
            if (intInitialPrice>intPrice && !initialPrice.equals(" ") && !initialPrice.isEmpty() && !price.equals(" ") && !price.isEmpty()){
                productDiscountValue= ((intInitialPrice-intPrice)*100)/intInitialPrice;
                productDiscount.setText("("+productDiscountValue+"% OFF)");
            }
            else
                productDiscount.setText(" ");
            }
            else
                productDiscount.setText(" ");

            if (ratingOfProduct.equals("0")||ratingOfProduct.equals("0.0")||ratingOfProduct.isEmpty()){
                productRating.setVisibility(View.INVISIBLE);
                no_of_ratings.setVisibility(View.INVISIBLE);
            }else{
                productRating.setVisibility(View.VISIBLE);
                no_of_ratings.setVisibility(View.VISIBLE);
                productRating.setText(ratingOfProduct);
                no_of_ratings.setText("("+totalNoOfRatings+" ratings)");
            }


            if (freeCoupons!=0){
                freeCoupon.setVisibility(View.VISIBLE);
                if (freeCoupons==1)
                    freeCoupon.setText(freeCoupons+" coupon available");
                else
                    freeCoupon.setText(freeCoupons+" coupons available");
            }else freeCoupon.setVisibility(View.INVISIBLE);
//            freeCoupon.setText(freeCoupons);

        }
    }
}
