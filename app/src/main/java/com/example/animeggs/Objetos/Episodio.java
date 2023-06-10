package com.example.animeggs.Objetos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Episodio implements Serializable, Parcelable {
    private String ep;

    public Episodio() {
    }

    public Episodio(String ep) {
        this.ep = ep;
    }

    protected Episodio(Parcel in) {
        ep = in.readString();
    }

    public static final Creator<Episodio> CREATOR = new Creator<Episodio>() {
        @Override
        public Episodio createFromParcel(Parcel in) {
            return new Episodio(in);
        }

        @Override
        public Episodio[] newArray(int size) {
            return new Episodio[size];
        }
    };

    public String getEp() {
        return ep;
    }


    public void setEp(String ep) {
        this.ep = ep;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(ep);
    }
}

