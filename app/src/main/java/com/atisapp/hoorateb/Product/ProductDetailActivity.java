package com.atisapp.hoorateb.Product;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atisapp.hoorateb.MainFragment.ui.basket.CardAPIService;
import com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewAdapter;
import com.atisapp.hoorateb.MainFragment.ui.home.HomeMultiViewModel;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductSpecificationModel;
import com.atisapp.hoorateb.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    private static final String TAG = "ProductDetailActivity";
    private Context context;
    private ProductAPIService productAPIService;
    private RecyclerView recyclerView;
    private List<HomeMultiViewModel> totalMultiViewModelList;
    private HomeMultiViewAdapter multiViewAdapter;
    private int listSize;
    private ProgressDialog progressDialog;
    private ProductSpecificationModel specificationModel;
    private String productId;
    private Toolbar productToolbar;
    private ProductModel myProductModel;
    public LinearLayout addToCardLayout;
    private TextView addToCardPrice;
    private Button addToCardBtn;
    private CardAPIService cardAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        SetupViews();

        addToCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetInCard();
            }
        });
    }

    private void SetupViews()
    {
        context = getApplicationContext();
        cardAPIService = new CardAPIService(context);
        recyclerView = findViewById(R.id.product_detail_recycler);
        addToCardLayout = findViewById(R.id.product_detail_add_card);
        addToCardPrice = findViewById(R.id.basket_tab_complete_payment_price);
        addToCardBtn = findViewById(R.id.basket_tab_complete_payment_button);

        Intent intent = getIntent();
        productId = intent.getStringExtra("productId");
        totalMultiViewModelList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        setupToolBar();
        GetProductDetail();
        IsInCard();
    }

    private void GetProductDetail()
    {
        myProductModel = new ProductModel();
        productAPIService = new ProductAPIService(context);
        productAPIService.GetProductDetail(productId, new ProductAPIService.onGetProductDetail() {
            @Override
            public void onGetDetail(boolean res, ProductModel productModel) {

                myProductModel = productModel;

                totalMultiViewModelList.add(new HomeMultiViewModel(HomeMultiViewModel.PRODUCT_DETAIL_HEADER,productModel));
                totalMultiViewModelList.add(new HomeMultiViewModel(HomeMultiViewModel.PRODUCT_DETAIL_SPECIFICATION,"مشخصات",productModel.getSpecificationModelList(),false));
                totalMultiViewModelList.add(new HomeMultiViewModel(HomeMultiViewModel.PRODUCT_DETAIL_REVIEWS,productModel.getReviewsModelList(),0));
                multiViewAdapter = new HomeMultiViewAdapter(totalMultiViewModelList,context);
                recyclerView.setAdapter(multiViewAdapter);
                multiViewAdapter.notifyDataSetChanged();
                addToCardPrice.setText(convertNumberToBalance(myProductModel.getPrice()));
                GetProductList();

            }
        });

    }

    private void GetProductList()
    {
        Log.i(TAG, "GetProductList: "+myProductModel.getCategories().get(0).getCategoryId());
        final String categoryId = myProductModel.getCategories().get(0).getCategoryId();

        productAPIService.GetProductList(categoryId, new ProductAPIService.onGetProductList() {
            @Override
            public void onGetList(boolean res, List<ProductModel> productModelList) {
                totalMultiViewModelList.add(new HomeMultiViewModel(HomeMultiViewModel.MAIN_HORIZONTAL_LIST,"محصولات مشابه","category",categoryId,productModelList));
                totalMultiViewModelList.add(new HomeMultiViewModel(HomeMultiViewModel.PRODUCT_DETAIL_EMPTY,true));
                multiViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void SetInCard()
    {
        cardAPIService.AddToCard(myProductModel.getProductId(), new CardAPIService.onAddToCard() {
            @Override
            public void onGet(boolean msg) {
                Toast.makeText(context,"این محصول به سبد خرید شما افزوده شد",Toast.LENGTH_SHORT).show();
                myProductModel.setInBasket(true);
                multiViewAdapter.notifyDataSetChanged();
                addToCardLayout.setVisibility(View.GONE);
            }
        });

    }

    private void IsInCard()
    {
        productAPIService.GetProductFeatures(productId, new ProductAPIService.onGetProductFeatures() {
            @Override
            public void onGetFeatures(boolean res, boolean favorite, boolean cart) {
                if(cart)
                {
                    addToCardLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setupToolBar()
    {
        productToolbar     = (Toolbar) findViewById(R.id.product_detail_toolBar);
        setSupportActionBar(productToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        productToolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.trans));
        productToolbar.setTitle("");

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

    private String convertNumberToBalance(int balance) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(balance);
    }
}