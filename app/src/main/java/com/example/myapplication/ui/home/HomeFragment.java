package com.example.myapplication.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Models.GridProductModel;
import com.example.myapplication.Models.HomePageModel;
import com.example.myapplication.Models.HorizontalProductScrollModel;
import com.example.myapplication.Models.SliderModel;
import com.example.myapplication.Models.ViewAllWithRatingModel;
import com.example.myapplication.Pojos.Categories;
import com.example.myapplication.Models.CategoryModel;
import com.example.myapplication.Pojos.Products;
import com.example.myapplication.R;
import com.example.myapplication.OtherAdapters.AllProductsAdapter;
import com.example.myapplication.Adapters.CategoryAdapter;
import com.example.myapplication.OtherAdapters.HomeProductsAdapter;
import com.example.myapplication.OtherAdapters.HomePageSliderAdapterUsingGlide;
import com.example.myapplication.OtherAdapters.SubCategoryAdapter;
import com.example.myapplication.Adapters.HomePageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.DBqueries.categoryModelList;
import static com.example.myapplication.DBqueries.lists;
import static com.example.myapplication.DBqueries.loadCategories;
import static com.example.myapplication.DBqueries.loadCategoriesNames;
import static com.example.myapplication.DBqueries.loadFragmentRecyclerData;

public class HomeFragment extends Fragment {

    public HomeFragment(){

    }
    private  ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private Button retryBtn;
    public static SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView homePageRecyclerView;
    private List<HomePageModel> homePageModelFakeList=new ArrayList<>();
    private HomePageAdapter adapter;
    private RecyclerView categoryRecyclerView,subCategoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private SubCategoryAdapter subCategoryAdapter;

    private List<CategoryModel> categoryModelFakeList=new ArrayList<>();
//    private FirebaseFirestore firebaseFirestore;

    private ImageView noInternetConnection;
    private TextView tv;
    private View layout;


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



    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);


        swipeRefreshLayout=root.findViewById(R.id.refresh_layout);
        categoryRecyclerView = root.findViewById(R.id.categoryhome_recyclerView);
        homePageRecyclerView = root.findViewById(R.id.home_page_recycler_view);
        retryBtn=root.findViewById(R.id.retryBtn);
        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.black_overlay3),getContext().getResources().getColor(R.color.black_overlay3),getContext().getResources().getColor(R.color.black_overlay3));

        noInternetConnection=root.findViewById(R.id.noInternetConnection);

        LinearLayoutManager layoutManager =new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager) ;
//            categoryModelList=new ArrayList<CategoryModel>();
        LinearLayoutManager testingLayoutManager=new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(RecyclerView.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);

////////categories Fake List
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("",""));
        categoryModelFakeList.add(new CategoryModel("",""));
        categoryModelFakeList.add(new CategoryModel("",""));
        categoryModelFakeList.add(new CategoryModel("",""));
        categoryModelFakeList.add(new CategoryModel("",""));
////////categories Fake List

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

        homePageModelFakeList.add(new HomePageModel(2 ,"","#ffffff",horizontalProductScrollModelFakeList,new ArrayList<ViewAllWithRatingModel>()));
        homePageModelFakeList.add(new HomePageModel(0 ,sliderModelFakeList));
        homePageModelFakeList.add(new HomePageModel(1 ,"","#ffffff"));
        homePageModelFakeList.add(new HomePageModel(3 ,"",gridProductModelFakeList));


//        homePageModelFakeList.add(new HomePageModel(0 ,sliderModelList));
//        homePageModelFakeList.add(new HomePageModel(1 ,R.drawable.ic_like,"#000000"));
//        homePageModelFakeList.add(new HomePageModel(0 ,sliderModelList));
//        homePageModelFakeList.add(new HomePageModel(1 ,R.drawable.ic_menu_camera,"#ff0000"));
//        homePageModelFakeList.add(new HomePageModel(1 ,R.drawable.ic_menu_camera,"#ff0000"));


        categoryAdapter=new CategoryAdapter(categoryModelFakeList);

        adapter =new HomePageAdapter(homePageModelFakeList);

        connectivityManager= (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo=connectivityManager.getActiveNetworkInfo();
        if (networkInfo!=null&& networkInfo.isConnected()==true){
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

            if (categoryModelList.size()==0){
                loadCategories(categoryRecyclerView,context);
            }
            else {
                categoryAdapter=new CategoryAdapter(categoryModelList);
                categoryAdapter.notifyDataSetChanged();
            }
            categoryRecyclerView.setAdapter(categoryAdapter);

//            firebaseFirestore=FirebaseFirestore.getInstance();
//            firebaseFirestore.collection("Categories").orderBy("index").get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()){
//                                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
//                                    categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(),documentSnapshot.get("categoryName").toString()));
//                                }
//                                categoryAdapter.notifyDataSetChanged();
//
//
//                            }else{
//                                String error= task.getException().getMessage();
//                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });


            subCategoryRecyclerView = root.findViewById(R.id.subcategoryHome_recyclerView);
            SubCategoryHomeLayoutMananger();


//////////
            Window window = ((MainActivity) getActivity()).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#ffffff"));
            ((MainActivity) getActivity()).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            ((MainActivity) getActivity()).getSupportActionBar().show();
//////////


            //////////////// Banner Slider

//        List<SliderModel>sliderModelList=new ArrayList<SliderModel>( );

//
//        sliderModelList.add(new SliderModel(R.drawable.unknown,"#ffffff" ));
//        sliderModelList.add(new SliderModel(R.drawable.unknown1 ,"#ffffff" ));
//        sliderModelList.add(new SliderModel(R.drawable.unknown2,"#ffffff"));
//        sliderModelList.add(new SliderModel(R.drawable.unknown3,"#F3BABA"));
//        sliderModelList.add(new SliderModel(R.drawable.unknown4,"#ffffff"));
//        sliderModelList.add(new SliderModel(R.drawable.unknown5,"#ffffff" ));
            //////////////// Banner Slider


            //////////////// Strip Ad
            // nothing required
            //////////////// Strip Ad

            //////////////// HorizontalProductLayout
//        List<HorizontalProductScrollModel > horizontalProductScrollModelList=new ArrayList<>();
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.unknown1,"Jar","200 ml, small size jar","₹5000","₹7600"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.unknown2,"Jar","200 ml, small size jar","₹5000","₹7600"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.unknown3,"Jar","200 ml, small size jar","₹5000","₹7600"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.unknown4,"Jar","200 ml, small size jar","₹5000","₹7600"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.unknown5,"Jar","200 ml, small size jar","₹5000","₹7600"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.unknown1,"Jar","200 ml, small size jar","₹5000","₹7600"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.unknown,"Jar","200 ml, small size jar","₹5000","₹7600"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.unknown3,"Jar","200 ml, small size jar","₹5000","₹7600"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.unknown3,"Jar","200 ml, small size jar","₹5000","₹7600"));
            //////////////// HorizontalProductLayout

            //////////////// GridProductLayout

//        List<GridProductModel> gridProductModelList=new ArrayList<>();
//        gridProductModelList.add(new GridProductModel(R.drawable.unknown1,"Pink Jug","2 New Colors Available","₹5000/-"));
//        gridProductModelList.add(new GridProductModel(R.drawable.unknown2,"Yellow Mug","20% Discount","₹5000/-"));
//        gridProductModelList.add(new GridProductModel(R.drawable.unknown3,"Square Container","2 New Colors Available","₹5000/-"));
//        gridProductModelList.add(new GridProductModel(R.drawable.unknown4,"Round Container","New Designs Available","₹5000/-"));

            //////////////// GridProductLayout

            ///////////////////////////////// TESTING MULTIPLE LAYOUT RECYCLER VIEW W/O FIREBASE




            if (lists.size()==0){
                loadCategoriesNames.add("Home");
                lists.add(new ArrayList<HomePageModel>());
                loadFragmentRecyclerData(homePageRecyclerView,context,0,"Home");
            }
//            adapter=new HomePageAdapter(homePageModelList);
            else {
                adapter =new HomePageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(adapter);

//        homePageModelList.add(new HomePageModel(2 ,"Deals Of The Day",horizontalProductScrollModelList));
//        homePageModelList.add(new HomePageModel(0 ,sliderModelList));
//        homePageModelList.add(new HomePageModel(3 ,gridProductModelList,"#TRENDING"));
//        homePageModelList.add(new HomePageModel(2 ,"New Products",horizontalProductScrollModelList));

//        homePageModelList.add(new HomePageModel(0 ,sliderModelList));
//        homePageModelList.add(new HomePageModel(1 ,R.drawable.ic_like,"#000000"));
//        homePageModelList.add(new HomePageModel(1 ,R.drawable.ic_menu_camera,"#ff0000"));
//
////        homePageModelList.add(new HomePageModel(0 ,sliderModelList));
//        homePageModelList.add(new HomePageModel(1 ,R.drawable.ic_menu_camera,"#ff0000"));
//        homePageModelList.add(new HomePageModel(1 ,R.drawable.ic_menu_camera,"#ff0000"));
//        homePageModelList.add(new HomePageModel(3 ,gridProductModelList,"#TRENDING"));
////        homePageModelList.add(new HomePageModel(0 ,sliderModelList));
//        homePageModelList.add(new HomePageModel(3 ,gridProductModelList,"#TRENDING"));
//        homePageModelList.add(new HomePageModel(0 ,sliderModelList));


//            firebaseFirestore.collection("Categories").document("Fan Blades")
//                    .collection("TopDeals").orderBy("index").get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()){
//                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
//                                    if ((long)documentSnapshot.get("viewType")==0){
//                                        List<SliderModel> sliderModelList=new ArrayList<>();
//                                        long no_of_banners=(long)documentSnapshot.get("noOfBanners");
//                                        for (long x=1;x<no_of_banners+1;x++){
//                                            sliderModelList.add(new SliderModel(documentSnapshot.get("banner"+x).toString(),documentSnapshot.get("banner"+x+"Background").toString()));
//                                        }
//                                        homePageModelList.add(new HomePageModel(0,sliderModelList));
//
//                                    }else if ((long)documentSnapshot.get("viewType")==1){
//                                        homePageModelList.add(new HomePageModel(1,documentSnapshot.get("stripAdBanner").toString(),documentSnapshot.get("backgroundColor").toString()));
//
//                                    }else if ((long)documentSnapshot.get("viewType")==2){
//                                        List<HorizontalProductScrollModel > horizontalProductScrollModelList=new ArrayList<>();
//                                        long no_of_products=(long)documentSnapshot.get("noOfProducts");
//                                        for (long x=1;x<no_of_products+1;x++){
//                                            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("productId"+x).toString(),
//                                                    documentSnapshot.get("productImage"+x).toString(),documentSnapshot.get("productTitle"+x).toString(),
//                                                    documentSnapshot.get("productSubtitle"+x).toString(),documentSnapshot.get("productPrice"+x).toString(),
//                                                    documentSnapshot.get("productInitialPrice"+x).toString()));
//                                        }
//                                        homePageModelList.add(new HomePageModel(2,documentSnapshot.get("layoutTitle").toString(),documentSnapshot.get("layoutBackground").toString(),horizontalProductScrollModelList));
//
//                                    }else if ((long)documentSnapshot.get("viewType")==3){
//                                        List<GridProductModel> gridLayoutModelList=new ArrayList<>();
//                                        long no_of_products=(long)documentSnapshot.get("noOfProducts");
//                                        for (long x=1;x<no_of_products+1;x++){
//                                            gridLayoutModelList.add(new GridProductModel(documentSnapshot.get("productId"+x).toString(),
//                                                    documentSnapshot.get("productImage"+x).toString(),documentSnapshot.get("productTitle"+x).toString(),
//                                                    documentSnapshot.get("productSubtitle"+x).toString(),documentSnapshot.get("productPrice"+x).toString()));
//                                        }
//                                        homePageModelList.add(new HomePageModel(3,documentSnapshot.get("layoutTitle").toString(),gridLayoutModelList));
//
//
//                                    }
//                                }
//                                adapter.notifyDataSetChanged();
//
//                            }else{
//                                String error =task.getException().getMessage().toString();
//                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });

                    //////////48
        }else{
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);

            Glide.with(this).load(R.drawable.no_internet).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);


        }

        //////refresh

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                reloadPage();
            }
        });
        //////refresh

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                swipeRefreshLayout.setRefreshing(true);
                reloadPage();
            }
        });


        LayoutInflater inflater2 = getLayoutInflater();
        layout = inflater2.inflate(R.layout.custom_toast2, (ViewGroup) root.findViewById(R.id.custom_toast_container));
        tv = (TextView) layout.findViewById(R.id.text);

        return root;
    }
//


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    private void reloadPage() {
        networkInfo = connectivityManager.getActiveNetworkInfo();
        categoryModelList.clear();
        lists.clear();
        loadCategoriesNames.clear();

        if (networkInfo != null && networkInfo.isConnected() == true) {

            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);

            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            categoryAdapter = new CategoryAdapter(categoryModelFakeList);
            adapter = new HomePageAdapter(homePageModelFakeList);
            categoryRecyclerView.setAdapter(categoryAdapter);
            homePageRecyclerView.setAdapter(adapter);

            loadCategories(categoryRecyclerView, context);
            loadCategoriesNames.add("Home");
            lists.add(new ArrayList<HomePageModel>());
            loadFragmentRecyclerData(homePageRecyclerView, context, 0, "Home");
//                    swipeRefreshLayout.setRefreshing(false);

        } else {

            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);

            Glide.with(getContext()).load(R.drawable.no_internet).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);


            tv.setPadding(0,2,0,5);
            tv.setText("Internet Connection Unavailable");
            Toast toast = new Toast(context);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 670);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();


        }
    }


//    public void CategoryHomeLayoutManager(){
//
////
////        categoryModelList.add(new CategoryModel("link","Household"));
////        categoryModelList.add(new CategoryModel("link","Hotelware"));
////        categoryModelList.add(new CategoryModel("link","Kids' Toys"));
////        categoryModelList.add(new CategoryModel("link","Fan Blades"));
////        categoryModelList.add(new CategoryModel("link","Navratri Special"));
////        categoryModelList.add(new CategoryModel("link","Multipurpose Boxes"));
//
//
//    }

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