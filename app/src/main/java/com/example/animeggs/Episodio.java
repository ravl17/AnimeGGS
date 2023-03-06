package com.example.animeggs;

import java.io.Serializable;

public class Episodio implements Serializable {
    private int numero;
    private String titulo;
    private String enlaceEpisodio;

    public Episodio(int numero, String titulo, String enlaceEpisodio) {
        this.numero = numero;
        this.titulo = titulo;
        this.enlaceEpisodio = enlaceEpisodio;
    }

    public int getNumero() {
        return numero;
    }


    public String getTitulo() {
        return titulo;
    }
    public String getEnlaceEpisodio() {
        return enlaceEpisodio;
    }

}

