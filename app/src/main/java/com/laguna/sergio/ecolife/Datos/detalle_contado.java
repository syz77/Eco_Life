package com.laguna.sergio.ecolife.Datos;

import android.content.ContentResolver;
import android.content.ContentValues;

public class detalle_contado {
    private String Prodid,Ventaid,Ventanubeid,Online;

    public detalle_contado(){
        Prodid=null;Ventaid=null;Ventanubeid=null;Online="0";
    }
    public detalle_contado(String prodid,String ventaid,String ventanubeid){
        Prodid=prodid;Ventaid=ventaid;Ventanubeid=ventanubeid; ;Online="0";
    }

    public void insert(detalle_contado dc, ContentResolver r){
        ContentValues values=new ContentValues();
        values.put(ecolifedb.EcoLifeEntry.COLUMN_DETALLEC_PRODID,dc.Prodid);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_DETALLEC_VENTAID,dc.Ventaid);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_DETALLEC_VENTANUBEID,dc.Ventanubeid);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_DETALLEC_ONLINE,dc.Online);
        r.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_DETALLE_CONTADO,values);
    }
}
