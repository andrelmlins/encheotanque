package com.universedeveloper.encheotanque.fragment.Favorito;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.tabs.FavoritoTabs;
import com.universedeveloper.encheotanque.utils.Preferencias;

/**
 * Created by AndreLucas on 12/11/2016.
 */
public class FavoritosFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private View rootView;
    private Preferencias p;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.add).setVisible(false);
        menu.findItem(R.id.favorito).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        this.rootView = inflater.inflate(R.layout.tabs_fragment, container, false);
        this.getActivity().setTitle("Favoritos");
        p = Preferencias.getInstance(this.getActivity());
        this.tabs();
        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void tabs() {
        this.tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        Drawable t1 = ContextCompat.getDrawable(getActivity(), R.drawable.ic_fuel);
        Drawable t2 = ContextCompat.getDrawable(getActivity(), R.drawable.ic_state);
        Drawable t3 = ContextCompat.getDrawable(getActivity(), R.drawable.ic_pin);
        Drawable t4 = ContextCompat.getDrawable(getActivity(), R.drawable.ic_flag);
        t1.setTint(Color.WHITE);
        t2.setTint(Color.WHITE);
        t3.setTint(Color.WHITE);
        t4.setTint(Color.WHITE);
        this.tabLayout.addTab(tabLayout.newTab().setIcon(t1));
        this.tabLayout.addTab(tabLayout.newTab().setIcon(t2));
        this.tabLayout.addTab(tabLayout.newTab().setIcon(t3));
        this.tabLayout.addTab(tabLayout.newTab().setIcon(t4));
        this.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        this.viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        FavoritoTabs adapter = new FavoritoTabs(getActivity().getSupportFragmentManager());
        this.viewPager.setAdapter(adapter);
        this.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayout));
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        this.viewPager.setCurrentItem(tab.getPosition());
        this.viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
