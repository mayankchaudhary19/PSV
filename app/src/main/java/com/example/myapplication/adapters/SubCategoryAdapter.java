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

public class SubCategoryAdapter  extends RecyclerView.Adapter<SubCategoryAdapter.viewHolder>{
        Context context;
        ArrayList<Categories> arrayList;

        public SubCategoryAdapter(Context context, ArrayList<Categories> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }


        @Override
        public  SubCategoryAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_sub_categories, viewGroup, false);
            return new SubCategoryAdapter.viewHolder(view);
        }

        @Override
        public  void onBindViewHolder(SubCategoryAdapter.viewHolder viewHolder, int position) {
            viewHolder.t2.setText(arrayList.get(position).getTitle());
            viewHolder.img2.setImageResource(arrayList.get(position).getImg());
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class viewHolder extends RecyclerView.ViewHolder {
            TextView t2;
            ImageView img2;

            public viewHolder(View itemView) {
                super(itemView);
                img2 = (ImageView) itemView.findViewById(R.id.imageView14);
                t2 = (TextView) itemView.findViewById(R.id.textView10);
            }
        }
    }






