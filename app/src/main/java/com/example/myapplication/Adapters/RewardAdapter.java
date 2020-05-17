package com.example.myapplication.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.RewardModel;
import com.example.myapplication.R;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder> {

    private List<RewardModel> rewardModelList;
    public RewardAdapter(List<RewardModel> rewardModelList) {
        this.rewardModelList = rewardModelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout,parent,false);
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
        private void setData(int icon,String title,String body,String expiryDate){
            couponIcon.setImageResource(icon);
            couponTitle.setText(title);
            couponBody.setText(body);
            couponExpiryDate.setText(expiryDate);

        }
    }
}
