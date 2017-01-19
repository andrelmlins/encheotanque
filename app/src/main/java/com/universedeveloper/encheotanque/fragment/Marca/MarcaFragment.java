package com.universedeveloper.encheotanque.fragment.Marca;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.tabs.MarcaTabs;

/**
 * Created by AndreLucas on 17/09/2016.
 */
public class MarcaFragment extends Fragment implements TabLayout.OnTabSelectedListener{
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private View rootView;
    private String marca;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        this.rootView = inflater.inflate(R.layout.tabs_fragment, container, false);
        this.marca = getArguments().getString("marca");
        getActivity().setTitle(getArguments().getString("marca"));
        this.tabs();
        return rootView;
    }

    private void tabs() {
        this.tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        this.tabLayout.addTab(tabLayout.newTab().setText("Descrição"));
        this.tabLayout.addTab(tabLayout.newTab().setText("Carros"));
        this.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        this.viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        MarcaTabs adapter = new MarcaTabs(getActivity().getSupportFragmentManager(),this.marca);
        this.viewPager.setAdapter(adapter);
        this.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayout));
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        this.viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
