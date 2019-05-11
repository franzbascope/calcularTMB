package com.example.franz.proyecto;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.franz.proyecto.Dal.DbConnection;
import com.example.franz.proyecto.Utils.PreferenceHelper;
import com.example.franz.proyecto.brl.HistorialBrl;
import com.example.franz.proyecto.model.Historial;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Primera extends AppCompatActivity {

    private String[] fecha = new String[3];

    public static final String CINTURA = "CINTURA";
    public static final String CUELLO = "CUELLO";
    public static final String CADERA = "CADERA";

    public static final int DATA_GRASA_CODE = 3;

    private EditText txtFecha;
    private EditText txtPeso;
    private Spinner spinner_actividad;
    private Button Grasa;
    private Button Guardar;
    private float peso;
    private int actividad;
    private float[] resultadosMTB = new float[4];
    private String cintura;
    private String cuello;
    private String cadera;
    private float grasa = 0;
    private String Genero;
    private String Edad;
    private String Estatura;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        obtenerPreferencias();
        setContentView(R.layout.activity_primera);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] letra = {"Sedentario", "Actividad Ligera ", "Actividad Moderada", "Actividad Intensa", "Actividad muy Intensa"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));
        txtFecha = findViewById(R.id.fecha);
        txtPeso = findViewById(R.id.peso);
        spinner_actividad = findViewById(R.id.spinner);
        Grasa = findViewById(R.id.Grasa);
        Guardar = findViewById(R.id.Guardar);

    }


    private void obtenerPreferencias() {
        Log.e(AppContext.LOG_TAG, "Obteniendo Preferencias");
        SharedPreferences preferences = PreferenceHelper.getPreferences(this);
        Genero = preferences.getString(PreferenceHelper.GENERO, null);
        Edad = preferences.getString(PreferenceHelper.EDAD, null);
        Estatura = preferences.getString(PreferenceHelper.ESTATURA, null);
    }

    public void Obtener_Grasa(View view) {

        Log.e(AppContext.LOG_TAG, "Obteniendo % Grasa");
        if (txtFecha.getText().toString().isEmpty()) {
            Toast.makeText(this, "Debe seleccionar una Fecha", Toast.LENGTH_SHORT).show();
            return;
        }
        if (txtPeso.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese su Peso", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, Tercera.class);
        intent.putExtra(Tercera.PARAM_GENERO, Genero);
        startActivityForResult(intent, DATA_GRASA_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == DATA_GRASA_CODE) {
            Guardar.setEnabled(true);
            cuello = data.getStringExtra(CUELLO);
            cadera = data.getStringExtra(CADERA);
            cintura = data.getStringExtra(CINTURA);
            grasa = calcular_grasa(Integer.valueOf(cintura), Integer.valueOf(cuello), Integer.valueOf(cadera), Integer.valueOf(Estatura), Genero);
            peso = Float.valueOf(txtPeso.getText().toString());

            Historial hist = new Historial();
            HistorialBrl brl = new HistorialBrl();
            cargarHistorial(hist);
            try {
                brl.insert(hist);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    public void cargarHistorial(Historial hist) {
        Log.e(AppContext.LOG_TAG, "Cargando Objeto Historial" + hist.getHISTORIAL_ID());
        float[] tmb = calcular_datos(Genero, Integer.valueOf(Estatura), peso, actividad, Integer.valueOf(Edad));
        String date = fecha[0] + fecha[1] + fecha[2];


        //Porcentaje Sin Grasa
        float PSG = 100 - grasa;
        float KGCG = grasa / 100 * peso;
        float KGSG = peso - KGCG;
        if (KGSG < 0) {
            KGSG = KGSG * -1;
        }

        hist.setTMB((int) tmb[0]);
        hist.setFECHA(date);
        hist.setPESO(peso);
        hist.setPORCENTAJEGRASA(grasa);
        hist.setPORCENTAJESGRASA(PSG);
        hist.setKgSinGrasa(KGSG);
        hist.setKGGRASA(KGCG);


    }

    public float calcular_grasa(int cintura, int cuello, int cadera, int altura, String genero) {
        Log.e(AppContext.LOG_TAG, "Calculando % Grasa");
        float resultado = 0;
        if (genero.equals("M")) {
            //  495 / (1.0324 - 0.19077 * (log(cintura - cuello)) + 0.15456 * (log (altura))) - 450

            resultado = (float) (495 / (1.0324 - 0.19077 * (Math.log10(cintura - cuello)) + 0.15456 * (Math.log10(altura))) - 450);

        } else {
            //   Mujeres	% de Grasa = 495 / (1.29579 - 0.35004 * (log(cintura + cadera - cuello)) + 0.22100* (log(altura))) - 450
            resultado = (float) (495 / (1.29579 - 0.35004 * (Math.log10(cintura + cadera - cuello)) + 0.22100 * (Math.log10(altura))) - 450);

        }

        if (resultado < 0) {
            resultado = 0;

        }

        if (resultado > 100) {
            resultado = 100;

        }
        return resultado;
    }

    public float[] calcular_datos(String sexo, int estatura, float peso, int actividad, int edad) {
        float metabolismo;
        float pesoTotal = peso;
        float calorias;

        if (sexo.equals("M")) {

            calorias = (float) ((13.7 * pesoTotal) + (5 * estatura) - (6.8 * edad) + 66);


        } else {

            calorias = (float) ((9.6 * pesoTotal) + (1.8 * estatura) - (4.7 * edad) + 655);

        }

        metabolismo = calorias;
        if (actividad == 0) {

            calorias = (float) (calorias * 1.2);
        } else if (actividad == 1) {

            calorias = (float) (calorias * 1.375);
        } else if (actividad == 2) {

            calorias = (float) (calorias * 1.55);

        } else if (actividad == 3) {

            calorias = (float) (calorias * 1.725);
        } else if (actividad == 4) {

            calorias = (float) (calorias * 1.9);
        }

        float CNPMP = (float) (calorias * 1.2001);
        float[] resultado = {metabolismo, (float) (calorias), (float) (calorias * 0.85), (float) (calorias * 1.15)};

        return resultado;

        /*Intent intent = new Intent(this, DataActivity.class);
        intent.putExtra(DataActivity.PARAM_METABOLISMO,  String.valueOf(resultado[0]));
        intent.putExtra(DataActivity.PARAM_MANTENER, String.valueOf(resultado[1]));
        intent.putExtra(DataActivity.PARAM_ADELGAZAR,  String.valueOf(resultado[2]));
        intent.putExtra(DataActivity.PARAM_SUBIR,  String.valueOf(resultado[3]));
        this.startActivity(intent);*/


    }

    public boolean validarFecha(String date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date fecha = sdf.parse(date);
            Date actual = new Date();


            return (fecha.after(actual));


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;

    }

    public void obtenerFecha(View view) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day + " / " + (month) + " / " + year;
                month = month + 1;
                fecha[0] = String.valueOf(year);
                if (month < 10) {
                    fecha[1] = "0" + String.valueOf(month);
                    fecha[1].toString();

                } else {
                    fecha[1] = String.valueOf(month);

                }
                if (day < 10) {
                    fecha[2] = "0" + String.valueOf(day);

                } else {
                    fecha[2] = String.valueOf(day);

                }
                String a=fecha[0].toString();
                String b=fecha[1].toString();
                String c=fecha[2].toString();
                String fecha = day + "/" + month + "/" + year;
                String fechaPar = year + "-" + month + "-" + day;
                if (validarFecha(fechaPar)) {
                    Toast.makeText(getBaseContext(), "Ingrese una fecha Correcta", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    txtFecha.setText(String.valueOf(fecha));
                }


            }
        });
        newFragment.show(getFragmentManager(), "datePicker");
    }


    public void Guardar(View view) {
        Log.e(AppContext.LOG_TAG, "Se comenzo el activity Lista");
        Intent intent = new Intent(Primera.this, Lista.class);
        startActivity(intent);
    }
}
