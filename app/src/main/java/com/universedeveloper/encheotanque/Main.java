package com.universedeveloper.encheotanque;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;
import com.universedeveloper.encheotanque.fragment.Bandeira.BandeirasFragment;
import com.universedeveloper.encheotanque.fragment.Carro.CarrosFragment;
import com.universedeveloper.encheotanque.fragment.Cidade.CidadesFragment;
import com.universedeveloper.encheotanque.fragment.Estado.EstadosFragment;
import com.universedeveloper.encheotanque.fragment.Favorito.FavoritosFragment;
import com.universedeveloper.encheotanque.fragment.MainFragment;
import com.universedeveloper.encheotanque.fragment.Marca.MarcasFragment;
import com.universedeveloper.encheotanque.fragment.SobreFragment;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView name = (TextView) header.findViewById(R.id.name);
        TextView email = (TextView) header.findViewById(R.id.email);
        ImageView picture = (ImageView) header.findViewById(R.id.foto);

        SharedPreferences preferences = getSharedPreferences("usuario", 0);
        if(!preferences.getString("nome", "").equals("")){
            name.setText(preferences.getString("nome", ""));
            email.setText(preferences.getString("email", ""));
            Picasso.with(this).load(preferences.getString("foto", "")).into(picture);
        }

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content_fragment, new MainFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_estados) {
            fm.beginTransaction().replace(R.id.content_fragment, new EstadosFragment()).addToBackStack("").commit();
        } else if (id == R.id.nav_bandeiras) {
            fm.beginTransaction().replace(R.id.content_fragment, new BandeirasFragment()).addToBackStack("").commit();
        } else if (id == R.id.nav_carros) {
            fm.beginTransaction().replace(R.id.content_fragment, new CarrosFragment()).addToBackStack("").commit();
        } else if (id == R.id.nav_cidades) {
            fm.beginTransaction().replace(R.id.content_fragment, new CidadesFragment()).addToBackStack("").commit();
        } else if (id == R.id.nav_marcas) {
            fm.beginTransaction().replace(R.id.content_fragment, new MarcasFragment()).addToBackStack("").commit();
        } else if (id == R.id.nav_mapa) {
            fm.beginTransaction().replace(R.id.content_fragment, new MainFragment()).addToBackStack("").commit();
        } else if (id == R.id.nav_sobre) {
            fm.beginTransaction().replace(R.id.content_fragment, new SobreFragment()).addToBackStack("").commit();
        } else if(id == R.id.nav_favoritos) {
            fm.beginTransaction().replace(R.id.content_fragment, new FavoritosFragment()).addToBackStack("").commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
