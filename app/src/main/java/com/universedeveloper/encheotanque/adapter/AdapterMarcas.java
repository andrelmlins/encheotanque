package com.universedeveloper.encheotanque.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.bean.Marca;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by AndreLucas on 12/09/2016.
 */
public class AdapterMarcas extends BaseAdapter {
    private List<Marca> marcas;
    private ArrayList<Marca> arraylist;
    private Activity act;

    public AdapterMarcas(List<Marca> marcas, Activity act) {
        this.marcas = marcas;
        this.act = act;
        this.arraylist = new ArrayList<Marca>();
        this.arraylist.addAll(this.marcas);
    }

    @Override
    public int getCount() {
        return marcas.size();
    }

    @Override
    public Object getItem(int position) {
        return marcas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.itemthumb, parent, false);
        Marca marca = marcas.get(position);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        title.setText(marca.getNome());
        subtitle.setText(marca.getQuantidade()+" modelos");
        Picasso.with(view.getContext()).load(marca.getLogo()).into(image);

        return view;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        marcas.clear();
        if (charText.length() == 0) {
            marcas.addAll(arraylist);
        } else {
            for (Marca m : arraylist) {
                if (m.getNome().toLowerCase(Locale.getDefault()).contains(charText)){
                    marcas.add(m);
                }
            }
        }
        notifyDataSetChanged();
    }
}
