package com.atisapp.hoorateb.Product;

import android.content.Context;
import android.util.Log;

import com.atisapp.hoorateb.MainFragment.ui.home.HomeSliderModel;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class ProductSliderAdapter extends SliderAdapter {

    private static final String TAG = "MainSliderAdapter";
    private Context context;
    private ProductModel productModel;
    private List<String> sliderImages;

    public ProductSliderAdapter(Context context, ProductModel productModel) {
        this.context = context;
        this.productModel = productModel;
        sliderImages = productModel.getImagesUrl();
    }


    @Override
    public int getItemCount() {
        return sliderImages.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder) {


        imageSlideViewHolder.bindImageSlide(sliderImages.get(position));
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
