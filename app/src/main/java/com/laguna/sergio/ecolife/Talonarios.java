package com.laguna.sergio.ecolife;

import android.content.ContentResolver;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import com.laguna.sergio.ecolife.Datos.ecolifedb;
import com.laguna.sergio.ecolife.Datos.talonario;

public class Talonarios extends AppCompatActivity {

    List<DataAdapterTalo> DataAdapterClassListR;
    RecyclerView recyclerViewR;
    RecyclerView.Adapter recyclerViewadapterR;
    RecyclerView.LayoutManager recyclerViewlayoutManagerR;
    ArrayList<talonario> TaloList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talonarios);
        DataAdapterClassListR = new ArrayList<>();
        DataAdapterClassListR.clear();
        recyclerViewR = (RecyclerView) findViewById(R.id.recicladorT);
        recyclerViewR.setHasFixedSize(true);
        recyclerViewlayoutManagerR = new LinearLayoutManager(this);
        recyclerViewR.setLayoutManager(recyclerViewlayoutManagerR);
        //generar();
    }


    public List<DataAdapterTalo> generar(ContentResolver mContentResolver){
        DataAdapterClassListR = new ArrayList<>();
        DataAdapterClassListR.clear();
        TaloList=new ArrayList<>();
        //recyclerViewR.setAdapter(recyclerViewadapterR);
        String[] args=new String[]{"0"};
        Cursor talo=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO,null,
                ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO+"!=?",args,null);
        while(talo.moveToNext()){
            talonario t= new talonario();
            DataAdapterTalo GetDataAdapter3 = new DataAdapterTalo();
            String tidn=talo.getString(talo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_NUBEID));
            GetDataAdapter3.setNroTalo(tidn);
            t.Idnube=tidn;
            //SubjectNivelRaidlista.add("raidlvl");
            String tf=talo.getString(talo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_FECHA_C));
            GetDataAdapter3.setFechaTalo(tf);
            t.FechaC=tf;
            String te=talo.getString(talo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO));
            GetDataAdapter3.setEstado(trad(te));
            t.Estado=te;
            String tid=talo.getString(talo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._TALONARIOID));
            t.Id=tid;
            String[] args2=new String[]{tid};
            Cursor nrovent= mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CREDITO,null,
                    ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TALONARIOPID+"=?",args2,null);
            String nroVentas=Integer.toString(nrovent.getCount());
            GetDataAdapter3.setNroVentas(nroVentas);
            int i=0;
            if(nrovent.getCount()!=0) {
                while(nrovent.moveToNext()) {
                    String idvent=nrovent.getString(nrovent.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._VENTA_CREDITOID));
                    String[] args3=new String[]{idvent};
                    Cursor nroventComp = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_COBRO, null,
                            ecolifedb.EcoLifeEntry.COLUMN_COBRO_CREDITOID+"=?", args3,null);
                    if(nroventComp.getCount()!=0){
                        nroventComp.moveToLast();
                        String n=nroventComp.getString(nroventComp.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_COBRO_SUBTOTAL));
                        int subtotal=Integer.parseInt(n);
                        if(subtotal==140){
                            i++;
                        }
                    }
                    nroventComp.close();
                }
            }
            GetDataAdapter3.setNroVentasCompletadas(Integer.toString(i));
            DataAdapterClassListR.add(GetDataAdapter3);
            nrovent.close();
            TaloList.add(t);
        }
        talo.close();

        return DataAdapterClassListR;
        //recyclerViewadapterR = new RecyclerAdapTalo(DataAdapterClassListR, this);
        //recyclerViewR.setAdapter(recyclerViewadapterR);
    }
    public String trad(String a){
        String f="";
        if(a.equals("1")){
            f="Activo";
        }else if (a.equals("2")){
            f="Pasivo";
        }
        return f;
    }
    public ArrayList<talonario> talosplease(){
        return TaloList;
    }

}
