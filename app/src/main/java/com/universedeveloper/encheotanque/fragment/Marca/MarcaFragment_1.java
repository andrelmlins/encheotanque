package com.universedeveloper.encheotanque.fragment.Marca;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;
import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.adapter.AdapterMotor;
import com.universedeveloper.encheotanque.bean.Carro;
import com.universedeveloper.encheotanque.fragment.Carro.CarroFragment;
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
public class MarcaFragment_1 extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener,AdapterView.OnItemClickListener {
    private View rootView;
    private ProgressDialog progress;
    private AdapterMotor adapter;
    private ListView listView;
    private List<Carro> list = new ArrayList<Carro>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.add).setVisible(false);
        menu.findItem(R.id.favorito).setVisible(false);
        menu.findItem(R.id.search).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        this.rootView = inflater.inflate(R.layout.marca_fragment_1, container, false);
        this.progress = ProgressDialog.show(getActivity(), "","Carregando detalhes da marca...", true);
        this.listView = (ListView) rootView.findViewById(R.id.list);
        this.listView.setOnItemClickListener(this);
        Requests r = Requests.getInstance(getActivity());
        try {
            r.getObject(Requests.ROOT+"/marca.php?marca="+URLEncoder.encode(getArguments().getString("marca"), "UTF-8"),this,this);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this.rootView;
    }

    public void onResponse(JSONObject response) {
        try {
            JSONObject result = response.getJSONObject("marca");

            ImageView im = (ImageView) this.rootView.findViewById(R.id.img_marca);
            TextView t = (TextView) this.rootView.findViewById(R.id.quantidade);

            t.setText(result.getString("quantidade")+" modelos");
            Picasso.with(getContext()).load(result.getString("logo")).into(im);

            JSONArray modelos = result.getJSONArray("modelos");
            for(int i=0;i<modelos.length();i++) {
                JSONObject carro = modelos.getJSONObject(i);
                this.list.add(new Carro(carro.getString("quantidade"), carro.getString("motora"), carro.getString("modelo"), carro.getString("modelo"),carro.getString("categoria"),carro.getString("kmlgasolinacidade"),carro.getString("kmlgasolinaestrada")));
            }
            this.adapter = new AdapterMotor(this.list, getActivity());
            this.listView.setAdapter(adapter);
            this.progress.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Toast.makeText(getActivity(), volleyError.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Carro c = this.list.get(position);
        CarroFragment f = new CarroFragment();
        Bundle b = new Bundle();
        b.putString("carro",c.getModelo());
        f.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment,f).commit();
    }
}
