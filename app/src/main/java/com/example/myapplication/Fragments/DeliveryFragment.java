package com.example.myapplication.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.DBqueries;
import com.example.myapplication.MyAddressActivity;
import com.example.myapplication.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

import static com.example.myapplication.MyCartActivity.SELECT_ADDRESS;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryFragment extends BottomSheetDialogFragment {

    public DeliveryFragment() {
        // Required empty public constructor
    }

    private TextView name,addressLine1,addressLine2,contactNo;

    private Dialog loadingDialog;
    private TextView changeAddBtn, continueBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_delivery, container, false);
        name=view.findViewById(R.id.address_full_name);
        addressLine1=view.findViewById(R.id.address_line1);
        addressLine2=view.findViewById(R.id.address_line2_with_pincode);
        contactNo=view.findViewById(R.id.address_mobile_number);
        changeAddBtn=view.findViewById(R.id.changeAddress);
        continueBtn=view.findViewById(R.id.addAddressContinueBtn);

        changeAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeAddress =new Intent(getContext(), MyAddressActivity.class);
                changeAddress.putExtra("MODE",SELECT_ADDRESS);
                startActivity(changeAddress);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        name.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getFullName());
        addressLine1.setText( DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAddressLine1());
        addressLine2.setText (DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAddressLine2());
        contactNo.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getContactNumber());
    }


}
