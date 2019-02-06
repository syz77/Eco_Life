package com.laguna.sergio.ecolife.Datos;

import android.content.ContentResolver;
import android.content.ContentValues;

public class persona {
    private String Nombre,Correo,Password,Telefono,Fecha,Ci,Estado,Rol,Online;

    public persona(){
        Nombre=null;Correo=null;Password=null;Telefono=null;Fecha=null;Ci=null;
        Estado=null;Rol=null;Online="0";
    }

    public persona(String nombre,String correo,String password, String telefono,String fecha,String ci,String estado
                    ,String rol){
        Nombre=nombre;Correo=correo;Password=password;Telefono=telefono;Fecha=fecha;Ci=ci;
        Estado=estado;Rol=rol;Online="0";
    }

    public void insertar(persona p,ContentResolver r){
        ContentValues c=new ContentValues();
        c.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NOMBRE,p.Nombre);
        c.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CORREO,p.Correo);
        c.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_PASSWORD,p.Password);
        c.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TELEFONO,p.Telefono);
        c.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_FECHA,p.Fecha);
        c.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CI,p.Ci);
        c.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ESTADO,p.Estado);
        c.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ROLID,p.Rol);
        c.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ONLINE,p.Online);
        r.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA,c);
    }
}
