package com.example.animeggs.Objetos;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class Anime extends ArrayList<Anime> {
    private String caratula;
    private ArrayList<Comentario> comentarios;
    private String descripcion;
    private ArrayList<Episodio> episodios;
    private String estado;
    private String estudio;
    private String generos;
    private String img;
    private String nombre;
    private String tipo;


    private ArrayList<String> siguiendo; //Provisional hasta que se cambie en base de datos


    public ArrayList<String> getSiguiendo() {
        return siguiendo;
    }

    public void setSiguiendo(ArrayList<String> siguiendo) {
        this.siguiendo = siguiendo;
    }

    public Anime() {
    }

    public Anime(String caratula, ArrayList<Comentario> comentarios, String descripcion, ArrayList<Episodio> episodios, String estado, String estudio, String generos, String img, String nombre, String tipo, ArrayList<String> siguiendo) {
        this.caratula = caratula;
        this.comentarios = comentarios;
        this.descripcion = descripcion;
        this.episodios = episodios;
        this.estado = estado;
        this.estudio = estudio;
        this.generos = generos;
        this.img = img;
        this.nombre = nombre;
        this.tipo = tipo;
        this.siguiendo = siguiendo;
    }

    public String getCaratula() {
        return caratula;
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ArrayList<Episodio> getEpisodios() {
        return episodios;
    }

    public String getEstado() {
        return estado;
    }

    public String getEstudio() {
        return estudio;
    }

    public String getGeneros() {
        return generos;
    }

    public String getImg() {
        return img;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setCaratula(String caratula) {
        this.caratula = caratula;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEpisodios(ArrayList<Episodio> episodios) {
        this.episodios = episodios;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public void setGeneros(String generos) {
        this.generos = generos;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
