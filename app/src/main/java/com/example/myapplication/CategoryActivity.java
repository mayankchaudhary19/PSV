package com.example.myapplication;

import android.content.res.Resources;
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
import com.example.myapplication.adapters.HomePageAdapter;

import java.util.ArrayList;
import java.util.List;

import io.opencensus.resource.Resource;

public class CategoryActivity extends AppCompatActivity {
    private TextView categoryTitle;
    private RecyclerView categoryRecyclerView;
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
        upArrow.setColorFilter(getResources().getColor(R.color.black_overlay), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        String title = getIntent().getStringExtra("CategoryName");
        categoryTitle.setText(title);
//        getSupportActionBar().setTitle(title);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        categoryRecyclerView =findViewById(R.id.categoryPage_recyclerView);
        //////////////// Banner Slider

        List<SliderModel> sliderModelList=new ArrayList<SliderModel>( );

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#ffffff" ));
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_camera ,"#ffffff" ));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#ffffff"));
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_camera,"#F3BABA"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#ffffff"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher_round,"#ffffff" ));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#ffffff"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher ,"#ffffff"));

        //////////////// Banner Slider


        //////////////// Strip Ad
        // nothing required
        //////////////// Strip Ad

        //////////////// HorizontalProductLayout
        List<HorizontalProductScrollModel> horizontalProductScrollModelList=new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.sampleproductone,"Jar","200 ml, small size jar","₹5000","₹7600"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_like,"Jar","200 ml, small size jar","₹5000","₹7600"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_like,"Jar","200 ml, small size jar","₹5000","₹7600"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_like,"Jar","200 ml, small size jar","₹5000","₹7600"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_like,"Jar","200 ml, small size jar","₹5000","₹7600"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_like,"Jar","200 ml, small size jar","₹5000","₹7600"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_like,"Jar","200 ml, small size jar","₹5000","₹7600"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_like,"Jar","200 ml, small size jar","₹5000","₹7600"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.ic_like,"Jar","200 ml, small size jar","₹5000","₹7600"));
        //////////////// HorizontalProductLayout

        //////////////// GridProductLayout

        List<GridProductModel> gridProductModelList=new ArrayList<>();
        gridProductModelList.add(new GridProductModel(R.drawable.unknown1,"rsrstrsrstrst","₹5000/-"));
        gridProductModelList.add(new GridProductModel(R.drawable.unknown2,"rsrstrsrstrst","₹5000/-"));
        gridProductModelList.add(new GridProductModel(R.drawable.unknown3,"rsrstrsrstrst","₹5000/-"));
        gridProductModelList.add(new GridProductModel(R.drawable.unknown4,"rsrstrsrstrst","₹5000/-"));

        //////////////// GridProductLayout

        ///////////////////////////////// TESTING MULTIPLE LAYOUT RECYCLER VIEW W/O FIREBASE

        LinearLayoutManager testingLayoutManager=new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(RecyclerView.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);
        List<HomePageModel> homePageModelList =new ArrayList<>();
        homePageModelList.add(new HomePageModel(0 ,sliderModelList));
        homePageModelList.add(new HomePageModel(2 ,"Deals Of The Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3 ,gridProductModelList,"#TRENDING"));
        homePageModelList.add(new HomePageModel(1 ,R.drawable.ic_menu_camera,"#ff0000"));


        homePageModelList.add(new HomePageModel(0 ,sliderModelList));
        homePageModelList.add(new HomePageModel(1 ,R.drawable.ic_like,"#000000"));
        homePageModelList.add(new HomePageModel(0 ,sliderModelList));
        homePageModelList.add(new HomePageModel(1 ,R.drawable.ic_menu_camera,"#ff0000"));
        homePageModelList.add(new HomePageModel(1 ,R.drawable.ic_menu_camera,"#ff0000"));

        HomePageAdapter adapter=new HomePageAdapter(homePageModelList);
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ////////////////////////////////


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_search) {
            return true;
        }
        if (id == R.id.main_wishlist) {
            return true;
        }
        if (id == R.id.main_shopping_cart) {
            return true;
        }
        else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}