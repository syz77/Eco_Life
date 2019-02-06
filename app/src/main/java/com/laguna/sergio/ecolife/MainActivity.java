package com.laguna.sergio.ecolife;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.laguna.sergio.ecolife.Datos.Sync.EcoLifeSyncAdapter;
import com.laguna.sergio.ecolife.Datos.ecolifedb;

public class MainActivity extends AppCompatActivity {

    ContentResolver mResolver;
    TableObserver gps_obs;
    TableObserver talonario_obs;
    TableObserver ventaContado_obs;
    TableObserver ventaCredito_obs;
    TableObserver persona_obs;
    TableObserver detalleContado_obs;
    TableObserver cobro_obs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mResolver = getContentResolver();
        setContentView(R.layout.activity_main);
        persona_obs = new TableObserver(null);
        gps_obs = new TableObserver(null);
        talonario_obs = new TableObserver(null);
        ventaContado_obs = new TableObserver(null);
        ventaCredito_obs = new TableObserver(null);
        detalleContado_obs = new TableObserver(null);
        cobro_obs = new TableObserver(null);

        mResolver.registerContentObserver(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,true,gps_obs);
        mResolver.registerContentObserver(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA,true,persona_obs);
        mResolver.registerContentObserver(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO,true,talonario_obs);
        mResolver.registerContentObserver(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CONTADO,true,ventaContado_obs);
        mResolver.registerContentObserver(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CREDITO,true,ventaCredito_obs);
        mResolver.registerContentObserver(ecolifedb.EcoLifeEntry.CONTENT_URI_DETALLE_CONTADO,true,detalleContado_obs);
        mResolver.registerContentObserver(ecolifedb.EcoLifeEntry.CONTENT_URI_COBRO,true,cobro_obs);

        /*String latitud, longitud, online1;
        latitud="123";
        longitud="321";
        online1="0";
        ContentValues gps = new ContentValues();
        ContentValues  gps2 = new ContentValues();
        ContentValues  gps3 = new ContentValues();
        ContentValues  gps4 = new ContentValues();
        ContentValues  gps5 = new ContentValues();

        gps.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LATITUD, latitud);
        gps.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_LONGITUD, longitud);
        gps.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE, online1);
        mResolver.insert(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,gps);
        EcoLifeSyncAdapter.syncImmediately(this);*/


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent intent = new Intent(MainActivity.this, NavegacionMenu.class);

                startActivity(intent);
            }
        }, 2000);
    }
    protected void onDestroy(){
        super.onDestroy();
        mResolver.unregisterContentObserver(gps_obs);
        mResolver.unregisterContentObserver(talonario_obs);
        mResolver.unregisterContentObserver(ventaCredito_obs);
        mResolver.unregisterContentObserver(ventaContado_obs);
        mResolver.unregisterContentObserver(persona_obs);
        mResolver.unregisterContentObserver(detalleContado_obs);
        mResolver.unregisterContentObserver(cobro_obs);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //Acción
        }
        return false;
    }
    public class TableObserver extends ContentObserver {

        public TableObserver(Handler handler) {
            super(handler);
        }
        /*
         * Define a method that's called when data in the
         * observed content provider changes.
         * This method signature is provided for compatibility with
         * older platforms.
         */
        @Override
        public void onChange(boolean selfChange) {
            /*
             * Invoke the method signature available as of
             * Android platform version 4.1, with a null URI.
             */
            onChange(selfChange, null);
        }
        /*
         * Define a method that's called when data in the
         * observed content provider changes.
         */
        @Override
        public void onChange(boolean selfChange, Uri changeUri) {
            /*
             * Ask the framework to run your sync adapter.
             * To maintain backward compatibility, assume that
             * changeUri is null.
             */
            EcoLifeSyncAdapter.syncImmediately(getApplicationContext());
        }
    }
}
