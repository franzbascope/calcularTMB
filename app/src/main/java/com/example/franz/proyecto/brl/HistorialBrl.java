package com.example.franz.proyecto.brl;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.franz.proyecto.AppContext;
import com.example.franz.proyecto.Dal.DbConnection;
import com.example.franz.proyecto.Dal.Table;
import com.example.franz.proyecto.model.Historial;

import java.util.LinkedList;
import java.util.List;

public class HistorialBrl extends Brl {
   /* historialId INTEGER NOT NULL,
    fecha INTEGER ,
    peso REAL,
    porcentajeGrasa REAL,
    kgGrasa REAL,
    porcentajeSinGrasa REAL,
    kgSinGrasa REAL,
    PRIMARY KEY (historialId)*/

    private static final String tmb = "tmb";
    private static final String HISTORIAL_ID = "historialId";
    private static final String FECHA = "fecha";
    private static final String PESO = "peso";
    private static final String PORCENTAJEGRASA = "porcentajeGrasa";
    private static final String KGGRASA = "kgGrasa";
    private static final String PORCENTAJESGRASA = "porcentajeSinGrasa";
    private static final String kgSinGrasa = "kgSinGrasa";

    public HistorialBrl(String... columns) {
        super(HISTORIAL_ID, FECHA, PESO, PORCENTAJEGRASA, KGGRASA, PORCENTAJESGRASA, kgSinGrasa, tmb);
    }

    public int insert(Historial obj) throws Exception {

        String tmb = String.valueOf(obj.getTMB());
        String fecha = String.valueOf(obj.getFECHA());
        String peso = String.valueOf(obj.getPESO());
        String porcGrasa = String.valueOf(obj.getPORCENTAJEGRASA());
        String kgGrasa = String.valueOf(obj.getKGGRASA());
        String porSGrasa = String.valueOf(obj.getPORCENTAJESGRASA());
        String kgSinGrasa = String.valueOf(obj.getKGGRASA());


        if (obj == null)
            throw new IllegalArgumentException("El objeto a insertar no puede ser nulo");

        if (fecha == null || fecha.isEmpty())
            throw new IllegalArgumentException("La Fecha no puede ser nulo ni vacio");

        if (peso == null || peso.isEmpty())
            throw new IllegalArgumentException("El peso no puede ser nulo ni vacio");

        if (porcGrasa == null || porcGrasa.isEmpty())
            throw new IllegalArgumentException("El % Grasa no puede ser nulo ni vacio");

        if (kgGrasa == null || kgGrasa.isEmpty())
            throw new IllegalArgumentException("Kgs de Grasa no puede ser nulo ni vacio");

        if (porSGrasa == null || porSGrasa.isEmpty())
            throw new IllegalArgumentException("El % Sin Grasa no puede ser nulo ni vacio");

        if (kgSinGrasa == null || kgSinGrasa.isEmpty())
            throw new IllegalArgumentException("Kgs sin Grasa no puede ser nulo ni vacio");

        DbConnection db = DbConnection.getInstance();

        ContentValues values = new ContentValues();

        values.put(this.tmb, tmb);
        values.put(FECHA, fecha);
        values.put(PESO, peso);
        values.put(PORCENTAJEGRASA, porcGrasa);
        values.put(KGGRASA, kgGrasa);
        values.put(PORCENTAJESGRASA, porSGrasa);
        values.put(this.kgSinGrasa, kgSinGrasa);

        int id = db.insert(Table.tbl_historial, values);
        Log.e(AppContext.LOG_TAG, "Se inserto el Hisotrial : "+id);
        if (id <= 0)
            throw new Exception("La llave primaria no se genero correctamente");
        Log.e(AppContext.LOG_TAG, "La llave primaria no se genero correctamente");

        return id;

    }

    public void update(Historial obj) {

        String tmb = String.valueOf(obj.getTMB());
        String fecha = String.valueOf(obj.getFECHA());
        String peso = String.valueOf(obj.getPESO());
        String porcGrasa = String.valueOf(obj.getPORCENTAJEGRASA());
        String kgGrasa = String.valueOf(obj.getKGGRASA());
        String porSGrasa = String.valueOf(obj.getPORCENTAJESGRASA());
        String kgSinGrasa = String.valueOf(obj.getKGGRASA());


        if (obj == null)
            throw new IllegalArgumentException("El objeto a insertar no puede ser nulo");

        if (fecha == null || fecha.isEmpty())
            throw new IllegalArgumentException("La Fecha no puede ser nulo ni vacio");

        if (peso == null || peso.isEmpty())
            throw new IllegalArgumentException("El peso no puede ser nulo ni vacio");

        if (porcGrasa == null || porcGrasa.isEmpty())
            throw new IllegalArgumentException("El % Grasa no puede ser nulo ni vacio");

        if (kgGrasa == null || kgGrasa.isEmpty())
            throw new IllegalArgumentException("Kgs de Grasa no puede ser nulo ni vacio");

        if (porSGrasa == null || porSGrasa.isEmpty())
            throw new IllegalArgumentException("El % Sin Grasa no puede ser nulo ni vacio");

        if (kgSinGrasa == null || kgSinGrasa.isEmpty())
            throw new IllegalArgumentException("Kgs sin Grasa no puede ser nulo ni vacio");

        DbConnection db = DbConnection.getInstance();

        ContentValues values = new ContentValues();
        values.put(this.tmb, tmb);
        values.put(FECHA, fecha);
        values.put(PESO, peso);
        values.put(PORCENTAJEGRASA, porcGrasa);
        values.put(KGGRASA, kgGrasa);
        values.put(PORCENTAJESGRASA, porSGrasa);
        values.put(kgSinGrasa, kgSinGrasa);

        String where = " historialId = ? ";
        String[] whereParams = new String[1];
        whereParams[0] = String.valueOf(obj.getHISTORIAL_ID());

        db.update(Table.tbl_historial, values, where, whereParams);

    }

    public void delete(int id) {

        if (id <= 0)
            throw new IllegalArgumentException("El id no puede ser menor o igual que cero");


        DbConnection bd = DbConnection.getInstance();

        String where = " historialId = ? ";
        String[] whereParams = new String[1];
        whereParams[0] = String.valueOf(id);

        bd.delete(Table.tbl_historial, where, whereParams);

    }

    public List<Historial> getHistoriales() {

        String order= "fecha desc";
        DbConnection bd = DbConnection.getInstance();
      //  Cursor cursor = bd.executeQuery(Table.tbl_historial, columns, null, null);
        Cursor cursor = bd.executeQueryOrder(Table.tbl_historial, columns,order);
        List<Historial> result = new LinkedList<>();

        while (cursor.moveToNext()) {
            Historial obj = getContactFromCursor(cursor);

            result.add(obj);
        }

        //
        //  rows[0,1,2] 0-> contactoId, 1->nombre , 2 ->telefono
        //  rows
        //  rows
        //->

        return result;
    }

    public String getUltimoID()
    {
        String id="Hola";
        String order="fecha desc  limit 1";
        DbConnection bd = DbConnection.getInstance();
        Cursor cursor = bd.executeQueryOrder(Table.tbl_historial, columns, order);
        if (cursor.moveToFirst()) {
            Historial obj = getContactFromCursor(cursor);
             id= String.valueOf(obj.getHISTORIAL_ID());
        }
        return id;
    }


    public Historial getContactById(int id) {
        if (id <= 0)
            throw new IllegalArgumentException("El id no puede ser menor o igual que cero");


        DbConnection bd = DbConnection.getInstance();

        String where = " historialId = ? ";
        String[] whereParams = new String[1];
        whereParams[0] = String.valueOf(id);

        Cursor cursor = bd.executeQuery(Table.tbl_historial, columns, where, whereParams);

        if (cursor.moveToFirst()) {

            Historial obj = getContactFromCursor(cursor);
            return obj;
        }
        Log.e(AppContext.LOG_TAG, "Se obtuvo el historial  :" +id);
        //
        //  rows[0,1,2] 0-> contactoId, 1->nombre , 2 ->telefono
        //  rows
        //  rows
        //->

        return null;
    }


    private Historial getContactFromCursor(Cursor cursor) {


        int historialId = cursor.getInt(cursor.getColumnIndex(HISTORIAL_ID));
        String fecha = cursor.getString(cursor.getColumnIndex(FECHA));
        int peso = cursor.getInt(cursor.getColumnIndex(PESO));
        float porcGrasa = cursor.getInt(cursor.getColumnIndex(PORCENTAJEGRASA));
        float kgGrasa = cursor.getInt(cursor.getColumnIndex(KGGRASA));
        float porSGrasa = cursor.getInt(cursor.getColumnIndex(PORCENTAJESGRASA));
        float kgSinGrasa = cursor.getInt(cursor.getColumnIndex(this.kgSinGrasa));
        int tmb = cursor.getInt(cursor.getColumnIndex(this.tmb));

        Historial obj = new Historial();

        obj.setHISTORIAL_ID(historialId);
        obj.setFECHA(fecha);
        obj.setPESO(peso);
//      obj.setTMB(cursor.getInt(cursor.getColumnIndex(TMB)));
        obj.setPORCENTAJEGRASA(porcGrasa);
        obj.setKGGRASA(kgGrasa);
        obj.setPORCENTAJESGRASA(porSGrasa);
        obj.setKgSinGrasa(kgSinGrasa);
        obj.setTMB(tmb);


        return obj;
    }
}
