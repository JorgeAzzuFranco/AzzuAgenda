package com.includeazzu.azzuagenda;


import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
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
    ArrayList<Contacto> contactos;
    ArrayList<Contacto> favoritos = new ArrayList<>();

    //Probando a ver si sale
    Tab3_Favoritos tab3Sender = new Tab3_Favoritos();
    Bundle bundleFav;


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
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            if (savedInstanceState != null){
                contactos = savedInstanceState.getParcelableArrayList("cont");
                favoritos = savedInstanceState.getParcelableArrayList("fav");
                cAdapter = new ContactoAdapter(contactos);
                rv.setAdapter(cAdapter);
            }
            else{
                addContacts();
                cAdapter = new ContactoAdapter(contactos);
                rv.setAdapter(cAdapter);
            }
        }else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS},PERMISSIONS_REQUEST_READ_CONTACTS);
        }

        if (!favoritos.isEmpty()){
            bundleFav.putSerializable("ctc", favoritos);
            tab3Sender.setArguments(bundleFav);
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.pager, tab3Sender, "tag");
            ft.addToBackStack("tag");
            ft.commit();
        }

        return vContactos;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList("cont", contactos);
        savedInstanceState.putParcelableArrayList("fav", favoritos);
        super.onSaveInstanceState(savedInstanceState);
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
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addContacts();
                Toast.makeText(getContext(), "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public ContentResolver getContentResolver() {
        ContentResolver contactProvider = super.getContext().getContentResolver();
        return contactProvider;
    }

    public void addFavoritos(Contacto contacto){
        favoritos.add(contacto);
        Log.d("Anade a Fav", "Esta en fav: "+ contacto.getNombre()+ ", "+ contacto.getNumero());
    }

    public void borrarFavorito(String nombre) {
        int counter=0;
        Log.d("Entro en borrar", "Esta borrando el favorito con nombre de: " + nombre);
        for (Contacto cont : favoritos){
            if (cont.getNombre()== nombre)
                break;

            counter++;
        }

        favoritos.remove(counter);
    }

}
