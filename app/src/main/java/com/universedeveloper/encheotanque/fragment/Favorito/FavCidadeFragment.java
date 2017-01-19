package com.universedeveloper.encheotanque.fragment.Favorito;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.adapter.AdapterCidades;
import com.universedeveloper.encheotanque.bean.Cidade;
import com.universedeveloper.encheotanque.fragment.Cidade.CidadeFragment;
import com.universedeveloper.encheotanque.utils.Requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndreLucas on 13/11/2016.
 */
public class FavCidadeFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener, SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {
    private View rootView;
    private ListView listView;
    private ProgressDialog progress;
    private List<Cidade> list = new ArrayList<Cidade>();
    private AdapterCidades adapter;
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
        this.rootView = inflater.inflate(R.layout.list_fragment, container, false);
        this.listView = (ListView) rootView.findViewById(R.id.list);
        this.progress = ProgressDialog.show(getActivity(), "","Carregando cidades...", true);
        this.listView.setOnItemClickListener(this);
        this.r = Requests.getInstance(getActivity());
        SharedPreferences preferences = getActivity().getSharedPreferences("usuario", 0);
        r.getObject(Requests.ROOT+"preferencia.php?tipo=cidade&usuario="+preferences.getString("id",""),this,this);
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
            JSONArray result = response.getJSONArray("cidades");
            this.list.clear();
            for(int i=0;i<result.length();i++) {
                JSONObject cidade = result.getJSONObject(i);
                this.list.add(new Cidade(cidade.getString("nome"), cidade.getString("id1"), cidade.getString("id1"), cidade.getString("imagem"),cidade.getString("preco"),"Gasolina"));
            }
            this.adapter = new AdapterCidades(this.list, getActivity());
            this.listView.setAdapter(adapter);
            this.progress.dismiss();
        } catch (JSONException e) {
            this.progress.dismiss();
            e.printStackTrace();
        }
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cidade c = this.list.get(position);
        CidadeFragment f = new CidadeFragment();
        Bundle b = new Bundle();
        b.putString("estado",c.getEstadoabrev());
        b.putString("cidade",c.getNome());
        f.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,f).commit();
    }
}
