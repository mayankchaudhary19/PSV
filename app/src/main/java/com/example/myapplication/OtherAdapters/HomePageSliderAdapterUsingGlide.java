package com.example.myapplication.OtherAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class HomePageSliderAdapterUsingGlide extends
        SliderViewAdapter<HomePageSliderAdapterUsingGlide.SliderAdapterVH> {

    private Context context;
    private int mCount;

    public HomePageSliderAdapterUsingGlide(Context context) {
        this.context = context;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {


//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
//            }
//        });


        switch (position) {
            case 0:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.sampleproductone)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            case 1:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.sampleproducttwo)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;

            case 2:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.sampleproductthree)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;

            default:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.sampleproductone)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;

        }

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mCount;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            this.itemView = itemView;
        }
    }


}
