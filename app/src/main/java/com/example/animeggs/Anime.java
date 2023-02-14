package com.example.animeggs;

public class Anime {
    private String nombre;
    private String caratula;

    private String estudio;
    private String generos;

    private String descripcion;

    public Anime(String nombre, String caratula, String estudio, String generos, String descripcion) {
        this.nombre = nombre;
        this.caratula = caratula;
        this.estudio = estudio;
        this.generos = generos;
        this.descripcion = descripcion;
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
}
