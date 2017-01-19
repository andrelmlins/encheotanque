package com.universedeveloper.encheotanque.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.bean.Posto;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by AndreLucas on 15/09/2016.
 */
public class AdapterPostos extends BaseAdapter {
    private List<Posto> postos;
    private ArrayList<Posto> arraylist;
    private Activity act;

    public AdapterPostos(List<Posto> postos, Activity act) {
        this.postos = postos;
        this.act = act;
        this.arraylist = new ArrayList<Posto>();
        this.arraylist.addAll(this.postos);
    }

    @Override
    public int getCount() {
        return this.postos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.postos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.itemthumb_1, parent, false);
        Posto posto = this.postos.get(position);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
        TextView subtitle1 = (TextView) view.findViewById(R.id.subtitle1);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        title.setText(posto.getNome());
        subtitle.setText(posto.getCidade()+" - "+posto.getEstadoabrev());
        subtitle1.setText("Preço do "+posto.getCombustivel()+": "+posto.getPreco());
        Picasso.with(view.getContext()).load(posto.getLogo()).into(image);

        return view;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        postos.clear();
        if (charText.length() == 0) {
            postos.addAll(arraylist);
        } else {
            for (Posto p : arraylist) {
                if (p.getNome().toLowerCase(Locale.getDefault()).contains(charText)
                        || p.getBandeira().toLowerCase(Locale.getDefault()).contains(charText)){
                    postos.add(p);
                }
            }
        }
        notifyDataSetChanged();
    }
}
