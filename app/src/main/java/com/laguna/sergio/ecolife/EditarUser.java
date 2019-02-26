package com.laguna.sergio.ecolife;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.laguna.sergio.ecolife.Datos.persona;

import com.laguna.sergio.ecolife.Datos.ecolifedb;

public class EditarUser extends AppCompatActivity {

    Spinner SEstado;
    String txtCargo, txtEstado;
    ContentResolver mContentResolver;
    Button btnEditUser;
    persona p;
    EditText edituser,editpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_user);
        mContentResolver=getContentResolver();
        btnEditUser=findViewById(R.id.btnEdituser);
        edituser=findViewById(R.id.editUsuario);
        editpass=findViewById(R.id.editContrase);
        p=new persona();
        int b=0;//Integer.parseInt(p.Estado);
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

        edituser.setText(p.Correo);
        editpass.setText(p.Password);


        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user=p.Correo;
                final String newuser = edituser.getText().toString();
                final String pass = editpass.getText().toString();
                final String estado=conver(txtEstado);
                final String cargo=conver(txtCargo);
                Thread tr = new Thread() {
                    @Override
                    public void run() {
                        final Conexion con2 = new Conexion();
                        final String r = con2.UpdateUser(user,newuser, pass,estado,cargo);
                        final int i = con2.objJson(r);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (i > 0) {
                                    Toast.makeText(getApplicationContext(), "El telefono se modifico exitosamente", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error de conexion a internet", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                };
                if(verif()) {
                    tr.start();
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"Rellene todos los campos", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
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

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(EditarUser.this, NavegacionMenu.class);
            startActivity(intent);
        }
        return false;
    }*/
}
