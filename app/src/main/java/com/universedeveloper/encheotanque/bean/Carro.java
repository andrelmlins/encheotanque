package com.universedeveloper.encheotanque.bean;

/**
 * Created by AndreLucas on 17/09/2016.
 */
public class Carro {
    public String quantidade;
    public String marca;
    public String logo;
    public String modelo;
    public String categoria;
    public String maiorconsumo;
    public String menorconsumo;

    public Carro(String quantidade, String marca, String logo, String modelo, String categoria, String maiorconsumo, String menorconsumo) {
        this.quantidade = quantidade;
        this.marca = marca;
        this.logo = logo;
        this.modelo = modelo;
        this.categoria = categoria;
        this.maiorconsumo = maiorconsumo;
        this.menorconsumo = menorconsumo;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public String getMarca() {
        return marca;
    }

    public String getLogo() {
        return logo;
    }

    public String getModelo() {
        return modelo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getMaiorconsumo() {
        return maiorconsumo;
    }

    public String getMenorconsumo() {
        return menorconsumo;
    }
}
