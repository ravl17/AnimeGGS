package com.example.animeggs.Objetos;

import java.util.ArrayList;

public class Anime extends ArrayList<Anime> {
    private String nombre;
    private String caratula;

    private String estudio;
    private String generos;

    private String descripcion;
    private ArrayList<Episodio> episodios;

    public Anime(String nombre, String caratula, String estudio, String generos, String descripcion,ArrayList<Episodio> episodios) {
        this.nombre = nombre;
        this.caratula = caratula;
        this.estudio = estudio;
        this.generos = generos;
        this.descripcion = descripcion;
        this.episodios = episodios;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCaratula() {
        return caratula;
    }

    public String getEstudio() {return estudio;}

    public String getGeneros() {return generos;}

    public String getDescripcion() {return descripcion;}
    public ArrayList<Episodio> getEpisodios() {return episodios;}
}
