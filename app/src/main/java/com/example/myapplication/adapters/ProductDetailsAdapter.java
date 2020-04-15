package com.example.myapplication.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.Fragments.ProductDescriptionFragment;
import com.example.myapplication.Fragments.ProductSpecificationFragment;

public class ProductDetailsAdapter extends FragmentPagerAdapter {
    private int totalTabs;

    public ProductDetailsAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }


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
            return productDescriptionFragment;

        case 1:
            ProductSpecificationFragment productSpecificationFragment= new ProductSpecificationFragment();
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
