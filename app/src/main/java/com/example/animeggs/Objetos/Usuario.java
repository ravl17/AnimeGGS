package com.example.animeggs.Objetos;


import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.HashMap;

public class Usuario {
    private String nick;
    private String correo;
    @PropertyName("siguiendo") // Specify the actual field name in the database
    private ArrayList<SiguiendoItem> siguiendo;
    private ArrayList<VisualizacionItem> visualizaciones;

    public Usuario() {

    }


    public Usuario(String nick, String correo, ArrayList<SiguiendoItem> siguiendo, ArrayList<VisualizacionItem> visualizaciones) {
        this.nick = nick;
        this.correo = correo;
        this.siguiendo = siguiendo;
        this.visualizaciones = visualizaciones;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public ArrayList<SiguiendoItem> getSiguiendo() {
        return siguiendo;
    }

    public void setSiguiendo(ArrayList<SiguiendoItem> siguiendo) {
        this.siguiendo = siguiendo;
    }
    public ArrayList<VisualizacionItem> getVisualizaciones() {
        return visualizaciones;
    }

    public void setVisualizaciones(ArrayList<VisualizacionItem> visualizaciones) {
        this.visualizaciones = visualizaciones;
    }
    public static class SiguiendoItem {
        private String serie;

        public String getSerie() {
            return serie;
        }

        public void setSerie(String serie) {
            this.serie = serie;
        }
    }
    public static class VisualizacionItem {
        private String epVistos;
        private String serie;

        public String getEpVistos() {
            return epVistos;
        }

        public void setEpVistos(String epVistos) {
            this.epVistos = epVistos;
        }

        public String getSerie() {
            return serie;
        }

        public void setSerie(String serie) {
            this.serie = serie;
        }
    }
}
