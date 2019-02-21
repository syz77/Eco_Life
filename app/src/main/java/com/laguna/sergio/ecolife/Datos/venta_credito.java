package com.laguna.sergio.ecolife.Datos;

import android.content.ContentResolver;
import android.content.ContentValues;

public class venta_credito {
    private String Nombre,Telefono,Zona,Vendedor,Direccion,Fecha,Prodid,Talonarioid,Talonarionubeid,Foto,Fotonombre,Online;

    public venta_credito(){
        Nombre=null;Telefono=null;Zona=null;Vendedor=null;Direccion=null;Fecha=null;Prodid=null;
        Talonarioid=null;Talonarionubeid=null; Foto=null; Fotonombre=null; Online="0";
    }
    public venta_credito(String nombre, String telefono, String zona,String vendedor, String direccion, String fecha
            ,String prodid, String talonarioid, String talonarionubeid,String foto,String nombrefoto){
        Nombre=nombre;Telefono=telefono;Zona=zona;Vendedor=vendedor;Direccion=direccion;Fecha=fecha;Prodid=prodid;
        Talonarioid=talonarioid;Talonarionubeid=talonarionubeid; Foto=foto; Fotonombre=nombrefoto; Online="0";
    }

    public void insert(venta_credito vc, ContentResolver r){
        ContentValues values=new ContentValues();
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_NOMBRE,vc.Nombre);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TELEFONO,vc.Telefono);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_ZONA,vc.Zona);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_VENDEDOR,vc.Vendedor);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_DIRECCION,vc.Direccion);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_FECHA,vc.Fecha);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_PRODID,vc.Prodid);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TALONARIOPID,vc.Talonarioid);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TALONARIONUBEID,vc.Talonarionubeid);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_FOTO,vc.Foto);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_FOTO_NOMBRE,vc.Fotonombre);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_ONLINE,vc.Online);
        r.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CREDITO,values);
    }
}
