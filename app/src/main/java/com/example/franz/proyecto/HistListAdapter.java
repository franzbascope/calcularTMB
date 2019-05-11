package com.example.franz.proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.franz.proyecto.model.Historial;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistListAdapter extends BaseAdapter {

    private Context context;
    private List<Historial> hists;

    public HistListAdapter(Context context, List<Historial> hists) {
        this.context = context;
        this.hists = hists;
    }

    @Override
    public int getCount() {
        return hists.size();
    }

    @Override
    public Object getItem(int i) {
        return hists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return hists.get(i).getHISTORIAL_ID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_hist_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else
            holder = (ViewHolder) view.getTag();

        Historial hist = hists.get(i);


        holder.labelFecha.setText("Fecha : "+fechaFormat(hist.getFECHA()));
        holder.labelPeso.setText("Peso : "+String.valueOf(hist.getPESO()));

        return view;
    }

    public String fechaFormat(String s)
    {
        String resultado= s.substring(6,8)+"/"+s.substring(4,6)+"/"+s.substring(0,4);
       return resultado;
    }

    class ViewHolder{

        TextView labelFecha;
        TextView labelPeso;

        public ViewHolder(View view){
            labelFecha = view.findViewById(R.id.lbl_fecha);
            labelPeso = view.findViewById(R.id.lbl_peso);
        }

    }

}
