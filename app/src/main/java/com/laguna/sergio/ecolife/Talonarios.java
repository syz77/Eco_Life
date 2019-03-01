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
            String tid=talo.getString(talo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_NUBEID));
            GetDataAdapter3.setNroTalo(tid);
            t.Id=tid;
            //SubjectNivelRaidlista.add("raidlvl");
            String tf=talo.getString(talo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_FECHA_C));
            GetDataAdapter3.setFechaTalo(tf);
            t.FechaC=tf;
            String te=talo.getString(talo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO));
            GetDataAdapter3.setEstado(trad(te));
            t.Estado=te;
            DataAdapterClassListR.add(GetDataAdapter3);
            TaloList.add(t);
        }

        return DataAdapterClassListR;
        //recyclerViewadapterR = new RecyclerAdapTalo(DataAdapterClassListR, this);
        //recyclerViewR.setAdapter(recyclerViewadapterR);
    }
    public String trad(String a){
        String f="";
        if(a.equals("1")){
            f="Activo";
        }else if (a.equals("2")){
            f="Inactivo";
        }
        return f;
    }
    public ArrayList<talonario> talosplease(){
        return TaloList;
    }

}
