package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OrderConfirmationActivity extends AppCompatActivity {

    private TextView orderId,continueShopping;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        getSupportActionBar().hide();

        orderId=findViewById(R.id.orderId);
        continueShopping=findViewById(R.id.continueShopping);

        String orderIdString= getIntent().getStringExtra("OrderId");
        orderId.setText("Your Order Id is "+orderIdString);

        intent =new Intent(OrderConfirmationActivity.this,MainActivity.class);

        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finishAffinity();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(intent);
        finishAffinity();
    }
}
