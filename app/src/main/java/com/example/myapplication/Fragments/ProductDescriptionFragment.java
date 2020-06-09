package com.example.myapplication.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class  ProductDescriptionFragment extends Fragment {

    public ProductDescriptionFragment() {
        // Required empty public constructor
    }

    private TextView descriptionBody;
    public static String productDescriptionData;
    public static String productCareDescriptionData;
    private TextView descriptionProductCare;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_product_description, container, false);

        descriptionBody=view.findViewById(R.id.tv_product_desc);
        descriptionProductCare=view.findViewById(R.id.tv_product_material_care);

        descriptionBody.setText(productDescriptionData);
        descriptionProductCare.setText(productCareDescriptionData);

       return view;
    }
}
