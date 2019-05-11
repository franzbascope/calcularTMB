package com.example.franz.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Tercera extends AppCompatActivity {

    public static final String PARAM_GENERO = "S";
    private String genero;
    private EditText txtCintura;
    private EditText txtCuello;
    private EditText txtCadera;
    private TextView vCadera;
    private Button btncalcular;
    private int cintura=0;
    private int cuello=0;
    private int cadera=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tercera);
        txtCintura = findViewById(R.id.cintura);
        txtCuello = findViewById(R.id.cuello);
        txtCadera = findViewById(R.id.cadera);
        vCadera = findViewById(R.id.vcadera);
        btncalcular = findViewById(R.id.botonCalcular);
        Intent parentIntent = getIntent();
        this.genero = parentIntent.getStringExtra(PARAM_GENERO);
        if (genero.equals("F")) {
            txtCadera.setVisibility(View.VISIBLE);
            txtCadera.setEnabled(true);
            vCadera.setVisibility(View.VISIBLE);
            vCadera.setEnabled(true);
        }
    }

    public void calcularTotal(View view) {
        if (txtCintura.getText().toString().isEmpty() ) {
            Toast.makeText(this, "Ingrese Dato Cintura", Toast.LENGTH_SHORT).show();
            return;
        }
        if (txtCuello.getText().toString().isEmpty() ) {
            Toast.makeText(this, "Ingrese Dato Cuello", Toast.LENGTH_SHORT).show();
            return;
        }

        if (txtCintura.getText().toString().isEmpty() && !txtCintura.isEnabled() ) {
            Toast.makeText(this, "Ingrese Dato Cintura", Toast.LENGTH_SHORT).show();
            return;
        }
        cintura = Integer.valueOf(String.valueOf(txtCintura.getText()));
        cuello = Integer.valueOf(String.valueOf(txtCuello.getText()));
        if (txtCadera.isEnabled()) {
            cadera = Integer.valueOf(String.valueOf(txtCadera.getText()));
        }
   //     Toast.makeText(this, "Cuello: "+cuello+"\nCintura: "+cintura+"\nCadera: "+cadera, Toast.LENGTH_LONG).show();
        Intent intent = getIntent();
        intent.putExtra(Primera.CADERA,String.valueOf(cadera) );
        intent.putExtra(Primera.CUELLO,String.valueOf(cuello) );
        intent.putExtra(Primera.CINTURA,String.valueOf(cintura) );
        setResult(RESULT_OK, intent);
        finish();

    }
}
