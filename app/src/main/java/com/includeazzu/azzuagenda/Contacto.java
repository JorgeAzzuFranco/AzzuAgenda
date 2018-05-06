package com.includeazzu.azzuagenda;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jorge Azzu Franco on 3/5/2018.
 */

public class Contacto implements Parcelable {

    private String nombre;
    private String numero;
    private int img;
    private boolean favorito;
    public static String KEY_CONTACT = "KEY_CONTACT";

    public Contacto(String nombre, String numero, int img) {
        this.nombre = nombre;
        this.numero = numero;
        this.img = img;
        favorito = false;
    }

    public Contacto(String nombre, String numero, int img, boolean favorito) {
        this.nombre = nombre;
        this.numero = numero;
        this.img = img;
        this.favorito = favorito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    protected Contacto(Parcel in) {
        //para leer debe estar en el mismo orden que los escribo
        nombre = in.readString();
        numero = in.readString();
        img = in.readInt();
        favorito = in.readByte() != 0;
    }

    public static final Creator<Contacto> CREATOR = new Creator<Contacto>() {
        @Override
        public Contacto createFromParcel(Parcel in) {
            return new Contacto(in);
        }

        @Override
        public Contacto[] newArray(int size) {
            return new Contacto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeString(numero);
        parcel.writeInt(img);
        parcel.writeByte((byte) (favorito?1:0));
    }
}
