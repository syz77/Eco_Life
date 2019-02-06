package com.laguna.sergio.ecolife.Datos;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class EcoLifeProvider extends ContentProvider {

    public static final String LOG_TAG = EcoLifeProvider.class.getSimpleName();

    /** URI matcher code for the content URI for the cobro table */
    private static final int COBRO = 100;

    /** URI matcher code for the content URI for a single cobro in the cobro table */
    private static final int COBRO_ID = 101;

    private static final int PERSONA = 102;

    private static final int PERSONA_ID = 103;

    private static final int TALONARIO = 104;

    private static final int TALONARIO_ID = 105;

    private static final int GPS = 106;

    private static final int GPS_ID = 107;

    private static final int VENTA_CONTADO = 108;

    private static final int VENTA_CONTADO_ID = 109;

    private static final int DETALLE_CONTADO = 110;

    private static final int DETALLE_CONTADO_ID = 111;

    private static final int VENTA_CREDITO = 112;

    private static final int VENTA_CREDITO_ID = 113;


    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android." will map to the
        // integer code {@link #..}. This URI is used to provide access to MULTIPLE rows
        // of the table.
        sUriMatcher.addURI(ecolifedb.CONTENT_AUTHORITY, ecolifedb.PATH_COBRO, COBRO);

        sUriMatcher.addURI(ecolifedb.CONTENT_AUTHORITY, ecolifedb.PATH_PERSONA, PERSONA);

        sUriMatcher.addURI(ecolifedb.CONTENT_AUTHORITY, ecolifedb.PATH_TALONARIO, TALONARIO);

        sUriMatcher.addURI(ecolifedb.CONTENT_AUTHORITY, ecolifedb.PATH_GPS, GPS);

        sUriMatcher.addURI(ecolifedb.CONTENT_AUTHORITY, ecolifedb.PATH_VENTA_CONTADO, VENTA_CONTADO);

        sUriMatcher.addURI(ecolifedb.CONTENT_AUTHORITY, ecolifedb.PATH_DETALLE_CONTADO, DETALLE_CONTADO);

        sUriMatcher.addURI(ecolifedb.CONTENT_AUTHORITY, ecolifedb.PATH_VENTA_CREDITO, VENTA_CREDITO);

        // The content URI of the form "content://com.example.android../#" will map to the
        // integer code {@link #table_ID}. This URI is used to provide access to ONE single row
        // of the table.
        //
        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.package/table/3" matches, but
        // "content://com.example.android.package/table" (without a number at the end) doesn't match.
        sUriMatcher.addURI(ecolifedb.CONTENT_AUTHORITY, ecolifedb.PATH_COBRO + "/#", COBRO_ID);
        sUriMatcher.addURI(ecolifedb.CONTENT_AUTHORITY, ecolifedb.PATH_PERSONA + "/#", PERSONA_ID);
        sUriMatcher.addURI(ecolifedb.CONTENT_AUTHORITY, ecolifedb.PATH_TALONARIO + "/#", TALONARIO_ID);
        sUriMatcher.addURI(ecolifedb.CONTENT_AUTHORITY, ecolifedb.PATH_GPS + "/#", GPS_ID);
        sUriMatcher.addURI(ecolifedb.CONTENT_AUTHORITY, ecolifedb.PATH_VENTA_CONTADO + "/#", VENTA_CONTADO_ID);
        sUriMatcher.addURI(ecolifedb.CONTENT_AUTHORITY, ecolifedb.PATH_DETALLE_CONTADO + "/#", DETALLE_CONTADO_ID);
        sUriMatcher.addURI(ecolifedb.CONTENT_AUTHORITY, ecolifedb.PATH_VENTA_CREDITO + "/#", VENTA_CREDITO_ID);

    }

    /** Database helper object */
    private EcoLifeDBHelper ecolifeDbHelper;

    @Override
    public boolean onCreate() {
        ecolifeDbHelper = new EcoLifeDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Get readable database
        SQLiteDatabase database = ecolifeDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case COBRO:
                // For the code, query the table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the table.
                cursor = database.query(ecolifedb.EcoLifeEntry.COBRO_TABLE , projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case PERSONA:
                cursor = database.query(ecolifedb.EcoLifeEntry.PERSONA_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case TALONARIO:
                cursor = database.query(ecolifedb.EcoLifeEntry.TALONARIO_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case GPS:
                cursor = database.query(ecolifedb.EcoLifeEntry.GPS_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case VENTA_CONTADO:
                cursor = database.query(ecolifedb.EcoLifeEntry.VENTA_CONTADO_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case DETALLE_CONTADO:
                cursor = database.query(ecolifedb.EcoLifeEntry.DETALLE_CONTADO_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case VENTA_CREDITO:
                cursor = database.query(ecolifedb.EcoLifeEntry.VENTA_CREDITO_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case COBRO_ID:
                selection = ecolifedb.EcoLifeEntry._COBROID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(ecolifedb.EcoLifeEntry.COBRO_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case PERSONA_ID:
                selection = ecolifedb.EcoLifeEntry._PERSONAID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(ecolifedb.EcoLifeEntry.PERSONA_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case TALONARIO_ID:
                selection = ecolifedb.EcoLifeEntry._PERSONAID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(ecolifedb.EcoLifeEntry.TALONARIO_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case GPS_ID:
                // For the _ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.package/table/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = ecolifedb.EcoLifeEntry._GPSID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the fragrance table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(ecolifedb.EcoLifeEntry.GPS_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case VENTA_CONTADO_ID:
                selection = ecolifedb.EcoLifeEntry._VENTA_CONTADOID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(ecolifedb.EcoLifeEntry.VENTA_CONTADO_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;

            case DETALLE_CONTADO_ID:
                selection = ecolifedb.EcoLifeEntry._DETALLE_CONTADOID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(ecolifedb.EcoLifeEntry.DETALLE_CONTADO_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case VENTA_CREDITO_ID:
                selection = ecolifedb.EcoLifeEntry._VENTA_CREDITOID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(ecolifedb.EcoLifeEntry.VENTA_CREDITO_TABLE, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:

                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor,
        // so we know what content URI the Cursor was created for.
        // If the data at this URI changes, then we know we need to update the Cursor.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Return the cursor
        return cursor;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case COBRO:
                return insertCobro(uri, contentValues);

            case PERSONA:
                return insertPersona(uri, contentValues);

            case TALONARIO:
                return insertTalonario(uri, contentValues);

            case GPS:
                return insertGps(uri, contentValues);

            case VENTA_CONTADO:
                return insertVentaContado(uri, contentValues);

            case DETALLE_CONTADO:
                return insertDetalleContado(uri, contentValues);

            case VENTA_CREDITO:
                return insertVentaCredito(uri, contentValues);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }


    private Uri insertCobro(Uri uri, ContentValues values) {

        // Get writeable database
        SQLiteDatabase database = ecolifeDbHelper.getWritableDatabase();

        // Insert the new cobro with the given values
        long id = database.insert(ecolifedb.EcoLifeEntry.COBRO_TABLE, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the cobro content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertPersona(Uri uri, ContentValues values) {

        // Get writeable database
        SQLiteDatabase database = ecolifeDbHelper.getWritableDatabase();

        // Insert the new persona with the given values
        long id = database.insert(ecolifedb.EcoLifeEntry.PERSONA_TABLE, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the persona content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertTalonario(Uri uri, ContentValues values) {

        // Get writeable database
        SQLiteDatabase database = ecolifeDbHelper.getWritableDatabase();

        // Insert the new talonario with the given values
        long id = database.insert(ecolifedb.EcoLifeEntry.TALONARIO_TABLE, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for talonario content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertGps(Uri uri, ContentValues values) {

        // Get writeable database
        SQLiteDatabase database = ecolifeDbHelper.getWritableDatabase();

        // Insert the new gps with the given values
        long id = database.insert(ecolifedb.EcoLifeEntry.GPS_TABLE, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the gps content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertVentaContado(Uri uri, ContentValues values) {

        // Get writeable database
        SQLiteDatabase database = ecolifeDbHelper.getWritableDatabase();

        // Insert the new ventacontado with the given values
        long id = database.insert(ecolifedb.EcoLifeEntry.VENTA_CONTADO_TABLE, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the ventacontado content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertDetalleContado(Uri uri, ContentValues values) {

        // Get writeable database
        SQLiteDatabase database = ecolifeDbHelper.getWritableDatabase();

        // Insert the new detallecontado with the given values
        long id = database.insert(ecolifedb.EcoLifeEntry.DETALLE_CONTADO_TABLE, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the detallecontado content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertVentaCredito(Uri uri, ContentValues values) {

        // Get writeable database
        SQLiteDatabase database = ecolifeDbHelper.getWritableDatabase();

        // Insert the new ventacredito with the given values
        long id = database.insert(ecolifedb.EcoLifeEntry.VENTA_CREDITO_TABLE, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the ventacredito content URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        // Get access to the database and write URI matching code to recognize a single item
       /* final SQLiteDatabase db = ecolifeDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        // Keep track of the number of deleted tasks
        int cartDeleted; // starts as 0


        // Write the code to delete a single row of data
        // [Hint] Use selections to delete an item by its row ID
        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case CART_ID:
                // Get the task ID from the URI path
                String id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                cartDeleted = db.delete(FragranceContract.FragranceEntry.CART_TABLE, "_id=?", new String[]{id});
                break;
            case WISH_ID:
                // Get the task ID from the URI path
                String wishId = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                cartDeleted = db.delete(FragranceContract.FragranceEntry.WISH_TABLE, "_id=?", new String[]{wishId});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver of a change and return the number of items deleted
        if (cartDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted*/
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = ecolifeDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;
        int a;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case COBRO:
                a=database.update(ecolifedb.EcoLifeEntry.COBRO_TABLE, values,selection,selectionArgs);
                break;
            case PERSONA:
                a=database.update(ecolifedb.EcoLifeEntry.PERSONA_TABLE, values, selection, selectionArgs);
                break;
            case TALONARIO:
                a=database.update(ecolifedb.EcoLifeEntry.TALONARIO_TABLE, values, selection, selectionArgs);
                break;
            case GPS:
                a=database.update(ecolifedb.EcoLifeEntry.GPS_TABLE, values, selection, selectionArgs);
                break;
            case VENTA_CONTADO:
                a=database.update(ecolifedb.EcoLifeEntry.VENTA_CONTADO_TABLE, values, selection, selectionArgs);
                break;
            case DETALLE_CONTADO:
                a=database.update(ecolifedb.EcoLifeEntry.DETALLE_CONTADO_TABLE, values, selection, selectionArgs);
                break;
            case VENTA_CREDITO:
                a=database.update(ecolifedb.EcoLifeEntry.VENTA_CREDITO_TABLE, values, selection, selectionArgs);
                break;
            default:

                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        //notify changes to the current observers
        if (a != 0) {
            assert getContext() != null;
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return a;
    }

}
