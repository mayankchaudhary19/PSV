package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.SystemClock;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ImageView img_acc,img_wish,img_order,img_cart,back_btn;
    private LinearLayout nav_about_us_and_contact_us;
    private CircleImageView img_user;
    private TextView txt_acc,txt_wish,txt_order,txt_cart,txt_username,nav_rate,nav_share;
    private StorageReference storage ;
    private FirebaseAuth firebaseAuth;
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_right_black_24dp);


        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_products, R.id.nav_offer_zone,
                R.id.nav_psv_store,R.id.nav_we_care)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.getMenu().getItem(2).setActionView(R.layout.offer_zone_new);

        View headerview = navigationView.getHeaderView(0);
        txt_username= headerview.findViewById(R.id.username);
        img_user= headerview.findViewById(R.id.user_img);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        storage= FirebaseStorage.getInstance().getReference();
        db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(final DocumentSnapshot documentSnapshot) {
               if(documentSnapshot.getString("firstName")!= "") {
                   txt_username.setText(documentSnapshot.getString("firstName"));
               }
                else{
                   String str= documentSnapshot.getString("email");
                   String[] arrOfStr = str.split("@", 2);

                   for (String a : arrOfStr)
                    txt_username.setText(arrOfStr[0]);

               }
                final StorageReference ref = storage.child("profiles/" + firebaseAuth.getCurrentUser().getUid() + ".jpg");
                ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Glide
                                    .with(getApplicationContext())
                                    .load(task.getResult())
                                    .centerCrop()
                                    .placeholder(R.drawable.ic_account)
                                    .into(img_user);
//                            Toast.makeText(EditAccountActivity.this, "set", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent=new Intent(MainActivity.this,EditAccountActivity.class);
                intent.putExtra("color","#F5977A");
                startActivity(intent);
            }
        });

        img_acc= headerview.findViewById(R.id.account2);
        img_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent=new Intent(MainActivity.this,EditAccountActivity.class);

                intent.putExtra("color","#F5977A");
                startActivity(intent);

//                Toast.makeText(getBaseContext(), "account", Toast.LENGTH_SHORT).show();

////                FragmentManager fm = getSupportFragmentManager();
////                Fragment fragment = new AccountFragment();
////                fm.beginTransaction().add(R.id.nav_send,fragment).commit();
//
////                Fragment fragment= new AccountFragment();
////                FragmentTransaction transaction = getFragmentManager().beginTransaction();
////                transaction.replace(R.id., fragment); // fragment container id in first parameter is the  container(Main layout id) of Activity
////                transaction.addToBackStack(null);  // this will manage backstack
////                transaction.commit();
            }
        });
        txt_acc= headerview.findViewById(R.id.account);
        txt_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                Intent intent=new Intent(MainActivity.this,EditAccountActivity.class);

                intent.putExtra("color","#F5977A");
                startActivity(intent);
            }
        });

        img_wish= headerview.findViewById(R.id.wishlist2);
        img_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Toast.makeText(getBaseContext(), "Wish", Toast.LENGTH_SHORT).show();
            }
        });

        img_order= headerview.findViewById(R.id.order_history2);
        img_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Toast.makeText(getBaseContext(), "order", Toast.LENGTH_SHORT).show();
            }
        });

        img_cart= headerview.findViewById(R.id.cart2);
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Toast.makeText(getBaseContext(), "cart", Toast.LENGTH_SHORT).show();
            }
        });



        txt_order= headerview.findViewById(R.id.order_history);
        txt_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Toast.makeText(getBaseContext(), "ord", Toast.LENGTH_SHORT).show();
            }
        });
        txt_cart= headerview.findViewById(R.id.cart);
        txt_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Toast.makeText(getBaseContext(), "cart", Toast.LENGTH_SHORT).show();
            }
        });
        txt_wish= headerview.findViewById(R.id.wishlist);
        txt_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Toast.makeText(getBaseContext(), "wish", Toast.LENGTH_SHORT).show();
            }
        });
        back_btn=headerview.findViewById(R.id.nav_header_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();

            }
        });
        nav_rate= navigationView.findViewById(R.id.nav_rate);
        nav_share= navigationView.findViewById(R.id.nav_share);
        nav_about_us_and_contact_us= navigationView.findViewById(R.id.nav_about_us_contact_us);
        nav_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            Intent intent = new Intent(MainActivity.this,)
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
