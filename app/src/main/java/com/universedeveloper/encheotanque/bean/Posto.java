package com.universedeveloper.encheotanque.bean;

/**
 * Created by AndreLucas on 15/09/2016.
 */
public class Posto {
    private String logo;
    private String cidade;
    private String estado;
    private String estadoabrev;
    private String hash;
    private String preco;
    private String nome;
    private String bandeira;
    private String combustivel;

    public Posto(String logo, String cidade, String estado, String estadoabrev, String hash, String preco, String nome, String bandeira, String combustivel) {
        this.logo = logo;
        this.cidade = cidade;
        this.estado = estado;
        this.estadoabrev = estadoabrev;
        this.hash = hash;
        this.preco = preco;
        this.nome = nome;
        this.bandeira = bandeira;
        this.combustivel = combustivel;
    }

    public String getLogo() {
        return logo;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getEstadoabrev() {
        return estadoabrev;
    }

    public String getHash() {
        return hash;
    }

    public String getPreco() {
        return preco;
    }

    public String getNome() {
        return nome;
    }

    public String getBandeira() {
        return bandeira;
    }

    public String getCombustivel() {
        return combustivel;
    }
}
