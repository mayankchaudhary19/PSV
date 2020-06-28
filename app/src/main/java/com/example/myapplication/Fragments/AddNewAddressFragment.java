package com.example.myapplication.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AddAddressActivity;
import com.example.myapplication.DBqueries;
import com.example.myapplication.Models.AddressModel;
import com.example.myapplication.MyAddressActivity;
import com.example.myapplication.MyCartActivity;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNewAddressFragment extends BottomSheetDialogFragment {

    private ConstraintLayout bottomSheetBackground;
    private TextView totalAmount,addAddressContinueBtn;
    private EditText name,contactNo,addressLine1,addressLine2,additionalInfo;
    private Dialog loadingDialog;
    private String totalAmt;



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
        totalAmount=view.findViewById(R.id.cart_total_amount);
        addAddressContinueBtn=view.findViewById(R.id.addAddressContinueBtn);
        additionalInfo=view.findViewById(R.id.additionalInfoAdd);

        Bundle bundle = getArguments();
        String totalAmt = bundle.getString("params");

        totalAmount.setText(totalAmt);

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



        addAddressContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setError(null);
                contactNo.setError(null);
                addressLine1.setError(null);
                addressLine2.setError(null);

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
                if (addressLine1.getText().toString().isEmpty()) {
                    addressLine1.setError("Required!");
                    return;
                }
                if (addressLine2.getText().toString().isEmpty()) {
                    addressLine2.setError("Required!");
                    return;
                }

                loadingDialog.show();


                Map<String, Object> addAddress = new HashMap();
                addAddress.put("addressListSize", (long) DBqueries.addressesModelList.size()+1);
                addAddress.put("fullName"+String.valueOf((long)DBqueries.addressesModelList.size()+1),name.getText().toString());
                addAddress.put("contactNo"+String.valueOf((long)DBqueries.addressesModelList.size()+1),contactNo.getText().toString());
                addAddress.put("addressLineOne"+String.valueOf((long)DBqueries.addressesModelList.size()+1),addressLine1.getText().toString());
                addAddress.put("addressLineTwo"+String.valueOf((long)DBqueries.addressesModelList.size()+1),addressLine2.getText().toString());
                addAddress.put("additionalInfo"+String.valueOf((long)DBqueries.addressesModelList.size()+1),additionalInfo.getText().toString());
                addAddress.put("selectedAddress"+String.valueOf((long)DBqueries.addressesModelList.size()+1),true);


                if (DBqueries.addressesModelList.size() > 0) {

                    addAddress.put("selectedAddress" + (DBqueries.selectedAddress + 1), false);
                }

                FirebaseFirestore.getInstance().collection("Users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection("UserData")
                    .document("Addresses")
                    .update(addAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                if (DBqueries.addressesModelList.size() > 0) {
                                    DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelectedAddress(false);
                                }
                                DBqueries.addressesModelList.add(new AddressModel(name.getText().toString(),
                                        contactNo.getText().toString(),addressLine1.getText().toString(),
                                        addressLine2.getText().toString(),additionalInfo.getText().toString(),true));

//                                if (getIntent().getStringExtra("INTENT").equals("deliveryIntent")) {
//                                    Intent deliveryIntent = new Intent(AddAddressActivity.this, DeliveryActivity.class);
//                                    startActivity(deliveryIntent);
//                                }
//                                else{
//                                    MyAddressActivity.refreshItem(DBqueries.selectedAddress, DBqueries.addressesModelList.size()-1);
//                                }
//                                DBqueries.selectedAddress = DBqueries.addressesModelList.size() - 1;
//                                finish();
                                MyCartActivity.openSelectedAdd=true;
                                dismiss();

                            }
                            else{
                                MyCartActivity.openSelectedAdd=false;
                                String error = task.getException().getMessage();
                                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                            }
                        loadingDialog.dismiss();
                    }
                });

            }
        });

        return view;
    }

    private static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}
