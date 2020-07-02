package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Fragments.AddNewAddressFragment;
import com.example.myapplication.Models.AddressModel;
import com.example.myapplication.MyAddressActivity;
import com.example.myapplication.MyCartActivity;
import com.example.myapplication.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import static com.example.myapplication.EditAccountActivity.MANAGE_ADDRESS;
import static com.example.myapplication.MyAddressActivity.refreshItem;
import static com.example.myapplication.MyCartActivity.SELECT_ADDRESS;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    private List<AddressModel> addressModelList;
    private int MODE;
    private int preSelectedPosition=-1;
    private Context context;

    public AddressAdapter(List<AddressModel> addressModelList,int MODE,Context context) {
        this.addressModelList = addressModelList;
        this.MODE=MODE;
        this.context=context;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_layout2,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {
        String name=addressModelList.get(position).getFullName();
        String contactNo=addressModelList.get(position).getContactNumber();
        String addressLine1=addressModelList.get(position).getAddressLine1();
        String addressLine2=addressModelList.get(position).getAddressLine2();
        String state=addressModelList.get(position).getState();
        String pinCode=addressModelList.get(position).getPincode();
        String selectAddressAs =addressModelList.get(position).getAddressType();
        Boolean selectedAdd =addressModelList.get(position).isSelectedAddress();
        holder.setData(name,addressLine1,addressLine2,state,pinCode,contactNo,selectAddressAs,selectedAdd,position);
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView fullName,addressLine1,addressLine2,state,pincode,contactNumber,saveAddressAsTxt,editAdd,selectAdd;
        private LinearLayout selectedPositionLL;
        private ConstraintLayout contactLL;

//        private ImageView icon;
//        private View view;
//        private ConstraintLayout optionContainer;
//        private ConstraintLayout address_item_constraint;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            fullName=itemView.findViewById(R.id.full_name);
            addressLine1=itemView.findViewById(R.id.address_line1);
            addressLine2=itemView.findViewById(R.id.address_line2);
            state=itemView.findViewById(R.id.state);
            pincode=itemView.findViewById(R.id.pincode);
            contactNumber = itemView.findViewById(R.id.mobile_number);
            saveAddressAsTxt=itemView.findViewById(R.id.saveAddressAsTxt);
            editAdd=itemView.findViewById(R.id.editAddress);
            selectAdd=itemView.findViewById(R.id.selectAddress);
            contactLL=itemView.findViewById(R.id.contactLL);
            selectedPositionLL=itemView.findViewById(R.id.selectedPositionLL);

//            icon=itemView.findViewById(R.id.icon_view);
//            view=itemView.findViewById(R.id.view);
//            optionContainer=itemView.findViewById(R.id.option_container);
//            address_item_constraint=itemView.findViewById(R.id.address_item_CL);

        }

        private void setData(String full_name , String address_Line1 , String address_Line2 ,String State,String pinCode, String contact_number,String select_address_as, final Boolean selectedAddress, final int position){
            fullName.setText(full_name);
            addressLine1.setText(address_Line1);
            addressLine2.setText(address_Line2);
            state.setText(State);
            pincode.setText(pinCode);
            contactNumber.setText(contact_number);
            saveAddressAsTxt.setText(select_address_as);


            ////////////////////////////////////// in order Summary / Address (after cart)

            if (MODE==SELECT_ADDRESS) {
                selectAdd.setText("SELECT");
                editAdd.setText("EDIT");
                if (selectedAddress) {
                    contactLL.setVisibility(View.VISIBLE);
                    selectedPositionLL.setVisibility(View.VISIBLE);
                    saveAddressAsTxt.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.bg_address_save_as));

                }else{
                    contactLL.setVisibility(View.GONE);
                    selectedPositionLL.setVisibility(View.GONE);
                    saveAddressAsTxt.setTextColor(ColorStateList.valueOf(Color.parseColor("#747474")));
                    saveAddressAsTxt.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.bg_address_save_as_grey));
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (preSelectedPosition != position) {
                                addressModelList.get(position).setSelectedAddress(true);
                                addressModelList.get(preSelectedPosition).setSelectedAddress(false);
                                refreshItem(preSelectedPosition, position);
                                preSelectedPosition = position;
                                contactLL.setVisibility(View.VISIBLE);
                                selectedPositionLL.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }

            }
            ////////////////////////////////////// in Edit Account

            else if(MODE==MANAGE_ADDRESS){
                selectAdd.setText("EDIT");
                editAdd.setText("REMOVE");
//                if (selectedAddress) {
//                    contactLL.setVisibility(View.VISIBLE);
//                    selectedPositionLL.setVisibility(View.VISIBLE);
////                    saveAddressAsTxt.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_address_save_as));
//
//                }else{
                    contactLL.setVisibility(View.GONE);
                    selectedPositionLL.setVisibility(View.GONE);
                    saveAddressAsTxt.setTextColor(ColorStateList.valueOf(Color.parseColor("#747474")));
                    saveAddressAsTxt.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.bg_address_save_as_grey));
//                }






                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        refreshItem(preSelectedPosition,preSelectedPosition);
//                        preSelectedPosition=-1;
                        if (preSelectedPosition != position){
                            saveAddressAsTxt.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFAB91")));
                            saveAddressAsTxt.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.bg_address_save_as_selected));
                            contactLL.setVisibility(View.VISIBLE);
                            selectedPositionLL.setVisibility(View.VISIBLE);

                            refreshItem(preSelectedPosition,preSelectedPosition);
                            preSelectedPosition=position;
                        }
                        else {
                            saveAddressAsTxt.setTextColor(ColorStateList.valueOf(Color.parseColor("#747474")));
                            saveAddressAsTxt.setBackground(ContextCompat.getDrawable(itemView.getContext(), R.drawable.bg_address_save_as_grey));
                            contactLL.setVisibility(View.GONE);
                            selectedPositionLL.setVisibility(View.GONE);
                            refreshItem(preSelectedPosition,preSelectedPosition);
                            preSelectedPosition=-1;

                        }

                    }
                });

                selectAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddNewAddressFragment bottomSheetDialogFragment = new AddNewAddressFragment();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("showContinueBtn", false);
                        bottomSheetDialogFragment.setArguments(bundle);
//                        bottomSheet.show(((MyCartActivity) context.getApplicationContext()).getSupportFragmentManager(),"TAG");

                        bottomSheetDialogFragment.show(((MyAddressActivity)itemView.getContext()).getSupportFragmentManager(), "TAG");
                    }
                });




        }
//                    saveAddressAsTxt.setTextColor(ColorStateList.valueOf(Color.parseColor("#747474")));
//                    saveAddressAsTxt.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_address_save_as_grey));
//                    itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (preSelectedPosition != position) {
//                                addressModelList.get(position).setSelectedAddress(true);
//                                addressModelList.get(preSelectedPosition).setSelectedAddress(false);
//                                refreshItem(preSelectedPosition, position);
//                                preSelectedPosition = position;
//                                contactLL.setVisibility(View.VISIBLE);
//                                selectedPositionLL.setVisibility(View.VISIBLE);
//
//                            }
//                        }
//                    });
//                    }




//            if (MODE==SELECT_ADDRESS) {
//                icon.setImageResource(R.drawable.ic_check_black_24dp);
//                if (selectedAddress) {
//                    view.setBackgroundColor(Color.parseColor("#FFAB91"));
//                    icon.setVisibility(View.VISIBLE);
//                    icon.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFAB91")));
//                    preSelectedPosition = position;
//                } else {
//                    view.setBackgroundColor(Color.parseColor("#1A000000"));
//                    icon.setVisibility(View.GONE);
//                    itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (preSelectedPosition != position) {
//                                addressModelList.get(position).setSelectedAddress(true);
//                                addressModelList.get(preSelectedPosition).setSelectedAddress(false);
//                                refreshItem(preSelectedPosition, position);
//                                preSelectedPosition = position;
//                            }
//                        }
//                    });
//                }
//            }
//            else if(MODE==MANAGE_ADDRESS){
////                address_item_constraint.setForeground(null);
//
//                icon.setVisibility(View.VISIBLE);
//                icon.setPadding(10,20,10,10);
//                optionContainer.setVisibility(View.GONE);
//                view.setBackgroundColor(Color.parseColor("#1A000000"));
//                icon.setImageResource(R.drawable.ic_more_vert_black_24dp);
//                icon.setImageTintList(ColorStateList.valueOf(Color.parseColor("#34000000")));
//                icon.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        optionContainer.setVisibility(View.VISIBLE);
//                        refreshItem(preSelectedPosition,preSelectedPosition);
//                        preSelectedPosition=position;
//                        icon.setVisibility(View.INVISIBLE);
//                    }
//                });
//
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        refreshItem(preSelectedPosition,preSelectedPosition);
//                        preSelectedPosition=-1;
//                        icon.setVisibility(View.VISIBLE);
//
//                    }
//                });
//
//
//
//            }
//        }
        }
    }
}



//
//    private List<AddressModel> addressModelList;
//    private int MODE;
//    private int preSelectedPosition=-1;
//
//    public AddressAdapter(List<AddressModel> addressModelList,int MODE) {
//        this.addressModelList = addressModelList;
//        this.MODE=MODE;
//    }
//
//    @NonNull
//    @Override
//    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_layout,parent,false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, int position) {
//        String name=addressModelList.get(position).getFullName();
//        String mobile_number=addressModelList.get(position).getContactNumber();
//        String addressLine1=addressModelList.get(position).getAddressLine1();
//        String addressLine2=addressModelList.get(position).getAddressLine2();
////        String state=addressModelList.get(position).getState();
////        String pinCode=addressModelList.get(position).getPinCode();
//        Boolean selectedAdd =addressModelList.get(position).isSelectedAddress();
//        holder.setData(name,mobile_number,addressLine1,addressLine2,selectedAdd,position);
//    }
//
//    @Override
//    public int getItemCount() {
//        return addressModelList.size();
//    }
//
//public class ViewHolder extends RecyclerView.ViewHolder{
//    private TextView fullName,mobileNumber,addressLine1,addressLine2,state,pinCode;
//    private ImageView icon;
//    private View view;
//    private ConstraintLayout optionContainer;
////        private ConstraintLayout address_item_constraint;
//
//    public ViewHolder(@NonNull final View itemView) {
//        super(itemView);
//        fullName=itemView.findViewById(R.id.full_name);
//        mobileNumber = itemView.findViewById(R.id.mobile_number);
//        addressLine1=itemView.findViewById(R.id.address_line1);
//        addressLine2=itemView.findViewById(R.id.address_line2);
//        state=itemView.findViewById(R.id.state);
//        pinCode=itemView.findViewById(R.id.pincode);
//        icon=itemView.findViewById(R.id.icon_view);
//        view=itemView.findViewById(R.id.view);
//        optionContainer=itemView.findViewById(R.id.option_container);
////            address_item_constraint=itemView.findViewById(R.id.address_item_CL);
//
//    }
//
//    private void setData(String full_name, String mobile_number , String address_Line1 , String address_Line2 , final Boolean selectedAddress, final int position){
//        fullName.setText(full_name);
//        mobileNumber.setText(mobile_number);
//        addressLine1.setText(address_Line1);
//        addressLine2.setText(address_Line2);
////            state.setText(State);
////            pinCode.setText(PinCode);
//        if (MODE==SELECT_ADDRESS) {
//            icon.setImageResource(R.drawable.ic_check_black_24dp);
//            if (selectedAddress) {
//                view.setBackgroundColor(Color.parseColor("#FFAB91"));
//                icon.setVisibility(View.VISIBLE);
//                icon.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFAB91")));
//                preSelectedPosition = position;
//            } else {
//                view.setBackgroundColor(Color.parseColor("#1A000000"));
//                icon.setVisibility(View.GONE);
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (preSelectedPosition != position) {
//                            addressModelList.get(position).setSelectedAddress(true);
//                            addressModelList.get(preSelectedPosition).setSelectedAddress(false);
//                            refreshItem(preSelectedPosition, position);
//                            preSelectedPosition = position;
//                        }
//                    }
//                });
//            }
//        }
//        else if(MODE==MANAGE_ADDRESS){
////                address_item_constraint.setForeground(null);
//
//            icon.setVisibility(View.VISIBLE);
//            icon.setPadding(10,20,10,10);
//            optionContainer.setVisibility(View.GONE);
//            view.setBackgroundColor(Color.parseColor("#1A000000"));
//            icon.setImageResource(R.drawable.ic_more_vert_black_24dp);
//            icon.setImageTintList(ColorStateList.valueOf(Color.parseColor("#34000000")));
//            icon.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    optionContainer.setVisibility(View.VISIBLE);
//                    refreshItem(preSelectedPosition,preSelectedPosition);
//                    preSelectedPosition=position;
//                    icon.setVisibility(View.INVISIBLE);
//                }
//            });
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    refreshItem(preSelectedPosition,preSelectedPosition);
//                    preSelectedPosition=-1;
//                    icon.setVisibility(View.VISIBLE);
//
//                }
//            });
//
//
//
//        }
//    }
//}