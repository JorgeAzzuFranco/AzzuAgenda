package com.includeazzu.azzuagenda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

public class Tab1_Contactos extends Fragment {

    public Tab1_Contactos() {}

    //Declaracion de objetos
    View vContactos;
    RecyclerView rv;
    LinearLayoutManager llm;

    //Declarando objetos para mostrar los contactos
    ContactoAdapter cAdapter;
    ArrayList<Contacto> contactos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Vista a trabajar
        vContactos = inflater.inflate(R.layout.tab1__contactos, container, false);
        //Preparando objetos rv, llm  y contactos para el recyclerView
        rv = vContactos.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        //Añadiendo contactos de prueba
        contactos = new ArrayList<>();
        addSampleContacts();

        cAdapter = new ContactoAdapter(contactos);
        rv.setAdapter(cAdapter);

        return vContactos;
    }

    private void addSampleContacts() {
        Log.d("Anade", "Anadiendo contactos");
        contactos.add(new Contacto("Jorgito", "12312321",R.drawable.persona));
        contactos.add(new Contacto("Jorgito", "12312321", R.drawable.persona));
        contactos.add(new Contacto("Jorgito", "12312321", R.drawable.persona));
        contactos.add(new Contacto("Jorgito", "12312321", R.drawable.persona));
    }
}