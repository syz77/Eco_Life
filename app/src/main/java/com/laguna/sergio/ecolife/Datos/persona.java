package com.laguna.sergio.ecolife.Datos;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class persona implements Serializable {
    public String Nombre,Correo,Password,Telefono,Fecha,Ci,Estado,Rol,Online,IdUsuario;
    public final String TAG = persona.class.getSimpleName();

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

    public void insertar(String id,String nombre,String correo, String password, String telefono, String fecha
            ,String id_rol,String ci,String estado,String imei,ContentResolver r){
        String online="1";
        String token="1";
        ContentValues values=new ContentValues();
        values.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NUBEID,id);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NOMBRE,nombre);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CORREO,correo);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_PASSWORD,password);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TELEFONO,telefono);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_FECHA,fecha);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CI,ci);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ESTADO,estado);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ROLID,id_rol);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ONLINE,online);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN,token);
        values.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_IMEI,imei);
        r.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA,values);
    }
    public void login(String s,ContentResolver r){
        try {
            JSONArray json = new JSONArray(s);
            //for (int i = 0; i < json.length(); i++) {
                JSONObject c = json.getJSONObject(0);

                String id = c.getString("id");
                String nombre = c.getString("nombre");
                String correo = c.getString("correo");
                String password = c.getString("password");
                String telefono = c.getString("telefono");
                String fecha = c.getString("fecha");
                String id_rol=c.getString("id_rol");
                String ci=c.getString("ci");
                String estado=c.getString("estado");
                String imei=c.getString("imei");
                insertar(id,nombre,correo,password,telefono,fecha,id_rol,ci,estado,imei,r);
            //}

        }catch( final JSONException e){

        }

    }
    public persona[] listaP(String s){
        try {
                JSONArray json = new JSONArray(s);
                persona[] p = new persona[json.length()];
                for (int i = 0; i < json.length(); i++) {
                    JSONObject c = json.getJSONObject(i);
                    String id = c.getString("id");
                    String nombre = c.getString("nombre");
                    String correo = c.getString("correo");
                    String password = c.getString("password");
                    String telefono = c.getString("telefono");
                    String fecha = c.getString("fecha");
                    String id_rol = c.getString("id_rol");
                    String ci = c.getString("ci");
                    String estado = c.getString("estado");
                    persona pers = new persona(nombre, correo, password, telefono, fecha, ci, estado, id_rol);
                    p[i] = pers;
                }
                return p;

        }catch( final JSONException e){
                Log.e(TAG, "Json parsing error:vegeta " + e.getMessage());
                return null;
        }

    }
}
