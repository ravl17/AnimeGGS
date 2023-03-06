package com.example.animeggs;

import java.io.Serializable;

public class Episodio implements Serializable {
    private int number;
    private String title;

    public Episodio(int number, String title) {
        this.number = number;
        this.title = title;
    }

    public int getNumber() {
        return number;
    }


    public String getTitle() {
        return title;
    }

}

