package com.laguna.sergio.ecolife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText txtUser, txtPass;
    TextView btnIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUser=(EditText)findViewById(R.id.editUser);
        txtPass=(EditText)findViewById(R.id.editPass);
        btnIngresar=(TextView)findViewById(R.id.textView2);

        btnIngresar.setOnClickListener(new View.OnClickListener(){
                                           @Override
                                           public void onClick(View v){
                                               //txtCor.setText("hola");
                                               //txtPass.setText("noo");
                                               Thread tr=new Thread(){
                                                   @Override
                                                   public void run() {
                                                       final Conexion con=new Conexion();
                                                       final String res= con.enviarPost(txtUser.getText().toString(),txtPass.getText().toString());
                                                       runOnUiThread(new Runnable() {
                                                           @Override
                                                           public void run() {
                                                               int r=con.objJson(res);

                                                               if (r>0){
                                                                   Intent i= new Intent(Login.this,NavegacionMenu.class);
                                                                   startActivity(i);
                                                                   Toast.makeText(getApplicationContext(),res, Toast.LENGTH_SHORT).show();
                                                               }else{
                                                                   Toast.makeText(getApplicationContext(),"Usuario o password incorrectos", Toast.LENGTH_SHORT).show();
                                                               }

                                                           }
                                                       });
                                                   }
                                               };
                                               tr.start();
                                           }
                                       }
        );

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //Acción
        }
        return false;
    }

}
