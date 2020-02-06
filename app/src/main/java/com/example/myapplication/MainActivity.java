package com.example.myapplication;

import android.os.Bundle;

import com.example.myapplication.ui.account.AccountFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ImageView img_acc,img_wish,img_order,img_cart,img_user;
    private TextView txt_acc,txt_wish,txt_order,txt_cart,txt_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_right_black_24dp);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send,R.id.nav_we_care)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        View headerview = navigationView.getHeaderView(0);


        img_user= headerview.findViewById(R.id.user_img);
        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "img", Toast.LENGTH_SHORT).show();
            }
        });

        txt_username= headerview.findViewById(R.id.username);
        txt_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "acc", Toast.LENGTH_SHORT).show();
            }
        });


        img_acc= headerview.findViewById(R.id.account2);
        img_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "account", Toast.LENGTH_SHORT).show();

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


        img_wish= headerview.findViewById(R.id.wishlist2);
        img_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Wish", Toast.LENGTH_SHORT).show();
            }
        });

        img_order= headerview.findViewById(R.id.order_history2);
        img_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "order", Toast.LENGTH_SHORT).show();
            }
        });

        img_cart= headerview.findViewById(R.id.cart2);
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "cart", Toast.LENGTH_SHORT).show();
            }
        });


        txt_acc= headerview.findViewById(R.id.account);
        txt_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "acc", Toast.LENGTH_SHORT).show();
            }
        });
        txt_order= headerview.findViewById(R.id.order_history);
        txt_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "ord", Toast.LENGTH_SHORT).show();
            }
        });
        txt_cart= headerview.findViewById(R.id.cart);
        txt_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "cart", Toast.LENGTH_SHORT).show();
            }
        });
        txt_wish= headerview.findViewById(R.id.wishlist);
        txt_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "wish", Toast.LENGTH_SHORT).show();
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
