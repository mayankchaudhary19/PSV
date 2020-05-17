package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.ViewAllWithRatingModel;
import com.example.myapplication.R;

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
        int resource =viewAllWithRatingModelList.get(position).getProductImage();
        String title =viewAllWithRatingModelList.get(position).getProductTitle();
        String subtitle =viewAllWithRatingModelList.get(position).getProductSubtitle();
        String price  =viewAllWithRatingModelList.get(position).getProductPrice();
        String initialPrice  =viewAllWithRatingModelList.get(position).getProductInitialPrice();
        String discount =viewAllWithRatingModelList.get(position).getProductDiscount();
        String rating  =viewAllWithRatingModelList.get(position).getRating();
        String noOfRating  =viewAllWithRatingModelList.get(position).getNo_of_ratings();

        holder.setData(resource,title,subtitle,price,initialPrice,discount,rating,noOfRating);

    }

    @Override
    public int getItemCount() {
        return viewAllWithRatingModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle, productSubtitle, productPrice, productInitialPrice, productDiscount, no_of_ratings, productRating;

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

        }

        private void setData(int resource, String title, String subtitle, String price,String initialPrice,String discount,String ratingOfProduct, String totalNoOfRatings) {
            productImage.setImageResource(resource);
            productTitle.setText(title);
            productSubtitle.setText(subtitle);
            productPrice.setText(price);
            productInitialPrice .setText(initialPrice);
            productDiscount.setText(discount);
            productRating.setText(ratingOfProduct);
            no_of_ratings.setText(totalNoOfRatings);
        }
    }
}
