package com.universedeveloper.encheotanque.fragment.Carro;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Picasso;
import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.utils.Requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by AndreLucas on 17/09/2016.
 */
public class CarroFragment_1 extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    private View rootView;
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
        menu.findItem(R.id.favorito).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        this.rootView = inflater.inflate(R.layout.carro_fragment_1, container, false);
        this.progress = ProgressDialog.show(getActivity(), "","Carregando detalhes do carro...", true);
        Requests r = Requests.getInstance(getActivity());
        try {
            r.getObject(Requests.ROOT+"/carro.php?carro="+ URLEncoder.encode(getArguments().getString("carro"), "UTF-8"),this,this);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this.rootView;
    }

    public void onResponse(JSONObject response) {
        try {
            JSONObject result = response.getJSONObject("carro");
            getActivity().setTitle(result.getString("modelo"));
            ImageView image = (ImageView) this.rootView.findViewById(R.id.image);
            TextView nome = (TextView) this.rootView.findViewById(R.id.nome);
            TextView categoria = (TextView) this.rootView.findViewById(R.id.categoria);
            TextView versao = (TextView) this.rootView.findViewById(R.id.versao);
            TextView consumogasomaior = (TextView) this.rootView.findViewById(R.id.consumogasomaior);
            TextView consumogasomenor = (TextView) this.rootView.findViewById(R.id.consumogasomenor);
            TextView consumoetamaior = (TextView) this.rootView.findViewById(R.id.consumoetamaior);
            TextView consumoetamenor = (TextView) this.rootView.findViewById(R.id.consumoetamenor);
            TextView consumonomee = (TextView) this.rootView.findViewById(R.id.consumonomee);
            TextView consumonomeg = (TextView) this.rootView.findViewById(R.id.consumonomeg);
            LinearLayout consumogaso = (LinearLayout) this.rootView.findViewById(R.id.consumogaso);
            LinearLayout consumoeta = (LinearLayout) this.rootView.findViewById(R.id.consumoeta);

            String aux = "";
            JSONArray versoes = result.getJSONArray("versao");
            for(int i=0;i<versoes.length();i++) aux+=versoes.getString(i)+" ";

            Picasso.with(getContext()).load(result.getString("logo")).into(image);
            nome.setText(result.getString("modelo"));
            versao.setText(aux);
            categoria.setText(result.getString("categoria"));

            consumogasomaior.setText(result.getString("menorconsumog")+"km/l");
            consumogasomenor.setText(result.getString("maiorconsumog")+"km/l");
            consumoetamaior.setText(result.getString("menorconsumoe")+"km/l");
            consumoetamenor.setText(result.getString("maiorconsumoe")+"km/l");
            consumonomeg.setText("Consumo Médio da Gasolina");
            consumonomee.setText("Consumo Médio do Etanol");

            if(result.getJSONArray("combustivel").getString(0).equals("D")) consumonomeg.setText("Consumo Médio do Diesel");

            if(result.getString("menorconsumoe").equals("0.00")) {
                consumoeta.setVisibility(View.GONE);
                consumonomee.setVisibility(View.GONE);
            }

            this.progress.dismiss();
        } catch (JSONException e) {
            this.progress.dismiss();
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        this.progress.dismiss();
        Toast.makeText(getActivity(), volleyError.toString(),Toast.LENGTH_SHORT).show();
    }
}
