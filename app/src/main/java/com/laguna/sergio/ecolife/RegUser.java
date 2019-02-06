package com.laguna.sergio.ecolife;

import android.app.DatePickerDialog;
import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_user);
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
                                              //txtCor.setText("hola");
                                              //txtPass.setText("noo");
                                              Thread tr=new Thread(){
                                                  @Override
                                                  public void run() {
                                                      final Conexion con=new Conexion();
                                                      final String res= con.InsertRegistro(txtnombre.getText().toString(),txtemail.getText().toString(),txtpass.getText().toString(),txttelefono.getText().toString(),
                                                              txtfecha.getText().toString(), cargoT(txtcargo), txtci.getText().toString(),"activo");
                                                      runOnUiThread(new Runnable() {
                                                          @Override
                                                          public void run() {
                                                              Toast.makeText(getApplicationContext(),res, Toast.LENGTH_SHORT).show();
                                                              if(res=="El EMAIL ya esta registrado por favor utilice otro"){
                                                                  Toast.makeText(getApplicationContext(),res, Toast.LENGTH_SHORT).show();
                                                              }else{
                                                                  Toast.makeText(getApplicationContext(),"Registrado correctamente", Toast.LENGTH_SHORT).show();
                                                                  //Intent i= new Intent(registro.this,MainActivity.class);
                                                                  //startActivity(i);
                                                              }
                                /*int r=con.objJson(res);
                                if (r>0){
                                    Intent i= new Intent(registro.this,MainActivity.class);
                                    startActivity(i);
                                    Toast.makeText(getApplicationContext(),res, Toast.LENGTH_SHORT).show();
                                    }else{
                                    Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
                                    }*/
                                                          }
                                                      });
                                                  }
                                              };
                                              tr.start();
                                          }
                                      }
        );

    }
    String cargoT(String c)
    {
        int a;
        if(c=="supervisor"){
            a=1;
        }else{
            a=2;
        }
        return Integer.toString(a);
    }




}
