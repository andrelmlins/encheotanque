package com.universedeveloper.encheotanque.fragment.Carro;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.adapter.AdapterModelo;
import com.universedeveloper.encheotanque.bean.Carro;
import com.universedeveloper.encheotanque.utils.Requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndreLucas on 17/09/2016.
 */
public class CarroFragment_2 extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener, SearchView.OnQueryTextListener {
    private View rootView;
    private ListView listView;
    private ProgressDialog progress;
    private List<Carro> list = new ArrayList<Carro>();
    private AdapterModelo adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        this.rootView = inflater.inflate(R.layout.list_spinner_fragment, container, false);
        this.progress = ProgressDialog.show(getActivity(), "","Carregando modelos do carro...", true);
        this.listView = (ListView) rootView.findViewById(R.id.list);
        Requests r = Requests.getInstance(getActivity());
        try {
            r.getObject(Requests.ROOT+"/modeloporcarro.php?carro="+ URLEncoder.encode(getArguments().getString("carro"), "UTF-8"),this,this);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this.rootView;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        this.progress.dismiss();
        Toast.makeText(getActivity(), volleyError.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray result = response.getJSONArray("modelos");
            for(int i=0;i<result.length();i++) {
                JSONObject carro = result.getJSONObject(i);
                this.list.add(new Carro(carro.getString("versao"), carro.getString("combustivel"), carro.getString("motora"), carro.getString("modelo"),carro.getString("categoria"),carro.getString("kmlgasolinacidade"),carro.getString("kmlgasolinaestrada")));
            }
            this.adapter = new AdapterModelo(this.list, getActivity());
            this.listView.setAdapter(adapter);
            this.progress.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(true);
        menu.findItem(R.id.add).setVisible(false);
        menu.findItem(R.id.favorito).setVisible(false);
        MenuItem mSearchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        this.adapter.filter(newText);
        return true;
    }
}
