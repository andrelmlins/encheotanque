package com.universedeveloper.encheotanque.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.bean.Cidade;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by AndreLucas on 11/09/2016.
 */
public class AdapterCidades extends BaseAdapter {
    private List<Cidade> cidades;
    private ArrayList<Cidade> arraylist;
    private Activity act;

    public AdapterCidades(List<Cidade> cidades, Activity act) {
        this.cidades = cidades;
        this.act = act;
        this.arraylist = new ArrayList<Cidade>();
        this.arraylist.addAll(this.cidades);
    }

    @Override
    public int getCount() {
        return cidades.size();
    }

    @Override
    public Object getItem(int position) {
        return cidades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.itemfoto, parent, false);
        Cidade cidade = cidades.get(position);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        title.setText(cidade.getNome() + " - " + cidade.getEstadoabrev());
        subtitle.setText("Preço Médio do "+cidade.getCombustivel()+": " + cidade.getPreco());
        Picasso.with(view.getContext()).load(cidade.getBandeira()).into(image);

        return view;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        cidades.clear();
        if (charText.length() == 0) {
            cidades.addAll(arraylist);
        } else {
            for (Cidade c : arraylist) {
                if (c.getNome().toLowerCase(Locale.getDefault()).contains(charText)
                        || c.getEstado().toLowerCase(Locale.getDefault()).contains(charText)
                        || c.getEstadoabrev().toLowerCase(Locale.getDefault()).contains(charText)){
                    cidades.add(c);
                }
            }
        }
        notifyDataSetChanged();
    }
}
