package com.atisapp.hoorateb.MainFragment.ui.home;

import android.content.Context;
import android.util.Log;

import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {

    private static final String TAG = "MainSliderAdapter";
    private Context context;
    private List<HomeSliderModel> sliderModelList;

    public MainSliderAdapter(Context context, List<HomeSliderModel> sliderModelList) {
        this.context = context;
        this.sliderModelList = sliderModelList;
    }


    @Override
    public int getItemCount() {
        return sliderModelList.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {

        Log.i(TAG, "onBindImageSlide: "+sliderModelList.get(position).getImageUrl());
        imageSlideViewHolder.bindImageSlide(sliderModelList.get(position).getImageUrl());
//        switch (position) {
//            case 0:
//                imageSlideViewHolder.bindImageSlide("http://cdn.jazbe.com/public/images/banners/banner1.png");
//                break;
//            case 1:
//                imageSlideViewHolder.bindImageSlide("http://cdn.jazbe.com/public/images/banners/banner2.png");
//                break;
//        }
    }
}
