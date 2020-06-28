package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myapplication.Adapters.AddressAdapter;
import com.example.myapplication.Models.AddressModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.MyCartActivity.SELECT_ADDRESS;

public class MyAddressActivity extends AppCompatActivity {

    private RecyclerView addressRecyclerView;
    private static AddressAdapter addressAdapter;
    private LinearLayout deliverHereContainer;
    private Button deliverHereBtn;
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

        addressRecyclerView=findViewById(R.id.adresses_recycler_view);
        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        addressRecyclerView.setLayoutManager(layoutManager);

        List<AddressModel> addressModelList =new ArrayList<>();
//        addressModelList.add(new AddressModel("Mukesh Chaudhary","9211397674","H.No.-9577, Library Road, Azad Market,","Near Sanatan Dharam Mandir","Delhi","110009",true));
//        addressModelList.add(new AddressModel("Mukesh Chaudhary","9211397674","H.No.-9577, Library Road, Azad Market,","Near Sanatan Dharam Mandir","Delhi","110009",false));
//        addressModelList.add(new AddressModel("Mukesh Chaudhary","9211397674","H.No.-9577, Library Road, Azad Market,","Near Sanatan Dharam Mandir","Delhi","110009",false));
//        addressModelList.add(new AddressModel("Mukesh Chaudhary","9211397674","H.No.-9577, Library Road, Azad Market,","Near Sanatan Dharam Mandir","Delhi","110009",false));
//        addressModelList.add(new AddressModel("Mukesh Chaudhary","9211397674","H.No.-9577, Library Road, Azad Market,","Near Sanatan Dharam Mandir","Delhi","110009",false));
//        addressModelList.add(new AddressModel("Mukesh Chaudhary","9211397674","H.No.-9577, Library Road, Azad Market,","Near Sanatan Dharam Mandir","Delhi","110009",false));

        int mode =getIntent().getIntExtra("MODE",-1);
        if (mode==SELECT_ADDRESS){
            deliverHereContainer.setVisibility(View.VISIBLE);
            ((SimpleItemAnimator)addressRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        }else {
            ((SimpleItemAnimator)addressRecyclerView.getItemAnimator()).setSupportsChangeAnimations(true);
            deliverHereContainer.setVisibility(View.GONE);
        }
        addressAdapter =new AddressAdapter(addressModelList,mode);
        addressRecyclerView.setAdapter(addressAdapter);
        addressAdapter.notifyDataSetChanged();



    }


    public static void refreshItem(int notSelected,int selected){
        addressAdapter.notifyItemChanged(notSelected);
        addressAdapter.notifyItemChanged(selected);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == android.R.id.home) {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
