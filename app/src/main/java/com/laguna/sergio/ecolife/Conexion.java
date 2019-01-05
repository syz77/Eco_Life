package com.laguna.sergio.ecolife;

import org.json.JSONArray;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Conexion {
    public String enviarPost(String user, String pass){
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
        }catch(Exception e){ }
        return respuesta;
    }
    public String enviarPostR(String nombre, String email,String pass,String telefono, String fecha, String cargo, String ci){
        String parametros="nombre="+nombre+"&email="+email+"&pass="+pass+"&telefono="+telefono+"&fecha="+fecha+"&cargo="+cargo+"&ci="+ci;
        HttpURLConnection connection=null;
        String respuesta="";
        try{
            URL url=new URL("http://u209922277.hostingerapp.com/servicios_ecolife/registro.php?nombre="+nombre+"&email="+email+"&pass="+pass+"&telefono="+telefono+"&fecha="+fecha+"&cargo="+cargo
                    +"&ci="+ci);
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
