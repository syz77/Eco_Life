package com.laguna.sergio.ecolife;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class EditarUser extends AppCompatActivity {

    Spinner SCargo,SEstado;
    String txtCargo, txtEstado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_user);

        SCargo=(Spinner) findViewById(R.id.spinnerCargo);
        String[] cargo = {"Supervisor","Administrador"};
        SCargo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,cargo));
        SCargo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(parent.getContext(), (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                txtCargo = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SEstado=(Spinner) findViewById(R.id.spinnerEstado);
        String[] estado = {"Habilitado","Vacaciones","Bloqueado"};
        SEstado.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,estado));
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(EditarUser.this, NavegacionMenu.class);
            startActivity(intent);
        }
        return false;
    }
}
