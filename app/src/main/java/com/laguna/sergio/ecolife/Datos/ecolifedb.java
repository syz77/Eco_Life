package com.laguna.sergio.ecolife.Datos;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class ecolifedb {

    private ecolifedb() {}


    public static final String CONTENT_AUTHORITY = "com.laguna.sergio.ecolife";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_COBRO = "cobro-path";

    public static final String PATH_PERSONA= "persona-path";

    public static final String PATH_TALONARIO= "talonario-path";

    public final static String PATH_GPS = "gps-path";

    public final static String PATH_VENTA_CONTADO = "venta_contado-path";

    public final static String PATH_DETALLE_CONTADO = "detalle_contado-path";

    public final static String PATH_VENTA_CREDITO = "venta_credito-path";


    public static final class EcoLifeEntry implements BaseColumns {

        /** The content URI to access the fragrance data in the provider */
        public static final Uri CONTENT_URI_COBRO = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_COBRO);

        public static final Uri CONTENT_URI_PERSONA = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PERSONA);

        public static final Uri CONTENT_URI_TALONARIO = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TALONARIO);

        public static final Uri CONTENT_URI_GPS = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_GPS);

        public static final Uri CONTENT_URI_VENTA_CONTADO = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_VENTA_CONTADO);

        public static final Uri CONTENT_URI_DETALLE_CONTADO = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DETALLE_CONTADO);

        public static final Uri CONTENT_URI_VENTA_CREDITO = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_VENTA_CREDITO);


        //NOMBRE DE LAS TABLAS
        public final static String COBRO_TABLE = "cobro";

        public final static String PERSONA_TABLE = "persona";

        public final static String TALONARIO_TABLE = "talonario";

        public final static String GPS_TABLE = "gps";

        public final static String VENTA_CONTADO_TABLE = "venta_contado";

        public final static String DETALLE_CONTADO_TABLE = "detalle_contado";

        public final static String VENTA_CREDITO_TABLE = "venta_credito";

        //ID SOLO PARA USO DE LA DB
        public final static String _COBROID = BaseColumns._ID;

        public final static String _PERSONAID = BaseColumns._ID;

        public final static String _TALONARIOID = BaseColumns._ID;

        public final static String _GPSID = BaseColumns._ID;

        public final static String _VENTA_CONTADOID = BaseColumns._ID;

        public final static String _DETALLE_CONTADOID = BaseColumns._ID;

        public final static String _VENTA_CREDITOID = BaseColumns._ID;

        //COLUMNS FOR COBRO
        public final static String COLUMN_COBRO_MONTO = "monto";
        public final static String COLUMN_COBRO_FECHA = "fecha";
        public final static String COLUMN_COBRO_NRO_CUOTA = "nro_cuota";
        public final static String COLUMN_COBRO_SUBTOTAL = "subtotal";
        public final static String COLUMN_COBRO_CREDITOID = "id_credito";
        public final static String COLUMN_COBRO_CREDITONUBEID="creditonube_id";
        public final static String COLUMN_COBRO_GPSID = "id_gps";
        public final static String COLUMN_COBRO_GPSNUBEID="gpsnube_id";
        public final static String COLUMN_COBRO_NUBEID="cobronube_id";
        public final static String COLUMN_COBRO_ONLINE="cobro_online";

        //COLUMNS FOR PERSONA
        public final static String COLUMN_PERSONA_NOMBRE = "nombre";
        public final static String COLUMN_PERSONA_CORREO = "correo";
        public final static String COLUMN_PERSONA_PASSWORD = "password";
        public final static String COLUMN_PERSONA_TELEFONO = "telefono";
        public final static String COLUMN_PERSONA_FECHA = "fecha";
        public final static String COLUMN_PERSONA_CI = "ci";
        public final static String COLUMN_PERSONA_ESTADO = "estado";
        public final static String COLUMN_PERSONA_ROLID = "id_rol";
        public final static String COLUMN_PERSONA_NUBEID="personanube_id";
        public final static String COLUMN_PERSONA_ONLINE="persona_online";
        public final static String COLUMN_PERSONA_TOKEN="persona_token";
        public final static String COLUMN_PERSONA_IMEI="persona_imei";

        //COLUMNS FOR TALONARIO
        public final static String COLUMN_TALONARIO_ESTADO = "estado";
        public final static String COLUMN_TALONARIO_FECHA_C = "fecha_c";
        public final static String COLUMN_TALONARIO_SUPERVISORID = "id_supervisor";
        public final static String COLUMN_TALONARIO_SUPERVISORNUBEID="supervisornube_id";
        public final static String COLUMN_TALONARIO_NUBEID="talonarionube_id";
        public final static String COLUMN_TALONARIO_ONLINE="talonario_online";

        //COLUMNS FOR GPS
        public final static String COLUMN_GPS_LATITUD = "latitud";
        public final static String COLUMN_GPS_LONGITUD = "longitud";
        public final static String COLUMN_GPS_NUBEID = "gpsnube_id";
        public final static String COLUMN_GPS_ONLINE = "gps_online";

        //COLUMNS FOR VENTA_CONTADO
        public final static String COLUMN_VENTACONT_NOMBRE = "nombre";
        public final static String COLUMN_VENTACONT_TELEFONO = "telefono";
        public final static String COLUMN_VENTACONT_ZONA = "zona";
        public final static String COLUMN_VENTACONT_VENDEDOR = "vendedor";
        public final static String COLUMN_VENTACONT_DIRECCION = "direccion";
        public final static String COLUMN_VENTACONT_FECHA = "fecha";
        public final static String COLUMN_VENTACONT_SUPID = "id_sup";
        public final static String COLUMN_VENTACONT_SUPNUBEID="supervisornube_id";
        public final static String COLUMN_VENTACONT_NUBEID="ventacontnube_id";
        public final static String COLUMN_VENTACONT_ONLINE="ventacont_online";

        //COLUMNS FOR DETALLE_CONTADO
        public final static String COLUMN_DETALLEC_PRODID = "id_prod";
        public final static String COLUMN_DETALLEC_VENTAID = "id_venta";
        public final static String COLUMN_DETALLEC_CANTIDAD = "cantidad";
        public final static String COLUMN_DETALLEC_VENTANUBEID="ventanube_id";
        public final static String COLUMN_DETALLEC_NUBEID = "detallecnube_id";
        public final static String COLUMN_DETALLEC_ONLINE="detallec_online";

        //COLUMNS FOR VENTA_CREDITO
        public final static String COLUMN_VENTACRED_NOMBRE = "nombre";
        public final static String COLUMN_VENTACRED_TELEFONO = "telefono";
        public final static String COLUMN_VENTACRED_ZONA = "zona";
        public final static String COLUMN_VENTACRED_VENDEDOR = "vendedor";
        public final static String COLUMN_VENTACRED_DIRECCION = "direccion";
        public final static String COLUMN_VENTACRED_FECHA = "fecha";
        public final static String COLUMN_VENTACRED_PRODID = "id_prod";
        public final static String COLUMN_VENTACRED_TALONARIOPID = "id_talonario";
        public final static String COLUMN_VENTACRED_TALONARIONUBEID="talonarionube_id";
        public final static String COLUMN_VENTACRED_FOTO_NOMBRE="foto_nombre";
        public final static String COLUMN_VENTACRED_FOTO = "foto";
        public final static String COLUMN_VENTACRED_NUBEID="ventacrednube_id";
        public final static String COLUMN_VENTACRED_ONLINE="ventacred_online";


    }

}
