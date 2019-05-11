package com.example.franz.proyecto.model;

public class Historial {

    private int TMB;
    private  int HISTORIAL_ID;
    private  String FECHA;
    private  float PESO;
    private  float PORCENTAJEGRASA;
    private  float KGGRASA;
    private  float PORCENTAJESGRASA;
    private  float kgSinGrasa;

    public Historial() {
    }

    public Historial(int TMB, int HISTORIAL_ID, String FECHA, float PESO, float PORCENTAJEGRASA, float KGGRASA, float PORCENTAJESGRASA, float kgSinGrasa) {
        this.TMB = TMB;
        this.HISTORIAL_ID = HISTORIAL_ID;
        this.FECHA = FECHA;
        this.PESO = PESO;
        this.PORCENTAJEGRASA = PORCENTAJEGRASA;
        this.KGGRASA = KGGRASA;
        this.PORCENTAJESGRASA = PORCENTAJESGRASA;
        this.kgSinGrasa = kgSinGrasa;
    }

    public int getTMB() {
        return TMB;
    }

    public void setTMB(int TMB) {
        this.TMB = TMB;
    }

    public int getHISTORIAL_ID() {
        return HISTORIAL_ID;
    }

    public void setHISTORIAL_ID(int HISTORIAL_ID) {
        this.HISTORIAL_ID = HISTORIAL_ID;
    }

    public String getFECHA() {
        return FECHA;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public float getPESO() {
        return PESO;
    }

    public void setPESO(float PESO) {
        this.PESO = PESO;
    }

    public float getPORCENTAJEGRASA() {
        return PORCENTAJEGRASA;
    }

    public void setPORCENTAJEGRASA(float PORCENTAJEGRASA) {
        this.PORCENTAJEGRASA = PORCENTAJEGRASA;
    }

    public float getKGGRASA() {
        return KGGRASA;
    }

    public void setKGGRASA(float KGGRASA) {
        this.KGGRASA = KGGRASA;
    }

    public float getPORCENTAJESGRASA() {
        return PORCENTAJESGRASA;
    }

    public void setPORCENTAJESGRASA(float PORCENTAJESGRASA) {
        this.PORCENTAJESGRASA = PORCENTAJESGRASA;
    }

    public float getKgSinGrasa() {
        return kgSinGrasa;
    }

    public void setKgSinGrasa(float kgSinGrasa) {
        this.kgSinGrasa = kgSinGrasa;
    }
}
