package com.universedeveloper.encheotanque.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.universedeveloper.encheotanque.R;

/**
 * Created by AndreLucas on 25/09/2016.
 */
public class SobreFragment extends Fragment {

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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View rootView = inflater.inflate(R.layout.sobre_fragment, container, false);
        getActivity().setTitle("Sobre");
        return rootView;
    }
}
