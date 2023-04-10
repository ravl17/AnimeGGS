package com.example.animeggs.Objetos;

import java.io.Serializable;
import java.util.ArrayList;

public class Episodio implements Serializable {
    private String ep;
    private ArrayList<String> usuariosVisto;

    public Episodio() {
    }

    public Episodio(String ep, ArrayList<String> usuariosVisto) {
        this.ep = ep;
        this.usuariosVisto = usuariosVisto;
    }

    public String getEp() {
        return ep;
    }

    public ArrayList<String> getUsuariosVisto() {
        return usuariosVisto;
    }

    public void setEp(String ep) {
        this.ep = ep;
    }

    public void setUsuariosVisto(ArrayList<String> usuariosVisto) {
        this.usuariosVisto = usuariosVisto;
    }
}

