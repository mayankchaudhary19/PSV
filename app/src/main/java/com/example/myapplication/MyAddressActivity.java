package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapters.AddressAdapter;
import com.example.myapplication.Fragments.AddNewAddressFragment;
import com.example.myapplication.Models.AddressModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.myapplication.EditAccountActivity.MANAGE_ADDRESS;
import static com.example.myapplication.MyCartActivity.SELECT_ADDRESS;

public class MyAddressActivity extends AppCompatActivity {

    private RecyclerView addressRecyclerView;
    private static AddressAdapter addressAdapter;
    private LinearLayout deliverHereContainer,addNewAddressBtn;
    private Button deliverHereBtn;
    private TextView savedAddressNo;
    Context context;
    private int previousAddress;
    public static Dialog loadingDialog;
    private int mode;
    private TextView myAddressTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.black_overlay2), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        deliverHereBtn=findViewById(R.id.deliver_here_btn);
        deliverHereContainer=findViewById(R.id.deliverHereContainer);
        addNewAddressBtn=findViewById(R.id.add_new_address_btn);
        savedAddressNo=findViewById(R.id.address_saved_tv);
        addressRecyclerView=findViewById(R.id.adresses_recycler_view);
        myAddressTitle=findViewById(R.id.myAddressTitle);

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        previousAddress = DBqueries.selectedAddress;

        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        addressRecyclerView.setLayoutManager(layoutManager);

//        List<AddressModel> addressModelList =new ArrayList<>();
//        addressModelList.add(new AddressModel("Mukesh Chaudhary","9211397674","H.No.-9577, Library Road, Azad Market,","Near Sanatan Dharam Mandir","Delhi","110009","HOME",true));
//        addressModelList.add(new AddressModel("Mukesh Chaudhary","9211397674","H.No.-9577, Library Road, Azad Market,","Near Sanatan Dharam Mandir","Delhi","110009","HOME",false));
//        addressModelList.add(new AddressModel("Mukesh Chaudhary","9211397674","H.No.-9577, Library Road, Azad Market,","Near Sanatan Dharam Mandir","Delhi","110009","HOME",false));
//        addressModelList.add(new AddressModel("Mukesh Chaudhary","9211397674","H.No.-9577, Library Road, Azad Market,","Near Sanatan Dharam Mandir","Delhi","110009",false));
//        addressModelList.add(new AddressModel("Mukesh Chaudhary","9211397674","H.No.-9577, Library Road, Azad Market,","Near Sanatan Dharam Mandir","Delhi","110009",false));
//        addressModelList.add(new AddressModel("Mukesh Chaudhary","9211397674","H.No.-9577, Library Road, Azad Market,","Near Sanatan Dharam Mandir","Delhi","110009",false));
//        addressModelList.add(new AddressModel("Mukesh Chaudhary","9211397674","H.No.-9577, Library Road, Azad Market,","Near Sanatan Dharam Mandir","Delhi","110009",false));
//        addressModelList.add(new AddressModel("Mukesh Chaudhary","9211397674","H.No.-9577, Library Road, Azad Market,","Near Sanatan Dharam Mandir","Delhi","110009",false));

        mode =getIntent().getIntExtra("MODE",-1);
        if (mode==SELECT_ADDRESS){
            myAddressTitle.setText("Change Address");
            deliverHereContainer.setVisibility(View.VISIBLE);
            ((SimpleItemAnimator)addressRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            deliverHereBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (DBqueries.selectedAddress != previousAddress) {

                        final int previousAddressIndex = previousAddress;
                        loadingDialog.show();
                        Map<String, Object> updateSelection= new HashMap<>();
                        updateSelection.put("selectedAddress"+String.valueOf(previousAddress+1),false);
                        updateSelection.put("selectedAddress"+String.valueOf(DBqueries.selectedAddress+1),true);
                        previousAddress = DBqueries.selectedAddress;

                        FirebaseFirestore.getInstance().collection("Users")
                                .document(FirebaseAuth.getInstance().getUid())
                                .collection("UserData")
                                .document("Addresses")
                                .update(updateSelection)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            finish();
                                        }
                                        else{
                                            previousAddress = previousAddressIndex;
                                            String error = task.getException().getMessage();
                                            Toast.makeText(MyAddressActivity.this, error, Toast.LENGTH_SHORT).show();
                                        }
                                        loadingDialog.dismiss();
                                    }
                                });
                    }
                    else{
                        finish();
                    }
                }
            });


        }else {
            myAddressTitle.setText("Manage Address");
            ((SimpleItemAnimator)addressRecyclerView.getItemAnimator()).setSupportsChangeAnimations(true);
            deliverHereContainer.setVisibility(View.GONE);
        }




        addressAdapter =new AddressAdapter(DBqueries.addressesModelList,mode,context);
        addressRecyclerView.setAdapter(addressAdapter);
//        ((SimpleItemAnimator) addressRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        addressAdapter.notifyDataSetChanged();

//        if (addressAdapter.getItemCount()==1)
//            savedAddressNo.setText(DBqueries.addressesModelList.size()+" Saved Address");
//        else if (addressAdapter.getItemCount()>1)
//            savedAddressNo.setText(DBqueries.addressesModelList.size()+" Saved Addresses");
//        else
//            savedAddressNo.setText("");


        addNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewAddressFragment bottomSheet = new AddNewAddressFragment();

//                bottomSheet.setArguments(bundle);
//                Bundle bundle2 = new Bundle();
                Bundle bundle = new Bundle();
                bundle.putBoolean("showContinueBtn",false);
                if (mode==MANAGE_ADDRESS){
                    bundle.putBoolean("NewAddInManageAddress",true);
                }


                bottomSheet.setArguments(bundle);

                bottomSheet.show(getSupportFragmentManager(),"TAG");
                getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                    @Override
                    public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                        super.onFragmentDestroyed(fm, f);
                        if (addressAdapter.getItemCount()==1)
                            savedAddressNo.setText(DBqueries.addressesModelList.size()+" Saved Address");
                        else if (addressAdapter.getItemCount()>1)
                            savedAddressNo.setText(DBqueries.addressesModelList.size()+" Saved Addresses");
                        else
                            savedAddressNo.setText("");
                        getSupportFragmentManager().unregisterFragmentLifecycleCallbacks(this);
                    }
                },false);
            }
        });

    }


    public static void refreshItem(int notSelected,int selected){
        addressAdapter.notifyItemChanged(notSelected);
        addressAdapter.notifyItemChanged(selected);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (addressAdapter.getItemCount()==1)
            savedAddressNo.setText(DBqueries.addressesModelList.size()+" Saved Address");
        else if (addressAdapter.getItemCount()>1)
            savedAddressNo.setText(DBqueries.addressesModelList.size()+" Saved Addresses");
        else
            savedAddressNo.setText("");
//        savedAddressNo.setText(String.valueOf(DBqueries.addressesModelList.size())+" saved addresses");
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (addressAdapter.getItemCount()==1)
//            savedAddressNo.setText(DBqueries.addressesModelList.size()+" Saved Address");
//        else if (addressAdapter.getItemCount()>1)
//            savedAddressNo.setText(DBqueries.addressesModelList.size()+" Saved Addresses");
//        else
//            savedAddressNo.setText("");
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (DBqueries.selectedAddress != previousAddress){
                DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelectedAddress(false);
                DBqueries.addressesModelList.get(previousAddress).setSelectedAddress(true);
                DBqueries.selectedAddress = previousAddress;
            }
//            finish();
    }

    //    @Override
//    public void onBackPressed() {
//        // matlab agar user ne deliver here btn pr click hi nhi kiya
//        // to uske changes save nhi krna h
//        if (mode==SELECT_ADDRESS){
//            if (DBqueries.selectedAddress != previousAddress){
//                DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelectedAddress(false);
//                DBqueries.addressesModelList.get(previousAddress).setSelectedAddress(true);
//                DBqueries.selectedAddress = previousAddress;
//            }
//            finish();
//        }
//    }
    public Context getContext() {
        return context;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == android.R.id.home) {
            if (DBqueries.selectedAddress != previousAddress){
                DBqueries.addressesModelList.get(DBqueries.selectedAddress).setSelectedAddress(false);
                DBqueries.addressesModelList.get(previousAddress).setSelectedAddress(true);
                DBqueries.selectedAddress = previousAddress;
            }
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
