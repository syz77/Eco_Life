package com.laguna.sergio.ecolife;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.laguna.sergio.ecolife.Datos.persona;

import com.laguna.sergio.ecolife.Datos.ecolifedb;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

public class EditarUser extends AppCompatActivity {

    Spinner SEstado;
    String txtCargo, txtEstado,IDusuario;
    ContentResolver mContentResolver;
    Button btnEditUser,btnEditImei;
    //DataAdapterGesU dataAdapterGesU;
    EditText edituser,editpass;
    TextView TextEstado;

    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult;
    String HttpURLGUEdit = "http://u209922277.hostingerapp.com/servicios_ecolife/InsertarGUEditarUsuario.php";// Verificacion de Imei en la nube
    String HttpURLIMEI = "http://u209922277.hostingerapp.com/servicios_ecolife/InsertarGUEditarImei.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_user);
        mContentResolver=getContentResolver();
        btnEditUser=findViewById(R.id.btnEdituser);
        btnEditImei= findViewById(R.id.btnEditImei);
        edituser=findViewById(R.id.editUsuario);
        editpass=findViewById(R.id.editContrase);
        DataAdapterGesU dataAdapterGu=new DataAdapterGesU();
        TextEstado=findViewById(R.id.EditviewEstado);
        int b=0;//Integer.parseInt(p.Estado);
        persona personaGUEdit = (persona) getIntent().getExtras().getSerializable("Persona");



        SEstado=(Spinner) findViewById(R.id.spinnerEstado);
        String[] estado = {"Habilitado","Vacaciones","Bloqueado"};
        SEstado.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,estado));
        SEstado.setSelection(b);
        SEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(parent.getContext(), (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                txtEstado = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edituser.setText(personaGUEdit.Nombre);
        editpass.setText(personaGUEdit.Password);
        IDusuario=personaGUEdit.IdUsuario;
        if (personaGUEdit.Estado.equals("0")){
            TextEstado.setText("Estado actual: Bloqueado");
        }else if (personaGUEdit.Estado.equals("1")){
            TextEstado.setText("Estado actual: Activo");
        }else if (personaGUEdit.Estado.equals("2")){
            TextEstado.setText("Estado actual: Vacaciones");
        }

    }

    public void buttonEditarUser(View v){
        String estado = conver(txtEstado);
        String pass = editpass.getText().toString();
        if(editpass.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debe llenar la contraseña", Toast.LENGTH_SHORT).show();
            //EnviarEditUser(IDusuario, editpass.toString(), estado);
        }
        else{
            //Toast.makeText(getApplicationContext(), "Todo correcto "+estado, Toast.LENGTH_SHORT).show();
            //String estado = conver(txtEstado);
            EnviarEditUser(IDusuario, pass, estado);
            }
    }

    public void buttonEditarImei(View v){
            EnviarEditImei(IDusuario);

    }

    public boolean verif(){
        boolean b=false;
        if(edituser.getText().toString().isEmpty()==false &&editpass.getText().toString().isEmpty()==false){
            b=true;
        }
        return b;
    }

    public String conver(String a){
        String b="";
        if(a.equals("Habilitado")){
            b="1";
        }else{
            if(a.equals("Vacaciones")){
                b="2";
                }
                else{
                if(a.equals("Bloqueado")){
                    b="0";
                }
            }
        }
        return b;
    }

    public void EnviarEditUser(final String Id,final String Password, final String Estado){

        class HTVentCredCobroFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progressDialog = ProgressDialog.show(NavegacionMenu.this,"Loading Data",null,true,true);
            }
            @Override
            public void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                Toast.makeText(EditarUser.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("id",params[0]);
                hashMap.put("password",params[1]);
                hashMap.put("estado",params[2]);


                finalResult = httpParse.postRequest(hashMap, HttpURLGUEdit);

                return finalResult;
            }
        }
        HTVentCredCobroFunctionClass RegisterFunctionClass = new HTVentCredCobroFunctionClass();
        RegisterFunctionClass.execute(Id,Password,Estado);
    }

    public void EnviarEditImei(final String Id){

        class HTVentCredCobroFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progressDialog = ProgressDialog.show(NavegacionMenu.this,"Loading Data",null,true,true);
            }
            @Override
            public void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                Toast.makeText(EditarUser.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();


            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("id",params[0]);

                finalResult = httpParse.postRequest(hashMap, HttpURLIMEI);

                return finalResult;
            }
        }
        HTVentCredCobroFunctionClass RegisterFunctionClass = new HTVentCredCobroFunctionClass();
        RegisterFunctionClass.execute(Id);
    }
}
