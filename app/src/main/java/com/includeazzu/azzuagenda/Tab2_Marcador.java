package com.includeazzu.azzuagenda;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Tab2_Marcador extends Fragment {


    public Tab2_Marcador() {
        // Required empty public constructor
    }

    //Declarando objetos de vista
    View vMarcador;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Vista a trabajar
        vMarcador = inflater.inflate(R.layout.tab2__marcador, container, false);

        return vMarcador;
    }

}
