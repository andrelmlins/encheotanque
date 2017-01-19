package com.universedeveloper.encheotanque.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Set;

/**
 * Created by AndreLucas on 06/10/2016.
 */
public class Usuario implements Parcelable {

    public Usuario(Set<String> data) {
    }

    protected Usuario(Parcel in) {
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
