package com.example.franz.proyecto;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.franz.proyecto.Dal.DbConnection;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText txtPeso;
    private EditText txtEdad;
    private RadioButton rbMasculino;
    private RadioButton rbFemenino;
    private RadioButton rbKgs;
    private RadioButton rbLibs;
    private EditText txtEstatura;
    private Spinner spinner_actividad;
    int[] fecha = new int[3];
    private int edad;
    private float libra = (float) 0.453592;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] letra = {"Sedentario", "Actividad Ligera ", "Actividad Moderada", "Actividad Intensa", "Actividad muy Intensa"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));

        txtPeso = findViewById(R.id.txt_peso);
        txtEstatura = findViewById(R.id.txt_estatura);
        txtEdad = findViewById(R.id.txtEdad);

        rbMasculino = findViewById(R.id.radioMasculino);
        rbFemenino = findViewById(R.id.radioFemenino);
        rbKgs = findViewById(R.id.radioKilos);
        rbLibs = findViewById(R.id.radioLibras);
        spinner_actividad = findViewById(R.id.spinner);


    }

    public void accionBoton(View view) {


        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                fecha[0] = year;
                fecha[1] = month;
                fecha[2] = day;

                 edad = getEdad(fecha[0], fecha[1], fecha[2]);
                txtEdad.setText(String.valueOf(edad));


            }
        });
        newFragment.show(getFragmentManager(), "datePicker");


    }


    public void calcular(View view) {


        if (!rbFemenino.isChecked() && !rbMasculino.isChecked()) {
            Toast.makeText(this, "Debe seleccionar un genero", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!rbKgs.isChecked() && !rbLibs.isChecked()) {
            Toast.makeText(this, "Debe seleccionar una medida de peso", Toast.LENGTH_SHORT).show();
            return;
        }

        if (txtPeso.getText().toString().isEmpty() || txtEstatura.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese su Peso y Estatura", Toast.LENGTH_SHORT).show();
            return;
        }


        int peso = Integer.valueOf(txtPeso.getText().toString());
        int estatura = Integer.valueOf(txtEstatura.getText().toString());

        int posicion = spinner_actividad.getSelectedItemPosition();

        String genero = rbMasculino.isChecked() ? "M" : "F";
        String medida = rbKgs.isChecked() ? "K" : "L";



        calcular_datos(genero, medida, estatura, peso, posicion, edad);


    }

    private int getEdad(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);

        if(ageInt<0)
        {
            ageInt=0;

        }


        return ageInt;
    }


    public void calcular_datos(String sexo, String medida, int estatura, int peso, int actividad, int edad) {
        float metabolismo;
        float pesoTotal = peso;
        float calorias;
        if (medida == "L") {
            pesoTotal = peso * libra;
        }

        if (sexo.equals("M")) {

            calorias = (float) ((13.7 * pesoTotal) + (5  * estatura) - (6.8 * edad) +66);


        } else {

            calorias = (float) ((9.6 * pesoTotal) + (1.8 * estatura) - (4.7 * edad) + 655);

        }

        metabolismo=calorias;
        if(actividad==0)
        {

            calorias=  (float) (calorias*1.2);
        }
        else if(actividad==1)
        {

         calorias=  (float) (calorias*1.375);
        }
        else if(actividad==2)
        {

            calorias=  (float) (calorias*1.55);

        }
        else if(actividad==3)
        {

            calorias=  (float) (calorias*1.725);
        }
        else if(actividad==4)
        {

            calorias=  (float) (calorias*1.9);
        }

        float CNPMP= (float) (calorias*1.2001);
        float[] resultado= {metabolismo, (float) (calorias), (float) (calorias*0.85),(float) (calorias*1.15)};

       Intent intent = new Intent(this, DataActivity.class);
        intent.putExtra(DataActivity.PARAM_METABOLISMO,  String.valueOf(resultado[0]));
        intent.putExtra(DataActivity.PARAM_MANTENER, String.valueOf(resultado[1]));
        intent.putExtra(DataActivity.PARAM_ADELGAZAR,  String.valueOf(resultado[2]));
        intent.putExtra(DataActivity.PARAM_SUBIR,  String.valueOf(resultado[3]));
        this.startActivity(intent);


    }


}