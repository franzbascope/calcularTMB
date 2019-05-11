package com.example.franz.proyecto;

import android.app.Application;

public class AppContext extends Application
{
    private static AppContext instancia;
    public static final String LOG_TAG = "franz.proyecto";

    @Override
    public void onCreate() {
        super.onCreate();
        instancia = this;
    }

    public static AppContext getInstancia() {
        return instancia;
    }
}
