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

public class Login extends AppCompatActivity {
    EditText txtUser, txtPass;
    TextView btnIngresar;
    Button prueba;
    ContentResolver mContentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUser=(EditText)findViewById(R.id.editUser);
        txtPass=(EditText)findViewById(R.id.editPass);
        btnIngresar=(TextView)findViewById(R.id.textView2);
        prueba=(Button)findViewById(R.id.button);
        mContentResolver=this.getContentResolver();
        //final EcoLifeDBHelper admin = new EcoLifeDBHelper(this);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        /*String latitud = "1";
        String longitud = "1";
        String latitud2 = "2";
        String longitud2 = "2";
        String latitud3 = "0";
        String longitud3 = "0";
        String latitud4 = "9";
        String longitud4 = "9";
        String latitud5 = "6";
        String longitud5 = "6";
        String online0="0";
        String online1="1";
        ContentValues  gps = new ContentValues();
        ContentValues  gps2 = new ContentValues();
        ContentValues  gps3 = new ContentValues();
        ContentValues  gps4 = new ContentValues();
        ContentValues  gps5 = new ContentValues();

        gps.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LATITUD, latitud);
        gps.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LONGITUD, longitud);
        gps.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE, online1);
        gps2.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LATITUD, latitud2);
        gps2.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LONGITUD, longitud2);
        gps2.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE, online1);
        gps3.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LATITUD, latitud3);
        gps3.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LONGITUD, longitud3);
        gps3.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE, online0);
        gps4.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LATITUD, latitud4);
        gps4.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LONGITUD, longitud4);
        gps4.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE, online0);
        gps5.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LATITUD, latitud5);
        gps5.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LONGITUD, longitud5);
        gps5.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE, online0);
        mContentResolver.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,gps);
        mContentResolver.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,gps2);
        mContentResolver.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,gps3);
        mContentResolver.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,gps4);
        mContentResolver.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,gps5);
       // bd.insert(ecolifedb.EcoLifeEntry.GPS_TABLE, null, gps);
        //bd.close();
        String latitud, longitud, online1;
        latitud="661";
        longitud="616";
        online1="0";
        ContentValues  gps = new ContentValues();
        ContentValues  gps2 = new ContentValues();
        ContentValues  gps3 = new ContentValues();
        ContentValues  gps4 = new ContentValues();
        ContentValues  gps5 = new ContentValues();

        gps.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LATITUD, latitud);
        gps.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LONGITUD, longitud);
        gps.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE, online1);
        mContentResolver.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,gps);*/

        btnIngresar.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               /*Thread tr=new Thread(){
                                                   @Override
                                                   public void run() {
                                               String nombre,correo,password,telefono,fecha,ci,estado,rolid,online;
                                               nombre="Many";correo="rivera";password="eltigre";telefono="666";
                                               fecha="1000-10-21";ci="123";estado="0";rolid="1";online="0";
                                               insertPersona(nombre,correo,password,telefono,fecha,ci,estado,rolid,online);}};
                                               tr.start();*/
                                               String nombre,correo,password,telefono,fecha,ci,estado,rolid,online;
                                               nombre="Many";correo="riveraE2";password="eltigre";telefono="666";
                                               fecha="1000-10-21";ci="123456";estado="0";rolid="1";online="0";
                                               persona p=new persona(nombre,correo,password,telefono,fecha,ci,estado,rolid);
                                               p.insertar(p,mContentResolver);
                                               String nubeid="";
                                               String lat = "";
                                               String lon = "";
                                               String id = "";
                                               String ol = "";
                                               Cursor d = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA, null,
                                                       null, null, null);
                                               while(d.moveToNext()) {
                                                   id = id + d.getString((d.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._PERSONAID)));
                                                   lat = lat + d.getString(d.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NOMBRE));
                                                   lon = lon + d.getString(d.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CORREO));
                                                   ol = ol + d.getString(d.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ONLINE));
                                                   nubeid = nubeid+ d.getString(d.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NUBEID));

                                               }
                                               Toast.makeText(getApplicationContext(), id + " " + lat + " " + lon + " " + ol+" "+nubeid, Toast.LENGTH_SHORT).show();
                                               /*Thread tr=new Thread(){
                                                   @Override
                                                   public void run() {
                                                       final Conexion con=new Conexion();
                                                       final String res= con.login(txtUser.getText().toString(),txtPass.getText().toString());
                                                       runOnUiThread(new Runnable() {
                                                           @Override
                                                           public void run() {
                                                               int r=con.objJson(res);

                                                               if (r>0){
                                                                   Intent i= new Intent(Login.this,NavegacionMenu.class);
                                                                   startActivity(i);
                                                                   Toast.makeText(getApplicationContext(),res, Toast.LENGTH_SHORT).show();
                                                               }else{
                                                                   Toast.makeText(getApplicationContext(),"Usuario o password incorrectos", Toast.LENGTH_SHORT).show();
                                                               }

                                                           }
                                                       });
                                                   }
                                               };
                                               tr.start();*/
                                           }
                                       }
        );
        prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String latitud, longitud;
                latitud="661666";
                longitud="616777";
                gps g=new gps(latitud,longitud);
                g.insert(g,mContentResolver);
                String nubeid="";
                String lat = "";
                String lon = "";
                String id = "";
                String ol = "";
                Cursor d = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS, null,
                        null, null, null);
                while(d.moveToNext()) {
                    id = id + d.getString((d.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._GPSID)));
                    lat = lat + d.getString(d.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_GPS_LATITUD));
                    lon = lon + d.getString(d.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_GPS_LONGITUD));
                    ol = ol + d.getString(d.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE));
                    nubeid = nubeid+ d.getString(d.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_GPS_NUBEID));

                }
                Toast.makeText(getApplicationContext(), id + " " + lat + " " + lon + " " + ol+" "+nubeid, Toast.LENGTH_SHORT).show();
                /*Thread tr=new Thread() {
                    @Override
                    public void run() {
                String latitud, longitud, online1;
                latitud="123";
                longitud="321";
                online1="0";
                ContentValues  gps = new ContentValues();
                ContentValues  gps2 = new ContentValues();
                ContentValues  gps3 = new ContentValues();
                ContentValues  gps4 = new ContentValues();
                ContentValues  gps5 = new ContentValues();

                gps.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LATITUD, latitud);
                gps.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LONGITUD, longitud);
                gps.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE, online1);
                mContentResolver.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,gps);
               /* Thread tr=new Thread(){
                    @Override
                    public void run() {
                        final Conexion con=new Conexion();
                        String la="1";
                        String lo="2";
                        final String res= con.InsertarGPS(la,lo);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int r=con.objJson(res);
                                String cut=cortar(res);

                                //if (r>0){
                                   // Intent i= new Intent(Login.this,NavegacionMenu.class);
                                    //startActivity(i);
                                    txtUser.setText(cut);
                                    Toast.makeText(getApplicationContext(),res+" "+res.length(), Toast.LENGTH_SHORT).show();
                                //}else{
                                //    Toast.makeText(getApplicationContext(),"Usuario o password incorrectos", Toast.LENGTH_SHORT).show();
                                //}

                            }
                        });
                    }
                };
                tr.start();


                        //SQLiteDatabase bd = admin.getWritableDatabase();
                        //Cursor prueba = bd.rawQuery("select * from gps", null);
                        String[] args;
                        //};
                        String w = "1";
                        //ContentValues cont=new ContentValues();
                        //cont.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE,w);
                        //String wher = ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE + "=?";
                        //mContentResolver.update(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,cont,wher,whereArgs);
                        //Cursor c = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS, null,
                         //       ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE + "=0", null, null);


                        //if (test.moveToFirst()) {
                        //test.moveToFirst();
                        // } else
                        //     Toast.makeText(getApplicationContext(), "No existe una persona con dicho dni",
                        //            Toast.LENGTH_SHORT).show();
                        //bd.close();
                        //}
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Cursor d = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS, null,
                                        null, null, null);
                                while(d.moveToNext()) {
                                    id = id + d.getString((d.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._GPSID)));
                                    lat = lat + d.getString(d.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_GPS_LATITUD));
                                    lon = lon + d.getString(d.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_GPS_LONGITUD));
                                    ol = ol + d.getString(d.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE));

                                }
                                Toast.makeText(getApplicationContext(), id + " " + lat + " " + lon + " " + ol, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                };
            tr.start();*/}
        });


    }
    public void insertGps(String latitud, String longitud,String online1){
        ContentValues  gps = new ContentValues();
        gps.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LATITUD, latitud);
        gps.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LONGITUD, longitud);
        gps.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE, online1);
        mContentResolver.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,gps);
    }
    public void insertPersona(String nombre, String correo, String password, String telefono,String fecha,String ci,
                              String estado, String rolid, String online){
        ContentValues pers=new ContentValues();
        pers.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NOMBRE,nombre);
        pers.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CORREO,correo);
        pers.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_PASSWORD,password);
        pers.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TELEFONO, telefono);
        pers.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_FECHA,fecha);
        pers.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CI,ci);
        pers.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ESTADO,estado);
        pers.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ROLID,rolid);
        pers.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ONLINE,online);
        mContentResolver.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA,pers);
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
