package com.example.myapplication.Adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.AddressModel;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import static com.example.myapplication.EditAccountActivity.MANAGE_ADDRESS;
import static com.example.myapplication.MyAddressActivity.refreshItem;
import static com.example.myapplication.MyCartActivity.SELECT_ADDRESS;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private List<AddressModel> addressModelList;
    private int MODE;
    private int preSelectedPosition=-1;

    public AddressAdapter(List<AddressModel> addressModelList,int MODE) {
        this.addressModelList = addressModelList;
        this.MODE=MODE;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {
        String name=addressModelList.get(position).getFullName();
        String mobile_number=addressModelList.get(position).getPhoneNumber();
        String addressLine1=addressModelList.get(position).getAddressLine1();
        String addressLine2=addressModelList.get(position).getAddressLine2();
        String state=addressModelList.get(position).getState();
        String pinCode=addressModelList.get(position).getPinCode();
        Boolean selectedAdd =addressModelList.get(position).getSelectedAddress();
        holder.setData(name,mobile_number,addressLine1,addressLine2,state,pinCode,selectedAdd,position);
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView fullName,mobileNumber,addressLine1,addressLine2,state,pinCode;
        private ImageView icon;
        private View view;
        private ConstraintLayout optionContainer;
//        private ConstraintLayout address_item_constraint;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            fullName=itemView.findViewById(R.id.full_name);
            mobileNumber = itemView.findViewById(R.id.mobile_number);
            addressLine1=itemView.findViewById(R.id.address_line1);
            addressLine2=itemView.findViewById(R.id.address_line2);
            state=itemView.findViewById(R.id.state);
            pinCode=itemView.findViewById(R.id.pincode);
            icon=itemView.findViewById(R.id.icon_view);
            view=itemView.findViewById(R.id.view);
            optionContainer=itemView.findViewById(R.id.option_container);
//            address_item_constraint=itemView.findViewById(R.id.address_item_CL);

        }

        private void setData(String full_name, String mobile_number , String address_Line1 , String address_Line2 , String State , String PinCode, final Boolean selectedAddress, final int position){
            fullName.setText(full_name);
            mobileNumber.setText(mobile_number);
            addressLine1.setText(address_Line1);
            addressLine2.setText(address_Line2);
            state.setText(State);
            pinCode.setText(PinCode);
            if (MODE==SELECT_ADDRESS) {
                icon.setImageResource(R.drawable.ic_check_black_24dp);
                if (selectedAddress) {
                    view.setBackgroundColor(Color.parseColor("#FFAB91"));
                    icon.setVisibility(View.VISIBLE);
                    icon.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFAB91")));
                    preSelectedPosition = position;
                } else {
                    view.setBackgroundColor(Color.parseColor("#1A000000"));
                    icon.setVisibility(View.GONE);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (preSelectedPosition != position) {
                                addressModelList.get(position).setSelectedAddress(true);
                                addressModelList.get(preSelectedPosition).setSelectedAddress(false);
                                refreshItem(preSelectedPosition, position);
                                preSelectedPosition = position;
                            }
                        }
                    });
                }
            }
            else if(MODE==MANAGE_ADDRESS){
//                address_item_constraint.setForeground(null);

                icon.setVisibility(View.VISIBLE);
                icon.setPadding(10,20,10,10);
                optionContainer.setVisibility(View.GONE);
                view.setBackgroundColor(Color.parseColor("#1A000000"));
                icon.setImageResource(R.drawable.ic_more_vert_black_24dp);
                icon.setImageTintList(ColorStateList.valueOf(Color.parseColor("#34000000")));
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionContainer.setVisibility(View.VISIBLE);
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition=position;
                        icon.setVisibility(View.INVISIBLE);
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition=-1;
                        icon.setVisibility(View.VISIBLE);

                    }
                });



            }
        }
    }
}
