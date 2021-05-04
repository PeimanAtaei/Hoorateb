package com.atisapp.hoorateb.MainFragment.ui.basket;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.atisapp.hoorateb.MainFragment.ui.basket.ui.main.SectionsPagerAdapter;
import com.atisapp.hoorateb.R;
import com.google.android.material.tabs.TabLayout;

public class BasketFragment extends Fragment {

    private static final String TAG = "BasketFragment";
    private Context context;
    private View basketRoot;
    private SectionsPagerAdapter sectionsPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        basketRoot = inflater.inflate(R.layout.fragment_basket, container, false);
        SetupViews();
        return basketRoot;
    }

    public void SetupViews()
    {
        context =getContext();
        CheckRegistration();
    }

    public void CheckRegistration()
    {
        sectionsPagerAdapter = new SectionsPagerAdapter(context, getChildFragmentManager());
        ViewPager viewPager = basketRoot.findViewById(R.id.basket_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = basketRoot.findViewById(R.id.basket_tabs);
        tabs.setupWithViewPager(viewPager);
    }
}