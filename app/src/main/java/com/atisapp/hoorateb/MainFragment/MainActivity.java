package com.atisapp.hoorateb.MainFragment;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.atisapp.hoorateb.MainFragment.ui.basket.ui.main.BasketTabFragment;
import com.atisapp.hoorateb.R;
import com.atisapp.hoorateb.Search.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Context context;
    private Toolbar main_toolbar;
    private DrawerLayout main_draw;
    private NavigationView main_navigation;
    private ProgressDialog progressDialog;
    private FirebaseAnalytics mFirebaseAnalytics;
    private ImageView searchButton;
    private int CODE_GALLERY_REQUEST=999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_category, R.id.navigation_basket,R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        SetupViews();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.i(TAG, "onRequestPermissionsResult: ");
        if(requestCode == CODE_GALLERY_REQUEST)
        {
            Log.i(TAG, "onRequestPermissionsResult3: ");
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"تصویر حساب کاربری خود را انتخاب نمایید"),CODE_GALLERY_REQUEST);
            }else {
                Toast.makeText(context,"شما اجازه دسترسی به گالری را به اپلیکیشن حورا طب نداده اید",Toast.LENGTH_LONG).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void SetupViews()
    {

        context = MainActivity.this;
        main_draw = findViewById(R.id.container);
        main_navigation = findViewById(R.id.main_navigation);
        searchButton = findViewById(R.id.mainSearchButton);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setupToolBar();
        setupNavigationView();
    }

    private void setupToolBar()
    {
        main_toolbar     = (Toolbar) findViewById(R.id.main_toolBar);
        setSupportActionBar(main_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        main_toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white_trans));
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,main_draw,main_toolbar,0,0);
        main_draw.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        main_toolbar.setNavigationIcon(R.drawable.ic_baseline_dehaze);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }
    private void setupNavigationView()

    {
        main_navigation.getMenu().setGroupCheckable(0,false,true);
        main_navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.menu_navigation_explain :
                    {

                        //Intent intent = new Intent(context, AboutActivity.class);
                        //intent.putExtra("aboutType", "explain");
                        //startActivity(intent);
                        break;
                    }
                    case R.id.menu_navigation_usage :
                    {
                        //Intent intent = new Intent(context, AboutActivity.class);
                        //intent.putExtra("aboutType", "usage");
                        //startActivity(intent);
                        break;
                    }

                    case R.id.menu_navigation_sell :
                    {

                        progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("در حال ورود به پنل نمایندگان");
                        progressDialog.show();
                        break;
                    }

                    case R.id.menu_navigation_about :
                    {
//                        Intent intent = new Intent(context, AboutActivity.class);
//                        intent.putExtra("aboutType", "about");
//                        startActivity(intent);
                        break;
                    }

                }

                return false;
            }
        });


    }

    public void OpenDetailDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.layout_detail_dialog, viewGroup, false);

        Button okBtn = dialogView.findViewById(R.id.detail_btn);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final AlertDialog accountDialog = builder.create();
        accountDialog.show();
        accountDialog.setCanceledOnTouchOutside(false);
        accountDialog.setCancelable(false);

        okBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                accountDialog.dismiss();
            }
        });

    }

}