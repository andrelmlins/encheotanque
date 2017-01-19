package com.universedeveloper.encheotanque.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.universedeveloper.encheotanque.fragment.Favorito.FavBandeiraFragment;
import com.universedeveloper.encheotanque.fragment.Favorito.FavCidadeFragment;
import com.universedeveloper.encheotanque.fragment.Favorito.FavEstadoFragment;
import com.universedeveloper.encheotanque.fragment.Favorito.FavPostoFragment;


/**
 * Created by AndreLucas on 13/11/2016.
 */
public class FavoritoTabs extends FragmentStatePagerAdapter {
    public FavoritoTabs(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FavPostoFragment tab1 = new FavPostoFragment();
                return tab1;
            case 1:
                FavEstadoFragment tab2 = new FavEstadoFragment();
                return tab2;
            case 2:
                FavCidadeFragment tab3 = new FavCidadeFragment();
                return tab3;
            case 3:
                FavBandeiraFragment tab4 = new FavBandeiraFragment();
                return tab4;
            default:
                return null;
        }
    }

    public int getCount() {
        return 4;
    }
}
