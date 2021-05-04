package com.atisapp.hoorateb.Search;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.Product.ProductAPIService;
import com.atisapp.hoorateb.Product.ProductListAdapter;
import com.atisapp.hoorateb.R;

import java.util.List;


public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private Context context;
    private Toolbar searchToolbar;
    private EditText searchEditText;
    private TextView searchButton;
    private RecyclerView searchRecyclerView;
    private ProgressDialog progressDialog;
    //private CourseAPIService courseAPIService;
    //private EpisodeAPIService episodeAPIService;
    //private List<MultiViewModel> totalMultiViewModelList;
    private int courseSize,episodeSize;
    private LinearLayout emptyView;
    private ProductAPIService productAPIService;
    private ProductListAdapter productListAdapter;
    //private MultiViewAdapter multiViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SetupView();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWord = searchEditText.getText().toString();
                if (keyWord.length()>0)
                    GetSearchList(keyWord);
                else
                    Toast.makeText(context,"کلمه مورد نظر را وارد نمایید",Toast.LENGTH_LONG).show();
            }
        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyWord = searchEditText.getText().toString();
                    GetSearchList(keyWord);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(progressDialog!= null){
            progressDialog.dismiss();
        }
    }

    private void SetupView()
    {
        context = SearchActivity.this;
        productAPIService = new ProductAPIService(context);
        //emptyView = findViewById(R.id.search_empty_list_view);
        searchEditText = findViewById(R.id.searchKeyWord);
        searchButton = findViewById(R.id.searchActivityButton);
        searchRecyclerView = findViewById(R.id.search_activity_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchRecyclerView.setLayoutManager(layoutManager);


        setupToolBar();
    }

    private void GetSearchList(String keyWord)
    {
        productAPIService.GetSearchList(keyWord, new ProductAPIService.onGetProductList() {
            @Override
            public void onGetList(boolean res, List<ProductModel> productModelList) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                gridLayoutManager.setInitialPrefetchItemCount(productModelList.size());

                productListAdapter = new ProductListAdapter(context,productModelList);
                searchRecyclerView.setLayoutManager(gridLayoutManager);
                searchRecyclerView.setHasFixedSize(true);
                searchRecyclerView.setAdapter(productListAdapter);
            }
        });
    }

    private void setupToolBar()
    {
        searchToolbar     = (Toolbar) findViewById(R.id.search_toolBar);
        setSupportActionBar(searchToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        searchToolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.trans));
        searchToolbar.setTitle("");

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
