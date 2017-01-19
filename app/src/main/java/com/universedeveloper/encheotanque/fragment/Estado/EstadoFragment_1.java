package com.universedeveloper.encheotanque.fragment.Estado;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

/**
 * Created by AndreLucas on 08/09/2016.
 */
public class EstadoFragment_1 extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    private View rootView;
    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.search);
        mSearchMenuItem.setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        this.rootView = inflater.inflate(R.layout.estado_fragment_1, container, false);
        this.progress = ProgressDialog.show(getActivity(), "","Carregando detalhes do estado...", true);
        Requests r = Requests.getInstance(getActivity());
        r.getObject("http://apifuel.universedeveloper.com/estado.php?estado="+getArguments().getString("estado"),this,this);
        return this.rootView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void onResponse(JSONObject response) {
        try {
            JSONObject result = response.getJSONObject("estado");
            getActivity().setTitle(result.getString("estado"));
            JSONArray precos = result.getJSONArray("precos");
            TextView quantidade = (TextView) this.rootView.findViewById(R.id.quantidade);
            ImageView image = (ImageView) this.rootView.findViewById(R.id.img_estado);
            quantidade.setText(result.getString("quantidade")+" postos");
            Picasso.with(getContext()).load(result.getString("bandeira")).into(image);

            LinearLayout l = (LinearLayout) this.rootView.findViewById(R.id.listfuel);
            LinearLayout lchild = (LinearLayout) l.findViewById(R.id.listele);
            TextView t = (TextView) lchild.findViewById(R.id.preco);
            TextView t1 = (TextView) lchild.findViewById(R.id.fuel);
            l.removeAllViews();

            for(int i=0;i<precos.length();i++){
                JSONObject preco = precos.getJSONObject(i);
                LinearLayout lchild_aux = new LinearLayout(getContext());
                TextView t_aux = new TextView(getContext());
                TextView t1_aux = new TextView(getContext());

                lchild_aux.setLayoutParams(lchild.getLayoutParams());
                lchild_aux.setOrientation(lchild.getOrientation());
                lchild_aux.setWeightSum(0.2f);
                lchild_aux.setPadding(5,5,5,5);

                t_aux.setLayoutParams(t.getLayoutParams());
                t_aux.setTextSize(14);
                t_aux.setTextColor(t.getTextColors());
                t_aux.setBackground(t.getBackground());
                t_aux.setTextAlignment(t.getTextAlignment());
                t_aux.setPadding(0,20,0,20);

                t1_aux.setLayoutParams(t1.getLayoutParams());
                t1_aux.setTextAlignment(t1.getTextAlignment());
                t1_aux.setTextSize(10);

                t_aux.setText(preco.getString("precomedio"));
                t1_aux.setText(preco.getString("combustivel"));

                lchild_aux.addView(t_aux);
                lchild_aux.addView(t1_aux);
                l.addView(lchild_aux);
            }

            JSONObject top = response.getJSONObject("posicao");
            ImageView medalgasolina = (ImageView) this.rootView.findViewById(R.id.medalgasolina);
            ImageView medaletanol = (ImageView) this.rootView.findViewById(R.id.medaletanol);
            ImageView medaldiesel = (ImageView) this.rootView.findViewById(R.id.medaldiesel);
            ImageView medalgnv = (ImageView) this.rootView.findViewById(R.id.medalgnv);
            ImageView medaldiesels10 = (ImageView) this.rootView.findViewById(R.id.medaldiesels10);

            TextView textmedalgasolina = (TextView) this.rootView.findViewById(R.id.textmedalgasolina);
            TextView textmedaldiesel = (TextView) this.rootView.findViewById(R.id.textmedaldiesel);
            TextView textmedaletanol = (TextView) this.rootView.findViewById(R.id.textmedaletanol);
            TextView textmedaldiesels10 = (TextView) this.rootView.findViewById(R.id.textmedaldiesels10);
            TextView textmedalgnv = (TextView) this.rootView.findViewById(R.id.textmedalgnv);

            LinearLayout medalgasol = (LinearLayout) this.rootView.findViewById(R.id.medalgasol);
            LinearLayout medaleta = (LinearLayout) this.rootView.findViewById(R.id.medaleta);
            LinearLayout medaldie = (LinearLayout) this.rootView.findViewById(R.id.medaldie);
            LinearLayout medals10 = (LinearLayout) this.rootView.findViewById(R.id.medals10);
            LinearLayout medalgn = (LinearLayout) this.rootView.findViewById(R.id.medalgn);

            textmedalgasolina.setText(top.getString("gasolina"));
            textmedaldiesel.setText(top.getString("diesel"));
            textmedaletanol.setText(top.getString("etanol"));
            textmedaldiesels10.setText(top.getString("diesels10"));
            textmedalgnv.setText(top.getString("gnv"));

            if(top.getInt("gasolina")==1) medalgasolina.setImageResource(R.drawable.gold_medal);
            else if(top.getInt("gasolina")==2) medalgasolina.setImageResource(R.drawable.silver_medal);
            else if(top.getInt("gasolina")==3) medalgasolina.setImageResource(R.drawable.bronze_medal);
            else if(top.getInt("gasolina")==0) medalgasol.setVisibility(View.GONE);
            else medalgasolina.setImageResource(R.drawable.bad_medal);

            if(top.getInt("etanol")==1) medaletanol.setImageResource(R.drawable.gold_medal);
            else if(top.getInt("etanol")==2) medaletanol.setImageResource(R.drawable.silver_medal);
            else if(top.getInt("etanol")==3) medaletanol.setImageResource(R.drawable.bronze_medal);
            else if(top.getInt("etanol")==0) medaleta.setVisibility(View.GONE);
            else medaletanol.setImageResource(R.drawable.bad_medal);

            if(top.getInt("diesel")==1) medaldiesel.setImageResource(R.drawable.gold_medal);
            else if(top.getInt("diesel")==2) medaldiesel.setImageResource(R.drawable.silver_medal);
            else if(top.getInt("diesel")==3) medaldiesel.setImageResource(R.drawable.bronze_medal);
            else if(top.getInt("diesel")==0) medaldie.setVisibility(View.GONE);
            else medaldiesel.setImageResource(R.drawable.bad_medal);

            if(top.getInt("diesels10")==1) medaldiesels10.setImageResource(R.drawable.gold_medal);
            else if(top.getInt("diesels10")==2) medaldiesels10.setImageResource(R.drawable.silver_medal);
            else if(top.getInt("diesels10")==3) medaldiesels10.setImageResource(R.drawable.bronze_medal);
            else if(top.getInt("diesels10")==0) medals10.setVisibility(View.GONE);
            else medaldiesels10.setImageResource(R.drawable.bad_medal);

            if(top.getInt("gnv")==1) medalgnv.setImageResource(R.drawable.gold_medal);
            else if(top.getInt("gnv")==2) medalgnv.setImageResource(R.drawable.silver_medal);
            else if(top.getInt("gnv")==3) medalgnv.setImageResource(R.drawable.bronze_medal);
            else if(top.getInt("gnv")==0) medalgn.setVisibility(View.GONE);
            else medalgnv.setImageResource(R.drawable.bad_medal);

            this.progress.dismiss();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Toast.makeText(getActivity(), volleyError.toString(), Toast.LENGTH_SHORT).show();
    }
}
