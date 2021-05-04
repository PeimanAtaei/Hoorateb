package com.atisapp.hoorateb.Product;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.atisapp.hoorateb.MainFragment.ui.home.HomeAPIService;
import com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductHorizontalListAdapter;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.R;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private static final String TAG = "ProductListActivity";
    private Context context;
    private ProductAPIService productAPIService;
    private HomeAPIService homeAPIService;
    private ProductListAdapter productListAdapter;
    private RecyclerView recyclerView;
    private String categoryId;
    private Toolbar productListToolbar;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        SetupViews();
    }

    private void SetupViews()
    {
        context = getApplicationContext();
        productAPIService = new ProductAPIService(context);
        homeAPIService = new HomeAPIService(context);
        recyclerView = findViewById(R.id.product_list_recycler);
        setupToolBar();

        Intent intent = getIntent();
        int isMainList = intent.getIntExtra("isMainList",0);
        if(isMainList == 1)
        {
            Log.i(TAG, "SetupViews: "+type);
            type = intent.getStringExtra("type");
            GetTopSellLists(type);
        }else {
            categoryId = intent.getStringExtra("categoryId");
            GetProductList();
        }

        Log.i(TAG, "SetupViews: "+categoryId);

    }

    private void GetProductList()
    {
        productAPIService.GetProductList(categoryId, new ProductAPIService.onGetProductList() {
            @Override
            public void onGetList(boolean res, List<ProductModel> productModelList) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                gridLayoutManager.setInitialPrefetchItemCount(productModelList.size());

                productListAdapter = new ProductListAdapter(context,productModelList);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(productListAdapter);
            }
        });
    }

    private void GetTopSellLists(String type)
    {
        homeAPIService.GetMainLists(type, new HomeAPIService.onGetMainLists() {
            @Override
            public void onGet(boolean msg, List<ProductModel> productModelList) {
                if(productModelList.size()>0)
                {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
                    gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                    gridLayoutManager.setInitialPrefetchItemCount(productModelList.size());

                    productListAdapter = new ProductListAdapter(context,productModelList);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(productListAdapter);
                }
            }
        });
    }

    private void setupToolBar()
    {
        productListToolbar     = (Toolbar) findViewById(R.id.product_list_toolBar);
        setSupportActionBar(productListToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        productListToolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.trans));
        productListToolbar.setTitle("");

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