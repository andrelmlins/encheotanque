package com.universedeveloper.encheotanque.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.bean.Estado;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by AndreLucas on 04/09/2016.
 */
public class AdapterEstados extends BaseAdapter{
    private List<Estado> estados;
    private ArrayList<Estado> arraylist;
    private Activity act;

    public AdapterEstados(List<Estado> estados, Activity act) {
        this.estados = estados;
        this.act = act;
        this.arraylist = new ArrayList<Estado>();
        this.arraylist.addAll(this.estados);
    }

    @Override
    public int getCount() {
        return estados.size();
    }

    @Override
    public Object getItem(int position) {
        return estados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.itemfoto, parent, false);
        Estado estado = estados.get(position);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        title.setText(estado.getEstado());
        subtitle.setText("Preço Médio da Gasolina: " + estado.getPreco());
        Picasso.with(view.getContext()).load(estado.getBandeira()).into(image);

        return view;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        estados.clear();
        if (charText.length() == 0) {
            estados.addAll(arraylist);
        } else {
            for (Estado e : arraylist) {
                if (e.getEstado().toLowerCase(Locale.getDefault()).contains(charText)){
                    estados.add(e);
                }
            }
        }
        notifyDataSetChanged();
    }
}
