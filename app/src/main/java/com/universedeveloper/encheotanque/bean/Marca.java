package com.universedeveloper.encheotanque.bean;

/**
 * Created by AndreLucas on 12/09/2016.
 */
public class Marca {
    private String nome;
    private String logo;
    private String quantidade;

    public Marca(String nome, String logo, String quantidade) {
        this.nome = nome;
        this.logo = logo;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public String getLogo() {
        return logo;
    }

    public String getQuantidade() {
        return quantidade;
    }
}
