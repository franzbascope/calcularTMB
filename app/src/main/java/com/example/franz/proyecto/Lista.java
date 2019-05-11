package com.example.franz.proyecto;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.franz.proyecto.brl.HistorialBrl;
import com.example.franz.proyecto.model.Historial;

import java.util.List;

public class Lista extends AppCompatActivity {


    private ListView historialList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_lista);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        historialList = findViewById(R.id.historialList);

        historialList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {

                Historial obj = (Historial) adapterView.getAdapter().getItem(index);
                // Intent intent = new Intent(Lista.this, ContactActivity.class);
                // intent.putExtra(ContactActivity.PARAM_ID, obj.getContactoId());
                //   startActivity(intent);

            }
        });
        registerForContextMenu(historialList);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadContacts();
    }

    private void loadContacts() {


        try {
            Log.d(AppContext.LOG_TAG, "Leyendo contactos de la BD");
            HistorialBrl brl = new HistorialBrl();
            List<Historial> hists = brl.getHistoriales();
            for (int i=0;i<hists.size();i++)
            {
                String id= String.valueOf(hists.get(i).getHISTORIAL_ID());


            }
            Log.d(AppContext.LOG_TAG, "Se obtuvieron " + hists.size() + " registros");
            HistListAdapter adapter = new HistListAdapter(this, hists);
            historialList.setAdapter(adapter);
        } catch (Exception ex) {

            Log.e(AppContext.LOG_TAG, "Error al cargar los contactos", ex);

        }

    }


    public void fab_click(View view) {
        Intent intent = new Intent(this, Primera.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == historialList.getId())
            getMenuInflater().inflate(R.menu.contextual_menu_contact, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.action_consultar_contact )
        {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final int historialID = (int) info.id;
            Intent intent = new Intent(this, Inicio.class);

            intent.putExtra(Inicio.PARAM_IDHISTORIAL,String.valueOf(historialID) );
            startActivity(intent);
            Log.e(AppContext.LOG_TAG, "Se consulto el contacto : "+id);

        }
        if (id == R.id.action_delete_contact) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final int historialID = (int) info.id;

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Eliminar Historial")
                    .setMessage("¿Esta seguro(a) que desea eliminar el historial seleccionado?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            try {
                                HistorialBrl brl = new HistorialBrl();
                                brl.delete(historialID);
                                Toast.makeText(Lista.this, "Se eliminó el contacto", Toast.LENGTH_SHORT).show();
                                Log.e(AppContext.LOG_TAG, "Se eliminó el contacto");
                                loadContacts();
                            } catch (Exception e) {
                                Toast.makeText(Lista.this, "No se pudo eliminar el contacto", Toast.LENGTH_SHORT).show();
                                Log.e(AppContext.LOG_TAG, "No se pudo eliminar el Contacto");
                                Log.e(AppContext.LOG_TAG, "Error al eliminar el contacto", e);

                            }


                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();







        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Se presiono la opción settings", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_logout) {
            Toast.makeText(this, "Se presiono la opción log out", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
