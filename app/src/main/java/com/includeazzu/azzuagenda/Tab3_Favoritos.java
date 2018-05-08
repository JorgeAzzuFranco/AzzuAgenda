package com.includeazzu.azzuagenda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class Tab3_Favoritos extends Fragment {

    public Tab3_Favoritos() {
        // Required empty public constructor
    }

    View vFavoritos;
    RecyclerView rv;
    LinearLayoutManager llm;
    ContactoAdapter cAdapter;
    ArrayList<Contacto> favoritos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vFavoritos = inflater.inflate(R.layout.tab3__favoritos, container, false);

        rv = vFavoritos.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        favoritos = new ArrayList<>();

        if(getArguments() != null){
            favoritos = (ArrayList<Contacto>) getArguments().getSerializable("ctc");
            cAdapter = new ContactoAdapter(favoritos);
            rv.setAdapter(cAdapter);
            Toast.makeText(getContext(), "Recibo favoritos", Toast.LENGTH_SHORT);
        }
        else{
            favoritos.add(new Contacto("asd","123", R.drawable.persona));
            cAdapter = new ContactoAdapter(favoritos);
            rv.setAdapter(cAdapter);
            Log.d("No Fav", "No se obtuvieron favoritos");
        }

        return vFavoritos;
    }

}
