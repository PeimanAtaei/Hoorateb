package com.atisapp.hoorateb.MainFragment.ui.category;

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
import android.view.MenuItem;
import android.view.View;

import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.R;

import java.util.List;

public class SubCategoryActivity extends AppCompatActivity {

    private static final String TAG = "SubCategoryActivity";
    private Context context;
    private CategoryAPIService categoryAPIService;
    private SubCategoryListAdapter subCategoryListAdapter;
    private RecyclerView recyclerView;
    private String parentId;
    private Toolbar subCategoryToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        SetupViews();
    }

    private void SetupViews()
    {
        context = SubCategoryActivity.this;
        categoryAPIService = new CategoryAPIService(context);
        recyclerView = findViewById(R.id.sub_category_list_recycler);

        Intent intent = getIntent();
        parentId = intent.getStringExtra("categoryId");

        setupToolBar();
        GetSubCategories();
    }

    private void GetSubCategories()
    {
        categoryAPIService.GetSubCategories(parentId, new CategoryAPIService.onGetSubCategories() {
            @Override
            public void onGet(boolean msg, List<ProductModel> productModels) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
                gridLayoutManager.setInitialPrefetchItemCount(productModels.size());

                subCategoryListAdapter = new SubCategoryListAdapter(context,productModels);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(subCategoryListAdapter);
            }
        });
    }

    private void setupToolBar()
    {
        subCategoryToolbar     = (Toolbar) findViewById(R.id.sub_category_list_toolBar);
        setSupportActionBar(subCategoryToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        subCategoryToolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.trans));
        subCategoryToolbar.setTitle("");

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