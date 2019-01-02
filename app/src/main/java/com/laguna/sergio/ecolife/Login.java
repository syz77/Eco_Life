package com.laguna.sergio.ecolife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText txtCor, txtPass;
    TextView btnIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtCor=(EditText)findViewById(R.id.editText2);
        txtPass=(EditText)findViewById(R.id.editText3);
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
                                                       final String res= con.enviarPost(txtCor.getText().toString(),txtPass.getText().toString());
                                                       runOnUiThread(new Runnable() {
                                                           @Override
                                                           public void run() {
                                                               int r=con.objJson(res);

                                                               if (r>0){
                                                                   Intent i= new Intent(Login.this,Menu.class);
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


}
