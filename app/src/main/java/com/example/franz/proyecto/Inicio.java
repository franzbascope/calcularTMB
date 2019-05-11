package com.example.franz.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.franz.proyecto.brl.HistorialBrl;
import com.example.franz.proyecto.model.Historial;

public class Inicio extends AppCompatActivity {

    public static final String PARAM_IDHISTORIAL = "PARAM_IDHISTORIAL";
    private String historialID;

    TextView  txt_peso;
    TextView txt_kgcg;
    TextView txt_kgsg;
    TextView txt_pcg;
    TextView txt_psg;
    TextView txt_tmb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt_peso = findViewById(R.id.peso);
        txt_kgcg = findViewById(R.id.kgcg);
        txt_kgsg = findViewById(R.id.kgsg);
        txt_pcg = findViewById(R.id.grasa);
        txt_psg = findViewById(R.id.psg);
        txt_tmb = findViewById(R.id.tmb);
        setSupportActionBar(toolbar);

        Intent parentIntent = getIntent();
        historialID=parentIntent.getStringExtra(PARAM_IDHISTORIAL);
        cargarDatos(Integer.valueOf(historialID));
        Log.e(AppContext.LOG_TAG, "Cargando Historial : "+historialID);

    }


    public void cargarDatos(int id)
    {
        HistorialBrl brl = new HistorialBrl();
        Historial hist= brl.getContactById(id);

        String peso =String.valueOf(hist.getPESO());
        String psg =String.valueOf(hist.getPORCENTAJESGRASA());
        String pcg =String.valueOf(hist.getPORCENTAJEGRASA());
        String kgcg =String.valueOf(hist.getKGGRASA());
        String kgsg =String.valueOf(hist.getKgSinGrasa());
        String tmb =String.valueOf(hist.getTMB());

        txt_tmb.setText(tmb+" calorias");
        txt_psg.setText(psg+"%");
        txt_pcg.setText(pcg+"%");
        txt_peso.setText(peso+" Kg");
        txt_kgsg.setText(kgsg+" Kg");
        txt_kgcg.setText(kgcg+" Kg");

    }

    public void cambiarHistorial(View view) {
        Intent intent = new Intent(this, Lista.class);
        startActivity(intent);
    }
}
