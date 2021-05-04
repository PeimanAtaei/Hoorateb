package com.atisapp.hoorateb.MainFragment.ui.account;

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

import com.atisapp.hoorateb.Comment.CommentAdapter;
import com.atisapp.hoorateb.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AddressManagementActivity extends AppCompatActivity {

    private static final String TAG = "AddressManagementActivi";
    private Context context;
    private ProfileAPIService profileAPIService;
    private AddressAdapter addressAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private ShimmerFrameLayout addressManagementShimmer;
    private Toolbar addressManagementToolbar;


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        SetupViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_management);

        SetupViews();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EditAdressActivity.class);
                intent.putExtra("type","add");
                startActivity(intent);
            }
        });
    }

    private void SetupViews()
    {
        context = AddressManagementActivity.this;
        profileAPIService = new ProfileAPIService(context);
        recyclerView = findViewById(R.id.address_management_recyclerView);
        actionButton = findViewById(R.id.address_management_add_btn);
        addressManagementShimmer = findViewById(R.id.address_management_shimmer_view_container);
        addressManagementShimmer.startShimmer();


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        setupToolBar();
        GetAddresses();
    }

    private void GetAddresses()
    {
        profileAPIService.GetUserAddresses(new ProfileAPIService.onGetUserAddress() {
            @Override
            public void onGet(boolean msg, List<AddressModel> addressModelList, int length) {
                addressManagementShimmer.stopShimmer();
                addressManagementShimmer.setVisibility(View.GONE);
                addressAdapter = new AddressAdapter(context,addressModelList);
                recyclerView.setAdapter(addressAdapter);
                addressAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setupToolBar()
    {
        addressManagementToolbar     = (Toolbar) findViewById(R.id.address_management_toolBar);
        setSupportActionBar(addressManagementToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        addressManagementToolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.trans));
        addressManagementToolbar.setTitle("");

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