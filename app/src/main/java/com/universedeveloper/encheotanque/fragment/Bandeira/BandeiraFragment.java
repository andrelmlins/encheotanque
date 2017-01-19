package com.universedeveloper.encheotanque.fragment.Bandeira;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.bean.Favorito;
import com.universedeveloper.encheotanque.tabs.BandeiraTabs;
import com.universedeveloper.encheotanque.utils.Preferencias;
import com.universedeveloper.encheotanque.utils.Requests;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by AndreLucas on 17/09/2016.
 */
public class BandeiraFragment extends Fragment implements TabLayout.OnTabSelectedListener, Response.Listener<JSONObject>, Response.ErrorListener, MenuItem.OnMenuItemClickListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private View rootView;
    private String bandeira;
    private Preferencias p;
    private Favorito favorito;
    private Menu menu;
    private Requests r;
    private boolean addPref = false;
    private boolean rmPref = false;
    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.add).setVisible(false);
        MenuItem star = menu.findItem(R.id.favorito);
        star.setOnMenuItemClickListener(this);
        if(this.p!=null){
            if(this.p.isPref(this.favorito)) star.setIcon(getResources().getDrawable(R.drawable.ic_star));
            else star.setIcon(getResources().getDrawable(R.drawable.ic_star_border));
        }
        star.setVisible(true);
        this.menu = menu;
        super.onPrepareOptionsMenu(menu);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        this.rootView = inflater.inflate(R.layout.tabs_fragment, container, false);
        this.bandeira = getArguments().getString("bandeira");
        p = Preferencias.getInstance(this.getActivity());
        favorito = new Favorito("bandeira",bandeira," ");
        this.r = Requests.getInstance(getActivity());
        this.tabs();
        return rootView;
    }

    private void tabs() {
        this.tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        this.tabLayout.addTab(tabLayout.newTab().setText("Descrição"));
        this.tabLayout.addTab(tabLayout.newTab().setText("Estados"));
        this.tabLayout.addTab(tabLayout.newTab().setText("Postos"));
        this.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        this.viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        BandeiraTabs adapter = new BandeiraTabs(getActivity().getSupportFragmentManager(),this.bandeira);
        this.viewPager.setAdapter(adapter);
        this.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayout));
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        this.viewPager.setCurrentItem(tab.getPosition());
        this.viewPager.setOffscreenPageLimit(2);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if(this.progress!=null) this.progress.dismiss();
        Toast.makeText(getActivity(), error.toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        if(this.addPref){
            this.p.add(this.favorito);
            MenuItem star = menu.findItem(R.id.favorito);
            star.setIcon(getResources().getDrawable(R.drawable.ic_star));
            this.addPref = false;
            if(this.progress!=null) this.progress.dismiss();
        }
        else if(this.rmPref){
            this.p.remove(this.favorito);
            MenuItem star = menu.findItem(R.id.favorito);
            star.setIcon(getResources().getDrawable(R.drawable.ic_star_border));
            this.rmPref = false;
            if(this.progress!=null) this.progress.dismiss();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        try {
            if (!this.p.isPref(this.favorito)){
                this.addPref = true;
                this.progress = ProgressDialog.show(getActivity(), "","Adicionando aos favoritos...", true);
                this.r.post(this.r.ROOT + "adicionarpreferencia.php", this.p.get(this.favorito), this, this);
            }
            else{
                this.rmPref = true;
                this.progress = ProgressDialog.show(getActivity(), "","Removendo dos favoritos...", true);
                this.r.post(this.r.ROOT + "removerpreferencia.php", this.p.get(this.favorito), this, this);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }
}
