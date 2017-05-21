package com.universedeveloper.encheotanque.fragment.Estado;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.adapter.AdapterEstados;
import com.universedeveloper.encheotanque.bean.Estado;
import com.universedeveloper.encheotanque.utils.Requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndreLucas on 04/09/2016.
 */
public class EstadosFragment extends Fragment implements Response.Listener<JSONObject>, AdapterView.OnItemClickListener, Response.ErrorListener, SearchView.OnQueryTextListener,AbsListView.OnScrollListener {
    private ListView listView;
    private List<Estado> list = new ArrayList<Estado>();
    private ProgressDialog progress;
    private AdapterEstados adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        View rootView = inflater.inflate(R.layout.list_fragment, container, false);
        this.progress = ProgressDialog.show(getActivity(), "","Carregando os estados...", true);
        getActivity().setTitle("Estados");
        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
        Requests r = Requests.getInstance(getActivity());
        r.getObject(Requests.ROOT+"/estados.php", this, this);
        return rootView;
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray result = response.getJSONArray("estados");
            for(int i=0;i<result.length();i++) {
                JSONObject estado = result.getJSONObject(i);
                this.list.add(new Estado(estado.getString("bandeira"), estado.getString("estado"), estado.getString("estadoabrev"), estado.getString("precocombustivel")));
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

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Toast.makeText(getActivity(), volleyError.toString(),
                Toast.LENGTH_SHORT).show();
    }

    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.search).setVisible(true);
        menu.findItem(R.id.add).setVisible(false);
        menu.findItem(R.id.favorito).setVisible(false);
        MenuItem mSearchMenuItem = menu.findItem(R.id.search);
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
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
