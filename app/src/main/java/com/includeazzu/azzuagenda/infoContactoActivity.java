package com.includeazzu.azzuagenda;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class infoContactoActivity extends AppCompatActivity {

    //Declarando objetos de vista
    ImageButton volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_contacto);

        //Inicializando boton volver
        volver = findViewById(R.id.back);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Va a volver", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        //Obteniendo valores del bundle y asignando a variables
        Bundle infoEnv = getIntent().getExtras();
        String nombre = infoEnv.get("nom").toString();
        final String numero = infoEnv.get("num").toString();

        final TextView etNombre = findViewById(R.id.editNombre);
        final TextView etNumero = findViewById(R.id.editNum);
        final ImageButton llamar = findViewById(R.id.btnllamar);
        ImageButton compartir = findViewById(R.id.btnShare);

        etNombre.setText(nombre);
        etNumero.setText(numero + "");

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamar(etNumero);
            }
        });

        compartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String shareNombre = etNombre.getText().toString();
                String shareNumero = etNumero.getText().toString();
                String mensaje = "Nombre: " + shareNombre + "\nNumero: " + shareNumero;
                share.putExtra(Intent.EXTRA_TEXT, mensaje);
                startActivity(Intent.createChooser(share, "Share via"));
            }
        });
    }

    private static final int PERMISSIONS_REQUEST_CALL = 101;
    String strNumero;

    private void llamar(TextView numero) {
        //Iniciara la llamada
        strNumero = numero.getText().toString();
        if (!TextUtils.isEmpty(strNumero)) {
            String dial = "tel:" + strNumero;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_CALL);
                return;
            }
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }else {
            Toast.makeText(this, "Enter a phone number", Toast.LENGTH_SHORT).show();
        }
    }
}
