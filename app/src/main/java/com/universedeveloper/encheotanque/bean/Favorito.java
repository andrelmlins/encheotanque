package com.universedeveloper.encheotanque.bean;

/**
 * Created by AndreLucas on 12/11/2016.
 */
public class Favorito {
    private String tipo;
    private String atributo;
    private String atributo1;

    public Favorito(String tipo, String atributo, String atributo1) {
        this.tipo = tipo;
        this.atributo = atributo;
        this.atributo1 = atributo1;
    }

    public Favorito(String pref) {
        String[] aux = pref.split("[#]");
        this.tipo = aux[0];
        this.atributo = aux[1];
        this.atributo1 = aux[2];
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getAtributo1() {
        return atributo1;
    }

    public void setAtributo1(String atributo1) {
        this.atributo1 = atributo1;
    }

    @Override
    public String toString() {
        return this.tipo+"#"+this.atributo+"#"+this.atributo1;
    }
}
