package com.universedeveloper.encheotanque.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.universedeveloper.encheotanque.fragment.Estado.EstadoFragment_1;
import com.universedeveloper.encheotanque.fragment.Estado.EstadoFragment_2;
import com.universedeveloper.encheotanque.fragment.Estado.EstadoFragment_3;

/**
 * Created by AndreLucas on 08/09/2016.
 */
public class EstadoTabs extends FragmentStatePagerAdapter {

    private String estado;

    public EstadoTabs(FragmentManager fragmentManager, String estado) {
        super(fragmentManager);
        this.estado = estado;
    }

    public Fragment getItem(int position) {
        Bundle b = new Bundle();
        b.putString("estado",this.estado);
        switch (position) {
            case 0:
                EstadoFragment_1 tab1 = new EstadoFragment_1();
                tab1.setArguments(b);
                return tab1;
            case 1:
                EstadoFragment_2 tab2 = new EstadoFragment_2();
                tab2.setArguments(b);
                return tab2;
            case 2:
                EstadoFragment_3 tab3 = new EstadoFragment_3();
                tab3.setArguments(b);
                return tab3;
            default:
                return null;
        }
    }

    public int getCount() {
        return 3;
    }
}
