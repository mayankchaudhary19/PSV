package com.example.myapplication.Adapters;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.MyOrderItemModel;
import com.example.myapplication.OrderDetailsActivity;
import com.example.myapplication.R;

import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ViewHolder> {
    private List<MyOrderItemModel> myOrderItemModelList;
    //////////////////// rating layout
    private LinearLayout rateNowContainer;
    //////////////////// rating layout
    public MyOrdersAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public MyOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersAdapter.ViewHolder holder, int position) {
        int resource =myOrderItemModelList.get(position).getProductImage();
        int rating=myOrderItemModelList.get(position).getRating();
        String title =myOrderItemModelList.get(position).getProductTitle();
        String subtitle =myOrderItemModelList.get(position).getProductSubtitle();
        String quantity =myOrderItemModelList.get(position).getQuantity();
        String deliveryDate =myOrderItemModelList.get(position).getDeliveryStatus();

        holder.setData(resource,title,subtitle,quantity,deliveryDate,rating);
    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productTitle,productSubTitle,deliveryStatus,productQuantity;
        private ImageView productImage,orderIndicator;
        private LinearLayout rateNowContainer;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_img);
            productTitle=itemView.findViewById(R.id.product_title);
            productSubTitle=itemView.findViewById(R.id.product_subtitle);
            productQuantity=itemView.findViewById(R.id.quantity_value);
            orderIndicator=itemView.findViewById(R.id.order_indicator);
            deliveryStatus=itemView.findViewById(R.id.order_delivery_date);
            rateNowContainer=itemView.findViewById(R.id.rate_now_orders_container);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderDetailsIntent =new Intent(itemView.getContext(), OrderDetailsActivity.class);
                    itemView.getContext().startActivity(orderDetailsIntent);
                }
            });

        }
        private void setData(int resource,String title, String subtitle, String quantity, String deliveredDate, int rating){
            productImage.setImageResource(resource);
            productTitle.setText(title);
            productSubTitle.setText(subtitle);
            productQuantity.setText(quantity);
            if (deliveredDate.equals("Cancelled")){
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.redIndicator)) );
            }
            else if (deliveredDate.equals("Waiting")){
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.yellowIndicator)) );
            }
            else {
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.greenIndicator)) );
            }
            deliveryStatus.setText(deliveredDate);

            //////////////////// rating layout
            setRating(rating);
            for(int x=0;x<rateNowContainer.getChildCount();x++){
                final int starPosition=x;
                rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRating(starPosition);
                    }
                });
            }
            //////////////////// rating layout
            }

        private void setRating(int starPosition) {
            for (int x = 0;x < rateNowContainer.getChildCount(); x++){
                ImageView starBtn =(ImageView) rateNowContainer.getChildAt(x);
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#33000000")));
                if (x <= starPosition) {
                    if(starPosition<=4){
                        starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF91E35E")));
                    }if (starPosition<=2){
                        starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFFFC400")));
                    }
                    if (starPosition<=1){
                        starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFDD2C00")));
                        }
                    }
                }
            }
        }
    }
