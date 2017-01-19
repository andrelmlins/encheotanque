package com.universedeveloper.encheotanque.fragment.Marca;

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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.adapter.AdapterMarcas;
import com.universedeveloper.encheotanque.bean.Marca;
import com.universedeveloper.encheotanque.utils.Requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AndreLucas on 12/09/2016.
 */
public class MarcasFragment extends Fragment implements Response.Listener<JSONObject>, AdapterView.OnItemClickListener, Response.ErrorListener, SearchView.OnQueryTextListener {
    private ListView listView;
    private List<Marca> list = new ArrayList<Marca>();
    private ProgressDialog progress;
    private AdapterMarcas adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState){
        View rootView = inflater.inflate(R.layout.list_fragment  ,container,false);
        this.progress = ProgressDialog.show(getActivity(), "","Carregando as marcas...", true);
        getActivity().setTitle("Marcas");
        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        Requests r = Requests.getInstance(getActivity());
        r.getObject("http://apifuel.universedeveloper.com/marcas.php",this,this);
        return rootView;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Toast.makeText(getActivity(), volleyError.toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray result = response.getJSONArray("marcas");
            for(int i=0;i<result.length();i++) {
                JSONObject marca = result.getJSONObject(i);
                this.list.add(new Marca(marca.getString("marca"), marca.getString("logo"), marca.getString("quantidade")));
            }
            this.adapter = new AdapterMarcas(this.list, getActivity());
            this.listView.setAdapter(adapter);
            this.progress.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Marca m = this.list.get(position);
        MarcaFragment f = new MarcaFragment();
        Bundle b = new Bundle();
        b.putString("marca",m.getNome());
        f.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,f).commit();
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
}
