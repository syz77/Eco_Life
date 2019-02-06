package com.laguna.sergio.ecolife.Datos.Sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import com.laguna.sergio.ecolife.Conexion;
import com.laguna.sergio.ecolife.Datos.ecolifedb;
import com.laguna.sergio.ecolife.R;


import com.laguna.sergio.ecolife.Datos.EcoLifeDBHelper;

public class EcoLifeSyncAdapter extends AbstractThreadedSyncAdapter {

    public final String LOG_TAG = EcoLifeSyncAdapter.class.getSimpleName();

    // 60 seconds (1 minute) * 180 = 3 hours
    public static final int SYNC_INTERVAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;
    ContentResolver mContentResolver;


    public EcoLifeSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);

        mContentResolver = context.getContentResolver();

    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        Log.d(LOG_TAG, "Starting sync");
        //khkjkhgk


        try{
            Cursor talonario = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO,null,
                    ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ONLINE+"=0",null,null);
            Cursor ventacredito = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CREDITO,null,
                    ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_ONLINE+"=0",null,null);
            Cursor ventacontado = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CONTADO,null,
                    ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_ONLINE+"=0",null,null);
            Cursor detallecontado = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_DETALLE_CONTADO,null,
                    ecolifedb.EcoLifeEntry.COLUMN_DETALLEC_ONLINE+"=0",null,null);
            Cursor gps = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,null,
                    ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE+"=0",null,null);
            Cursor cobro = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_COBRO,null,
                    ecolifedb.EcoLifeEntry.COLUMN_COBRO_ONLINE+"=0",null,null);
            Cursor persona = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA, null,
                    ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ONLINE+"=0",null,null );

            if(persona != null){
                insertData(persona,"persona");
            }
            if(gps != null){
                insertData(gps,"gps");
            }
            if(ventacontado != null){
                insertData(ventacontado,"ventacontado");
            }
            if (detallecontado !=null){
                insertData(detallecontado,"detallecontado");
            }
            if(gps != null){
                insertData(gps,"gps");
            }
            if(talonario != null){
                insertData(talonario,"talonario");
            }
            if (ventacredito !=null){
                insertData(ventacredito,"ventacredito");
            }
            if (cobro !=null){
                insertData(cobro,"cobro");
            }

            } catch (Exception e) {
                Log.e(LOG_TAG, "Error passing data ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                }
    }

    private void insertData(Cursor c, String b){
        Conexion con=new Conexion();
        String online="1";
        ContentValues content=new ContentValues();
        String respuesta,where;
        String[] args;
        switch (b){
            case "talonario":
                String Testado,Tfecha,Tid_sup,Tid;
                where=ecolifedb.EcoLifeEntry._TALONARIOID+"=?";
                while (c.moveToNext()){
                    Tid=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._TALONARIOID));
                    Testado=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO));
                    Tfecha=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_FECHA_C));
                    Tid_sup=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_SUPERVISORID));
                    respuesta=con.InsertarTalonario(Testado,Tfecha,Tid_sup);
                    respuesta=cortar(respuesta);
                    args=new String[]{Tid};
                    content.put(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_NUBEID,Integer.parseInt(respuesta));
                    content.put(ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ONLINE, online);
                    mContentResolver.update(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO,content,where,args);
                }

                break;
            case "gps":
                String Glatitud,Glongitud,Gid;
                where=ecolifedb.EcoLifeEntry._GPSID+"=?";
                while (c.moveToNext()){
                    Gid=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._GPSID));
                    Glatitud=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_GPS_LATITUD));
                    Glongitud=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_GPS_LONGITUD));
                    respuesta=con.InsertarGPS(Glatitud,Glongitud);
                    respuesta=cortar(respuesta);
                    args=new String[]{Gid};
                    content.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_NUBEID,Integer.parseInt(respuesta));
                    content.put(ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE, online);
                    mContentResolver.update(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS,content,where,args);
                }

                break;
            case "persona":
                String Pid,Pnombre,Ppassword,Pcorreo,Ptelefono,Pfecha,Pci,Pestado,Prolid;
                where=ecolifedb.EcoLifeEntry._PERSONAID+"=?";
                while (c.moveToNext()){
                    Pid=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._PERSONAID));
                    Pnombre=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NOMBRE));
                    Ppassword=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_PASSWORD));
                    Pcorreo=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CORREO));
                    Ptelefono=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TELEFONO));
                    Pfecha=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_COBRO_FECHA));
                    Pci=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CI));
                    Pestado=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ESTADO));
                    Prolid=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ROLID));
                    respuesta=con.InsertRegistro(Pnombre,Pcorreo,Ppassword,Ptelefono,Pfecha,Prolid,Pci,Pestado);
                    respuesta=cortar(respuesta);
                    args=new String[]{Pid};
                    content.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NUBEID,Integer.parseInt(respuesta));
                    content.put(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ONLINE, online);
                    mContentResolver.update(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA,content,where,args);
                }

                break;
            case "venta_contado":
                String VCTid, VCTnombre, VCTtelefono, VCTzona, VCTvendedor, VCTdireccion,VCTfecha,VCTprodid,VCTsupidnube;
                where=ecolifedb.EcoLifeEntry._VENTA_CONTADOID+"=?";
                while (c.moveToNext()){
                    VCTid=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._VENTA_CONTADOID));
                    VCTnombre=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_NOMBRE));
                    VCTtelefono=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_TELEFONO));
                    VCTzona=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_ZONA));
                    VCTvendedor=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_VENDEDOR));
                    VCTdireccion=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_DIRECCION));
                    VCTfecha=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_FECHA));
                    VCTprodid=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_PRODID));
                    VCTsupidnube=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_SUPNUBEID));
                    respuesta=con.InsertarVentaContado(VCTnombre,VCTtelefono,VCTdireccion,VCTzona,VCTfecha,VCTvendedor,VCTsupidnube
                    ,VCTprodid);
                    respuesta=cortar(respuesta);
                    args=new String[]{VCTid};
                    content.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_NUBEID,Integer.parseInt(respuesta));
                    content.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_ONLINE, online);
                    mContentResolver.update(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CONTADO,content,where,args);
                }

                break;
            case "venta_credito":
                String VCid, VCnombre, VCtelefono, VCzona, VCvendedor, VCdireccion,VCfecha,VCprodid,VCfoto,VCtalonarioidnube;
                where=ecolifedb.EcoLifeEntry._VENTA_CREDITOID+"=?";
                while (c.moveToNext()){
                    VCid=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._VENTA_CREDITOID));
                    VCnombre=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_NOMBRE));
                    VCtelefono=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TELEFONO));
                    VCzona=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_ZONA));
                    VCvendedor=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_VENDEDOR));
                    VCdireccion=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_DIRECCION));
                    VCfecha=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_FECHA));
                    VCprodid=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_PRODID));
                    VCfoto=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_FOTO));
                    VCtalonarioidnube=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TALONARIONUBEID));
                    respuesta=con.InsertarVentaCredito(VCnombre,VCtelefono,VCdireccion,VCzona,VCfecha,VCvendedor,VCfoto,VCprodid,
                            VCtalonarioidnube);
                    respuesta=cortar(respuesta);
                    args=new String[]{VCid};
                    content.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_NUBEID,Integer.parseInt(respuesta));
                    content.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_ONLINE, online);
                    mContentResolver.update(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CREDITO,content,where,args);
                }

                break;
            case "detalle_contado":
                String DCid,DCprod_id,DCventa_nubeid;
                where=ecolifedb.EcoLifeEntry._DETALLE_CONTADOID+"=?";
                while (c.moveToNext()){
                    DCid=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._DETALLE_CONTADOID));
                    DCprod_id=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_DETALLEC_PRODID));
                    DCventa_nubeid=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_DETALLEC_VENTANUBEID));
                    respuesta=con.InsertarDetalleContado(DCventa_nubeid,DCprod_id);
                    respuesta=cortar(respuesta);
                    args=new String[]{DCid};
                    content.put(ecolifedb.EcoLifeEntry.COLUMN_DETALLEC_NUBEID,Integer.parseInt(respuesta));
                    content.put(ecolifedb.EcoLifeEntry.COLUMN_DETALLEC_ONLINE, online);
                    mContentResolver.update(ecolifedb.EcoLifeEntry.CONTENT_URI_DETALLE_CONTADO,content,where,args);
                }


                break;
            case "cobro":
                String Cid, Cmonto,Cnro_cuota,Csubtotal,Cfecha,Ccreditonube_id, Cgpsnube_id;
                where=ecolifedb.EcoLifeEntry._COBROID+"=?";
                while (c.moveToNext()){
                    Cid=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._COBROID));
                    Cmonto=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_COBRO_MONTO));
                    Cnro_cuota=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_COBRO_NRO_CUOTA));
                    Csubtotal=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_COBRO_SUBTOTAL));
                    Cfecha=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_COBRO_FECHA));
                    Ccreditonube_id=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_COBRO_CREDITONUBEID));
                    Cgpsnube_id=c.getString(c.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_COBRO_GPSNUBEID));
                    respuesta=con.InsertarCobro(Cmonto,Cnro_cuota,Csubtotal,Cfecha,Ccreditonube_id,Cgpsnube_id);
                    respuesta=cortar(respuesta);
                    args=new String[]{Cid};
                    content.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_NUBEID,Integer.parseInt(respuesta));
                    content.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_ONLINE, online);
                    mContentResolver.update(ecolifedb.EcoLifeEntry.CONTENT_URI_COBRO,content,where,args);
                }

                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown table " + b);

        }
    }

    public String cortar(String s){
        int n=s.length();
        String sub="";
        int d;
        if (n==37) {
            sub = s.substring(18, 19);
        }
        if (n==39){
            sub = s.substring(18, 20);
        }
        if(n==41){
            sub = s.substring(18,21);
        }
        if(n==43){
            sub = s.substring(18,22);
        }
        if (n==45){
            sub = s.substring(18,23);
        }
        if (n==47){
            sub = s.substring(18,24);
        }
        if (n==49){
            sub = s.substring(18,25);
        }
        return sub;
    }




    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = ecolifedb.CONTENT_AUTHORITY;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    /**
     * Helper method to have the sync adapter sync immediately
     * @param context The context used to access the account service
    */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                ecolifedb.CONTENT_AUTHORITY, bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.*/

    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), "http://u209922277.hostingerapp.com");


        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

            /*
             * Add the account and account type, no password or user data
             * If successful, return the Account object, otherwise report an error.*/

            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.*/


            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
        */
       // EcoLifeSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
           */
        //ContentResolver.setSyncAutomatically(newAccount, ecolifedb.CONTENT_AUTHORITY, true);

        /*
         * Finally, let's do a sync to get things started
        */
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}
