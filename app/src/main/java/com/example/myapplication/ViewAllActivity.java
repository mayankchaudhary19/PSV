package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.widget.GridView;
import android.widget.TextView;

import com.example.myapplication.Adapters.GridProductLayoutAdapter;
import com.example.myapplication.Adapters.ViewAllAdapter;
import com.example.myapplication.Adapters.WishlistAdapter;
import com.example.myapplication.Models.GridProductModel;
import com.example.myapplication.Models.ViewAllWithRatingModel;
import com.example.myapplication.Models.WishlistModel;
import com.example.myapplication.UserSession.LoginFragment;
import com.example.myapplication.UserSession.RegisterFragment;
import com.example.myapplication.UserSession.UserSessionActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.UserSession.UserSessionActivity.registerFrag;

public class ViewAllActivity extends AppCompatActivity {

    private TextView viewAllTitle;
    private RecyclerView recyclerView1,recyclerView2;
    private GridView gridView;
    public static List<ViewAllWithRatingModel> viewAllProductWithRatingModelList;
    public static List<GridProductModel> gridProductModelList = new ArrayList<>();
    private Dialog signInDialog;
    private TextView registerLoginDialogText;

    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        viewAllTitle= findViewById(R.id.viewAll_activity_title);
        setSupportActionBar(toolbar);
        viewAllTitle.setText(getIntent().getStringExtra("title"));

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.black_overlay2), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        recyclerView1=findViewById(R.id.recycler_view_1);
        recyclerView2=findViewById(R.id.recycler_view_2);
        gridView=findViewById(R.id.grid_view);


        //////signIn Dialog
        signInDialog=new Dialog(ViewAllActivity.this);
        signInDialog.setContentView(R.layout.register_or_login_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        signInDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        registerLoginDialogText= signInDialog.findViewById(R.id.registerLoginDilogText);
        TextView registerDialogBtn=signInDialog.findViewById(R.id.registerDialogButton);
        TextView loginDialogBtn=signInDialog.findViewById(R.id.loginDialogButton);
//                LinearLayout linearLayoutRegisterOrLogin=signInDialog.findViewById(R.id.linearLayoutRegisterOrLogin);
        final Intent registerIntent=new Intent(ViewAllActivity.this, UserSessionActivity.class);

//                linearLayoutRegisterOrLogin.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        signInDialog.dismiss();
//                        startActivity(registerIntent);
////                        finishAffinity();
//                    }
//                });
        registerDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment.disableCloseBtn=true;
                LoginFragment.disableCloseBtn=true;
                signInDialog.dismiss();
                registerFrag=true;
                startActivity(registerIntent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
        loginDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment.disableCloseBtn=true;
                LoginFragment.disableCloseBtn=true;
                signInDialog.dismiss();
                registerFrag=false;
                startActivity(registerIntent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

        //////signIn Dialog



        int LAYOUT_CODE=getIntent().getIntExtra("LAYOUT_CODE",-1);
        //////1
        //Like Wishlist
        if (LAYOUT_CODE==3) {

            recyclerView1.setVisibility(View.VISIBLE);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView1.setLayoutManager(layoutManager);
            List<WishlistModel> wishlistModelList = new ArrayList<>();
//            wishlistModelList.add(new WishlistModel(R.drawable.unknown1, "200ml Jug", "Easy to handle grip, Pink color", "₹1600/pc", "₹1800", "(10% OFF)"));
//            wishlistModelList.add(new WishlistModel(R.drawable.unknown3, "200ml Jug", "Easy to handle grip, Pink color", "₹1600/pc", "₹1800", "(10% OFF)"));
//            wishlistModelList.add(new WishlistModel(R.drawable.unknown2, "200ml Jug", "Easy to handle grip, Pink color", "₹170", "₹160", "₹10"));
//            wishlistModelList.add(new WishlistModel(R.drawable.unknown4, "200ml Jug", "Easy to handle grip, Pink color", "₹170", "₹160", "₹10"));
//            wishlistModelList.add(new WishlistModel(R.drawable.unknown5, "200ml Jug", "Easy to handle grip, Pink color", "₹170", "₹160", "₹10"));
//            wishlistModelList.add(new WishlistModel(R.drawable.unknown1, "200ml Jug", "Easy to handle grip, Pink color", "₹170", "₹160", "₹10"));
//            wishlistModelList.add(new WishlistModel(R.drawable.unknown4, "200ml Jug", "Easy to handle grip, Pink color", "₹170", "₹160", "₹10"));
//            wishlistModelList.add(new WishlistModel(R.drawable.unknown2, "200ml Jug", "Easy to handle grip, Pink color", "₹170", "₹160", "₹10"));

            WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelList,getApplicationContext(), false,false);
            recyclerView1.setAdapter(wishlistAdapter);
            wishlistAdapter.notifyDataSetChanged();
        }
        else if (LAYOUT_CODE==2) {
            /////2
            //Vertical RecyclerView
            recyclerView2.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
            layoutManager2.setOrientation(RecyclerView.VERTICAL);
            recyclerView2.setLayoutManager(layoutManager2);
//            List<ViewAllWithRatingModel> viewAllWithRatingModelList = new ArrayList<>();
//            viewAllWithRatingModelList.add(new ViewAllWithRatingModel(R.drawable.unknown1, "200ml Jug", "Easy to handle grip, Pink color", "₹1600/pc", "₹1800", "(10% OFF)", "4.5", "(13 ratings)"));
//            viewAllWithRatingModelList.add(new ViewAllWithRatingModel(R.drawable.unknown2, "200ml Jug", "Easy to handle grip, Pink color", "₹1600/pc", "₹1800", "(10% OFF)", "4.5", "(13 ratings)"));
//            viewAllWithRatingModelList.add(new ViewAllWithRatingModel(R.drawable.unknown3, "200ml Jug", "Easy to handle grip, Pink color", "₹1600/pc", "₹1800", "(10% OFF)", "4.5", "(13 ratings)"));
//            viewAllWithRatingModelList.add(new ViewAllWithRatingModel(R.drawable.unknown4, "200ml Jug", "Easy to handle grip, Pink color", "₹1600/pc", "₹1800", "(10% OFF)", "4.5", "(13 ratings)"));
//            viewAllWithRatingModelList.add(new ViewAllWithRatingModel(R.drawable.unknown5, "200ml Jug", "Easy to handle grip, Pink color", "₹1600/pc", "₹1800", "(10% OFF)", "4.5", "(13 ratings)"));
//            viewAllWithRatingModelList.add(new ViewAllWithRatingModel(R.drawable.unknown1, "200ml Jug", "Easy to handle grip, Pink color", "₹1600/pc", "₹1800", "(10% OFF)", "4.5", "(13 ratings)"));
//            viewAllWithRatingModelList.add(new ViewAllWithRatingModel(R.drawable.unknown2, "200ml Jug", "Easy to handle grip, Pink color", "₹1600/pc", "₹1800", "(10% OFF)", "4.5", "(13 ratings)"));
//            viewAllWithRatingModelList.add(new ViewAllWithRatingModel(R.drawable.unknown3, "200ml Jug", "Easy to handle grip, Pink color", "₹1600/pc", "₹1800", "(10% OFF)", "4.5", "(13 ratings)"));

            ViewAllAdapter viewAllAdapter = new ViewAllAdapter(viewAllProductWithRatingModelList);
            recyclerView2.setAdapter(viewAllAdapter);
            viewAllAdapter.notifyDataSetChanged();
        }
        //////3
        //Grid Layout
        else if(LAYOUT_CODE==1) {
            gridView.setVisibility(View.VISIBLE);
//            List<GridProductModel> gridProductModelList = new ArrayList<>();
//            gridProductModelList.add(new GridProductModel(R.drawable.unknown1, "Pink Jug", "2 New Colors Available", "₹5000/-"));
//            gridProductModelList.add(new GridProductModel(R.drawable.unknown2, "Yellow Mug", "20% Discount", "₹5000/-"));
//            gridProductModelList.add(new GridProductModel(R.drawable.unknown3, "Square Container", "2 New Colors Available", "₹5000/-"));
//            gridProductModelList.add(new GridProductModel(R.drawable.unknown4, "Round Container", "New Designs Available", "₹5000/-"));
//            gridProductModelList.add(new GridProductModel(R.drawable.unknown1, "Pink Jug", "2 New Colors Available", "₹5000/-"));
//            gridProductModelList.add(new GridProductModel(R.drawable.unknown2, "Yellow Mug", "20% Discount", "₹5000/-"));
//            gridProductModelList.add(new GridProductModel(R.drawable.unknown3, "Square Container", "2 New Colors Available", "₹5000/-"));
//            gridProductModelList.add(new GridProductModel(R.drawable.unknown4, "Round Container", "New Designs Available", "₹5000/-"));
//            gridProductModelList.add(new GridProductModel(R.drawable.unknown1, "Pink Jug", "2 New Colors Available", "₹5000/-"));
//            gridProductModelList.add(new GridProductModel(R.drawable.unknown2, "Yellow Mug", "20% Discount", "₹5000/-"));
//            gridProductModelList.add(new GridProductModel(R.drawable.unknown3, "Square Container", "2 New Colors Available", "₹5000/-"));
//            gridProductModelList.add(new GridProductModel(R.drawable.unknown4, "Round Container", "New Designs Available", "₹5000/-"));

            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(gridProductModelList);
            gridView.setAdapter(gridProductLayoutAdapter);
            gridProductLayoutAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.without_notification_menu, menu);

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if(id==R.id.main_search){
            //todo: search
            return true;
        }else if(id==R.id.main_wishlist){
            if (currentUser==null) {
                registerLoginDialogText.setText("Just few steps away from your wishlist!");
                signInDialog.show();
            }else{
            Intent wishlistIntent=new Intent(ViewAllActivity.this,WishlistActivity.class);
            startActivity(wishlistIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;}
        }else if(id==R.id.main_shopping_cart) {
            if (currentUser==null) {
                registerLoginDialogText.setText("Just few steps away from your cart!");
                signInDialog.show();
            }else{
            Intent cartIntent = new Intent(ViewAllActivity.this, MyCartActivity.class);
            startActivity(cartIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;}
        }else if (id == android.R.id.home) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            }
            return super.onOptionsItemSelected(item);

    }
}
