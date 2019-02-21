package com.laguna.sergio.ecolife;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.HashMap;

public class VentaCredito extends AppCompatActivity {

    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult;
    String HttpURL = "http://u209922277.hostingerapp.com/servicios_ecolife/insertarimagen.php";// Verificacion de Imei en la nube
    Boolean CheckEditText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_credito);
    }

    public void Enviar(){
        //CheckEditTextIsEmpty();
        //if(CheckEditText){
        //TelephonyManager telemamanger = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //String getSimSerialNumber = telemamanger.getSimSerialNumber();
        //NroEmei = getSimSerialNumber;
        //String Sfecha = getCurrentTimeStamp();
        //Sminutos= e7minutos.getText().toString();;
        //DataAdapterClassList.clear();
        //EnviarRegistrar(Sgym,Spokemon);
        //}
        //else {
        // If EditText is empty then this block will execute .
        //Toast.makeText(Menu_principal.this, "Los datos deben ser correctos", Toast.LENGTH_LONG).show();
        //}
    }

    public boolean CheckEditTextIsEmptyOrNot(String nombreC,String telefono,String direccion,String zona,String fecha,String nombreP,String producto,String gps,String talo,String talonube){

        if(TextUtils.isEmpty(nombreC)&&TextUtils.isEmpty(telefono)&&TextUtils.isEmpty(direccion)&&TextUtils.isEmpty(zona)&&TextUtils.isEmpty(fecha)&&TextUtils.isEmpty(nombreP)
                &&TextUtils.isEmpty(producto)&&TextUtils.isEmpty(gps)&&TextUtils.isEmpty(talo)&&TextUtils.isEmpty(talonube))
        {
            return false;
        }
        else {

            return true;
        }
    }

    public void EnviarRegistrar(final String nombreC,final String telefono,final String direccion,final String zona
            ,final String fecha,final String nombreP,final String nombreF,final String foto,final String producto,final String gps){

        class SolicitudFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(VentaCredito.this,"Loading Data",null,true,true);
            }

            @Override
            public void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();
                if (httpResponseMsg.toString().equals("Error Usuario No Registrado")) {

                    //Toast.makeText(VentaCredito.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                    //JSON_WEB_CALL();

                }
                //Toast.makeText(Menu_principal.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("nombreC",params[0]);
                hashMap.put("telefono",params[1]);
                hashMap.put("direccion",params[2]);
                hashMap.put("zona",params[3]);
                hashMap.put("fecha",params[4]);
                hashMap.put("nombreP",params[5]);
                hashMap.put("foto",params[6]);
                hashMap.put("producto",params[7]);
                hashMap.put("gps",params[8]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }
        SolicitudFunctionClass userRegisterFunctionClass = new SolicitudFunctionClass();
        userRegisterFunctionClass.execute(nombreC,telefono,direccion,zona,fecha,nombreP,nombreF,foto,producto,gps);
    }
}
