package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.Models.MyOrderItemModel;
import com.example.myapplication.Adapters.MyOrdersAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends AppCompatActivity {
    private RecyclerView myOrderRecyclerView;
    private TextView orderTitle;
    private int no_of_items_in_oderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        orderTitle= findViewById(R.id.order_activity_title);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.black_overlay2), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);



        myOrderRecyclerView= findViewById(R.id.order_items_recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        myOrderRecyclerView.setLayoutManager(layoutManager);

        List<MyOrderItemModel> myOrderItemModelList =new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.unknown2,"Small Mug [Bathroom Purpose]","Yellow Color","12 dozen","Delivered on Mon,16th Jan 2020",2));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.unknown1,"Small Mug [Bathroom Purpose]","Yellow Color","12 dozen","Cancelled",2));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.unknown3,"Small Mug [Bathroom Purpose]","Yellow Color","12 dozen","Delivered on Mon,16th Jan 2020",1));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.unknown4,"Small Mug [Bathroom Purpose]","Yellow Color","12 dozen","Waiting",4));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.unknown2,"Small Mug [Bathroom Purpose]","Yellow Color","12 dozen","Delivered on Mon,16th Jan 2020",2));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.unknown1,"Small Mug [Bathroom Purpose]","Yellow Color","12 dozen","Cancelled",2));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.unknown3,"Small Mug [Bathroom Purpose]","Yellow Color","12 dozen","Delivered on Mon,16th Jan 2020",1));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.unknown4,"Small Mug [Bathroom Purpose]","Yellow Color","12 dozen","Waiting",4));

        MyOrdersAdapter myOrdersAdapter=new MyOrdersAdapter(myOrderItemModelList);
        no_of_items_in_oderList= myOrderItemModelList.size();
        orderTitle.setText("Orders ("+no_of_items_in_oderList+")");
        myOrderRecyclerView.setAdapter(myOrdersAdapter);
        myOrdersAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.without_notification_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.main_search) {
            return true;
        }
        if (id == R.id.main_wishlist) {

            Intent wishlistIntent=new Intent(MyOrderActivity.this,WishlistActivity.class);
            startActivity(wishlistIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }
        if (id == R.id.main_shopping_cart) {
            Intent cartIntent=new Intent(MyOrderActivity.this,MyCartActivity.class);
            startActivity(cartIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }
        else if (id == android.R.id.home) {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
