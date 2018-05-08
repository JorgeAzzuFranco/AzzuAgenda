package com.includeazzu.azzuagenda;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Tab2_Marcador extends Fragment {


    public Tab2_Marcador() {
        // Required empty public constructor
    }

    //Declarando objetos de vista
    View vMarcador;

    EditText nombre;
    EditText numero;
    Button guardar;
    ImageButton llamar;

    //Declarando objetos a utilizar
    ArrayList<Contacto> contactos;
    String strNombre;
    String strNumero;
    Bundle bundle;
    int i = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Vista a trabajar
        vMarcador = inflater.inflate(R.layout.tab2__marcador, container, false);

        //Inicializando objetos de la view
        numero = vMarcador.findViewById(R.id.editNum);
        guardar = vMarcador.findViewById(R.id.btnGuardar);
        llamar = vMarcador.findViewById(R.id.btnllamar);

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pide permiso y relaliza
                int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED){
                    pedirPermiso(numero);
                }else {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS},MY_PERMISSIONS_REQUEST_CALL_PHONE);
                }

            }
        });

        return vMarcador;
    }

    final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Iniciara la llamada
                strNumero = numero.getText().toString();
                if(!TextUtils.isEmpty(strNumero)) {
                    String dial = "tel:" + strNumero;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }else {
                    Toast.makeText(getContext(), "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getContext(), "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void pedirPermiso(EditText numero){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(getParentFragment().getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getParentFragment().getContext(),
                        Manifest.permission.CALL_PHONE)) {

                } else {
                    ActivityCompat.requestPermissions((Activity) getParentFragment().getContext(),
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);

                    //Iniciara la llamada
                    strNumero = numero.getText().toString();
                    if(!TextUtils.isEmpty(strNumero)) {
                        String dial = "tel:" + strNumero;
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    }else {
                        Toast.makeText(getContext(), "Enter a phone number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        else{
            //Iniciara la llamada
            strNumero = numero.getText().toString();
            if(!TextUtils.isEmpty(strNumero)) {
                String dial = "tel:" + strNumero;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }else {
                Toast.makeText(getContext(), "Enter a phone number", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
