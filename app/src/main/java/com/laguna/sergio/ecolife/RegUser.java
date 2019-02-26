package com.laguna.sergio.ecolife;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;

import com.laguna.sergio.ecolife.Datos.ecolifedb;
import com.laguna.sergio.ecolife.Datos.persona;

import java.util.Calendar;

public class RegUser extends AppCompatActivity {
    Spinner CargoS;
    String txtcargo;
    EditText txtfecha;
    Calendar c;
    DatePickerDialog dpd;
    Button btnRegistrar;
    EditText txtnombre;
    EditText txttelefono;
    EditText txtemail;
    EditText txtpass;
    EditText txtci;
    ContentResolver mContentResolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_user);
        mContentResolver=this.getContentResolver();
        //Dialogo para fecha
        txtfecha=(EditText) findViewById(R.id.editDate);
        txtfecha.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                c=Calendar.getInstance();
                int day=c.get(Calendar.DAY_OF_MONTH);
                int month1=c.get(Calendar.MONTH);
                int year1=c.get(Calendar.YEAR);

                dpd=new DatePickerDialog(RegUser.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtfecha.setText(year+"/"+(month+1)+"/"+dayOfMonth);
                    }
                },year1,month1,day);
                dpd.show();
            }
        });
        /*Cursor talonario = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_TALONARIO, null,
                null, null, null);
        Cursor gps = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_GPS, null,
                null, null, null);
        Cursor ventacred = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CREDITO, null,
                null, null, null);
        Cursor cobro = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_COBRO, null,
                null, null, null);
        String todo="";
        if(talonario.getCount()>0) {
            todo=todo+Integer.toString(talonario.getCount())+" ";
            if(gps.getCount()>0) {
                todo=todo+Integer.toString(gps.getCount())+" ";
                if (ventacred.getCount()>0) {
                    todo=todo+Integer.toString(ventacred.getCount())+" ";
                    if(cobro.getCount()>0) {
                        todo=todo+Integer.toString(cobro.getCount());
                    }
                }
            }
        }
        Toast.makeText(getApplicationContext(),todo, Toast.LENGTH_LONG).show();
        Cursor pers=mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_PERSONA,null,
                null,null,null);
        while (pers.moveToNext()){
            txtnombre.setText(pers.getString(pers.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_NOMBRE)));
            txttelefono.setText(pers.getString(pers.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_TELEFONO)));
            txtemail.setText(pers.getString(pers.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CORREO)));
            txtpass.setText(pers.getString(pers.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_PASSWORD)));
            txtci.setText(pers.getString(pers.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_CI)));
            txtfecha.setText(pers.getString(pers.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_PERSONA_FECHA)));
        }*/

        //Lista desplegable de cargos
        CargoS=(Spinner) findViewById(R.id.spinner);
        String[] cargos = {"Supervisor","Administrador"};
        CargoS.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,cargos));
        CargoS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(parent.getContext(), (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                txtcargo = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txtcargo="0";
            }
        });
        //registro
        btnRegistrar=(Button)findViewById(R.id.btnregistrar);
        txtnombre=(EditText) findViewById(R.id.editName);
        txtemail=(EditText) findViewById(R.id.editEmail);
        txtpass=(EditText) findViewById(R.id.editPass);
        txttelefono=(EditText) findViewById(R.id.editTelf);
        txtci=(EditText) findViewById(R.id.editCI);

        btnRegistrar.setOnClickListener(new View.OnClickListener(){
                                          @Override
                                          public void onClick(View v){
                                              Thread tr=new Thread(){
                                                  @Override
                                                  public void run() {

                                                      final Conexion con=new Conexion();
                                                      String estado="1";
                                                      final String res= con.InsertRegistro(txtnombre.getText().toString(),txtemail.getText().toString(),txtpass.getText().toString(),txttelefono.getText().toString(),
                                                              txtfecha.getText().toString(), cargoT(txtcargo), txtci.getText().toString(),estado);
                                                      runOnUiThread(new Runnable() {
                                                          @Override
                                                          public void run() {
                                                              if(res=="El usuario ya esta registrado por favor utilice otro"){
                                                                  Toast.makeText(getApplicationContext(),res, Toast.LENGTH_SHORT).show();
                                                              }else{
                                                                  Toast.makeText(getApplicationContext(),"Registrado correctamente", Toast.LENGTH_SHORT).show();
                                                                  //Intent i= new Intent(registro.this,MainActivity.class);
                                                                  //startActivity(i);
                                                              }

                                                          }
                                                      });
                                                  }
                                              };
                                              if (verificarRegistrarUser()==true) {
                                                  tr.start();
                                              }else{
                                                  runOnUiThread(new Runnable() {
                                                      @Override
                                                      public void run() {
                                                          Toast.makeText(getApplicationContext(),"Rellene todos los campos",Toast.LENGTH_SHORT).show();
                                                      }
                                                  });
                                              }
                                          }
                                      }
        );

    }
    public Boolean verificarRegistrarUser(){
        boolean b=false;
        if(txtfecha.getText().toString().isEmpty()==false && txtnombre.getText().toString().isEmpty()==false
                &&txtemail.getText().toString().isEmpty()==false && txtpass.getText().toString().isEmpty()==false
                &&txttelefono.getText().toString().isEmpty()==false && txtci.getText().toString().isEmpty()==false
                && txtcargo.equals("0")==false){
            b=true;
        }
        return b;
    }
    String cargoT(String c)
    {
        String a;
        if(c=="supervisor"){
            a="1";
        }else{
            a="2";
        }
        return a;
    }




}
