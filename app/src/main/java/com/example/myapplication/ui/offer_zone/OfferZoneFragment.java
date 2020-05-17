package com.example.myapplication.ui.offer_zone;

import android.app.ActionBar;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.RewardAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Models.RewardModel;
import com.example.myapplication.MyCartActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OfferZoneFragment extends Fragment {

   private RecyclerView RewardsRecyclerView;
   private ImageView drawerToggleBtn,cartIcon;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_offer_zone, container, false);
        setHasOptionsMenu(true);


///////// to open navigation drawer
        drawerToggleBtn=root.findViewById(R.id.drawer_toogle_btn);
        final DrawerLayout drawer = ((MainActivity) getActivity()).findViewById(R.id.drawer_layout);

        drawerToggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else
                { drawer.openDrawer(GravityCompat.START); }
            }
        });
///////// to open navigation drawer

///////// to change the color of status bar to orange and status icons to white
        Window window = ((MainActivity) getActivity()).getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FA9A7E"));
        ((MainActivity) getActivity()).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_LAYOUT_FLAGS);
///////// to change the color of status bar to orange and status icons to white

///////// to hide the toolbar shown in Main activity

        ((MainActivity) getActivity()).getSupportActionBar().hide();
///////// RecyclerView in Fragment
        RewardsRecyclerView=root.findViewById(R.id.myRewardsRecyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        RewardsRecyclerView.setLayoutManager(layoutManager);
        List<RewardModel> rewardModelList =new ArrayList<>();
        rewardModelList.add(new RewardModel(R.drawable.ic_tag,"Discount","Get 10% OFF on any product above Rs.5000/- and get additional Rs.100/- discount on next purchase","Till 27th May,2020"));
        rewardModelList.add(new RewardModel(R.drawable.ic_tag,"New Deals","Get 20% OFF on any product above Rs.20000/-","Till 30th May,2020"));
        rewardModelList.add(new RewardModel(R.drawable.ic_new,"Sale","Buy 50 pieces get 2 free ","Till 15th June,2020"));

        rewardModelList.add(new RewardModel(R.drawable.ic_tag,"Discount","Get 10% OFF on any product above Rs.5000/- and get additional Rs.100/- discount on next purchase","Till 27th May,2020"));
        rewardModelList.add(new RewardModel(R.drawable.ic_tag,"New Deals","Get 20% OFF on any product above Rs.20000/-","Till 30th May,2020"));
        rewardModelList.add(new RewardModel(R.drawable.ic_new,"Sale","Buy 50 pieces get 2 free ","Till 15th June,2020"));

        RewardAdapter rewardAdapter =new RewardAdapter(rewardModelList);
        RewardsRecyclerView.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();
///////// RecyclerView in Fragment

        cartIcon=root.findViewById(R.id.cartIcon);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(), MyCartActivity.class);
//                startActivity(intent);
                ActivityOptions options =
                        ActivityOptions.makeCustomAnimation(getContext(), R.anim.fade_in, R.anim.fade_out);
                getActivity().startActivity(intent, options.toBundle());
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        return root;

    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        Window window = ((MainActivity) getActivity()).getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(Color.parseColor("#FA9A7E"));
//
//        final DrawerLayout drawer = ((MainActivity) getActivity()).findViewById(R.id.drawer_layout);
//
//        drawerToggleBtn=view.findViewById(R.id.drawer_toogle_btn);
//
//        drawerToggleBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(drawer.isDrawerVisible(GravityCompat.START)) {
//                    drawer.closeDrawer(GravityCompat.START);
//                } else
//                { drawer.openDrawer(GravityCompat.START); }
//            }
//        });
//
//        ((MainActivity) getActivity()).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_LAYOUT_FLAGS);
//
//    }

//    @Override
//    public void onResume() {
//        super.onResume();
//    }


/////////// trying to work with the lifecycle of fragment
//    @Override
//    public void onStop() {
//        super.onStop();
//        Window window = ((MainActivity) getActivity()).getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(Color.parseColor("#FA9A7E"));
//        ((MainActivity) getActivity()).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_LAYOUT_FLAGS);
//        ((MainActivity) getActivity()).getSupportActionBar().hide();
//
//    }
/////////// trying to work with the lifecycle of fragment


//////// to show white toolbar in other fragments
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Window window = ((MainActivity) getActivity()).getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(Color.parseColor("#ffffff"));
//        ((MainActivity) getActivity()).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        ((MainActivity) getActivity()).getSupportActionBar().show();
//    }
//////// to show white toolbar in other fragments


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Window window = ((MainActivity) getActivity()).getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(Color.parseColor("#ffffff"));
//        ((MainActivity) getActivity()).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//
//        ((MainActivity) getActivity()).getSupportActionBar().show();
//    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.offer_zone_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }




}
