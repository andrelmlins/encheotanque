package com.universedeveloper.encheotanque.bean;

/**
 * Created by AndreLucas on 11/09/2016.
 */
public class Cidade {
    private String nome;
    private String estadoabrev;
    private String estado;
    private String bandeira;
    private String preco;
    private String combustivel;

    public Cidade(String nome, String estadoabrev, String estado, String bandeira, String preco, String combustivel) {
        this.nome = nome;
        this.estadoabrev = estadoabrev;
        this.estado = estado;
        this.bandeira = bandeira;
        this.preco = preco;
        this.combustivel = combustivel;
    }

    public String getNome() {
        return nome;
    }

    public String getEstadoabrev() {
        return estadoabrev;
    }

    public String getEstado() {
        return estado;
    }

    public String getBandeira() {
        return bandeira;
    }

    public String getPreco() {
        return preco;
    }

    public String getCombustivel() {
        return combustivel;
    }
}
