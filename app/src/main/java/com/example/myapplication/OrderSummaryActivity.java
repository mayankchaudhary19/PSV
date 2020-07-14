package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapters.MyCartAdapter;
import com.example.myapplication.Models.MyCartItemModel;

import java.util.ArrayList;
import java.util.List;

public class OrderSummaryActivity extends AppCompatActivity {

    public static TextView orderSummaryTitle,proceedForPaymentBtn,placeOrderOnWhatsAppBtn;
    private RecyclerView deliveryRecyclerView;
    private Button changeOrAddAddress;
    public static LinearLayout priceDetailsLL;

    private TextView fullName,addressLine1,addressLine2,state,pincode,contactNumber,saveAddressAsTxt,editAdd,selectAdd;
    public static List <MyCartItemModel>cartItemModelList;
//    private Dialog loadingDialog;

    public static TextView  totalItems,totalItemsPrice,totalItemsDiscount,couponDiscountTxt,totalCouponDiscount,shippingCharges,subTotal;


    public static final int SELECT_ADDRESS = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        orderSummaryTitle= findViewById(R.id.order_summary_activity_title);
        changeOrAddAddress=findViewById(R.id.changeOrAddAddress);

        deliveryRecyclerView = findViewById(R.id.delivery_recyclerview);

        fullName=findViewById(R.id.full_name);
        addressLine1=findViewById(R.id.address_line1);
        addressLine2=findViewById(R.id.address_line2);
        state=findViewById(R.id.state);
        pincode=findViewById(R.id.pincode);
        contactNumber = findViewById(R.id.mobile_number);
        saveAddressAsTxt=findViewById(R.id.saveAddressAsTxt);

        proceedForPaymentBtn=findViewById(R.id.proceedForPayment);
        placeOrderOnWhatsAppBtn=findViewById(R.id.placeOrderOnWhatsApp);

        totalItems=findViewById(R.id.total_items);
        totalItemsPrice=findViewById(R.id.total_items_price);
        totalItemsDiscount=findViewById(R.id.saved_or_discount_price);
        couponDiscountTxt=findViewById(R.id.couponDiscountText);
        totalCouponDiscount=findViewById(R.id.coupon_discount_price);
        shippingCharges=findViewById(R.id.shipping_price);
        subTotal=findViewById(R.id.total_or_subTotal_price);
        priceDetailsLL=findViewById(R.id.PriceDetailsLL);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.black_overlay2), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

//        loadingDialog = new Dialog(OrderSummaryActivity.this);
//        loadingDialog.setContentView(R.layout.loading_progress_dialog);
//        loadingDialog.setCancelable(false);
//        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);

        MyCartAdapter cartAdapter = new MyCartAdapter(cartItemModelList, getApplicationContext());
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeOrAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAddressesIntent = new Intent(OrderSummaryActivity.this,MyAddressActivity.class);
                myAddressesIntent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(myAddressesIntent);
            }
        });
        proceedForPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OrderSummaryActivity.this,PaymentActivity.class);
                startActivity(intent);
            }
        });

        placeOrderOnWhatsAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//todo: whatsApp feature
            }
        });


    }





    @Override
    protected void onStart() {
        super.onStart();

        fullName.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getFullName());
        addressLine1.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAddressLine1());
        addressLine2.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAddressLine2());
        state.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getState());
        pincode.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getPincode());
        contactNumber.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getContactNumber());
        saveAddressAsTxt.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAddressType());

        int totalItems = 0;
        int totalItemPrice = 0;
        int discountItemsPrice=0;
        int discountInitialItemsPrice=0;
        long couponAvailable=0;

        for (int x = 0; x<MyCartAdapter.myCartItemModelList.size(); x++){

            if (MyCartAdapter.myCartItemModelList.get(x).isInStock()){
                totalItems++;
                totalItemPrice = totalItemPrice + Integer.parseInt(MyCartAdapter.myCartItemModelList.get(x).getProductPrice());
                discountInitialItemsPrice=discountInitialItemsPrice+Integer.parseInt(MyCartAdapter.myCartItemModelList.get(x).getProductInitialPrice());
                discountItemsPrice=discountInitialItemsPrice-totalItemPrice;
                couponAvailable=couponAvailable+Long.parseLong(String.valueOf(MyCartAdapter.myCartItemModelList.get(x).getFreeCouponsAvailable()));
            }
        }

//
//        Bundle bundle = new Bundle();
//        bundle.putString("params", "₹"+totalItemPrice);
//// set MyFragment Arguments
//        AddNewAddressFragment myObj = new AddNewAddressFragment();
//        myObj.setArguments(bundle);

        if (totalItems==1){
            OrderSummaryActivity.totalItems.setText("Price ( "+totalItems+" Item )");
        }else{
            OrderSummaryActivity.totalItems.setText("Price ( "+totalItems+" Items )");
        }
        OrderSummaryActivity.totalItemsPrice.setText("₹"+discountInitialItemsPrice);
        OrderSummaryActivity.totalItemsDiscount.setText("₹"+discountItemsPrice);
        //todo: amount for coupon
        if (couponAvailable!=0){
            OrderSummaryActivity.totalCouponDiscount.setText("Apply Coupon");
            OrderSummaryActivity.couponDiscountTxt .setVisibility(View.VISIBLE);
            OrderSummaryActivity.totalCouponDiscount.setVisibility(View.VISIBLE);
        }else{
            OrderSummaryActivity.totalCouponDiscount.setText("");
            OrderSummaryActivity.couponDiscountTxt .setVisibility(View.GONE);
            OrderSummaryActivity.totalCouponDiscount.setVisibility(View.GONE);
        }
        //todo: amount for delivery
        if (totalItemPrice > 20000){
            OrderSummaryActivity.shippingCharges.setText("Free");
        }
        else{
            OrderSummaryActivity.shippingCharges.setText("Extra*");
        }
        OrderSummaryActivity.subTotal.setText("₹"+totalItemPrice);
//        MyCartActivity.totalAmount.setText("₹"+totalItemPrice);


        if (totalItemPrice==0){
            priceDetailsLL.setVisibility(View.GONE);
        }else {
            priceDetailsLL.setVisibility(View.VISIBLE);

        }






    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        loadingDialog.dismiss();
//    }
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
