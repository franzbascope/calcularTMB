package com.example.franz.proyecto.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private static final String PREFERENCE_NAME = "com.example.franz.proyecto.preferencesexample_preferences";

    public static final String GENERO = "GENERO";
    public static final String ESTATURA = "ESTATURA";
    public static final String EDAD = "EDAD";


    private static SharedPreferences preferences;

    public static SharedPreferences getPreferences(Context context){
        if(preferences == null)
            preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return  preferences;
    }



}
