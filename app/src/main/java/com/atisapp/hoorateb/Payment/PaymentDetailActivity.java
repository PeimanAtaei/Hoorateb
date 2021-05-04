package com.atisapp.hoorateb.Payment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.atisapp.hoorateb.MainFragment.ui.basket.BasketMultiViewAdapter;
import com.atisapp.hoorateb.MainFragment.ui.basket.BasketMultiViewModel;
import com.atisapp.hoorateb.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class PaymentDetailActivity extends AppCompatActivity {

    private static final String TAG = "PaymentDetailActivity";
    private Context context;
    private PaymentAPIService paymentAPIService;
    private List<PaymentMultiViewModel> totalMultiViewModelList;
    private PaymentMultiViewAdapter paymentMultiViewAdapter;
    public RecyclerView recyclerView;
    private String paymentId;
    private ShimmerFrameLayout paymentDetailShimmer;
    private Toolbar paymentDetailToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);
        SetupViews();
    }

    private void SetupViews()
    {
        context = PaymentDetailActivity.this;
        paymentAPIService = new PaymentAPIService(context);
        recyclerView = findViewById(R.id.payment_detail_activity_recyclerView);
        paymentDetailShimmer = findViewById(R.id.payment_detail_view_container);
        paymentDetailShimmer.startShimmer();
        setupToolBar();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        paymentId = intent.getStringExtra("paymentId");
        Log.i(TAG, "SetupViews: "+paymentId);

        GetPaymentDetail();
    }

    private void GetPaymentDetail()
    {
        totalMultiViewModelList = new ArrayList<>();
        paymentAPIService.GetPaymentDetail(paymentId, new PaymentAPIService.onGetPaymentDetail() {
            @Override
            public void onGet(boolean msg, PaymentModel paymentModel) {
                paymentDetailShimmer.stopShimmer();
                paymentDetailShimmer.setVisibility(View.GONE);

                totalMultiViewModelList.add(new PaymentMultiViewModel(PaymentMultiViewModel.PAYMENT_STEP_VIEW,paymentModel.getPaymentStatus()));
                totalMultiViewModelList.add(new PaymentMultiViewModel(PaymentMultiViewModel.PAYMENT_TOTAL_ORDER,paymentModel));
                totalMultiViewModelList.add(new PaymentMultiViewModel(PaymentMultiViewModel.PAYMENT_PRODUCT_HEADER,"محصولات این سفارش",false));
                totalMultiViewModelList.add(new PaymentMultiViewModel(PaymentMultiViewModel.PAYMENT_PRODUCT_LIST,paymentModel.getOrderList()));

                paymentMultiViewAdapter = new PaymentMultiViewAdapter(totalMultiViewModelList, context);
                recyclerView.setAdapter(paymentMultiViewAdapter);
                paymentMultiViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setupToolBar()
    {
        paymentDetailToolbar = (Toolbar) findViewById(R.id.payment_detail_toolBar);
        setSupportActionBar(paymentDetailToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        paymentDetailToolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.trans));
        paymentDetailToolbar.setTitle("");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}