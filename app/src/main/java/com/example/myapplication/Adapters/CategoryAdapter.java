package com.example.myapplication.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.CategoryActivity;
import com.example.myapplication.Models.CategoryModel;
import com.example.myapplication.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;
    private int lastPosition;


    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder  onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_home_category_item,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder viewHolder , int position) {
        String name = categoryModelList.get(position).getCategoryName();
        String icon = categoryModelList.get(position).getCategoryIconLink();
        viewHolder.setCategory(name);
        viewHolder.setCategoryIcon(icon);
        if (lastPosition <position) {
            Animation animation = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), R.anim.fade_in);
            viewHolder.itemView.setAnimation(animation);
            lastPosition=position;
        }
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView  categoryIcon;
        private TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName  = itemView.findViewById(R.id.categoryName);
            categoryIcon = itemView.findViewById(R.id.categoryIcon);
        }

        private void setCategoryIcon(String iconUrl){
           // todo: set category names from database
//            if (!iconUrl.equals("null"))
                    Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(categoryIcon);
        }

        private void setCategory(final String name){
            categoryName.setText(name);
            if (name.equals("Home")){
                itemView.setVisibility(View.GONE);
                itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                categoryName.setHeight(0);
                categoryName.setWidth(0);
            }
            if (!name.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent categoryIntent = new Intent(itemView.getContext(), CategoryActivity.class);
                        categoryIntent.putExtra("CategoryName", name);
                        itemView.getContext().startActivity(categoryIntent);

                    }
                });
            }
        }
    }
}

//    Context context;
//    private List<Categories> categoriesList;
//
//    public CategoryAdapter(Context context,List<Categories> categoriesList) {
//
//        this.context = context;
//        this.categoriesList = categoriesList;
//    }
//
//    @Override
//    public  CategoryAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(context).inflate(R.layout.recycler_home_category_item, viewGroup, false);
//        return new viewHolder(view);
//    }
//
//    @Override
//    public  void onBindViewHolder(CategoryAdapter.viewHolder viewHolder,int position) {
//        String name = categoriesList.get(position).getCategoryName();
//        String icon = categoriesList.get(position).getCategoryIconLink();
//        viewHolder.setCategoryName(name);
//        viewHolder.setCategoryIcon(icon);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return categoriesList.size();
//    }
//
//    public class viewHolder extends RecyclerView.ViewHolder {
//        private TextView categoryName;
//        private ImageView categoryIcon;
//
//        public viewHolder(View itemView) {
//            super(itemView);
//            categoryName  = itemView.findViewById(R.id.categoryName);
//            categoryIcon = itemView.findViewById(R.id.categoryIcon);
//        }
//
//        private void setCategoryIcon(String iconUrl){
//            //todo: set category names from database
////            Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.drawable.shape)).into(categoryIcon);
//        }
//
//        private void setCategoryName(String name){
//            categoryName.setText(name);
//
//        }
//    }
//}
