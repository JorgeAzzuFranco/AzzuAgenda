package com.includeazzu.azzuagenda;


import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import java.util.ArrayList;

public class Tab1_Contactos extends Fragment {

    public Tab1_Contactos() {}

    //Declaracion de objetos
    View vContactos;
    RecyclerView rv;
    LinearLayoutManager llm;

    //Declarando objetos para mostrar los contactos
    ContactoAdapter cAdapter;
    ArrayList<Contacto> contactos = new ArrayList<>();
    ArrayList<Contacto> favoritos = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS},1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Añadiendo contactos

        if (savedInstanceState != null){

            //Vista a trabajar
            vContactos = inflater.inflate(R.layout.tab1__contactos, container, false);

            //Preparando objetos rv, llm  y contactos para el recyclerView
            rv = vContactos.findViewById(R.id.rv);
            rv.setHasFixedSize(true);
            llm = new LinearLayoutManager(getContext());
            rv.setLayoutManager(llm);

            contactos = savedInstanceState.getParcelableArrayList("cont");

            int i = 0;
            for (Contacto cont : contactos){
                if (cont.isFavorito()){
                    favoritos.add(cont);
                    Log.d("anadiendo", favoritos.get(i).getNombre());
                    i++;
                }
            }

            Log.d("ArregloFav", "Arreglo contactos tiene: "+contactos.size());
            Log.d("ArregloFav", "Arreglo favorito tiene: "+favoritos.size());
            cAdapter = new ContactoAdapter(contactos);
            rv.setAdapter(cAdapter);
            return vContactos;
        }
        else{

            //Vista a trabajar
            vContactos = inflater.inflate(R.layout.tab1__contactos, container, false);

            //Preparando objetos rv, llm  y contactos para el recyclerView
            rv = vContactos.findViewById(R.id.rv);
            rv.setHasFixedSize(true);
            llm = new LinearLayoutManager(getContext());
            rv.setLayoutManager(llm);

            addContacts();
            cAdapter = new ContactoAdapter(contactos);
            rv.setAdapter(cAdapter);
            return vContactos;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("cont", contactos);
        savedInstanceState.putParcelableArrayList("fav", favoritos);
    }

    //Metodo para obtener los contactos
    private void addContacts() {
        String help = "";
        try {
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                if(!(help.equals(name))){
                contactos.add(new Contacto(name, phoneNumber, R.drawable.persona));
                }
                help = name;
            }
            phones.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Pidiendo permiso al sistema para acceder a los contactos (ANDROID MARSHMALLOW Y SUPERIORES)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults){
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addContacts();
                cAdapter = new ContactoAdapter(contactos);
                rv.setAdapter(cAdapter);
                Toast.makeText(getContext(), "Reinicie la aplicacion", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public ContentResolver getContentResolver() {
        ContentResolver contactProvider = super.getContext().getContentResolver();
        return contactProvider;
    }

    public void addFavoritos(Contacto contacto){
        favoritos.add(contacto);
        Log.d("Fav", "Favoritos tiene: "+favoritos.size());
    }

    public void borrarFavorito(String nombre) {
        int counter=0;
        for (Contacto cont : favoritos){
            Log.d("Contador For", counter+"");
            if (cont.getNombre()== nombre)
                break;

            counter++;
        }
        Log.d("Contador", counter+"");
        try {
            favoritos.remove(counter);
        }catch (Exception e){
            Log.d("ERROR", e.toString()+"");
        }
            Log.d("Fav", "Favoritos tiene: "+favoritos.size());
    }
}
