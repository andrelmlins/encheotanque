package com.universedeveloper.encheotanque.bean;

/**
 * Created by AndreLucas on 12/09/2016.
 */
public class Preco {
    private String combustivel;
    private String preco;
    private String data;

    public Preco(String combustivel, String preco, String data) {
        this.combustivel = combustivel;
        this.preco = preco;
        this.data = data;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public String getPreco() {
        return preco;
    }

    public String getData() {
        return data;
    }
}
