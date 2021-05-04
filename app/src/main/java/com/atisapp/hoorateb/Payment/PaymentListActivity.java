package com.atisapp.hoorateb.Payment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.atisapp.hoorateb.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

public class PaymentListActivity extends AppCompatActivity {

    private static final String TAG = "PaymentListActivity";
    private Context context;
    private PaymentAPIService paymentAPIService;
    private RecyclerView recyclerView;
    private PaymentListAdapter paymentListAdapter;
    private ShimmerFrameLayout paymentListShimmer;
    private LinearLayout emptyView;
    private Toolbar paymentListToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);

        SetupViews();
    }

    private void SetupViews()
    {
        context = PaymentListActivity.this;
        paymentAPIService = new PaymentAPIService(context);

        recyclerView = findViewById(R.id.payment_list_activity_recyclerView);
        paymentListShimmer = findViewById(R.id.payment_list_view_container);
        emptyView = findViewById(R.id.history_empty_list_view);
        setupToolBar();
        paymentListShimmer.startShimmer();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        GetPaymentList();
    }

    private void GetPaymentList()
    {
        paymentAPIService.GetPaymentList(new PaymentAPIService.onGetPaymentList() {
            @Override
            public void onGet(boolean msg, List<PaymentModel> paymentModelList) {
                Log.i(TAG, "onGet: "+paymentModelList.size());
                paymentListShimmer.stopShimmer();
                paymentListShimmer.setVisibility(View.GONE);

                if(paymentModelList != null)
                {
                    paymentListAdapter = new PaymentListAdapter(context,paymentModelList);
                    recyclerView.setAdapter(paymentListAdapter);
                    paymentListAdapter.notifyDataSetChanged();
                }else {
                    emptyView.setVisibility(View.VISIBLE);
                }


            }
        });
    }

    private void setupToolBar()
    {
        paymentListToolbar = (Toolbar) findViewById(R.id.payment_list_toolBar);
        setSupportActionBar(paymentListToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        paymentListToolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.trans));
        paymentListToolbar.setTitle("");

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