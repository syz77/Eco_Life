package com.laguna.sergio.ecolife;

import org.json.JSONArray;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Conexion {
    public String login(String user, String pass){
        String parametros="email="+user+"&pass="+pass;
        HttpURLConnection connection;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/login.php?email="+user+"&pass="+pass);
            //"https://localhostproyectowebandroid000webhostapp.xyz/Consultas/valida.php");
            //"http://u209922277.hostingerapp.com/val.php?user="+u+"&pwd="+pa
            //"https://u209922277.hostingerapp.com/api/v1/usuario"
            //"http://u209922277.hostingerapp.com/val.php"
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
            //"http://u209922277.hostingerapp.com/servicio/registro.php?name="+name+"&pwd="+pas+"&email="+cor+"&fecha="+date
            //http://u209922277.hostingerapp.com/servicio/registro.php?name=juanp&pwd=123456&email=juanpi@gmail.com&fecha=2000-11-12
            //"https://localhostproyectowebandroid000webhostapp.xyz/Consultas/valida.php");
            //"http://u209922277.hostingerapp.com/val.php?user="+u+"&pwd="+pa
            //"https://u209922277.hostingerapp.com/api/v1/usuario"
            //"http://u209922277.hostingerapp.com/val.php"
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
