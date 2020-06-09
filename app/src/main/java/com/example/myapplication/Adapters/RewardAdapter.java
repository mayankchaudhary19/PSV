package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.RewardModel;
import com.example.myapplication.ProductDetailsActivity;
import com.example.myapplication.R;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder> {

    private List<RewardModel> rewardModelList;
    private boolean useMiniLayout=false;
    public RewardAdapter(List<RewardModel> rewardModelList,boolean useMiniLayout ) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout=useMiniLayout;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (useMiniLayout)
          view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_rewards_item_layout2,parent,false);
       else
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int icon =rewardModelList.get(position).getCouponIcon();
        String title=rewardModelList.get(position).getCouponTitle();
        String body=rewardModelList.get(position).getCouponBody();
        String expiryDate=rewardModelList.get(position).getExpiryDate();
        holder.setData(icon,title,body,expiryDate);
    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView couponTitle,couponBody,couponExpiryDate;
        private ImageView couponIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            couponIcon=itemView.findViewById(R.id.couponIcon);
            couponTitle=itemView.findViewById(R.id.couponTitle);
            couponBody=itemView.findViewById(R.id.couponBody);
            couponExpiryDate=itemView.findViewById(R.id.couponValidityDate);
        }
        private void setData(final int icon, final String title, final String body, final String expiryDate){
            couponIcon.setImageResource(icon);
            couponTitle.setText(title);
            couponBody.setText(body);
            couponExpiryDate.setText(expiryDate);
            if (useMiniLayout)
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProductDetailsActivity.couponTile.setText(title);
                        ProductDetailsActivity.couponBody.setText(body);
                        ProductDetailsActivity.couponExpiryDate.setText(expiryDate);
                        ProductDetailsActivity.couponIcon.setImageResource(icon);
                        ProductDetailsActivity.showDialogRecyclerView();
                    }
                });

        }
    }
}
