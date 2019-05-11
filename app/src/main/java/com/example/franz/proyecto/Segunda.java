package com.example.franz.proyecto;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class Segunda extends AppCompatActivity {

    private TextView txtEdad;
    private int[] fecha=new int[3];
    private int edad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);
        txtEdad = findViewById(R.id.txtEdad);
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

    public void calcularTotal(View view) {
        String edad= String.valueOf(this.edad);
        Intent intent = getIntent();
       // intent.putExtra(Primera.PARAM_EDAD,edad );
        setResult(RESULT_OK, intent);
        finish();
    }
}
