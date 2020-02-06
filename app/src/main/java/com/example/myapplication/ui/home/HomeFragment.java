package com.example.myapplication.ui.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Pojos.Categories;
import com.example.myapplication.Pojos.Products;
import com.example.myapplication.R;
import com.example.myapplication.adapters.AllProductsAdapter;
import com.example.myapplication.adapters.CategoryAdapter;
import com.example.myapplication.adapters.HomeProductsAdapter;
import com.example.myapplication.adapters.SliderAdapter;
import com.example.myapplication.adapters.SubCategoryAdapter;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

//    private HomeViewModel homeViewModel;
    SliderView sliderView;
    Context context;
    ArrayList<Categories> list,list2;
    ArrayList<Products> list1, list3;


    RecyclerView recyclerView,recyclerView1,recyclerView2,recyclerView3;

    int img[] = {R.drawable.ic_home_3,R.drawable.ic_hotel,R.drawable.ic_puzzle,R.drawable.ic_fan,R.drawable.ic_hindu,R.drawable.ic_stock};
    String t[]= {"Household","Hotelware","Kids' Toys","Fan Blades","Navratri Special","Multipurpose Boxes"};
    int img2[]= {R.drawable.ic_tupperware,R.drawable.ic_box_svgrepo_com,R.drawable.ic_vegetables,R.drawable.ic_plate,R.drawable.ic_macaroni,R.drawable.ic_water,R.drawable.ic_jug,R.drawable.ic_soup,R.drawable.ic_bucket_svgrepo_com,R.drawable.ic_mug_coffee_svgrepo_com,R.drawable.ic_laundry,R.drawable.ic_sponge,R.drawable.ic_dustpan};
    String t2[]= {"LockBoxes","Containers","Baskets","Plates","Bowls","Glasses","Jugs","Soup Bowls","Buckets","Bath Mugs","Tub","Soap Dishes","Dustbins & Dust-Pan"};


    int img1[]={R.drawable.unknown5,R.drawable.unknown,R.drawable.unknown3,R.drawable.abcd,R.drawable.unknown1,R.drawable.unknown2,R.drawable.unknown4};
    String[] title1={"Lock-box","Container","Tub","Glass","Kids' Toys","Fan Blades","Navratri Special"};
    String[] subtitle1={"Medium, 250g","Large,145g","Medium, 250g","Large,145g","Medium, 250g","Large,145g","Medium, 250g"};
    String[] price1={"₹150/pc","₹110/pc","₹150/pc","₹110/pc","₹150/pc","₹110/pc","₹150/pc"};
    String[] initialPrice1={"₹200","₹160","₹200","₹160","₹200","₹160","₹200"};

    int img3[]={R.drawable.unknown5,R.drawable.unknown,R.drawable.unknown3,R.drawable.abcd,R.drawable.unknown1,R.drawable.unknown2,R.drawable.unknown4};
    String[] title3={"Lock-box","Container","Tub","Glass","Kids' Toys","Fan Blades","Navratri Special"};
    String[] subtitle3={"Medium, 250g","Large,145g","Medium, 250g","Large,145g","Medium, 250g","Large,145g","Medium, 250g"};
    String[] price3={"₹150/pc","₹110/pc","₹150/pc","₹110/pc","₹150/pc","₹110/pc","₹150/pc"};
    String[] initialPrice3={"₹200","₹160","₹200","₹160","₹200","₹160","₹200"};


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }
//
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

//for sliderView
        sliderView = view.findViewById(R.id.imageSlider);

        final SliderAdapter adapter = new SliderAdapter(context);
        adapter.setCount(5);
        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });


//for recycler views
        //for recyclerView and recyclerView2

        recyclerView = view.findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        addCategories();

        recyclerView2 = view.findViewById(R.id.recyclerView2);
        list2 = new ArrayList<>();
        addSubCategories();

        //for recyclerView1 and recyclerView3 and recyclerView4

        recyclerView1 = view.findViewById(R.id.recyclerView1);
        list1 = new ArrayList<>();
        addHomeProducts();

        recyclerView3 = view.findViewById(R.id.recyclerView3);
        list3 = new ArrayList<>();
        addAllProducts();

//        recyclerView4 = view.findViewById(R.id.recyclerView4);
//        list4 = new ArrayList<>();
//        addAllProducts();

    }


    public void addCategories() {
        recyclerView.setLayoutManager( new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        );
        for (int i = 0; i < img.length; i++) {
            Categories itemModel = new Categories();
            itemModel.setImg(img[i]);
            itemModel.setTitle(t[i]);
            list.add(itemModel);
        }

        CategoryAdapter adapter = new CategoryAdapter(context, list);
        recyclerView.setAdapter(adapter);
    }
    public void addSubCategories() {
        recyclerView2.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        );
        for (int i = 0; i < img2.length; i++) {
            Categories itemModel = new Categories();
            itemModel.setImg(img2[i]);
            itemModel.setTitle(t2[i]);
            list2.add(itemModel);
        }

        SubCategoryAdapter adapter = new SubCategoryAdapter(context, list2);
        recyclerView2.setAdapter(adapter);
    }

    public void addHomeProducts() {

        recyclerView1.setLayoutManager( new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        );
//        RecyclerView.LayoutManager manager = new GridLayoutManager(context, 1);
//        recyclerView1.setLayoutManager(manager);
//        recyclerView1.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL){
//        @Override
//        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//            // Do not draw the divider
//        }
//    });
        for (int i = 0; i < img1.length; i++) {
            Products itemModel = new Products();
            itemModel.setImg(img1[i]);
            itemModel.setTitle(title1[i]);
            itemModel.setSubtitle(subtitle1[i]);
            itemModel.setPrice(price1[i]);
            itemModel.setInitialPrice(initialPrice1[i]);

            list1.add(itemModel);
        }

        HomeProductsAdapter adapter = new HomeProductsAdapter(context, list1);
        recyclerView1.setAdapter(adapter);

    }
    public void addAllProducts() {
//
//        RecyclerView.LayoutManager manager = new GridLayoutManager(context, 2);
//        recyclerView3.setLayoutManager(manager);
//        recyclerView3.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL){
//        @Override
//        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//            // Do not draw the divider
//        }
//    });

        recyclerView3.setLayoutManager( new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        );

        for (int i = 0; i < img3.length; i++) {
            Products itemModel = new Products();
            itemModel.setImg(img3[i]);
            itemModel.setTitle(title3[i]);
            itemModel.setSubtitle(subtitle3[i]);
            itemModel.setPrice(price3[i]);
            itemModel.setInitialPrice(initialPrice3[i]);

            list3.add(itemModel);
        }

        AllProductsAdapter adapter = new AllProductsAdapter(context, list3);
        recyclerView3.setAdapter(adapter);

    }

}