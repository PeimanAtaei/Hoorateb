package com.atisapp.hoorateb.MainFragment.ui.category;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewAdapter;
import com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel;
import com.atisapp.hoorateb.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private static final String TAG = "CategoryFragment";
    private Context context;
    private View categoryRoot;
    private CategoryAPIService categoryAPIService;
    private RecyclerView recyclerView;
    private List<HomeMultiViewModel> totalMultiViewModelList;
    private HomeMultiViewAdapter multiViewAdapter;
    private ShimmerFrameLayout categoryShimmer;
    private RadioGroup radioGroup;
    private RadioButton radioGeneral,radioProfessional;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        categoryRoot = inflater.inflate(R.layout.fragment_category, container, false);
        SetupViews();

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if(checkedId == R.id.category_fragment_type_professional)
//                {
//                    Log.i(TAG, "onCheckedChanged: professional");
//                    GetAllCategories("professional");
//                }else {
//                    Log.i(TAG, "onCheckedChanged: general");
//                    GetAllCategories("general");
//                }
//            }
//        });

        return categoryRoot;
    }


    private void SetupViews()
    {
        context = getContext();
        categoryAPIService = new CategoryAPIService(context);
        recyclerView = categoryRoot.findViewById(R.id.category_fragment_recycler);
        categoryShimmer = categoryRoot.findViewById(R.id.category_shimmer_view_container);
        radioGroup = categoryRoot.findViewById(R.id.category_fragment_type_radio_group);
        radioGeneral = categoryRoot.findViewById(R.id.category_fragment_type_general);
        radioProfessional = categoryRoot.findViewById(R.id.category_fragment_type_professional);
        categoryShimmer.startShimmer();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        totalMultiViewModelList = new ArrayList<>();

        radioGeneral.setChecked(true);
        GetAllCategories("general");
    }

    private void GetAllCategories(String type)
    {
        categoryAPIService.GetCategories(type,new CategoryAPIService.onGetCategories() {
            @Override
            public void onGet(boolean msg, List<CategoryModel> categoryModelList) {
                categoryShimmer.stopShimmer();
                categoryShimmer.setVisibility(View.GONE);
                Log.i(TAG, "onGet: "+categoryModelList.size());
                for (CategoryModel categoryModel :
                        categoryModelList) {
                        totalMultiViewModelList.add(new HomeMultiViewModel(HomeMultiViewModel.CATEGORY_HORIZONTAL_LIST,categoryModel,false));
                }
                Log.i(TAG, "onGet: "+totalMultiViewModelList.size());
                multiViewAdapter = new HomeMultiViewAdapter(totalMultiViewModelList,context);
                recyclerView.setAdapter(multiViewAdapter);
                multiViewAdapter.notifyDataSetChanged();
            }
        });
    }
}