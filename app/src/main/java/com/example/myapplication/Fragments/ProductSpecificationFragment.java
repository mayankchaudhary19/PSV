package com.example.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Models.ProductSpecificationModel;
import com.example.myapplication.R;
import com.example.myapplication.adapters.ProductSpecificationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductSpecificationFragment extends Fragment {

    public ProductSpecificationFragment() {
        // Required empty public constructor
    }

    private RecyclerView productSpecificationRecyclerView;
//    private TextView materialAndSizeTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_product_specification, container, false);
       productSpecificationRecyclerView=view.findViewById(R.id.product_specification_recyclerView);
       LinearLayoutManager linearLayoutManager =new LinearLayoutManager(view.getContext());
       linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
       productSpecificationRecyclerView.setLayoutManager(linearLayoutManager);

        List<ProductSpecificationModel> productSpecificationModelList=new ArrayList<>();
        productSpecificationModelList.add(new ProductSpecificationModel(0,"Material and Size"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"Material","Polypropylene"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"Height","20cm"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"Width","13cm"));
        productSpecificationModelList.add(new ProductSpecificationModel(1,"Length","25cm"));
        productSpecificationModelList.add(new ProductSpecificationModel(1," Weight","28gm"));

        ProductSpecificationAdapter productSpecificationAdapter=new ProductSpecificationAdapter(productSpecificationModelList);
        productSpecificationRecyclerView.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();

       return view;

    }
}
