package com.example.myapplication.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.GridProductModel;
import com.example.myapplication.Models.HomePageModel;
import com.example.myapplication.Models.HorizontalProductScrollModel;
import com.example.myapplication.Models.SliderModel;
import com.example.myapplication.Pojos.Categories;
import com.example.myapplication.Models.CategoryModel;
import com.example.myapplication.Pojos.Products;
import com.example.myapplication.R;
import com.example.myapplication.OtherAdapters.AllProductsAdapter;
import com.example.myapplication.adapters.CategoryAdapter;
import com.example.myapplication.OtherAdapters.HomeProductsAdapter;
import com.example.myapplication.OtherAdapters.HomePageSliderAdapterUsingGlide;
import com.example.myapplication.OtherAdapters.SubCategoryAdapter;
import com.example.myapplication.adapters.HomePageAdapter;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment(){

    }
    private RecyclerView testing;
    private RecyclerView categoryRecyclerView,subCategoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private SubCategoryAdapter subCategoryAdapter;


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    SliderView sliderView;
    Context context;
    ArrayList<Categories> list2;
    ArrayList<Products> list1, list3;


    RecyclerView recyclerView1,recyclerView2,recyclerView3;


    int img2[]= {R.drawable.ic_tupperware,R.drawable.ic_box_svgrepo_com,R.drawable.ic_vegetables,R.drawable.ic_plate,R.drawable.ic_macaroni,R.drawable.ic_water,R.drawable.ic_jug,R.drawable.ic_soup,R.drawable.ic_bucket_svgrepo_com,R.drawable.ic_mug_coffee_svgrepo_com,R.drawable.ic_laundry,R.drawable.ic_sponge,R.drawable.ic_dustpan};
    String t2[]= {"LockBoxes","Containers","Baskets","Plates","Bowls","Glasses","Jugs","Soup Bowls","Buckets","Bath Mugs","Tub","Soap Dishes","Dustbins & Dust-Pan"};


    int img1[]={R.drawable.unknown5,R.drawable.unknown,R.drawable.unknown3,R.drawable.sampleabcd,R.drawable.unknown1,R.drawable.unknown2,R.drawable.unknown4};
    String[] title1={"Lock-box","Container","Tub","Glass","Kids' Toys","Fan Blades","Navratri Special"};
    String[] subtitle1={"Medium, 250g","Large,145g","Medium, 250g","Large,145g","Medium, 250g","Large,145g","Medium, 250g"};
    String[] price1={"₹150/pc","₹110/pc","₹150/pc","₹110/pc","₹150/pc","₹110/pc","₹150/pc"};
    String[] initialPrice1={"₹200","₹160","₹200","₹160","₹200","₹160","₹200"};

    int img3[]={R.drawable.unknown5,R.drawable.unknown,R.drawable.unknown3,R.drawable.sampleabcd,R.drawable.unknown1,R.drawable.unknown2,R.drawable.unknown4};
    String[] title3={"Lock-box","Container","Tub","Glass","Kids' Toys","Fan Blades","Navratri Special"};
    String[] subtitle3={"Medium, 250g","Large,145g","Medium, 250g","Large,145g","Medium, 250g","Large,145g","Medium, 250g"};
    String[] price3={"₹150/pc","₹110/pc","₹150/pc","₹110/pc","₹150/pc","₹110/pc","₹150/pc"};
    String[] initialPrice3={"₹200","₹160","₹200","₹160","₹200","₹160","₹200"};
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        categoryRecyclerView = root.findViewById(R.id.categoryhome_recyclerView);
        CategoryHomeLayoutManager();


        subCategoryRecyclerView = root.findViewById(R.id.subcategoryHome_recyclerView);
        SubCategoryHomeLayoutMananger();




        //////////////// Banner Slider

        List<SliderModel>sliderModelList=new ArrayList<SliderModel>( );

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
        List<HorizontalProductScrollModel > horizontalProductScrollModelList=new ArrayList<>();
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

        testing = root.findViewById(R.id.home_page_recycler_view);
        LinearLayoutManager testingLayoutManager=new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(RecyclerView.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);
        List<HomePageModel> homePageModelList =new ArrayList<>();
        homePageModelList.add(new HomePageModel(2 ,"Deals Of The Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3 ,gridProductModelList,"#TRENDING"));
        homePageModelList.add(new HomePageModel(0 ,sliderModelList));
        homePageModelList.add(new HomePageModel(1 ,R.drawable.ic_menu_camera,"#ff0000"));


        homePageModelList.add(new HomePageModel(0 ,sliderModelList));
        homePageModelList.add(new HomePageModel(1 ,R.drawable.ic_like,"#000000"));
        homePageModelList.add(new HomePageModel(0 ,sliderModelList));
        homePageModelList.add(new HomePageModel(1 ,R.drawable.ic_menu_camera,"#ff0000"));
        homePageModelList.add(new HomePageModel(1 ,R.drawable.ic_menu_camera,"#ff0000"));

        HomePageAdapter adapter=new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ////////////////////////////////




        return root;
    }
//
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void CategoryHomeLayoutManager(){
        List<CategoryModel> categoryModelList=new ArrayList<CategoryModel>();

        categoryModelList.add(new CategoryModel("link","Household"));
        categoryModelList.add(new CategoryModel("link","Hotelware"));
        categoryModelList.add(new CategoryModel("link","Kids' Toys"));
        categoryModelList.add(new CategoryModel("link","Fan Blades"));
        categoryModelList.add(new CategoryModel("link","Navratri Special"));
        categoryModelList.add(new CategoryModel("link","Multipurpose Boxes"));

        LinearLayoutManager layoutManager =new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager) ;
        categoryAdapter=new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();
    }

    public void SubCategoryHomeLayoutMananger(){
        LinearLayoutManager layoutManager =new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        subCategoryRecyclerView.setLayoutManager(layoutManager) ;
        List<CategoryModel> subCategoryModelList=new ArrayList<CategoryModel>();
        subCategoryModelList.add(new CategoryModel("link","Household"));
        subCategoryModelList.add(new CategoryModel("link","Hotelware"));
        subCategoryModelList.add(new CategoryModel("link","Kids' Toys"));
        subCategoryModelList.add(new CategoryModel("link","Fan Blades"));
        subCategoryModelList.add(new CategoryModel("link","Navratri Special"));
        subCategoryModelList.add(new CategoryModel("link","Multipurpose Boxes"));

        subCategoryAdapter=new SubCategoryAdapter(subCategoryModelList);
        subCategoryRecyclerView.setAdapter(subCategoryAdapter);
        subCategoryAdapter.notifyDataSetChanged();
    }







///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

//for sliderView
        sliderView = view.findViewById(R.id.imageSlider);

        final HomePageSliderAdapterUsingGlide adapter = new HomePageSliderAdapterUsingGlide(context);
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

//        recyclerView2 = view.findViewById(R.id.subcategoryHome_recyclerView);
//        list2 = new ArrayList<>();
//        addSubCategories();

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



//    public void addSubCategories() {
//        recyclerView2.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        );
//        for (int i = 0; i < img2.length; i++) {
//            Categories itemModel = new Categories();
//            itemModel.setImg(img2[i]);
//            itemModel.setTitle(t2[i]);
//            list2.add(itemModel);
//        }
//
//        SubCategoryAdapter adapter = new SubCategoryAdapter(context, list2);
//        recyclerView2.setAdapter(adapter);
//    }

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

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}