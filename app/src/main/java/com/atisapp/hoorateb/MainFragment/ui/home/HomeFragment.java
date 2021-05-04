package com.atisapp.hoorateb.MainFragment.ui.home;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.R;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private Context context;
    private HomeAPIService homeAPIService;
    private View homeRoot;
    private RecyclerView recyclerView;
    private List<HomeMultiViewModel> totalMultiViewModelList;
    private HomeMultiViewAdapter multiViewAdapter;
    private boolean favoriteRes = false ,topSellRes = false ,newRes = false ;
    private int listSize;
    private ProgressDialog progressDialog;
    private List<HomeSliderModel> sliderModels;
    private ShimmerFrameLayout mainShimmer;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        homeRoot = inflater.inflate(R.layout.fragment_home, container, false);
        SetupViews();
        return homeRoot;
    }

    private void SetupViews()
    {
        context = getContext();
        recyclerView = homeRoot.findViewById(R.id.home_fragment_recycler);
        mainShimmer = homeRoot.findViewById(R.id.main_shimmer_view_container);
        homeAPIService = new HomeAPIService(context);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        totalMultiViewModelList = new ArrayList<>();
        mainShimmer.startShimmer();

        GetSlider();
    }


    private void GetSlider()
    {
        homeAPIService.GetMainSlider(new HomeAPIService.onGetMainSlider() {
            @Override
            public void onGet(boolean msg, List<HomeSliderModel> sliderModelList) {

                Log.i(TAG, "onGet: "+sliderModelList.size());

                sliderModels = sliderModelList;
                totalMultiViewModelList.add(new HomeMultiViewModel(HomeMultiViewModel.MAIN_SLIDER,sliderModels));
                mainShimmer.stopShimmer();
                mainShimmer.setVisibility(View.GONE);
                Log.i(TAG, "SetLists: ready");
                multiViewAdapter = new HomeMultiViewAdapter(totalMultiViewModelList,context);
                recyclerView.setAdapter(multiViewAdapter);
                multiViewAdapter.notifyDataSetChanged();

                GetFavoriteLists();


            }
        });
    }

    private void GetFavoriteLists()
    {
        homeAPIService.GetMainLists("favorites", new HomeAPIService.onGetMainLists() {
            @Override
            public void onGet(boolean msg, List<ProductModel> productModelList) {
                if(productModelList.size()>0)
                {
                    Log.i(TAG, "onGet: favorite");
                    totalMultiViewModelList.add(new HomeMultiViewModel(HomeMultiViewModel.MAIN_HORIZONTAL_LIST,"محبوب ترین ها","favorites","",productModelList));
                    multiViewAdapter.notifyDataSetChanged();
                    GetNewestLists();
                }
            }
        });
    }
    private void GetNewestLists()
    {
        homeAPIService.GetMainLists("newest", new HomeAPIService.onGetMainLists() {
            @Override
            public void onGet(boolean msg, List<ProductModel> productModelList) {
                if(productModelList.size()>0)
                {
                    totalMultiViewModelList.add(new HomeMultiViewModel(HomeMultiViewModel.MAIN_HORIZONTAL_LIST,"جدید ترین ها","newest","",productModelList));
                    multiViewAdapter.notifyDataSetChanged();
                    GetTopSellLists();
                }
            }
        });
    }
    private void GetTopSellLists()
    {
        homeAPIService.GetMainLists("top_selling", new HomeAPIService.onGetMainLists() {
            @Override
            public void onGet(boolean msg, List<ProductModel> productModelList) {
                if(productModelList.size()>0)
                {
                    totalMultiViewModelList.add(new HomeMultiViewModel(HomeMultiViewModel.MAIN_HORIZONTAL_LIST,"پر فروش ترین ها","top_selling","",productModelList));
                    multiViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }



    private void SetLists()
    {
        Log.i(TAG, "SetLists size: "+totalMultiViewModelList.size());
        Log.i(TAG, "SetLists: "+favoriteRes+newRes+topSellRes);
        if(favoriteRes && newRes && topSellRes)
        {
            mainShimmer.stopShimmer();
            mainShimmer.setVisibility(View.GONE);
            Log.i(TAG, "SetLists: ready");
            multiViewAdapter = new HomeMultiViewAdapter(totalMultiViewModelList,context);
            recyclerView.setAdapter(multiViewAdapter);
            multiViewAdapter.notifyDataSetChanged();
        }
    }
//    private void GetPopularProducts()
//    {
//        if (progressDialog == null) {
//            progressDialog = new ProgressDialog(context);
//            progressDialog.setMessage("در حال دریافت اطلاعات");
//            progressDialog.show();
//        }
//
//        totalMultiViewModelList = new ArrayList<>();
//        totalMultiViewModelList.add(new HomeMultiViewModel(HomeMultiViewModel.MAIN_HORIZONTAL_LIST,"محبوب ترین ها",FakeListGenerator()));
//        totalMultiViewModelList.add(new HomeMultiViewModel(HomeMultiViewModel.MAIN_HORIZONTAL_LIST,"جدید ترین ها",FakeListGenerator()));
//        totalMultiViewModelList.add(new HomeMultiViewModel(HomeMultiViewModel.MAIN_HORIZONTAL_LIST,"پرفروش ترین ها",FakeListGenerator()));
//
//        multiViewAdapter = new HomeMultiViewAdapter(totalMultiViewModelList,context);
//        recyclerView.setAdapter(multiViewAdapter);
//        multiViewAdapter.notifyDataSetChanged();
//        if (progressDialog != null)progressDialog.dismiss();
//    }
    private List<ProductModel> FakeListGenerator()
    {
        List<ProductModel> productModelList = new ArrayList<>();
        for (int i=0 ; i<10 ; i++)
        {
            ProductModel productModel = new ProductModel();
            productModel.setTitle(" محصول شماره "+i);
            productModel.setDescription("توضیحات مربوط به هر محصول با توجه به ویژگی های آن");
            productModel.setPrice(i*10);
            productModel.setDisCount(i*5);
            productModelList.add(productModel);
        }
        return productModelList;
    }


}