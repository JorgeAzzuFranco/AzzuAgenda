package com.includeazzu.azzuagenda;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.security.Key;
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
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Vista a trabajar
        vContactos = inflater.inflate(R.layout.tab1__contactos, container, false);

        //Preparando objetos rv, llm  y contactos para el recyclerView
        rv = vContactos.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        //Añadiendo contactos
        contactos = new ArrayList<>();
        addContacts();

        /*bundle = this.getArguments();
        if (bundle == null){
            addContacts();
        }
        else{
            while(!bundle.isEmpty()) {
                String name = bundle.getParcelableArrayList("KEY").get(0).toString();
                String number = bundle.getParcelableArrayList("KEY").get(1).toString();
                int image = Integer.parseInt(bundle.getParcelableArrayList("KEY").get(2).toString());
                boolean fav = Boolean.parseBoolean(bundle.getParcelableArrayList("KEY").get(3).toString());
                Toast.makeText(getContext(),
                        "Datos: " + name + ", " + number + String.valueOf(fav),
                        Toast.LENGTH_SHORT);
                contactos.add(new Contacto(name, number, image, fav));
            }
            addContacts();
        }*/

        cAdapter = new ContactoAdapter(contactos);
        rv.setAdapter(cAdapter);

        return vContactos;
    }

    //Metodo para obtener los contactos
    private void addContacts() {
        Log.d("Anade", "Anadiendo contactos");
        //int i=0;
        try {
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC");
            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactos.add(new Contacto(name, phoneNumber, R.drawable.persona));
                /*Log.d("Numero: ", contactos.get(i).getNumero()+"");
                i++;*/
            }
            phones.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Pidiendo permiso al sistema para acceder a los contactos (ANDROID MARSHMALLOW Y SUPERIORES)
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                addContacts();
            } else {
                Toast.makeText(getContext(), "No se pueden mostrar contactos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public ContentResolver getContentResolver() {
        ContentResolver contactProvider = super.getContext().getContentResolver();
        return contactProvider;
    }
}