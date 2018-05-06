package com.includeazzu.azzuagenda;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class infoContactoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_contacto);

        //Obteniendo valores del bundle y asignando a variables
        Bundle infoEnv = getIntent().getExtras();
        String nombre = infoEnv.get("nom").toString();
        final String numero = infoEnv.get("num").toString();
        int img = Integer.parseInt(infoEnv.get("img").toString());

        EditText etNombre = findViewById(R.id.editNombre);
        final EditText etNumero = findViewById(R.id.editNum);
        ImageView image = findViewById(R.id.img);
        ImageButton llamar = findViewById(R.id.btnllamar);
        ImageButton compartir = findViewById(R.id.btnShare);

        etNombre.setText(nombre);
        etNumero.setText(numero+"");
        //image.setImageResource(R.drawable.persona);

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedirPermiso(etNumero);
            }
        });
    }

    final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;
    String strNumero;

    public void pedirPermiso(EditText numero){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CALL_PHONE)) {

                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);

                    //Iniciara la llamada
                    strNumero = numero.getText().toString();
                    if(!TextUtils.isEmpty(strNumero)) {
                        String dial = "tel:" + strNumero;
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    }else {
                        Toast.makeText(this, "Enter a phone number", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "Enter a phone number", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
