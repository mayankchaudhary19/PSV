package com.example.myapplication.ui.products;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Pojos.Categories;
import com.example.myapplication.Models.CategoryModel;
import com.example.myapplication.Pojos.Products;
import com.example.myapplication.R;
import com.example.myapplication.OtherAdapters.AllProductsAdapter;
import com.example.myapplication.adapters.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {

    Context context;


    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    RecyclerView recyclerView, recyclerView1;
    ArrayList<Products> list;
    ArrayList<Categories> list1;

    int img[] = {R.drawable.unknown5, R.drawable.unknown, R.drawable.unknown3, R.drawable.sampleabcd, R.drawable.unknown1, R.drawable.unknown2, R.drawable.unknown4};
    String[] title = {"Lock-box", "Container", "Tub", "Glass", "Kids' Toys", "Fan Blades", "Navratri Special"};
    String[] subtitle = {"Medium, 250g", "Large,145g", "Medium, 250g", "Large,145g", "Medium, 250g", "Large,145g", "Medium, 250g"};
    String[] price = {"₹150/pc", "₹110/pc", "₹150/pc", "₹110/pc", "₹150/pc", "₹110/pc", "₹150/pc"};
    String[] initialPrice = {"₹200", "₹160", "₹200", "₹160", "₹200", "₹160", "₹200"};

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product, container, false);

        categoryRecyclerView = root.findViewById(R.id.categoryProducts_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);
        List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("link", "Baskets"));
        categoryModelList.add(new CategoryModel("link", "Kids' Toys"));
        categoryModelList.add(new CategoryModel("link", "Soup Bowls"));
        categoryModelList.add(new CategoryModel("link", "Mugs"));
        categoryModelList.add(new CategoryModel("link", "Tub"));
        categoryModelList.add(new CategoryModel("link", "Soap Dishes"));
        categoryModelList.add(new CategoryModel("link", "Navratri Special"));
        categoryModelList.add(new CategoryModel("link", "Dustbins & Dust-Pan"));
        categoryModelList.add(new CategoryModel("link", "Multipurpose Boxes"));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();


        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }







////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView4);
        recyclerView.setNestedScrollingEnabled(false);
        list = new ArrayList<>();
        addAllProducts();


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

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}


