package com.example.franz.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.franz.proyecto.Dal.DbConnection;
import com.example.franz.proyecto.brl.HistorialBrl;
import com.example.franz.proyecto.model.Historial;

public class Principal extends AppCompatActivity {


    public static final int DATA_REQUEST_CODE = 1;
    public static final String PARAM_PRUEBA = "PRUEBA";
    public static final String PARAM_MTB0 = "PARAM_MTB0";
    public static final String PARAM_MTB1 = "PARAM_MTB1";
    public static final String PARAM_MTB2 = "PARAM_MTB2";
    public static final String PARAM_MTB3 = "PARAM_MTB3";
    public static final String PARAM_GRASA = "PARAM_GRASA";
    public static final String PARAM_EDAD = "PARAM_EDAD";
    public static final String PARAM_ALTURA = "PARAM_ALTURA";
    public static final String PARAM_PESO = "PARAM_PESO";
    public static final String PARAM_SEXO = "PARAM_SEXO";

    private TextView MTB0;
    private TextView MTB1;
    private TextView MTB2;
    private TextView MTB3;
    private TextView MTB4;
    private TextView grasa;
    private TextView peso;
    private TextView altura;
    private TextView edad;
    private TextView sexo;


    private EditText PESO;
    private EditText FECHA;
    private EditText PORCGRASA;
    private EditText PORSGRASA;
    private EditText KGCGRASA;
    private EditText kgSinGrasa;

    private int currentId=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

     //  DbConnection.createDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        MTB0 = findViewById(R.id.MTB0);
        MTB1 = findViewById(R.id.MTB1);
        MTB2 = findViewById(R.id.MTB2);
        MTB3 = findViewById(R.id.MTB3);
        grasa = findViewById(R.id.grasa);
        peso = findViewById(R.id.peso);
        altura = findViewById(R.id.altura);
        sexo = findViewById(R.id.sexo);
        edad = findViewById(R.id.edad);

        PESO = findViewById(R.id.PESO);
        FECHA   = findViewById(R.id.FECHA);
        PORCGRASA = findViewById(R.id.PCG);
        PORSGRASA = findViewById(R.id.PSG);
        KGCGRASA = findViewById(R.id.KGCG);
        kgSinGrasa = findViewById(R.id.kgSinGrasa);


    }


    public void calcularTotal(View view) {
        Intent intent = new Intent(this, Primera.class);
        startActivityForResult(intent, DATA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == DATA_REQUEST_CODE) {

            String MTB0 = data.getStringExtra(PARAM_MTB0);
            String MTB1 = data.getStringExtra(PARAM_MTB1);
            String MTB2 = data.getStringExtra(PARAM_MTB2);
            String MTB3 = data.getStringExtra(PARAM_MTB3);
            String GRASA = data.getStringExtra(PARAM_GRASA);
            String EDAD = data.getStringExtra(PARAM_EDAD);
            String SEXO = data.getStringExtra(PARAM_SEXO);
            String ALTURA = data.getStringExtra(PARAM_ALTURA);
            String PESO = data.getStringExtra(PARAM_PESO);


            this.MTB0.setVisibility(View.VISIBLE);
            this.MTB1.setVisibility(View.VISIBLE);
            this.MTB2.setVisibility(View.VISIBLE);
            this.MTB3.setVisibility(View.VISIBLE);
            this.peso.setVisibility(View.VISIBLE);
            this.edad.setVisibility(View.VISIBLE);
            this.grasa.setVisibility(View.VISIBLE);
            this.altura.setVisibility(View.VISIBLE);
            this.sexo.setVisibility(View.VISIBLE);

            this.MTB0.setText("Metabolismo Basal: " + MTB0);
            this.MTB1.setText("Calorias necesarias para mantener el peso : " + MTB1);
            this.MTB2.setText("Calorias necesarias para bajar de peso: " + MTB2);
            this.MTB3.setText("Calorias necesarias para aumentar de peso " + MTB3);
            this.peso.setText("Peso : " + PESO);
            this.edad.setText("Edad: " + EDAD);
            this.grasa.setText("Porcentaje de Grasa : " + GRASA);
            this.altura.setText("Altura: : " + ALTURA);
            String genero;
            if(SEXO.equals("M"))
            {
                genero="Masculino";

            }
            else
                {
                    genero="Femenino";

                }

            this.sexo.setText("Sexo : " + genero);

          /*  String nombre = data.getStringExtra(PARAM_NOMBRE);
            String genero = data.getStringExtra(PARAM_GENERO);

            labelNombre.setText("Nombre: "+ nombre);
            labelGenero.setText("Genero: "+ (genero.equals("M") ? "Masculino" : "Femenino"));

            labelNombre.setVisibility(View.VISIBLE);
            labelGenero.setVisibility(View.VISIBLE);*/
        }

    }

    public void insertarDatos(View view) {

        String fecha=(FECHA.getText().toString());
        float peso= Float.valueOf(PESO.getText().toString());
        float pcg=Float.valueOf(PORCGRASA.getText().toString());
        float psg=Float.valueOf(PORSGRASA.getText().toString());
        float kgcg=Float.valueOf(KGCGRASA.getText().toString());
        float kgsg=Float.valueOf(kgSinGrasa.getText().toString());

        Historial hist = new Historial();
        hist.setHISTORIAL_ID(1);
        hist.setFECHA(fecha);
        hist.setPESO(peso);
        hist.setKgSinGrasa(kgcg);
        hist.setKGGRASA(kgsg);
        hist.setPORCENTAJEGRASA(pcg);
        hist.setPORCENTAJESGRASA(psg);


        HistorialBrl brl = new HistorialBrl();
        try {
            if(currentId == 0)
                brl.insert(hist);
            else
                brl.update(hist);
            Log.e(AppContext.LOG_TAG, "El Historial se guardo correctamente");

        } catch (Exception e) {

            Log.e(AppContext.LOG_TAG, "Error al guardar el Historial", e);
            return;

        }
        finish();
    }
}
