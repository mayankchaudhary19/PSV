package com.example.myapplication.Adapters;

import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.Fragments.ProductDescriptionFragment;
import com.example.myapplication.Fragments.ProductSpecificationFragment;
import com.example.myapplication.Models.ProductSpecificationModel;
import com.example.myapplication.ProductDetailsActivity;

import java.util.List;

public class ProductDescriptionAdapter extends FragmentPagerAdapter {
    private int totalTabs;
    private String productDesc,productMaterialCare;
    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductDescriptionAdapter(@NonNull FragmentManager fm, int totalTabs, String productDesc, String productMaterialCare, List<ProductSpecificationModel> productSpecificationModelList) {
        super(fm);
        this.totalTabs = totalTabs;
        this.productDesc = productDesc;
        this.productMaterialCare = productMaterialCare;
        this.productSpecificationModelList = productSpecificationModelList;
    }
//    public ProductDescriptionAdapter(@NonNull FragmentManager fm, int totalTabs) {
//        super(fm);
//        this.totalTabs = totalTabs;
//    }


//    public ProductDetailsAdapter(@NonNull FragmentManager fm, int bint totalTabs) {
//        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//
//    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
    switch (position){
        case 0:
            ProductDescriptionFragment productDescriptionFragment= new ProductDescriptionFragment();
            productDescriptionFragment.productDescriptionData=productDesc;
            productDescriptionFragment.productCareDescriptionData=productMaterialCare;
            return productDescriptionFragment;

        case 1:
            ProductSpecificationFragment productSpecificationFragment= new ProductSpecificationFragment();
            productSpecificationFragment.productSpecificationModelList=productSpecificationModelList;
            return productSpecificationFragment;

        default:
            return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
