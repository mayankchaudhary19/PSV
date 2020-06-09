package com.example.myapplication.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Models.HorizontalProductScrollModel;
import com.example.myapplication.ProductDetailsActivity;
import com.example.myapplication.R;

import java.util.List;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter< HorizontalProductScrollAdapter.ViewHolder> {

    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public HorizontalProductScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder viewHolder, int position) {
        String  productId =horizontalProductScrollModelList.get(position).getProductId();
        String resource =horizontalProductScrollModelList.get(position).getProductImage();
        String title=horizontalProductScrollModelList.get(position).getProductTitle();
        String description=horizontalProductScrollModelList.get(position).getProductDescription();
        String price=horizontalProductScrollModelList.get(position).getProductPrice();
        String initialPrice=horizontalProductScrollModelList.get(position).getProductInitialPrice();

        viewHolder.setProductDetails(productId,resource,title,description,price,initialPrice);

    }

    @Override
    public int getItemCount() {
        if (horizontalProductScrollModelList.size()>8){
            return 8;
        }else {
            return horizontalProductScrollModelList.size();
        }
    }

    public class  ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView productDescription;
        private TextView productPrice;
        private TextView productInitialPrice;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.h_s_product_img);
            productTitle=itemView.findViewById(R.id.h_s_product_title);
            productDescription=itemView.findViewById(R.id.h_s_product_description);
            productPrice=itemView.findViewById(R.id.h_s_product_price);
            productInitialPrice=itemView.findViewById(R.id.h_s_product_initialPrice);
        }

        private void setProductDetails(final String productId, String  resource, String title, String description, String price, String initialPrice){
//            productImage.setImageResource(resource);
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.square_placeholder)).into(productImage);
            productTitle.setText(title);
            productDescription.setText(description);
            productPrice.setText("₹"+price+"/pc");
            productInitialPrice.setText("₹"+initialPrice);
            if (!title.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                        productDetailsIntent.putExtra("productId",productId);
                        Activity activity = (Activity) itemView.getContext();
                        activity.startActivity(productDetailsIntent);
                        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                });
            }
        }


    }
}
