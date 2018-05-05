package com.includeazzu.azzuagenda;

/**
 * Created by Jorge Azzu Franco on 3/5/2018.
 */

public class Contacto {

    private String nombre;
    private String numero;
    private int img;
    private boolean favorito;

    public Contacto(String nombre, String numero, int img) {
        this.nombre = nombre;
        this.numero = numero;
        this.img = img;
        favorito = false;
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
}
