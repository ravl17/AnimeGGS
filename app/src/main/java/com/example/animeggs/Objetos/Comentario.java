package com.example.animeggs.Objetos;

public class Comentario {
    private String comentario;
    private String usuario;

    public Comentario() {
    }

    public Comentario(String comentario, String usuario) {
        this.comentario = comentario;
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
