package com.laguna.sergio.ecolife.Datos;

import android.content.ContentResolver;
import android.content.ContentValues;

public class venta_contado {
    private String Nombre,Telefono,Zona,Vendedor,Direccion,Fecha,Prodid,Supid,Supnubeid,Online;

    public venta_contado(){
        Nombre=null;Telefono=null;Zona=null;Vendedor=null;Direccion=null;Fecha=null;Prodid=null;
        Supid=null;Supnubeid=null; Online="0";
    }
    public venta_contado(String nombre, String telefono, String zona,String vendedor, String direccion, String fecha
                        ,String prodid, String supid, String supnubeid){
        Nombre=nombre;Telefono=telefono;Zona=zona;Vendedor=vendedor;Direccion=direccion;Fecha=fecha;Prodid=prodid;
        Supid=supid;Supnubeid=supnubeid; Online="0";
    }

    public void insert(venta_contado vct, ContentResolver r){
        ContentValues values=new ContentValues();
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_NOMBRE,vct.Nombre);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_TELEFONO,vct.Telefono);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_ZONA,vct.Zona);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_VENDEDOR,vct.Vendedor);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_DIRECCION,vct.Direccion);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_FECHA,vct.Fecha);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_PRODID,vct.Prodid);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_SUPID,vct.Supid);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_SUPNUBEID,vct.Supnubeid);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_ONLINE,vct.Online);
        r.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CONTADO,values);
    }
}
