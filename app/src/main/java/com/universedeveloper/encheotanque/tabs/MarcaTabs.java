package com.universedeveloper.encheotanque.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.universedeveloper.encheotanque.fragment.Marca.MarcaFragment_1;
import com.universedeveloper.encheotanque.fragment.Marca.MarcaFragment_2;

/**
 * Created by AndreLucas on 17/09/2016.
 */
public class MarcaTabs extends FragmentStatePagerAdapter {

    private String marca;

    public MarcaTabs(FragmentManager fragmentManager, String marca) {
        super(fragmentManager);
        this.marca = marca;
    }

    public Fragment getItem(int position) {
        Bundle b = new Bundle();
        b.putString("marca",this.marca);
        switch (position) {
            case 0:
                MarcaFragment_1 tab1 = new MarcaFragment_1();
                tab1.setArguments(b);
                return tab1;
            case 1:
                MarcaFragment_2 tab2 = new MarcaFragment_2();
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
