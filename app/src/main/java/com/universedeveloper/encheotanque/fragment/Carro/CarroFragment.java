package com.universedeveloper.encheotanque.fragment.Carro;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.tabs.CarroTabs;

/**
 * Created by AndreLucas on 17/09/2016.
 */
public class CarroFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private View rootView;
    private String carro;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        this.rootView = inflater.inflate(R.layout.tabs_fragment, container, false);
        this.carro = getArguments().getString("carro");
        this.tabs();
        return rootView;
    }

    private void tabs() {
        this.tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        this.tabLayout.addTab(tabLayout.newTab().setText("Descrição"));
        this.tabLayout.addTab(tabLayout.newTab().setText("Modelos"));
        this.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        this.viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        CarroTabs adapter = new CarroTabs(getActivity().getSupportFragmentManager(),this.carro);
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
