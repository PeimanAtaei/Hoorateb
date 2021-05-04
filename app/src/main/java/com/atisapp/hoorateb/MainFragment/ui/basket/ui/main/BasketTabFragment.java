package com.atisapp.hoorateb.MainFragment.ui.basket.ui.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atisapp.hoorateb.Login.RegisterActivity;
import com.atisapp.hoorateb.MainFragment.MainActivity;
import com.atisapp.hoorateb.MainFragment.ui.basket.BasketMultiViewAdapter;
import com.atisapp.hoorateb.MainFragment.ui.basket.BasketMultiViewModel;
import com.atisapp.hoorateb.MainFragment.ui.basket.BasketProductListAdapter;
import com.atisapp.hoorateb.MainFragment.ui.basket.CardAPIService;
import com.atisapp.hoorateb.MainFragment.ui.basket.ItemOrderModel;
import com.atisapp.hoorateb.MainFragment.ui.basket.TotalOrderModel;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.Payment.CheckOrderActivity;
import com.atisapp.hoorateb.Product.ProductDetailActivity;
import com.atisapp.hoorateb.R;
import com.atisapp.hoorateb.Storage.SharedPre.IdentitySharedPref;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import static com.atisapp.hoorateb.MainFragment.ui.basket.BasketMultiViewModel.BASKET_PRODUCT_LIST;
import static com.atisapp.hoorateb.MainFragment.ui.basket.BasketMultiViewModel.BASKET_TOTAL_ORDER;

public class BasketTabFragment extends Fragment {

    private static final String TAG = "BasketTabFragment";
    private IdentitySharedPref identitySharedPref;
    private CardAPIService cardAPIService;
    private Context context;
    private View basketRoot;
    private List<BasketMultiViewModel> totalMultiViewModelList;
    private BasketMultiViewAdapter basketMultiViewAdapter;
    private ShimmerFrameLayout cartShimmer;
    public RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private LinearLayout emptyView;
    private LinearLayout registrationAlarm;
    private LinearLayout completePayment;
    public TextView completePaymentPrice;
    private Button completePaymentBtn,basketRegisterBtn;
    private int courseSize;
    private int totalPrice = 0;
    private ViewGroup container;

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: resume"+getContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        basketRoot = inflater.inflate(R.layout.fragment_basket_tab, container, false);
        SetupViews();

        basketRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: click");
                Intent intent = new Intent(context, RegisterActivity.class);
                context.startActivity(intent);
            }
        });

        completePaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("در حال دریافت اطلاعات");
                    progressDialog.show();
                }

                cardAPIService.GetUserCard(new CardAPIService.onGetUserCard() {
                    @Override
                    public void onGet(boolean msg, TotalOrderModel totalOrderModel) {
                        if(totalOrderModel.getItemsList()==null || totalOrderModel.getItemsList().size()==0)
                        {
                            Toast.makeText(context,"سبد خرید شما خالی می باشد",Toast.LENGTH_LONG).show();
                            if(progressDialog != null)
                                progressDialog.dismiss();

                        }else {
                            cardAPIService.CheckUserDetail(new CardAPIService.onCheckUserDetail() {
                                @Override
                                public void onCheck(boolean msg) {
                                    if(msg){
                                        Intent intent = new Intent(context, CheckOrderActivity.class);
                                        startActivity(intent);
                                    }else {
                                        MainActivity mainActivity =(MainActivity)getActivity();
                                        mainActivity.OpenDetailDialog();
                                    }
                                    if(progressDialog != null)
                                        progressDialog.dismiss();
                                }
                            });

                        }

                    }
                });
            }
        });
        return basketRoot;
    }

    public void SetupViews() {
        Log.i(TAG, "SetupViews: " + context);
        identitySharedPref = new IdentitySharedPref(context);
        cartShimmer = basketRoot.findViewById(R.id.cart_shimmer_view_container);
        recyclerView = basketRoot.findViewById(R.id.basket_tab_recycler);
        registrationAlarm = basketRoot.findViewById(R.id.registration_alarm_layout);
        completePayment = basketRoot.findViewById(R.id.basket_tab_complete_payment_layout);
        completePaymentPrice = basketRoot.findViewById(R.id.basket_tab_complete_payment_price);
        completePaymentBtn = basketRoot.findViewById(R.id.basket_tab_complete_payment_button);
        basketRegisterBtn = basketRoot.findViewById(R.id.basket_register_alarm_btn);
        emptyView = basketRoot.findViewById(R.id.basket_empty_list_view);
        cartShimmer.startShimmer();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        CheckRegistration();
    }

    public void CheckRegistration() {
        if (identitySharedPref.getIsRegistered() != 1) {
            registrationAlarm.setVisibility(View.VISIBLE);
            completePayment.setVisibility(View.GONE);
            cartShimmer.stopShimmer();
            cartShimmer.setVisibility(View.GONE);
        } else {
            registrationAlarm.setVisibility(View.GONE);
            GetCardData(context);
        }
    }

    public void GetCardData(Context ctx) {
        totalMultiViewModelList = new ArrayList<>();
        cardAPIService = new CardAPIService(ctx);
        cardAPIService.GetUserCard(new CardAPIService.onGetUserCard() {
            @Override
            public void onGet(boolean msg, TotalOrderModel totalOrderModel) {
                if(totalOrderModel.getItemsList()==null || totalOrderModel.getItemsList().size()==0)
                {
                    cartShimmer.stopShimmer();
                    cartShimmer.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    completePayment.setVisibility(View.GONE);
                }
                else
                {
                    completePayment.setVisibility(View.VISIBLE);
                    cartShimmer.stopShimmer();
                    cartShimmer.setVisibility(View.GONE);

                    totalMultiViewModelList.add(new BasketMultiViewModel(BasketMultiViewModel.BASKET_PRODUCT_LIST, totalOrderModel.getItemsList()));
                    //totalMultiViewModelList.add(new BasketMultiViewModel(BasketMultiViewModel.BASKET_TOTAL_ORDER, totalOrderModel));
                    basketMultiViewAdapter = new BasketMultiViewAdapter(totalMultiViewModelList, context,"basket");
                    recyclerView.setAdapter(basketMultiViewAdapter);
                    basketMultiViewAdapter.notifyDataSetChanged();

                }
            }
        });
    }

}






