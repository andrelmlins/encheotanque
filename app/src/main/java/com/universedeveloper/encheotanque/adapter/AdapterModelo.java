package com.universedeveloper.encheotanque.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.bean.Carro;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by AndreLucas on 25/09/2016.
 */
public class AdapterModelo extends BaseAdapter {
    private List<Carro> carros;
    private ArrayList<Carro> arraylist;
    private Activity act;

    public AdapterModelo(List<Carro> carros, Activity act) {
        this.carros = carros;
        this.act = act;
        this.arraylist = new ArrayList<Carro>();
        this.arraylist.addAll(this.carros);
    }

    @Override
    public int getCount() {
        return carros.size();
    }

    @Override
    public Object getItem(int position) {
        return carros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.item_circle, parent, false);
        Carro carro = carros.get(position);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
        TextView letra = (TextView) view.findViewById(R.id.letra);
        TextView subtitle1 = (TextView) view.findViewById(R.id.subtitle1);

        title.setText(carro.getModelo()+" - "+carro.getQuantidade());
        subtitle.setText("Combustivel: "+carro.getMarca());
        letra.setText(carro.getLogo());
        subtitle1.setText("Consumo entre "+carro.getMenorconsumo()+" a "+carro.getMaiorconsumo()+"km/l");

        return view;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        carros.clear();
        if (charText.length() == 0) {
            carros.addAll(arraylist);
        } else {
            for (Carro c : arraylist) {
                if (c.getModelo().toLowerCase(Locale.getDefault()).contains(charText)
                        || c.getCategoria().toLowerCase(Locale.getDefault()).contains(charText)
                        || c.getMarca().toLowerCase(Locale.getDefault()).contains(charText)){
                    carros.add(c);
                }
            }
        }
        notifyDataSetChanged();
    }
}
