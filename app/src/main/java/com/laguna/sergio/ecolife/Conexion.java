package com.laguna.sergio.ecolife;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.laguna.sergio.ecolife.Datos.ecolifedb;
import com.laguna.sergio.ecolife.Datos.persona;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class Conexion {

    String HttpURL = "http://u209922277.hostingerapp.com/servicios_ecolife/InsertarVentaCreditoFoto.php";
    String HttpURLCobro = "http://u209922277.hostingerapp.com/servicios_ecolife/InsertarCobroPost.php";
    String HTTP_SERVER_URLlogin ="http://u209922277.hostingerapp.com/servicios_ecolife/loginpost.php";
    String finalResult;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String s;
    String respuesta="";
    String FinalJSonObject= "";

    public String login(String user, String pass,String imei){
        String parametros="email="+user+"&pass="+pass;
        HttpURLConnection connection;
        //String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/login.php?email="+user+"&pass="+pass+"&imei="+imei);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }

        return respuesta;
    }

    public void logindos(String user, String pass,String imei){
        //loginpost(user,pass,imei);
        //return respuesta;
    }


    public void loginpost(final String email,final String pass, final String imei,final ContentResolver mContentResolver){

        class GUTCFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //progressDialog = ProgressDialog.show(NavegacionMenu.this,"Loading Data",null,true,true);
            }

            @Override
            public void onPostExecute(String httpResponseMsg) {


                super.onPostExecute(httpResponseMsg);
                //progressDialog.dismiss();
                //FinalJSonObject = httpResponseMsg ;
                //respuesta = httpResponseMsg;
                //Pararespuesta (FinalJSonObject);

                persona p=new persona();
                p.login(httpResponseMsg,mContentResolver);


                /*if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        //Toast.makeText(NavegacionMenu.this, FinalJSonObject, Toast.LENGTH_LONG).show();
                        jsonArray = new JSONArray(FinalJSonObject);

                        //respuesta = jsonArray.toString();
                        //JSON_PARSE_DATA_AFTER_WEBCALLGUtalo(jsonArray);
                        //JSONObject jsonObject;

                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        //JSON_PARSE_DATA_AFTER_WEBCALLRLU(jsonArray);
                        //ReCargarLista(jsonArray);
                        e.printStackTrace();
                    }
                }*/
                //Toast.makeText(NavegacionMenu.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                //if (httpResponseMsg.equals("Se cambio correctamente")){
                //Toast.makeText(Login.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                    //respuesta = httpResponseMsg;

                //}else if (httpResponseMsg.equals("Error no se pudo cambiar")){
                    //Toast.makeText(NavegacionMenu.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                //}
                //FinalJSonObject = httpResponseMsg ;

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("email",params[0]);

                hashMap.put("pass",params[1]);

                hashMap.put("imei",params[2]);

                finalResult = httpParse.postRequest(hashMap, HTTP_SERVER_URLlogin);

                return finalResult;
            }
        }

        GUTCFunctionClass RegisterFunctionClass = new GUTCFunctionClass();
        RegisterFunctionClass.execute(email,pass,imei);

    }

    public void JSON_PARSE_DATA_AFTER_WEBCALLGUtalo(JSONArray array){

        //for(int i = 0; i<array.length(); i++) {

            //DataAdapterGesUTalo GetDataAdapter3 = new DataAdapterGesUTalo();

            JSONObject json = null;
            try {

                json = array.getJSONObject(0);
                respuesta = json.toString();
                //GetDataAdapter3.setNroTalo(json.getString("id"));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        //}
    }

    public String InsertRegistro(String nombre, String email,String pass,String telefono, String fecha, String rolid, String ci
            ,String estado){
        String parametros="nombre="+nombre+"&email="+email+"&pass="+pass+"&telefono="+telefono+"&fecha="+fecha+"&cargo="+rolid+"&ci="+ci;
        HttpURLConnection connection=null;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/registro.php?nombre="+nombre+
                    "&email="+email+"&pass="+pass+"&telefono="+telefono+"&fecha="+fecha+"&cargo="+rolid+"&ci="+ci+"&estado="+estado);

            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }

    public String InsertarTalonario(String estado, String fecha,String id_sup){
        String parametros="estado="+estado+"&fecha="+fecha+"&id_sup="+id_sup;
        HttpURLConnection connection=null;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/inserttalonario.php?"+parametros);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }

    public String InsertarVentaCredito(String nombre, String telefono,String direccion, String zona, String fecha,
                                       String vendedor, String foto, String id_prod, String id_talonario){
        String parametros="nombre="+nombre+"&telefono="+telefono+"&direccion="+direccion+"&zona="+zona+
                "&fecha="+fecha+"&vendedor="+vendedor+"&foto="+foto+"&id_prod="+id_prod+"&id_talonario="+id_talonario;
        HttpURLConnection connection=null;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/insertventacredito.php?"+parametros);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void InsertarVentaCreditoFoto(final String nombre,final String telefono,final String direccion,final String zona,final String fecha,
                                         final String vendedor,final String fotonombre,final String foto,final String id_prod,final String id_talonario
                                            ,final String id,final ContentResolver mContentResolver){
        class SolicitudFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //progressDialog = ProgressDialog.show(Menu_principal.this,"Loading Data",null,true,true);
            }

            @Override
            public void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                if(httpResponseMsg.equals("")){
                }else {
                    String[] args = new String[]{id};
                    ContentValues values = new ContentValues();
                    String nubeid = convert(httpResponseMsg);
                    String online = "1";
                    values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_NUBEID, nubeid);
                    values.put(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_ONLINE, online);
                    mContentResolver.update(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CREDITO, values,
                            ecolifedb.EcoLifeEntry._VENTA_CREDITOID + "=?", args);
                }


            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("nombreC",params[0]);

                hashMap.put("telefono",params[1]);

                hashMap.put("direccion",params[2]);

                hashMap.put("zona",params[3]);

                hashMap.put("fecha",params[4]);

                hashMap.put("vendedor",params[5]);

                hashMap.put("fotonombre",params[6]);

                hashMap.put("foto",params[7]);

                hashMap.put("id_prod",params[8]);

                hashMap.put("id_talonario",params[9]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
            public String convert(String s){
                String id="";
                try {
                    JSONArray json = new JSONArray(s);
                    //for (int i = 0; i < json.length(); i++) {
                    JSONObject c = json.getJSONObject(0);

                    id = c.getString("MAX(id)");
                    //}

                }catch( final JSONException e){

                }
                return id;
            }
        }
        SolicitudFunctionClass userRegisterFunctionClass = new SolicitudFunctionClass();
        userRegisterFunctionClass.execute(nombre,telefono,direccion,zona,fecha,vendedor,fotonombre,foto,id_prod,id_talonario);
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //String monto, String nro_cuota,String subtotal, String fecha, String id_credito, String id_gps
    //Cmonto,Cnro_cuota,Csubtotal,Cfecha,Ccreditonube_id,Cgpsnube_id
    public void InsertarCobroPost(final String monto,final String nro_cuota,final String subtotal,final String fecha,final String id_credito,
                                         final String id_gps, final String id_cobro, final ContentResolver mContentResolver){
        class SolicitudFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //progressDialog = ProgressDialog.show(Menu_principal.this,"Loading Data",null,true,true);
            }

            @Override
            public void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                if(httpResponseMsg.equals("")){
                }else {
                    String[] args = new String[]{id_cobro};
                    ContentValues values = new ContentValues();
                    String nubeid = convert(httpResponseMsg);
                    String online = "1";
                    values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_NUBEID, nubeid);
                    values.put(ecolifedb.EcoLifeEntry.COLUMN_COBRO_ONLINE, online);

                    mContentResolver.update(ecolifedb.EcoLifeEntry.CONTENT_URI_COBRO, values,
                            ecolifedb.EcoLifeEntry._COBROID + "=?", args);
                }


            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("monto",params[0]);

                hashMap.put("nro_cuota",params[1]);

                hashMap.put("subtotal",params[2]);

                hashMap.put("fecha",params[3]);

                hashMap.put("id_credito",params[4]);

                hashMap.put("id_gps",params[5]);

                finalResult = httpParse.postRequest(hashMap, HttpURLCobro);

                return finalResult;
            }
            public String convert(String s){
                String id="";
                try {
                    JSONArray json = new JSONArray(s);
                    //for (int i = 0; i < json.length(); i++) {
                    JSONObject c = json.getJSONObject(0);

                    id = c.getString("MAX(id)");
                    //}

                }catch( final JSONException e){

                }
                return id;
            }
        }
        SolicitudFunctionClass userRegisterFunctionClass = new SolicitudFunctionClass();
        userRegisterFunctionClass.execute(monto,nro_cuota,subtotal,fecha,id_credito,id_gps);
    }


    public String InsertarVentaContado(String nombre, String telefono,String direccion, String zona, String fecha,
                                       String vendedor, String id_sup){
        String parametros="nombre="+nombre+"&telefono="+telefono+"&direccion="+direccion+"&zona="+zona+
                "&fecha="+fecha+"&vendedor="+vendedor+"&id_sup="+id_sup;
        HttpURLConnection connection=null;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/insertventacontado.php?"+parametros);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }

    public String InsertarGPS(String latitud, String longitud){
        String parametros="latitud="+latitud+"&longitud="+longitud;
        HttpURLConnection connection=null;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/insertgps.php?"+parametros);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
        }catch(Exception e){ }
        return respuesta;
    }

    public String InsertarDetalleContado(String id_venta, String id_prod,String cant){
        String parametros="id_venta="+id_venta+"&id_prod="+id_prod+"&cant="+cant;
        HttpURLConnection connection=null;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/insertdetallecontado.php?"+parametros);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }

    public String InsertarCobro(String monto, String nro_cuota,String subtotal, String fecha, String id_credito,
                                       String id_gps){
        String parametros="monto="+monto+"&nro_cuota="+nro_cuota+"&subtotal="+subtotal+"&fecha="+fecha+
                "&id_credito="+id_credito+"&id_gps="+id_gps;
        HttpURLConnection connection=null;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/insertcobro.php?"+parametros);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }
    public String todoTalonario(String user, String pass){
        String parametros="email="+user+"&pass="+pass;
        HttpURLConnection connection;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/todotalonario.php?email="+user+"&pass="+pass);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }
    public String todoVentaCredito(String user, String pass){
        String parametros="email="+user+"&pass="+pass;
        HttpURLConnection connection;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/todoventacredito.php?email="+user+"&pass="+pass);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }
    public String todoCobro(String user, String pass){
        String parametros="email="+user+"&pass="+pass;
        HttpURLConnection connection;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/todocobro.php?email="+user+"&pass="+pass);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }
    public String todoGPS(String user, String pass){
        String parametros="email="+user+"&pass="+pass;
        HttpURLConnection connection;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/todogps.php?email="+user+"&pass="+pass);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();
            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }

    public String CambiarPass(String user, String pass){
        String parametros="email="+user+"&pass="+pass;
        HttpURLConnection connection;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/updatePass.php?email="+user+"&pass="+pass);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }
    public String CambiarTelf(String user, String pass){
        String parametros="email="+user+"&pass="+pass;
        HttpURLConnection connection;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/updateTelf.php?email="+user+"&pass="+pass);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }
    public String UpdateUser(String user, String nuser,String pass, String estado, String cargo){
        String parametros="email="+user+"&ncorreo="+nuser+"&pass="+pass+"&estado="+estado+"&cargo="+cargo;
        HttpURLConnection connection;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/updateTelf.php?"+parametros);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }
    public String AllUser(String correo,String pass){
        String parametros="email="+correo+"&pass="+pass;
        HttpURLConnection connection;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/todoPersona.php?"+parametros);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }
    public String cantTalo(String id){
        String parametros="id="+id;
        HttpURLConnection connection;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/cantidadTalos.php?id="+id);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }

    public String todoVentaCredito2(String id){
        String parametros="id="+id;
        HttpURLConnection connection;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/todoventacredito2.php?id="+id);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }
    public String todoCobro2(String id){
        String parametros="id="+id;
        HttpURLConnection connection;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/todocobro2.php?id="+id);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }
    public String activopasivo(String id){
        String parametros="id="+id;
        HttpURLConnection connection;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/CambiarActivoPasivo.php?id="+id);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }
    public String tainactivo(String id){
        String parametros="id="+id;
        HttpURLConnection connection;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/TalonarioInactivo.php?id="+id);
            connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length",""+Integer.toString(parametros.getBytes().length));
            connection.setDoOutput(true);
            DataOutputStream wr=new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parametros);
            wr.close();

            Scanner inStream= new Scanner(connection.getInputStream());

            while(inStream.hasNextLine()){
                respuesta+=(inStream.nextLine());
            }
            connection.disconnect();
        }catch(Exception e){ }
        return respuesta;
    }

    public int objJson(String r) {
        int res=0;
        try{
            JSONArray json=new JSONArray(r);
            if (json.length()>0) {
                res=1;
            }
        }catch (Exception e){}
        return res;
    }
}
