package com.universedeveloper.encheotanque.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.universedeveloper.encheotanque.bean.Favorito;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by AndreLucas on 12/11/2016.
 */
public class Preferencias {
    private SharedPreferences preferencias;
    private SharedPreferences usuario;
    private Set<String> pref;
    private static Preferencias p;

    public static Preferencias getInstance(Context context) {
        if (p == null) {
            p = new Preferencias(context);
        }

        return p;
    }

    private Preferencias(Context context){
        this.usuario = context.getSharedPreferences("usuario", 0);
        this.preferencias = context.getSharedPreferences("preferencias", 0);
        this.pref = this.preferencias.getStringSet("lista",new HashSet<String>());
    }

    public void add(Favorito p){
        pref.add(p.toString());
        SharedPreferences.Editor edit = this.preferencias.edit();
        edit.clear();
        edit.putStringSet("lista",this.pref);
        edit.commit();
    }

    public boolean isPref(Favorito pref){
        for(String p : this.pref){
            if(p.equals(pref.toString())) return true;
        }
        return false;
    }

    public void remove(Favorito p){
        pref.remove(p.toString());
        SharedPreferences.Editor edit = this.preferencias.edit();
        edit.clear();
        edit.putStringSet("lista",this.pref);
        edit.commit();
    }

    public JSONObject get(Favorito p) throws JSONException {
        JSONObject aux = new JSONObject();
        aux.put("tipo",p.getTipo());
        aux.put("usuario",this.usuario.getString("id", ""));
        aux.put("atributo",p.getAtributo());
        aux.put("atributo1",p.getAtributo1());
        return aux;
    }
}
