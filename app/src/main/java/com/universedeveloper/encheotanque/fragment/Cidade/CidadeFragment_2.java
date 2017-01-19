package com.universedeveloper.encheotanque.fragment.Cidade;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.adapter.AdapterPostos;
import com.universedeveloper.encheotanque.bean.Posto;
import com.universedeveloper.encheotanque.fragment.PostoFragment;
import com.universedeveloper.encheotanque.utils.Requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndreLucas on 16/09/2016.
 */
public class CidadeFragment_2 extends Fragment implements Response.Listener<JSONObject>,  AdapterView.OnItemClickListener, Response.ErrorListener, SearchView.OnQueryTextListener, AdapterView.OnItemSelectedListener {
    private View rootView;
    private ListView listView;
    private ProgressDialog progress;
    private List<Posto> list = new ArrayList<Posto>();
    private AdapterPostos adapter;
    private Requests r;

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
        this.listView = (ListView) rootView.findViewById(R.id.list);
        this.listView.setOnItemClickListener(this);
        Spinner s = (Spinner) this.rootView.findViewById(R.id.fuel);
        s.setOnItemSelectedListener(this);
        this.r = Requests.getInstance(getActivity());
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
            JSONArray result = response.getJSONArray("postos");
            this.list.clear();
            for(int i=0;i<result.length();i++) {
                JSONObject posto = result.getJSONObject(i);
                this.list.add(new Posto(posto.getString("logo"), posto.getString("cidade"), posto.getString("estado"), posto.getString("estadoabrev"),posto.getString("hash"),posto.getString("preco"),posto.getString("razao"),posto.getString("bandeira"),posto.getString("combustivel")));
            }
            this.adapter = new AdapterPostos(this.list, getActivity());
            this.listView.setAdapter(adapter);
            this.progress.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Posto p = this.list.get(position);
        PostoFragment f = new PostoFragment();
        Bundle b = new Bundle();
        b.putString("hash",p.getHash());
        f.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,f).commit();
    }

    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.search);
        mSearchMenuItem.setVisible(true);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onPrepareOptionsMenu(menu);
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

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.progress = ProgressDialog.show(getActivity(), "","Carregando postos da cidade...", true);
        String result = parent.getItemAtPosition(position).toString();
        if(result.equals("Gás Natural")) result = "GNV";
        else if(result.equals("Diesel S10")) result="Diesel+S10";
        try {
            this.r.getObject("http://apifuel.universedeveloper.com/postoporcidade.php?combustivel="+result+"&estado="+getArguments().getString("estado")+"&cidade="+ URLEncoder.encode(getArguments().getString("cidade"), "UTF-8"),this,this);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
