package com.includeazzu.azzuagenda;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Tab2_Marcador extends Fragment {


    public Tab2_Marcador() {
        // Required empty public constructor
    }

    //Declarando objetos de vista
    View vMarcador;

    EditText nombre;
    Button guardar;
    ImageButton llamar;

    //Declarando objetos a utilizar
    Contacto contactos;
    String strNombre;
    String strNumero;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Vista a trabajar
        vMarcador = inflater.inflate(R.layout.tab2__marcador, container, false);

        nombre = vMarcador.findViewById(R.id.editNombre);
        final EditText numero = (EditText) vMarcador.findViewById(R.id.editNum);
        guardar = vMarcador.findViewById(R.id.btnGuardar);
        llamar = vMarcador.findViewById(R.id.btnllamar);

        strNombre = nombre.getText().toString();


        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pedirPermiso();
                strNumero = numero.getText().toString();
                if(!TextUtils.isEmpty(strNumero)) {
                    String dial = "tel:" + strNumero;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }else {
                    Toast.makeText(getContext(), "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return vMarcador;
    }

    final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;

    public void pedirPermiso(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(getParentFragment().getContext(),
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getParentFragment().getContext(),
                        Manifest.permission.CALL_PHONE)) {

                } else {
                    ActivityCompat.requestPermissions((Activity) getParentFragment().getContext(),
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                }
            }
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                } else {
                    System.out.println("El usuario ha rechazado el permiso");
                }
                return;
            }
        }
    }

    public void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setPackage("com.android.phone");
        intent.setData(Uri.parse("tel:" + strNumero));
        Context context = super.getContext();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            context.startActivity(intent);
            return;
        }
    }*/

}
