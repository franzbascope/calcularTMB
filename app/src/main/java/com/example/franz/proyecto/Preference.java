package com.example.franz.proyecto;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.franz.proyecto.Dal.DbConnection;
import com.example.franz.proyecto.Utils.PreferenceHelper;
import com.example.franz.proyecto.brl.HistorialBrl;

import java.util.Calendar;

public class Preference extends AppCompatActivity {

    private int[] fecha=new int[3];
    private int edad;
    private RadioButton rbMasculino;
    private RadioButton rbFemenino;
    private EditText txtEdad;
    private EditText txtEstatura;
    private DbConnection dbc = DbConnection.getInstance();
    private HistorialBrl brl = new HistorialBrl();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        txtEdad = findViewById(R.id.edad);
        txtEstatura = findViewById(R.id.txt_estatura);
        rbMasculino = findViewById(R.id.radioMasculino);
        rbFemenino = findViewById(R.id.radioFemenino);
        empezar(dbc.existeLaBase());


    }

    public void empezar(boolean empezar)
    {
        if(empezar==true)
        {
            String id= brl.getUltimoID();
            Intent intent = new Intent(this, Inicio.class);

            intent.putExtra(Inicio.PARAM_IDHISTORIAL,id);
            startActivity(intent);
        }

    }
    public void obtenerEdad(View view) {
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


    public void guardar(View view) {

        Log.e(AppContext.LOG_TAG, "Guardando Preferencias");
        DbConnection.createDatabase();

        if (!rbFemenino.isChecked() && !rbMasculino.isChecked()) {
            Toast.makeText(this, "Debe seleccionar un genero", Toast.LENGTH_SHORT).show();
        }
        if ( txtEstatura.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese su Estatura", Toast.LENGTH_SHORT).show();
        }
        if ( txtEdad.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese su Edad", Toast.LENGTH_SHORT).show();
        }
        String edad = txtEdad.getText().toString().trim();
        String estatura = txtEstatura.getText().toString().trim();
        String genero = rbMasculino.isChecked() ? "M" : "F";


        SharedPreferences preferences = PreferenceHelper.getPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(PreferenceHelper.GENERO, genero);
        editor.putString(PreferenceHelper.EDAD, edad);
        editor.putString(PreferenceHelper.ESTATURA, estatura);
        editor.commit();

        Toast.makeText(this, "Valor guardado", Toast.LENGTH_SHORT).show();

        Intent myIntent = new Intent(this, Primera.class);
        startActivity(myIntent);

       // mostarValorGuadado();
    }
}
