package com.example.franz.proyecto;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DataActivity extends AppCompatActivity {


    public static final String PARAM_METABOLISMO ="PARAM_METABOLISMO";
    public static final String PARAM_MANTENER ="PARAM_MANTENER";
    public static final String PARAM_ADELGAZAR ="PARAM_ADELGAZAR";
    public static final String PARAM_SUBIR ="PARAM_SUBIR";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        TextView labelMetabolismo = findViewById(R.id.MetabolismoBasal);
        TextView labelMantener = findViewById(R.id.MantenerPeso);
        TextView labelAdelgazar = findViewById(R.id.adelgazar);
        TextView labelSubir = findViewById(R.id.subir);

       Intent parentIntent = getIntent();
        String metabolismo = parentIntent.getStringExtra(PARAM_METABOLISMO);
        String mantener = parentIntent.getStringExtra(PARAM_MANTENER);
        String adelgazar = parentIntent.getStringExtra(PARAM_ADELGAZAR);
        String subir = parentIntent.getStringExtra(PARAM_SUBIR);


        labelMetabolismo.setText("Metabolismo basal: "+metabolismo);
        labelMantener.setText("Calorías necesarias para mantener el peso: "+mantener);
        labelAdelgazar.setText("Calorías para adelgazar: "+adelgazar);
        labelSubir.setText("Calorías para subir de peso : "+subir);

    }
}
