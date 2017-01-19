package com.universedeveloper.encheotanque.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.universedeveloper.encheotanque.fragment.Cidade.CidadeFragment_1;
import com.universedeveloper.encheotanque.fragment.Cidade.CidadeFragment_2;

/**
 * Created by AndreLucas on 16/09/2016.
 */
public class CidadeTabs extends FragmentStatePagerAdapter {
    private String cidade;
    private String estado;

    public CidadeTabs(FragmentManager fragmentManager, String cidade, String estado) {
        super(fragmentManager);
        this.cidade = cidade;
        this.estado = estado;
    }

    public Fragment getItem(int position) {
        Bundle b = new Bundle();
        b.putString("cidade",this.cidade);
        b.putString("estado",this.estado);
        switch (position) {
            case 0:
                CidadeFragment_1 tab1 = new CidadeFragment_1();
                tab1.setArguments(b);
                return tab1;
            case 1:
                CidadeFragment_2 tab2 = new CidadeFragment_2();
                tab2.setArguments(b);
                return tab2;
            default:
                return null;
        }
    }

    public int getCount() {
        return 2;
    }
}
