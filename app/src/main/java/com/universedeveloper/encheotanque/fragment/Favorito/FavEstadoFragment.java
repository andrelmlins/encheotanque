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
import com.universedeveloper.encheotanque.adapter.AdapterEstados;
import com.universedeveloper.encheotanque.bean.Estado;
import com.universedeveloper.encheotanque.fragment.Estado.EstadoFragment;
import com.universedeveloper.encheotanque.utils.Requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndreLucas on 13/11/2016.
 */
public class FavEstadoFragment extends Fragment implements Response.Listener<JSONObject>, AdapterView.OnItemClickListener, Response.ErrorListener, SearchView.OnQueryTextListener {
    private View rootView;
    private ListView listView;
    private ProgressDialog progress;
    private List<Estado> list = new ArrayList<Estado>();
    private AdapterEstados adapter;

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
        this.progress = ProgressDialog.show(getActivity(), "","Carregando estados...", true);
        this.listView = (ListView) rootView.findViewById(R.id.list);
        this.listView.setOnItemClickListener(this);
        Requests r = Requests.getInstance(getActivity());
        SharedPreferences preferences = getActivity().getSharedPreferences("usuario", 0);
        r.getObject(Requests.ROOT+"preferencia.php?tipo=estado&usuario="+preferences.getString("id",""),this,this);
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
            JSONArray result = response.getJSONArray("estados");
            for(int i=0;i<result.length();i++) {
                JSONObject estado = result.getJSONObject(i);
                this.list.add(new Estado(estado.getString("imagem"), estado.getString("nome"), estado.getString("descricao"), estado.getString("preco")));
            }
            this.adapter = new AdapterEstados(this.list, getActivity());
            this.listView.setAdapter(adapter);
            this.progress.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Estado e = this.list.get(position);
        EstadoFragment f = new EstadoFragment();
        Bundle b = new Bundle();
        b.putString("estado",e.getEstadoabrev());
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
}
