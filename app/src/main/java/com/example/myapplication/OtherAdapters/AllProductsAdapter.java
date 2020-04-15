package com.example.myapplication.OtherAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Pojos.Products;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.viewHolder> {
    Context context;
    ArrayList<Products> arrayList;

    public AllProductsAdapter(Context context, ArrayList<Products> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public  AllProductsAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_product, viewGroup, false);
        return new AllProductsAdapter.viewHolder(view);
    }

    @Override
    public  void onBindViewHolder(AllProductsAdapter.viewHolder viewHolder, int position) {
        viewHolder.img4.setImageResource(arrayList.get(position).getImg());
        viewHolder.title4.setText(arrayList.get(position).getTitle());
        viewHolder.subtitle4.setText(arrayList.get(position).getSubtitle());
        viewHolder.price4.setText(arrayList.get(position).getPrice());
        viewHolder.initialPrice4.setText(arrayList.get(position).getInitialPrice());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView title4,subtitle4,price4,initialPrice4;
        ImageView img4;

        public viewHolder(View itemView) {
            super(itemView);
            img4 = (ImageView) itemView.findViewById(R.id.product_img3);
            title4 = (TextView) itemView.findViewById(R.id.product_name3);
            subtitle4 =(TextView) itemView.findViewById(R.id.product_description3);
            price4 =(TextView) itemView.findViewById(R.id.product_price3);
            initialPrice4 =(TextView) itemView.findViewById(R.id.product_initialPrice3);




        }
    }
}