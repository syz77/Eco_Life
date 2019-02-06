package com.laguna.sergio.ecolife.Datos;

import android.content.ContentResolver;
import android.content.ContentValues;

public class talonario {
    private String Estado,FechaC,Supervisorid,Supervisornubeid,Online;

    public talonario(){
        Estado=null;FechaC=null;Supervisorid=null;Supervisornubeid=null; Online="0";
    }
    public talonario(String estado, String fechac, String supervisorid,String supervisornubeid){
        Estado=estado;FechaC=fechac;Supervisorid=supervisorid;Supervisornubeid=supervisornubeid; Online="0";
    }

    public void insert(talonario t, ContentResolver r){
        ContentValues values=new ContentValues();
        values.put(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO,t.Estado);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_FECHA_C,t.FechaC);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_SUPERVISORID,t.Supervisorid);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_SUPERVISORNUBEID,t.Supervisornubeid);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ONLINE,t.Online);
        r.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO,values);
    }
}
