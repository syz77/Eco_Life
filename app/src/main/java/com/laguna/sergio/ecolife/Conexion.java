package com.laguna.sergio.ecolife;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;

import com.laguna.sergio.ecolife.Datos.ecolifedb;

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
    String finalResult;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    public String login(String user, String pass){
        String parametros="email="+user+"&pass="+pass;
        HttpURLConnection connection;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/login.php?email="+user+"&pass="+pass);
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
                "&fecha="+fecha+"&vendedor="+vendedor+"&foto="+foto+"&id_sup="+id_prod+"&id_talonario="+id_talonario;
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
                                         final String vendedor,final String foto,final String id_prod,final String id_talonario){

        class SolicitudFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //progressDialog = ProgressDialog.show(Menu_principal.this,"Loading Data",null,true,true);
            }

            @Override
            public void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();
                if (httpResponseMsg.toString().equals("Registration Successfully")) {

                    //Toast.makeText(Menu_principal.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                    //JSON_WEB_CALL();

                }
                //Toast.makeText(Menu_principal.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("nombre",params[0]);

                hashMap.put("telefono",params[1]);

                hashMap.put("direccion",params[2]);

                hashMap.put("zona",params[3]);

                hashMap.put("fecha",params[4]);

                hashMap.put("vendedor",params[5]);

                hashMap.put("foto",params[6]);

                hashMap.put("producto",params[7]);

                hashMap.put("id_talonario",params[8]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }
        SolicitudFunctionClass userRegisterFunctionClass = new SolicitudFunctionClass();
        userRegisterFunctionClass.execute(nombre,telefono,direccion,zona,fecha,vendedor,foto,id_prod,id_talonario);

    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String InsertarVentaContado(String nombre, String telefono,String direccion, String zona, String fecha,
                                       String vendedor, String id_sup, String id_prod){
        String parametros="nombre="+nombre+"&telefono="+telefono+"&direccion="+direccion+"&zona="+zona+
                "&fecha="+fecha+"&vendedor="+vendedor+"&id_sup="+id_sup+"&id_prod="+id_prod;
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

    public String InsertarDetalleContado(String id_venta, String id_prod){
        String parametros="id_venta="+id_venta+"&id_prod="+id_prod;
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
