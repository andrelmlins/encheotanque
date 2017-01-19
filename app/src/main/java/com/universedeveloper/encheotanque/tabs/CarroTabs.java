package com.universedeveloper.encheotanque.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.universedeveloper.encheotanque.fragment.Carro.CarroFragment_1;
import com.universedeveloper.encheotanque.fragment.Carro.CarroFragment_2;

/**
 * Created by AndreLucas on 17/09/2016.
 */
public class CarroTabs extends FragmentStatePagerAdapter {
    private String carro;

    public CarroTabs(FragmentManager fragmentManager, String carro) {
        super(fragmentManager);
        this.carro = carro;
    }

    public Fragment getItem(int position) {
        Bundle b = new Bundle();
        b.putString("carro",this.carro);
        switch (position) {
            case 0:
                CarroFragment_1 tab1 = new CarroFragment_1();
                tab1.setArguments(b);
                return tab1;
            case 1:
                CarroFragment_2 tab2 = new CarroFragment_2();
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
