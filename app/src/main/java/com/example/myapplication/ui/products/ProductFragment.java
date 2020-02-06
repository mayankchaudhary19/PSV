package com.example.myapplication.ui.products;

import android.content.Context;
import android.graphics.Canvas;
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
import com.example.myapplication.adapters.SomeCategoryAdapter;
import com.example.myapplication.adapters.SubCategoryAdapter;

import java.util.ArrayList;

public class ProductFragment extends Fragment {

    Context context;
    RecyclerView recyclerView,recyclerView1;
    ArrayList<Products> list;
    ArrayList<Categories>list1;

    int img[]={R.drawable.unknown5,R.drawable.unknown,R.drawable.unknown3,R.drawable.abcd,R.drawable.unknown1,R.drawable.unknown2,R.drawable.unknown4};
    String[] title={"Lock-box","Container","Tub","Glass","Kids' Toys","Fan Blades","Navratri Special"};
    String[] subtitle={"Medium, 250g","Large,145g","Medium, 250g","Large,145g","Medium, 250g","Large,145g","Medium, 250g"};
    String[] price={"₹150/pc","₹110/pc","₹150/pc","₹110/pc","₹150/pc","₹110/pc","₹150/pc"};
    String[] initialPrice={"₹200","₹160","₹200","₹160","₹200","₹160","₹200"};


    int img1[]= {R.drawable.ic_tupperware,R.drawable.ic_box_svgrepo_com,R.drawable.ic_vegetables,R.drawable.ic_plate,R.drawable.ic_macaroni,R.drawable.ic_water,R.drawable.ic_jug,R.drawable.ic_soup,R.drawable.ic_bucket_svgrepo_com,R.drawable.ic_mug_coffee_svgrepo_com,R.drawable.ic_laundry,R.drawable.ic_sponge,R.drawable.ic_dustpan};
    String t1[]= {"LockBoxes","Containers","Baskets","Plates","Bowls","Glasses","Jugs","Soup Bowls","Buckets","Mugs","Tub","Soap Dishes","Dustbins & Dust-Pan"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product, container, false);
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView  = view.findViewById(R.id.recyclerView4);
        recyclerView.setNestedScrollingEnabled(false);
        list = new ArrayList<>();
        addAllProducts();

        recyclerView1=view.findViewById(R.id.recyclerView5);
        list1 =new ArrayList<>();
        addSomeCategories();

    }

    public void addAllProducts() {

//        RecyclerView.LayoutManager manager = new GridLayoutManager(context, 2);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL){
//            @Override
//            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//                // Do not draw the divider
//            }
//        });

        recyclerView.setLayoutManager( new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        );

        for (int i = 0; i < img.length; i++) {
            Products itemModel = new Products();
            itemModel.setImg(img[i]);
            itemModel.setTitle(title[i]);
            itemModel.setSubtitle(subtitle[i]);
            itemModel.setPrice(price[i]);
            itemModel.setInitialPrice(initialPrice[i]);

            list.add(itemModel);
        }

        AllProductsAdapter adapter = new AllProductsAdapter(context, list);
        recyclerView.setAdapter(adapter);

    }


    public void addSomeCategories() {
        recyclerView1.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        );
        for (int i = 0; i < img1.length; i++) {
            Categories itemModel = new Categories();
            itemModel.setImg(img1[i]);
            itemModel.setTitle(t1[i]);
            list1.add(itemModel);
        }

        SomeCategoryAdapter adapter = new SomeCategoryAdapter(context, list1);
        recyclerView1.setAdapter(adapter);
    }
}