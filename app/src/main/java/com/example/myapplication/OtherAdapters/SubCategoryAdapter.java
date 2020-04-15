package com.example.myapplication.OtherAdapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CategoryActivity;
import com.example.myapplication.Models.CategoryModel;
import com.example.myapplication.R;

import java.util.List;

public class SubCategoryAdapter  extends RecyclerView.Adapter<SubCategoryAdapter.VewHolder> {
    private List<CategoryModel> subCategoryModelList;

    public SubCategoryAdapter(List<CategoryModel> subCategoryModelList) {
        this.subCategoryModelList = subCategoryModelList;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.VewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_home_sub_category_item, viewGroup, false);
        return new VewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.VewHolder viewHolder, int position) {
        String name = subCategoryModelList.get(position).getCategoryName();
        String icon = subCategoryModelList.get(position).getCategoryIconLink();
        viewHolder.setCategory(name);
    }

    @Override
    public int getItemCount() {
        return subCategoryModelList.size();
    }

    public class VewHolder extends RecyclerView.ViewHolder {
        private ImageView subCategoryIcon;
        private TextView subCategoryName;

        public VewHolder(@NonNull View itemView) {
            super(itemView);
            subCategoryName = itemView.findViewById(R.id.subCategoryName);
            subCategoryIcon = itemView.findViewById(R.id.subCategoryIcon);
        }

        private void setCategoryIcon(String iconUrl) {
            // todo: set category names from database
//            Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.drawable.shape)).into(categoryIcon);
        }

        private void setCategory(final String name) {
            subCategoryName.setText(name);
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





