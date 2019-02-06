package com.laguna.sergio.ecolife.Datos;

import android.content.ContentResolver;
import android.content.ContentValues;

public class gps {
    private String Latitud,Longitud,Online;
    public gps(){
        Latitud=null;Longitud=null;Online="0";
    }

    public gps(String latitud,String longitud){
        Latitud=latitud;Longitud=longitud;Online="0";
    }

    public void insert(gps punto, ContentResolver r){
        ContentValues values=new ContentValues();
        values.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LATITUD,punto.Latitud);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LONGITUD,punto.Longitud);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE,punto.Online);
        r.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,values);
    }
}
