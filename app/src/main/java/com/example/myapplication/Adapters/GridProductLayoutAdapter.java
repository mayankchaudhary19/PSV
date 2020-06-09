package com.example.myapplication.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Models.GridProductModel;
import com.example.myapplication.ProductDetailsActivity;
import com.example.myapplication.R;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {

    List<GridProductModel> gridProductModelList;

    public GridProductLayoutAdapter(List<GridProductModel> gridProductModelList) {
        this.gridProductModelList = gridProductModelList;
    }


    @Override
    public int getCount() {
        return gridProductModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
       View view;
       if (convertView==null){
           view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout_item ,null);
           ImageView productImage =view.findViewById(R.id.grid_product_image);
           TextView productTitle= view.findViewById(R.id.grid_item_product_title);
           TextView productSubTitle= view.findViewById(R.id.grid_item_product_subtitle);
           TextView productPrice= view.findViewById(R.id.grid_item_product_price);

           view.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent productDetailsIntent =new Intent(parent.getContext(), ProductDetailsActivity.class);
                   productDetailsIntent.putExtra("productId",gridProductModelList.get(position).getProductId());
                   parent.getContext().startActivity(productDetailsIntent);
               }
           });

           Glide.with(view.getContext()).load(gridProductModelList.get(position).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.square_placeholder)).into(productImage);
//           productImage.setImageResource(gridProductModelList.get(position).getProductImage());
           productTitle.setText(gridProductModelList.get(position).getProductTitle());
           productSubTitle.setText(gridProductModelList.get(position).getProductSubTitle());
           productPrice.setText("₹"+  gridProductModelList.get(position).getProductPrice());
       }else{
           view= convertView;

       }
       return view;
    }



}
