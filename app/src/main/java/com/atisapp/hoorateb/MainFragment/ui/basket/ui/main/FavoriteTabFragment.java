package com.atisapp.hoorateb.MainFragment.ui.basket.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.atisapp.hoorateb.Login.RegisterActivity;
import com.atisapp.hoorateb.MainFragment.ui.basket.BasketMultiViewAdapter;
import com.atisapp.hoorateb.MainFragment.ui.basket.BasketMultiViewModel;
import com.atisapp.hoorateb.MainFragment.ui.basket.BasketProductListAdapter;
import com.atisapp.hoorateb.MainFragment.ui.basket.CardAPIService;
import com.atisapp.hoorateb.MainFragment.ui.basket.FavoriteAdapter;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.R;
import com.atisapp.hoorateb.Storage.SharedPre.IdentitySharedPref;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;


public class FavoriteTabFragment extends Fragment {

    private static final String TAG = "FavoriteTabFragment";
    private Context context;
    private IdentitySharedPref identitySharedPref;
    private CardAPIService cardAPIService;
    private List<BasketMultiViewModel> totalMultiViewModelList;
    private ShimmerFrameLayout favoriteShimmer;
    private BasketMultiViewAdapter basketMultiViewAdapter;
    private FavoriteAdapter favoriteAdapter;
    public RecyclerView recyclerView;
    private LinearLayout emptyView;
    private LinearLayout registrationAlarm;
    private View favoriteRoot;
    private LinearLayoutManager layoutManager;
    private Button favoriteRegisterBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        favoriteRoot = inflater.inflate(R.layout.fragment_favorite_tab, container, false);
        SetupViews();

        favoriteRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RegisterActivity.class);
                context.startActivity(intent);
            }
        });

        return favoriteRoot;
    }

    private void SetupViews()
    {
        context = getActivity().getApplicationContext();
        identitySharedPref = new IdentitySharedPref(context);
        cardAPIService =new CardAPIService(context);

        recyclerView = favoriteRoot.findViewById(R.id.favorite_tab_recycler);
        registrationAlarm = favoriteRoot.findViewById(R.id.registration_alarm_layout);
        favoriteShimmer = favoriteRoot.findViewById(R.id.favorite_shimmer_view_container);
        emptyView = favoriteRoot.findViewById(R.id.favorite_empty_list_view);
        favoriteRegisterBtn = favoriteRoot.findViewById(R.id.favorite_register_alarm_btn);
        favoriteShimmer.startShimmer();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        CheckRegistration();
    }

    public void CheckRegistration() {
        if (identitySharedPref.getIsRegistered() != 1) {
            registrationAlarm.setVisibility(View.VISIBLE);
            favoriteShimmer.stopShimmer();
            favoriteShimmer.setVisibility(View.GONE);
        } else {
            registrationAlarm.setVisibility(View.GONE);
            GetFavoriteData();
        }

    }

    private void GetFavoriteData()
    {
        totalMultiViewModelList = new ArrayList<>();
        cardAPIService.GetUseFavorite(new CardAPIService.onGetUserFavorite() {
            @Override
            public void onGet(boolean msg, List<ProductModel> productModelList) {

                if(productModelList.size() >0)
                {
                    favoriteShimmer.stopShimmer();
                    favoriteShimmer.setVisibility(View.GONE);

                    totalMultiViewModelList.add(new BasketMultiViewModel(BasketMultiViewModel.FAVORITE_PRODUCT_LIST, productModelList,false));
                    //totalMultiViewModelList.add(new BasketMultiViewModel(BasketMultiViewModel.BASKET_TOTAL_ORDER, totalOrderModel));
                    basketMultiViewAdapter = new BasketMultiViewAdapter(totalMultiViewModelList, context,"basket");
                    recyclerView.setAdapter(basketMultiViewAdapter);
                    basketMultiViewAdapter.notifyDataSetChanged();
                }else {
                    favoriteShimmer.stopShimmer();
                    favoriteShimmer.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }

            }
        });
    }
}