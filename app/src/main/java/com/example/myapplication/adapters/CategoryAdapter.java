package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Pojos.Categories;
import com.example.myapplication.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder>{
    Context context;
    ArrayList<Categories> arrayList;

    public CategoryAdapter(Context context, ArrayList<Categories> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public  CategoryAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_category_item, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public  void onBindViewHolder(CategoryAdapter.viewHolder viewHolder,int position) {
        viewHolder.t1.setText(arrayList.get(position).getTitle());
        viewHolder.img.setImageResource(arrayList.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView t1;
        ImageView img;

        public viewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.categ_imageView);
            t1 = (TextView) itemView.findViewById(R.id.title);
        }
    }
}





//    @Override
//    public MainActivity.MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.category_item, ViewGroup, false);
//        return new viewHolder(view);
//
//    }
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView t1;
//        ImageView img;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            t1 = itemView.findViewById(R.id.title);
//            img = itemView.findViewById(R.id.categ_imageView);
//        }
//    }
//
//    @NonNull
//    @Override
//    public MainActivity.MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater().inflate(R.layout.category_item,parent,false);
//        return new MainActivity.MyAdapter.ViewHolder(v);
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MainActivity.MyAdapter.ViewHolder holder, int position) {
//        Categories data = list.get(position);
//        holder.t1.setText(data.getTitle());
//        holder.img.setImageDrawable(getDrawable(data.getImg()));
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//
//}