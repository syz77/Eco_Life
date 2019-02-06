package com.laguna.sergio.ecolife.Datos;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EcoLifeDBHelper extends SQLiteOpenHelper {
    private static final String TAG = EcoLifeDBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "ecolife.db";
    private static final int DATABASE_VERSION = 1;
    Context context;
    SQLiteDatabase db;
    ContentResolver mContentResolver;



    //Used to read data from res/ and assets/
    private Resources mResources;



    public EcoLifeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mResources = context.getResources();
        mContentResolver = context.getContentResolver();

        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_COBRO_TABLE = "CREATE TABLE " + ecolifedb.EcoLifeEntry.COBRO_TABLE + " (" +
                ecolifedb.EcoLifeEntry._COBROID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ecolifedb.EcoLifeEntry.COLUMN_COBRO_MONTO + " INTEGER NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_COBRO_NRO_CUOTA + " INTEGER NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_COBRO_SUBTOTAL + " INTEGER NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_COBRO_FECHA + " DATE NOT NULL, " +
                ecolifedb.EcoLifeEntry.COLUMN_COBRO_CREDITOID + " INTEGER, " +
                ecolifedb.EcoLifeEntry.COLUMN_COBRO_CREDITONUBEID +"INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_COBRO_GPSID + " INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_COBRO_GPSNUBEID + " INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_COBRO_NUBEID + " INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_COBRO_ONLINE + " TEXT" + ");";

        final String SQL_CREATE_PERSONA_TABLE = "CREATE TABLE " + ecolifedb.EcoLifeEntry.PERSONA_TABLE + " (" +
                ecolifedb.EcoLifeEntry._PERSONAID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NOMBRE + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CORREO + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_PERSONA_PASSWORD + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TELEFONO + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_PERSONA_FECHA + " DATE NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CI + " INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ESTADO + " INTEGER NOT NULL, " +
                ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ROLID + " INTEGER NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NUBEID + " INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_PERSONA_ONLINE + " TEXT" + ");";

        final String SQL_CREATE_TALONARIO_TABLE = "CREATE TABLE " + ecolifedb.EcoLifeEntry.TALONARIO_TABLE + " (" +
                ecolifedb.EcoLifeEntry._TALONARIOID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ESTADO + " INTEGER NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_FECHA_C + " DATE NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_SUPERVISORID + " INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_SUPERVISORNUBEID + "INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_NUBEID + " INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_TALONARIO_ONLINE + " TEXT" + ");";

        final String SQL_CREATE_GPS_TABLE = "CREATE TABLE " + ecolifedb.EcoLifeEntry.GPS_TABLE + " (" +
                ecolifedb.EcoLifeEntry._GPSID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ecolifedb.EcoLifeEntry.COLUMN_GPS_LATITUD + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_GPS_LONGITUD + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_GPS_NUBEID + " INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_GPS_ONLINE + " TEXT" + " );";

        final String SQL_CREATE_VENTACONTADO_TABLE = "CREATE TABLE " + ecolifedb.EcoLifeEntry.VENTA_CONTADO_TABLE + " (" +
                ecolifedb.EcoLifeEntry._VENTA_CONTADOID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_NOMBRE + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_TELEFONO + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_ZONA + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_VENDEDOR + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_DIRECCION + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_FECHA + " DATE NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_PRODID + " INTEGER NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_SUPID + " INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_SUPNUBEID + "INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_NUBEID + " INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACONT_ONLINE + " TEXT" + ");";

        final String SQL_CREATE_DETALLECONTADO_TABLE = "CREATE TABLE " + ecolifedb.EcoLifeEntry.DETALLE_CONTADO_TABLE + " (" +
                ecolifedb.EcoLifeEntry._DETALLE_CONTADOID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ecolifedb.EcoLifeEntry.COLUMN_DETALLEC_PRODID + " INTEGER NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_DETALLEC_VENTAID + " INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_DETALLEC_NUBEID + " INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_DETALLEC_ONLINE + " TEXT," +
                ecolifedb.EcoLifeEntry.COLUMN_DETALLEC_VENTANUBEID + " INTEGER"+");";

        final String SQL_CREATE_VENTACREDITO_TABLE = "CREATE TABLE " + ecolifedb.EcoLifeEntry.VENTA_CREDITO_TABLE + " (" +
                ecolifedb.EcoLifeEntry._VENTA_CREDITOID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_NOMBRE + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TELEFONO + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_ZONA + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_VENDEDOR + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_DIRECCION + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_FECHA + " DATE NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_PRODID + " INTEGER NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TALONARIOPID + " INTEGER NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TALONARIONUBEID + "INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_FOTO + " TEXT NOT NULL," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_NUBEID + " INTEGER," +
                ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_ONLINE + " TEXT" + ");";



        db.execSQL(SQL_CREATE_GPS_TABLE);
        db.execSQL(SQL_CREATE_PERSONA_TABLE);
        db.execSQL(SQL_CREATE_TALONARIO_TABLE);
        db.execSQL(SQL_CREATE_VENTACONTADO_TABLE);
        db.execSQL(SQL_CREATE_DETALLECONTADO_TABLE);
        db.execSQL(SQL_CREATE_VENTACREDITO_TABLE);
        db.execSQL(SQL_CREATE_COBRO_TABLE);
        Log.d(TAG, "Database Created Successfully" );


       /* try {
            readFragrancesFromResources(db);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: Handle database version upgrades
        db.execSQL("DROP TABLE IF EXISTS " + ecolifedb.EcoLifeEntry.DETALLE_CONTADO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ecolifedb.EcoLifeEntry.COBRO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ecolifedb.EcoLifeEntry.VENTA_CONTADO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ecolifedb.EcoLifeEntry.VENTA_CREDITO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ecolifedb.EcoLifeEntry.TALONARIO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ecolifedb.EcoLifeEntry.PERSONA_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ecolifedb.EcoLifeEntry.GPS_TABLE);
        onCreate(db);
    }
}
