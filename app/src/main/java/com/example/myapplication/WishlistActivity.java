package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Models.WishlistModel;
import com.example.myapplication.Adapters.WishlistAdapter;
import com.example.myapplication.ui.wishlist.WishlistFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WishlistActivity extends AppCompatActivity {
    public static TextView wishlistTitle;
    private RecyclerView wishlistItemRecyclerView;
    private int no_of_items;
    private Dialog loadingDialog;
    public static  WishlistAdapter wishlistAdapter;
//    public static AtomicInteger activitiesLaunched = new AtomicInteger(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (activitiesLaunched.incrementAndGet() > 1) { finish(); }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        wishlistTitle= findViewById(R.id.wishlist_activity_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.black_overlay2), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        loadingDialog=new Dialog(WishlistActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.show();

        wishlistItemRecyclerView=findViewById(R.id.wishlist_item_RecyclerView);
        GridLayoutManager layoutManager =new GridLayoutManager(this,2);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        wishlistItemRecyclerView.setLayoutManager(layoutManager);

//        if (DBqueries.wishlistModelList.size()==0){
//            DBqueries.wishlistModelList.clear();
//            DBqueries.loadWishlist(getApplicationContext(),loadingDialog,true);
//        }else {
//            loadingDialog.dismiss();
//        }
//        List<WishlistModel> wishlistModelList =new ArrayList<>();
//        wishlistModelList.add(new WishlistModel(R.drawable.unknown1,"200ml Jug","Easy to handle grip, Pink color","₹1600/pc","₹1800","(10% OFF)"));
//        wishlistModelList.add(new WishlistModel(R.drawable.unknown3,"200ml Jug","Easy to handle grip, Pink color","₹170","₹160","₹10"));
//        wishlistModelList.add(new WishlistModel(R.drawable.unknown2,"200ml Jug","Easy to handle grip, Pink color","₹170","₹160","₹10"));
//        wishlistModelList.add(new WishlistModel(R.drawable.unknown4,"200ml Jug","Easy to handle grip, Pink color","₹170","₹160","₹10"));
//        wishlistModelList.add(new WishlistModel(R.drawable.unknown5,"200ml Jug","Easy to handle grip, Pink color","₹170","₹160","₹10"));
//        wishlistModelList.add(new WishlistModel(R.drawable.unknown1,"200ml Jug","Easy to handle grip, Pink color","₹170","₹160","₹10"));
//        wishlistModelList.add(new WishlistModel(R.drawable.unknown4,"200ml Jug","Easy to handle grip, Pink color","₹170","₹160","₹10"));
//        wishlistModelList.add(new WishlistModel(R.drawable.unknown2,"200ml Jug","Easy to handle grip, Pink color","₹170","₹160","₹10"));
        if (DBqueries.wishlistModelList.size()==0){
            DBqueries.wishList.clear();
            DBqueries.loadWishlist(getApplicationContext(),loadingDialog,true);
        }else {
            loadingDialog.dismiss();
        }
//        String currentProductId=getIntent().getStringExtra("currentProductId");
//        Boolean PDtoW=getIntent().getBooleanExtra("PDtoW",false);

        wishlistAdapter=new WishlistAdapter(DBqueries.wishlistModelList,this,true,false);


        wishlistItemRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
//        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
//        firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getUid())
//                .collection("UserData").document("Wishlist")
//                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    wishlistTitle.setText("Wishlist ("+task.getResult().getLong("wishlistSize")+")");
//
//                }else{
//                    wishlistTitle.setText("Wishlist");
//                    String error= task.getException().getMessage();
//                    Toast.makeText(WishlistActivity.this, error, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


//        no_of_items= DBqueries.wishlistModelList.size();
//        wishlistTitle.setText("Wishlist ("+DBqueries.wishList.size()+")");
    }

    @Override
    protected void onStart() {
        super.onStart();
        wishlistTitle.setText("Wishlist ("+DBqueries.wishList.size()+")");



    }

    //    @Override
//    protected void onDestroy() {
//
//        //remove this activity from the counter
//        activitiesLaunched.getAndDecrement();
//
//        super.onDestroy();
//
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wishlist_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.main_search) {
            return true;
        }
        if (id == R.id.main_shopping_cart) {
            Intent cartIntent=new Intent(WishlistActivity.this,MyCartActivity.class);
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