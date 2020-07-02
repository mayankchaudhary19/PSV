package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.GridProductModel;
import com.example.myapplication.Models.HomePageModel;
import com.example.myapplication.Models.HorizontalProductScrollModel;
import com.example.myapplication.Models.SliderModel;
import com.example.myapplication.Adapters.HomePageAdapter;
import com.example.myapplication.Models.ViewAllWithRatingModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.DBqueries.lists;
import static com.example.myapplication.DBqueries.loadCategoriesNames;
import static com.example.myapplication.DBqueries.loadFragmentRecyclerData;

public class CategoryActivity extends AppCompatActivity {
    private TextView categoryTitle;
    private RecyclerView categoryRecyclerView;
    private HomePageAdapter adapter;
    private List<HomePageModel> homePageModelFakeList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        categoryTitle= findViewById(R.id.category_activity_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_black_24dp);
        upArrow.setColorFilter(getResources().getColor(R.color.black_overlay2), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        String title = getIntent().getStringExtra("CategoryName");
        categoryTitle.setText(title);
        categoryTitle.setTextColor(Color.parseColor("#99090909"));
//        getSupportActionBar().setTitle(title);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        categoryRecyclerView =findViewById(R.id.categoryPage_recyclerView);
        //////////////// Banner Slider

//        List<SliderModel> sliderModelList=new ArrayList<SliderModel>( );

//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#ffffff" ));
//        sliderModelList.add(new SliderModel(R.drawable.ic_menu_camera ,"#ffffff" ));
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#ffffff"));
//        sliderModelList.add(new SliderModel(R.drawable.ic_menu_camera,"#F3BABA"));
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#ffffff"));
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#ffffff" ));
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#ffffff"));
//        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher ,"#ffffff"));

        //////////////// Banner Slider


        //////////////// Strip Ad
        // nothing required
        //////////////// Strip Ad

        //////////////// HorizontalProductLayout
//        List<HorizontalProductScrollModel> horizontalProductScrollModelList=new ArrayList<>();
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.sampleproductone,"Jar","200 ml, small size jar","₹5000","₹7600"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_like,"Jar","200 ml, small size jar","₹5000","₹7600"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_like,"Jar","200 ml, small size jar","₹5000","₹7600"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_like,"Jar","200 ml, small size jar","₹5000","₹7600"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_like,"Jar","200 ml, small size jar","₹5000","₹7600"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_like,"Jar","200 ml, small size jar","₹5000","₹7600"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_like,"Jar","200 ml, small size jar","₹5000","₹7600"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_like,"Jar","200 ml, small size jar","₹5000","₹7600"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_like,"Jar","200 ml, small size jar","₹5000","₹7600"));
        //////////////// HorizontalProductLayout

        //////////////// GridProductLayout

//        List<GridProductModel> gridProductModelList=new ArrayList<>();
//        gridProductModelList.add(new GridProductModel(R.drawable.unknown1,"rsrstrsrstrst","2 New Colors Available","₹5000/-"));
//        gridProductModelList.add(new GridProductModel(R.drawable.unknown2,"rsrstrsrstrst","20% Discount","₹5000/-"));
//        gridProductModelList.add(new GridProductModel(R.drawable.unknown3,"rsrstrsrstrst","2 New Colors Available","₹5000/-"));
//        gridProductModelList.add(new GridProductModel(R.drawable.unknown4,"rsrstrsrstrst","New Designs Available","₹5000/-"));

        //////////////// GridProductLayout


////////Home Page Fake List


        List<SliderModel> sliderModelFakeList=new ArrayList<SliderModel>( );

        sliderModelFakeList.add(new SliderModel("null","#ffffff" ));
        sliderModelFakeList.add(new SliderModel("null","#ffffff" ));
        sliderModelFakeList.add(new SliderModel("null","#ffffff" ));
        sliderModelFakeList.add(new SliderModel("null","#ffffff" ));
        sliderModelFakeList.add(new SliderModel("null","#ffffff" ));

        List<HorizontalProductScrollModel> horizontalProductScrollModelFakeList=new ArrayList<>();
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","","",""));

        List<GridProductModel> gridProductModelFakeList=new ArrayList<>();
        gridProductModelFakeList.add(new GridProductModel("", "","","",""));
        gridProductModelFakeList.add(new GridProductModel("", "","","",""));
        gridProductModelFakeList.add(new GridProductModel("", "","","",""));
        gridProductModelFakeList.add(new GridProductModel("", "","","",""));
        homePageModelFakeList.add(new HomePageModel(3 ,"",gridProductModelFakeList));

        homePageModelFakeList.add(new HomePageModel(0 ,sliderModelFakeList));
        homePageModelFakeList.add(new HomePageModel(1 ,"","#ffffff"));
        homePageModelFakeList.add(new HomePageModel(2 ,"","#ffffff",horizontalProductScrollModelFakeList,new ArrayList<ViewAllWithRatingModel>()));

////////Home Page Fake List


        ///////////////////////////////// TESTING MULTIPLE LAYOUT RECYCLER VIEW W/O FIREBASE

        LinearLayoutManager testingLayoutManager=new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(RecyclerView.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        adapter =new HomePageAdapter(homePageModelFakeList);
//        List<HomePageModel> homePageModelList =new ArrayList<>();

//        homePageModelList.add(new HomePageModel(0 ,sliderModelList));
//        homePageModelList.add(new HomePageModel(2 ,"Deals Of The Day",horizontalProductScrollModelList));
//        homePageModelList.add(new HomePageModel(3 ,gridProductModelList,"#TRENDING"));
//        homePageModelList.add(new HomePageModel(1 ,R.drawable.ic_menu_camera,"#ff0000"));
//
//
//        homePageModelList.add(new HomePageModel(0 ,sliderModelList));
//        homePageModelList.add(new HomePageModel(1 ,R.drawable.ic_like,"#000000"));
//        homePageModelList.add(new HomePageModel(0 ,sliderModelList));
//        homePageModelList.add(new HomePageModel(1 ,R.drawable.ic_menu_camera,"#ff0000"));
//        homePageModelList.add(new HomePageModel(1 ,R.drawable.ic_menu_camera,"#ff0000"));
        int listPosition=0;

        for (int x=0;x<loadCategoriesNames.size();x++){
            if (loadCategoriesNames.get(x).equals(title)){
                listPosition=x;
            }
        }
        if (listPosition==0){
            loadCategoriesNames.add(title);
            lists.add(new ArrayList<HomePageModel>());
         //   adapter =new HomePageAdapter(lists.get(loadCategoriesNames.size() - 1));
            loadFragmentRecyclerData(categoryRecyclerView, this,loadCategoriesNames.size() - 1,title);

        }else {
            adapter =new HomePageAdapter(lists.get(listPosition));
        }
//        HomePageAdapter adapter=new HomePageAdapter(lists);
        categoryRecyclerView.setAdapter(adapter);

       // categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ////////////////////////////////


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.without_notification_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_search) {
            return true;
        }
        if (id == R.id.main_wishlist) {
            Intent wishlistIntent=new Intent(CategoryActivity.this,WishlistActivity.class);
            startActivity(wishlistIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }
        if (id == R.id.main_shopping_cart) {
            Intent cartIntent=new Intent(CategoryActivity.this,MyCartActivity.class);
            startActivity(cartIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;

        }
        else if (id == android.R.id.home) {
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}