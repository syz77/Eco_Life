package com.laguna.sergio.ecolife.Datos;

import android.content.ContentResolver;
import android.content.ContentValues;

public class cobro {
    private String Monto,Nro_cuota,Subtotal,Fecha,Creditoid,Creditonubeid,Latitud,Longitud,Online;

    public cobro(){
        Monto=null;Nro_cuota=null;Subtotal=null;Fecha=null;Creditoid=null;Creditonubeid=null;Latitud=null;Longitud=null;
        Online="0";
    }
    public cobro(String monto, String nro_cuota,String subtotal,String fecha, String creditoid, String creditonubeid
            ,String latitud, String longitud){
        Monto=monto;Nro_cuota=nro_cuota;Subtotal=subtotal;Fecha=fecha;Creditoid=creditoid;Creditonubeid=creditonubeid;
        Latitud=latitud;Longitud=longitud; Online="0";
    }

    public void insert(cobro c, ContentResolver r){
        ContentValues values=new ContentValues();
        values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_MONTO,c.Monto);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_NRO_CUOTA,c.Nro_cuota);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_LATITUD,c.Latitud);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_LONGITUD,c.Longitud);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_SUBTOTAL,c.Subtotal);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_FECHA,c.Fecha);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_CREDITOID,c.Creditoid);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_CREDITONUBEID,c.Creditonubeid);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_ONLINE,c.Online);
        r.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_COBRO,values);
    }
}
