package com.example.myapplication.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AddAddressActivity;
import com.example.myapplication.DBqueries;
import com.example.myapplication.Models.AddressModel;
import com.example.myapplication.MyAddressActivity;
import com.example.myapplication.MyCartActivity;
import com.example.myapplication.OrderSummaryActivity;
import com.example.myapplication.ProductDetailsActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AddNewAddressFragment extends BottomSheetDialogFragment {

    private ConstraintLayout bottomSheetBackground;
    private TextView saveAddress,addAddressContinueBtn,addressType;
    private TextInputEditText name,contactNo,addressLine1,addressLine2,state,pincode;
    private Dialog loadingDialog;
    private String totalAmt;
    private LinearLayout addressSaveAsContainer;
    private final int initialPosition=0;

    private String[] saveAsAddressType = new String[1];

    public static final Pattern VALID_MOBILE_NUMBER__REGEX = Pattern.compile("^((?!(0))[0-9]{10})$");


    public AddNewAddressFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_add_new_address, container, false);

        bottomSheetBackground=view.findViewById(R.id.bottomSheetBackground);
        name=view.findViewById(R.id.nameAdd);
        contactNo=view.findViewById(R.id.contactAdd);
        addressLine1=view.findViewById(R.id.addressLine1Add);
        addressLine2=view.findViewById(R.id.addressLine2Add);
        state=view.findViewById(R.id.stateAdd);
        pincode=view.findViewById(R.id.pincodeAdd);
        saveAddress=view.findViewById(R.id.saveAddress);
        addAddressContinueBtn=view.findViewById(R.id.addAddressContinueBtn);
        addressSaveAsContainer=view.findViewById(R.id.addressSaveAsContainer);
//        additionalInfo=view.findViewById(R.id.additionalInfoAdd);

        Bundle bundle = getArguments();
        boolean toShowContinueBtn = bundle.getBoolean("showContinueBtn");

        if (toShowContinueBtn){
            addAddressContinueBtn.setVisibility(View.VISIBLE);
        }
        else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.setMargins(28,30,28,28);
            saveAddress.setLayoutParams(params);
            addAddressContinueBtn.setVisibility(View.GONE);
        }
//        totalAmount.setText(totalAmt);
        bottomSheetBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            hideKeyboardFrom(getContext(),view);
            }
        });


        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);



/////////
        final View[] previousView = {view.findViewById(R.id.shop_Add)};
        TextView shopAdd=view.findViewById(R.id.shop_Add);
        shopAdd.setSelected(true);
        shopAdd.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFAB91")));
        saveAsAddressType[0]="SHOP";


        View.OnClickListener clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView previousText = (TextView) previousView[0];
                TextView curText = (TextView) v;

                // If the clicked view is selected, deselect it
                if (curText.isSelected()) {
                    curText.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFAB91")));
                    curText.setSelected(true);
                } else { // If this isn't selected, deselect  the previous one (if any)
                    if (previousText != null && previousText.isSelected()) {
                        previousText.setTextColor(ColorStateList.valueOf(Color.parseColor("#747474")));
                        previousText.setSelected(false);
                    }
                    curText.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFAB91")));
                    curText.setSelected(true);
                    previousView[0] = v;
                    saveAsAddressType[0] =((TextView) v).getText().toString();
//                    Toast.makeText(getContext(), saveAsAddressType[0], Toast.LENGTH_SHORT).show();
                }
            }
        };
        view.findViewById(R.id.shop_Add).setOnClickListener(clickListener);
        view.findViewById(R.id.godown_Add).setOnClickListener(clickListener);
        view.findViewById(R.id.home_Add).setOnClickListener(clickListener);
        view.findViewById(R.id.office_Add).setOnClickListener(clickListener);

        addressSaveAsContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), saveAsAddressType[0], Toast.LENGTH_SHORT).show();
            }
        });

        saveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setError(null);
                contactNo.setError(null);
                addressLine1.setError(null);
                addressLine2.setError(null);
                state.setError(null);
                pincode.setError(null);


                if (name.getText().toString().isEmpty()) {
                    name.setError("Required!");
                    return;
                }
                if (contactNo.getText().toString().isEmpty()) {
                    contactNo.setError("Required!");
                    return;
                }
                if (contactNo.getText().toString().length() <8 ) {
                    contactNo.setError("Invalid Phone Number");
                    return;
                }
                if (!VALID_MOBILE_NUMBER__REGEX.matcher(contactNo.getText().toString()).find()) {
                    contactNo.setError("Invalid Phone Number");
                    return;
                }
                if (addressLine1.getText().toString().isEmpty()) {
                    addressLine1.setError("Required!");
                    return;
                }
                if (addressLine2.getText().toString().isEmpty()) {
                    addressLine2.setError("Required!");
                    return;
                }
                if (state.getText().toString().isEmpty()) {
                    state.setError("Required!");
                    return;
                }
                if (pincode.getText().toString().isEmpty()) {
                    pincode.setError("Required!");
                    return;
                }
                else{
                    addData();
                    Intent intent =new Intent(getContext(),OrderSummaryActivity.class);
                    startActivity(intent);
                }

            }
        });


        addAddressContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setError(null);
                contactNo.setError(null);
                addressLine1.setError(null);
                addressLine2.setError(null);
                state.setError(null);
                pincode.setError(null);


                if (name.getText().toString().isEmpty()) {
                    name.setError("Required!");
                    return;
                }
                if (contactNo.getText().toString().isEmpty()) {
                    contactNo.setError("Required!");
                    return;
                }
                if (contactNo.getText().toString().length() <8 ) {
                    contactNo.setError("Invalid Phone Number");
                    return;
                }
                if (!VALID_MOBILE_NUMBER__REGEX.matcher(contactNo.getText().toString()).find()) {
                    contactNo.setError("Invalid Phone Number");
                    return;
                }
                if (addressLine1.getText().toString().isEmpty()) {
                    addressLine1.setError("Required!");
                    return;
                }
                if (addressLine2.getText().toString().isEmpty()) {
                    addressLine2.setError("Required!");
                    return;
                }
                if (state.getText().toString().isEmpty()) {
                    state.setError("Required!");
                    return;
                }
                if (pincode.getText().toString().isEmpty()) {
                    pincode.setError("Required!");
                    return;
                }
                else { addData();
                }

            }
        });


        return view;
    }

    private static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void addData(){
        loadingDialog.show();


        Map<String, Object> addAddress = new HashMap();
        addAddress.put("addressListSize", (long) DBqueries.addressesModelList.size()+1);
        addAddress.put("fullName"+String.valueOf((long)DBqueries.addressesModelList.size()+1),name.getText().toString());
        addAddress.put("contactNo"+String.valueOf((long)DBqueries.addressesModelList.size()+1),contactNo.getText().toString());
        addAddress.put("addressLineOne"+String.valueOf((long)DBqueries.addressesModelList.size()+1),addressLine1.getText().toString());
        addAddress.put("addressLineTwo"+String.valueOf((long)DBqueries.addressesModelList.size()+1),addressLine2.getText().toString());
        addAddress.put("state"+String.valueOf((long)DBqueries.addressesModelList.size()+1),state.getText().toString());
        addAddress.put("pincode"+String.valueOf((long)DBqueries.addressesModelList.size()+1),pincode.getText().toString());
        addAddress.put("addressType"+String.valueOf((long)DBqueries.addressesModelList.size()+1),saveAsAddressType[0]);
//        addAddress.put("additionalInfo"+String.valueOf((long)DBqueries.addressesModelList.size()+1),additionalInfo.getText().toString());
        addAddress.put("selectedAddress"+String.valueOf((long)DBqueries.addressesModelList.size()+1),true);


        FirebaseFirestore.getInstance().collection("Users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("UserData")
                .document("Addresses")
                .update(addAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
//                            initialPosition = (Integer.parseInt(String.valueOf((long) task.getResult().get("" + x)))) - 1;

//                            if (DBqueries.addressesModelList.size() > 0) {
//                                DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelectedAddress(false);
//                            }
                            DBqueries.addressesModelList.add(new AddressModel(name.getText().toString(),
                                    contactNo.getText().toString(),addressLine1.getText().toString(),
                                    addressLine2.getText().toString(), state.getText().toString(),
                                    pincode.getText().toString(),saveAsAddressType[0],true));


//                                if (getIntent().getStringExtra("INTENT").equals("deliveryIntent")) {
//                                    Intent deliveryIntent = new Intent(AddAddressActivity.this, DeliveryActivity.class);
//                                    startActivity(deliveryIntent);
//                                }
//                                else{
//                                    MyAddressActivity.refreshItem(DBqueries.selectedAddress, DBqueries.addressesModelList.size()-1);
//                                }
//                                DBqueries.selectedAddress = DBqueries.addressesModelList.size() - 1;
//                                finish();
                            dismiss();

                        }
                        else{
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.dismiss();
                    }
                });

    }
}



//                name.setError(null);
//                contactNo.setError(null);
//                addressLine1.setError(null);
//                addressLine2.setError(null);
//
//                if (name.getText().toString().isEmpty()) {
//                    name.setError("Required!");
//                    return;
//                }
//                if (contactNo.getText().toString().isEmpty()) {
//                    contactNo.setError("Required!");
//                    return;
//                }
//                if (contactNo.getText().toString().length() <8 ) {
//                    contactNo.setError("Invalid Phone Number");
//                    return;
//                }
//                if (addressLine1.getText().toString().isEmpty()) {
//                    addressLine1.setError("Required!");
//                    return;
//                }
//                if (addressLine2.getText().toString().isEmpty()) {
//                    addressLine2.setError("Required!");
//                    return;
//                }
//
//                loadingDialog.show();
//
//
//                Map<String, Object> addAddress = new HashMap();
//                addAddress.put("addressListSize", (long) DBqueries.addressesModelList.size()+1);
//                addAddress.put("fullName"+String.valueOf((long)DBqueries.addressesModelList.size()+1),name.getText().toString());
//                addAddress.put("contactNo"+String.valueOf((long)DBqueries.addressesModelList.size()+1),contactNo.getText().toString());
//                addAddress.put("addressLineOne"+String.valueOf((long)DBqueries.addressesModelList.size()+1),addressLine1.getText().toString());
//                addAddress.put("addressLineTwo"+String.valueOf((long)DBqueries.addressesModelList.size()+1),addressLine2.getText().toString());
//                addAddress.put("additionalInfo"+String.valueOf((long)DBqueries.addressesModelList.size()+1),additionalInfo.getText().toString());
//                addAddress.put("selectedAddress"+String.valueOf((long)DBqueries.addressesModelList.size()+1),true);
//
//
//                if (DBqueries.addressesModelList.size() > 0) {
//
//                    addAddress.put("selectedAddress" + (DBqueries.selectedAddress + 1), false);
//                }
//
//                FirebaseFirestore.getInstance().collection("Users")
//                    .document(FirebaseAuth.getInstance().getUid())
//                    .collection("UserData")
//                    .document("Addresses")
//                    .update(addAddress)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful()){
//
//                                if (DBqueries.addressesModelList.size() > 0) {
//                                    DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelectedAddress(false);
//                                }
//                                DBqueries.addressesModelList.add(new AddressModel(name.getText().toString(),
//                                        contactNo.getText().toString(),addressLine1.getText().toString(),
//                                        addressLine2.getText().toString(),additionalInfo.getText().toString(),true));
//
////                                if (getIntent().getStringExtra("INTENT").equals("deliveryIntent")) {
////                                    Intent deliveryIntent = new Intent(AddAddressActivity.this, DeliveryActivity.class);
////                                    startActivity(deliveryIntent);
////                                }
////                                else{
////                                    MyAddressActivity.refreshItem(DBqueries.selectedAddress, DBqueries.addressesModelList.size()-1);
////                                }
////                                DBqueries.selectedAddress = DBqueries.addressesModelList.size() - 1;
////                                finish();
//                                dismiss();
//
//                            }
//                            else{
//                                String error = task.getException().getMessage();
//                                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
//                            }
//                        loadingDialog.dismiss();
//                    }
//                });