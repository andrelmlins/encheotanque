package com.universedeveloper.encheotanque.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.universedeveloper.encheotanque.R;
import com.universedeveloper.encheotanque.bean.Bandeira;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by AndreLucas on 11/09/2016.
 */
public class AdapterBandeiras extends BaseAdapter {
    private List<Bandeira> bandeiras;
    private ArrayList<Bandeira> arraylist;
    private Activity act;

    public AdapterBandeiras(List<Bandeira> bandeiras, Activity act) {
        this.bandeiras = bandeiras;
        this.act = act;
        this.arraylist = new ArrayList<Bandeira>();
        this.arraylist.addAll(this.bandeiras);
    }

    @Override
    public int getCount() {
        return bandeiras.size();
    }

    @Override
    public Object getItem(int position) {
        return bandeiras.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.itemthumb, parent, false);
        Bandeira bandeira = bandeiras.get(position);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
        ImageView image = (ImageView) view.findViewById(R.id.image);

        title.setText(bandeira.getNome());
        subtitle.setText(bandeira.getQuantidade()+" postos");
        Picasso.with(view.getContext()).load(bandeira.getLogo()).into(image);

        return view;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        bandeiras.clear();
        if (charText.length() == 0) {
            bandeiras.addAll(arraylist);
        } else {
            for (Bandeira b : arraylist) {
                if (b.getNome().toLowerCase(Locale.getDefault()).contains(charText)){
                    bandeiras.add(b);
                }
            }
        }
        notifyDataSetChanged();
    }
}
