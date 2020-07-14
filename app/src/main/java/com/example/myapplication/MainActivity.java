package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.myapplication.UserSession.LoginFragment;
import com.example.myapplication.UserSession.RegisterFragment;
import com.example.myapplication.UserSession.UserSessionActivity;
import com.example.myapplication.ui.wishlist.WishlistFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.myapplication.UserSession.UserSessionActivity.registerFrag;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView navigationView;

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    private AppBarConfiguration mAppBarConfiguration;
    private ConstraintLayout accountCL,wishlistCL,orderCL,cartCL;
    private ImageView back_btn;
    private LinearLayout nav_about_us_and_contact_us,headerItemLayout;
    private CircleImageView img_user;
    private TextView txt_username,nav_rate,nav_share;
    private StorageReference storage ;
    private FirebaseAuth firebaseAuth;
    private long mLastClickTime = 0;
    private Dialog signInDialog;
    private TextView registerLoginDialogText;

    private static final int HOME_FRAGMENT=0;
    private static final int CART_FRAGMENT=1;
    private static final int WISHLIST_FRAGMENT=2;

    private static int currentFragment;
//    public boolean skippedLogin;
//    private FrameLayout frameLayoutHome;


    public static FirebaseUser currentUser;



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

        connectivityManager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_products, R.id.nav_offer_zone,
                R.id.nav_psv_store, R.id.nav_we_care)
                .setDrawerLayout(drawer)
                .build();
//
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


//        navController.addOnDestinationChangedListener();


/////////////////////////
//        Navigation.findNavController(navigationView).navigate(R.id.nav_offer_zone);

//        ActivityNavigator activityNavigator = new ActivityNavigator(this);
//        activityNavigator.navigate(activityNavigator.createDestination().setIntent(new Intent(this, SecondActivity.class)), null, null, null);


///////////////////////
        navigationView.getMenu().getItem(2).setActionView(R.layout.offer_zone_new);

        View headerview = navigationView.getHeaderView(0);
        txt_username = headerview.findViewById(R.id.username);
        img_user = headerview.findViewById(R.id.user_img);
        accountCL = headerview.findViewById(R.id.accountConstraintLayout);
        wishlistCL = headerview.findViewById(R.id.wishlistConstraintLayout);
        orderCL = headerview.findViewById(R.id.ordersConstraintLayout);
        cartCL = headerview.findViewById(R.id.cartConstraintLayout);
        back_btn=headerview.findViewById(R.id.nav_header_back_btn);


        ///////// sign in dialog
        signInDialog=new Dialog(MainActivity.this);
        signInDialog.setContentView(R.layout.register_or_login_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        signInDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        registerLoginDialogText= signInDialog.findViewById(R.id.registerLoginDilogText);
        TextView registerDialogBtn=signInDialog.findViewById(R.id.registerDialogButton);
        TextView loginDialogBtn=signInDialog.findViewById(R.id.loginDialogButton);




//                LinearLayout linearLayoutRegisterOrLogin=signInDialog.findViewById(R.id.linearLayoutRegisterOrLogin);
        final Intent registerIntent=new Intent(MainActivity.this, UserSessionActivity.class);
//        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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


//        skippedLogin = getIntent().getBooleanExtra("Not Registered", false);




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

//
//    public void replaceFragment(Fragment someFragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_container, someFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }


    @Override
    protected void onStart() {
        super.onStart();
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {

            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        }else
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser==null){
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(false);
        }
        else
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(true);

        if (currentUser==null){
            img_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(GravityCompat.START);
                    registerLoginDialogText.setText("Sorry, we are unable to access your account!");
                    signInDialog.show();
                }
            });
            accountCL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(GravityCompat.START);
                    registerLoginDialogText.setText("Sorry, we are unable to access your account!");
                    signInDialog.show();
                }
            });
            wishlistCL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(GravityCompat.START);
                    registerLoginDialogText.setText("Just few steps away from your wishlist!");
                    signInDialog.show();
                }
            });
            orderCL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(GravityCompat.START);
                    registerLoginDialogText.setText("Sorry, we are unable to access your orders!");
                    signInDialog.show();
                }
            });


            cartCL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(GravityCompat.START);
                    registerLoginDialogText.setText("Just few steps away from your cart!");
                    signInDialog.show();
                }
            });
        } else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();
            storage = FirebaseStorage.getInstance().getReference();
            db.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(final DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.getString("firstName") != null) {
                        txt_username.setText(documentSnapshot.getString("firstName"));
                    } else {
                        String str = documentSnapshot.getString("email");
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
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    drawer.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(MainActivity.this, EditAccountActivity.class);
                    intent.putExtra("color", "#");
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });

            accountCL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    drawer.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(MainActivity.this, EditAccountActivity.class);
                    intent.putExtra("color", "#F5977A");
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                }
            });



            wishlistCL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    drawer.closeDrawer(GravityCompat.START);
                    Intent wishlistIntent = new Intent(MainActivity.this, WishlistActivity.class);
                    startActivity(wishlistIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });

            orderCL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    drawer.closeDrawer(GravityCompat.START);
                    Intent orderIntent = new Intent(MainActivity.this, MyOrderActivity.class);
                    startActivity(orderIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
            cartCL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    drawer.closeDrawer(GravityCompat.START);
                    invalidateOptionsMenu();
                    Intent cartIntent = new Intent(MainActivity.this, MyCartActivity.class);
                    startActivity(cartIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                }
            });

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if(id==R.id.main_search){
            //todo: search
            return true;
        }else if(id==R.id.main_notifications){
            //todo: notification
            return true;
        }else if(id==R.id.main_wishlist) {
//            myWishlist();
            networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected() == true) {
                if (currentUser == null) {
                    registerLoginDialogText.setText("Just few steps away from your wishlist!");
                    signInDialog.show();
                } else {
                    Intent wishlistIntent = new Intent(MainActivity.this, WishlistActivity.class);
                    startActivity(wishlistIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return true;
                }
            }
        }else if(id==R.id.main_shopping_cart){
            networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected() == true) {
                if (currentUser==null) {
                    registerLoginDialogText.setText("Just few steps away from your cart!");
                    signInDialog.show();
                }else{
                    Intent cartIntent=new Intent(MainActivity.this,MyCartActivity.class);
                startActivity(cartIntent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;}
            }

        }else if (id == android.R.id.home) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected() == true) {
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                if (drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawers();
                }else  drawer.openDrawer(GravityCompat.START);
            }else{
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//                drawer.closeDrawers();
                if (drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawers();
                }else  drawer.closeDrawers();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//    private void myWishlist(){
////        NavHostFragment.findNavController(WishlistFragment.newInstance());
//        setFragment(new WishlistFragment(),WISHLIST_FRAGMENT);
////        invalidateOptionsMenu();
//
//    }
//
//    private void setFragment(Fragment fragment,int fragmentNo){
//        currentFragment=fragmentNo;
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
//        fragmentTransaction.commit();
//    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
