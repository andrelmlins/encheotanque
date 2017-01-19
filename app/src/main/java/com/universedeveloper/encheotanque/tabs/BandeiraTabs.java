package com.universedeveloper.encheotanque.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.universedeveloper.encheotanque.fragment.Bandeira.BandeiraFragment_1;
import com.universedeveloper.encheotanque.fragment.Bandeira.BandeiraFragment_2;
import com.universedeveloper.encheotanque.fragment.Bandeira.BandeiraFragment_3;

/**
 * Created by AndreLucas on 17/09/2016.
 */
public class BandeiraTabs extends FragmentStatePagerAdapter {
    private String bandeira;

    public BandeiraTabs(FragmentManager fragmentManager, String bandeira) {
        super(fragmentManager);
        this.bandeira = bandeira;
    }

    public Fragment getItem(int position) {
        Bundle b = new Bundle();
        b.putString("bandeira",this.bandeira);
        switch (position) {
            case 0:
                BandeiraFragment_1 tab1 = new BandeiraFragment_1();
                tab1.setArguments(b);
                return tab1;
            case 1:
                BandeiraFragment_2 tab2 = new BandeiraFragment_2();
                tab2.setArguments(b);
                return tab2;
            case 2:
                BandeiraFragment_3 tab3 = new BandeiraFragment_3();
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
