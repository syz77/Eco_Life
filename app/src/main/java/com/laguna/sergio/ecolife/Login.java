package com.laguna.sergio.ecolife;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.laguna.sergio.ecolife.Datos.persona;
import com.laguna.sergio.ecolife.Datos.gps;

import com.laguna.sergio.ecolife.Datos.Sync.EcoLifeSyncAdapter;
import com.laguna.sergio.ecolife.Datos.ecolifedb;

import com.laguna.sergio.ecolife.Datos.EcoLifeDBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText txtUser, txtPass;
    TextView btnIngresar;
    ContentResolver mContentResolver;
    String email,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUser=(EditText)findViewById(R.id.editUser);
        txtPass=(EditText)findViewById(R.id.editPass);
        btnIngresar=(TextView)findViewById(R.id.textView2);
        mContentResolver=this.getContentResolver();
        btnIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
             public void onClick(View v){
             Thread tr=new Thread(){
                @Override
                public void run() {
                final Conexion con=new Conexion();
                email=txtUser.getText().toString();
                pass=txtPass.getText().toString();
                final String res= con.login(email,pass);
                final String tal=con.todoTalonario(email,pass);
                final String gps=con.todoGPS(email,pass);
                final String vc=con.todoVentaCredito(email,pass);
                final String c=con.todoCobro(email,pass);
                runOnUiThread(new Runnable() {
                @Override
                public void run() {
                int r=con.objJson(res);
                if (r>0){
                    persona p=new persona();
                    p.login(res,mContentResolver);
                    if(con.objJson(tal)>0) {
                        SincroT(tal);
                    }
                    /*if(con.objJson(gps)>0) {
                        SincroGPS(gps);
                    }*/
                    if(con.objJson(vc)>0) {
                        SincroVC(vc);
                    }
                    if(con.objJson(c)>0){
                        SincroC(c);
                    }
                    Intent i= new Intent(Login.this,NavegacionMenu.class);
                    startActivity(i);
                    //Toast.makeText(getApplicationContext(),Integer.toString(x), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Usuario o password incorrectos", Toast.LENGTH_SHORT).show();
                }

                }
                });
                }
                };
                tr.start();
                }
                }
        );
    }
    /*public void Sincro(String email, String pass)
    {
        SincroT(email,pass);
        SincroGPS(email,pass);
        SincroVC(email,pass);
        SincroC(email,pass);
    }*/
    public void SincroT(String s){
        try {
            JSONArray json = new JSONArray(s);
            Cursor pers=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA,null,
                    ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TOKEN+"=1",null,null);
            pers.moveToNext();
            String online="1";
            String idp,idn;
            idp=pers.getString(pers.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._PERSONAID));
            idn=pers.getString(pers.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NUBEID));
            for (int i = 0; i < json.length(); i++) {
                JSONObject c = json.getJSONObject(i);
                String id = c.getString("id");
                String estado=c.getString("estado");
                String fecha_c=c.getString("fecha_c");
                ContentValues values = new ContentValues();
                values.put(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_NUBEID, id);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO, estado);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_FECHA_C,fecha_c);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_SUPERVISORID,idp);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_SUPERVISORNUBEID,idn);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ONLINE,online);
                mContentResolver.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO, values);
            }
            pers.close();
        }catch (JSONException e){

        }
    }
    public void SincroVC(String s){
        String id,nombre,telefono,direccion,zona,fecha,vendedor,foto,id_prod,id_talonario,online;
        try {
            JSONArray json = new JSONArray(s);
            online="1";
            for (int i = 0; i < json.length(); i++) {
                JSONObject c = json.getJSONObject(i);
                id = c.getString("id");
                nombre=c.getString("nombre");
                telefono=c.getString("telefono");
                direccion=c.getString("direccion");
                zona=c.getString("zona");
                fecha=c.getString("fecha");
                vendedor=c.getString("vendedor");
                foto=c.getString("foto");
                id_prod=c.getString("id_prod");
                id_talonario=c.getString("id_talonario");
                String[] args= new String[]{id_talonario};
                Cursor v = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO, null,
                        ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_NUBEID + "=?", args, null);
                v.moveToNext();
                String localid=v.getString(v.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._TALONARIOID));
                ContentValues values = new ContentValues();
                values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_NUBEID, id);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_NOMBRE, nombre);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TELEFONO,telefono);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_DIRECCION, direccion);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_ZONA, zona);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_FECHA,fecha);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_VENDEDOR, vendedor);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_FOTO, foto);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_PRODID,id_prod);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TALONARIONUBEID,id_talonario);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TALONARIOPID,localid);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_ONLINE,online);
                mContentResolver.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CREDITO, values);
                v.close();
            }
        }catch (JSONException e){

        }
    }
    public void SincroGPS(String s){
        String online="1";
        try {
            JSONArray json = new JSONArray(s);
            for (int i = 0; i < json.length(); i++) {
                JSONObject c = json.getJSONObject(i);
                String id = c.getString("id");
                String latitud=c.getString("latitud");
                String longitud=c.getString("longitud");
                ContentValues values = new ContentValues();
                values.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_NUBEID, id);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LATITUD, latitud);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LONGITUD,longitud);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE,online);
                mContentResolver.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS, values);
            }
        }catch (JSONException e){

        }
    }
    public void SincroC(String s){
        String idnube,monto,nro_cuota,subtotal,fecha,idcreditonube,idgpsnube,idcreditolocal,idgpslocal;
        String online="1";
        try {
            JSONArray json = new JSONArray(s);
            for (int i = 0; i < json.length(); i++) {
                JSONObject c = json.getJSONObject(i);
                idnube = c.getString("id");
                monto=c.getString("monto");
                nro_cuota=c.getString("nro_cuota");
                subtotal=c.getString("subtotal");
                fecha=c.getString("fecha");
                idcreditonube=c.getString("id_credito");
                idgpsnube=c.getString("id_gps");
                String[] args= new String[]{idcreditonube};
                //String[] args2=new String[]{idgpsnube};
                Cursor v = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CREDITO, null,
                        ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_NUBEID + "=?", args, null);
                v.moveToNext();
                idcreditolocal=v.getString(v.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._VENTA_CREDITOID));
                /*Cursor g = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS, null,
                        ecolifedb.EcoLifeEntry.COLUMN_GPS_NUBEID + "=?", args2, null);
                g.moveToNext();
                idgpslocal=g.getString(g.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._GPSID));*/
                ContentValues values = new ContentValues();
                values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_NUBEID, idnube);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_MONTO, monto);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_NRO_CUOTA,nro_cuota);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_SUBTOTAL,subtotal);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_FECHA,fecha);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_CREDITONUBEID,idcreditonube);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_GPSNUBEID,idgpsnube);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_CREDITOID,idcreditolocal);
                values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_ONLINE,online);
                mContentResolver.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_COBRO, values);
                v.close();
            }
        }catch (JSONException e){

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //Acción
        }
        return false;
    }
    //{"persona":[{"0":"7","MAX(id)":"7"}]}

}
