package com.universedeveloper.encheotanque.bean;

/**
 * Created by AndreLucas on 04/09/2016.
 */
public class Estado {
    private String bandeira;
    private String estado;
    private String estadoabrev;
    private String preco;

    public Estado(String bandeira, String estado, String estadoabrev, String preco){
        this.bandeira = bandeira;
        this.estado = estado;
        this.estadoabrev = estadoabrev;
        this.preco = preco;
    }

    public String getEstado() {
        return this.estado;
    }

    public String getBandeira() {
        return this.bandeira;
    }

    public String getEstadoabrev() {
        return this.estadoabrev;
    }

    public String getPreco() {
        return this.preco;
    }
}
