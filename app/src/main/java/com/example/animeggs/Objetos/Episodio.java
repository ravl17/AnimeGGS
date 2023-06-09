package com.example.animeggs.Objetos;

import java.io.Serializable;
import java.util.ArrayList;

public class Episodio implements Serializable {
    private String ep;

    public Episodio() {
    }

    public Episodio(String ep) {
        this.ep = ep;
    }

    public String getEp() {
        return ep;
    }


    public void setEp(String ep) {
        this.ep = ep;
    }

}

