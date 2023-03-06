package com.example.animeggs;

import java.io.Serializable;

public class Episodio implements Serializable {
    private int numero;
    private String titulo;

    public Episodio(int numero, String titulo) {
        this.numero = numero;
        this.titulo = titulo;
    }

    public int getNumero() {
        return numero;
    }


    public String getTitulo() {
        return titulo;
    }

}

