package com.example.franz.proyecto.Dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.franz.proyecto.AppContext;
import com.example.franz.proyecto.R;
import com.example.franz.proyecto.brl.HistorialBrl;
import com.example.franz.proyecto.model.Historial;

import java.io.File;
import java.util.StringTokenizer;

public class DbConnection extends SQLiteOpenHelper {


    private static DbConnection instance;
    private Context context;
    private static final String DB_NAME = "com.example.franz.proyecto.calculadora.db";
    private static final int DB_VERSION = 1;

    public DbConnection(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory = null, DB_VERSION);
    }

    public DbConnection(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        executeDDL(R.string.db_v1, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void executeDDL(int sql, SQLiteDatabase db) {
        String strSql = context.getResources().getString(sql);
        StringTokenizer queries = new StringTokenizer(strSql, ";");
        while (queries.hasMoreTokens()) {
            String query = queries.nextToken().trim();
            if (query.isEmpty())
                continue;
            db.execSQL(query);
        }
    }

    public int insert(Table table, ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        String base = table.toString();
        int a = values.size();
        int b = 2;
        int id = (int) db.insertOrThrow(table.toString(), null, values);
        return id;
    }

    public void update(Table table, ContentValues values, String whereExpression, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();

        db.update(table.toString(), values, whereExpression, whereArgs);
    }

    public void delete(Table table, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(table.toString(), whereClause, whereArgs);
    }

    public Cursor executeQuery(Table table, String[] columns, String whereExpression, String[] selectionArgs) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(table.toString(), columns, whereExpression, selectionArgs, null, null, null);
        return cursor;
    }

    public Cursor executeQueryOrder(Table table, String[] columns, String order) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(table.toString(), columns, null, null, null, null, order);
        return cursor;
    }




    public Cursor executeQuery(int query, String[] selectionArgs) {
        SQLiteDatabase db = getReadableDatabase();


        String strQuery = this.context.getResources().getString(query);
        Cursor cursor = db.rawQuery(strQuery, selectionArgs);
        return cursor;
    }


    public synchronized static DbConnection getInstance() {
        if (instance == null) {
            instance = new DbConnection(AppContext.getInstancia()); //Change with Applicacion Context
        }
        return instance;
    }

    private void create() {

    }

    public boolean existeLaBase() {
        File database = AppContext.getInstancia().getDatabasePath(DB_NAME);
        if (database.exists()) {
            return true;
        } else {

            return false;
        }

    }

    public static void createDatabase() {
        File database = AppContext.getInstancia().getDatabasePath(DB_NAME);
        if (!database.exists()) {

            DbConnection connection = DbConnection.getInstance();
            connection.executeQuery(Table.tbl_historial, new String[]{"fecha"}, null, null);

        }
    }
}
