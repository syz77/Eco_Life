package com.laguna.sergio.ecolife;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import com.laguna.sergio.ecolife.Datos.persona;
import com.laguna.sergio.ecolife.Datos.ecolifedb;


public class GestionarUser extends AppCompatActivity {
    List<DataAdapterGesU> DataAdapterClassListG;
    RecyclerView recyclerViewG;
    RecyclerView.Adapter recyclerViewadapterG;
    RecyclerView.LayoutManager recyclerViewlayoutManagerG;
    String s;
    persona[] p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_user);

        DataAdapterClassListG = new ArrayList<>();
        DataAdapterClassListG.clear();
        recyclerViewG = findViewById(R.id.recicladorG);
        recyclerViewG.setHasFixedSize(true);
        recyclerViewlayoutManagerG = new LinearLayoutManager(this);
        recyclerViewG.setLayoutManager(recyclerViewlayoutManagerG);
    }

    public List<DataAdapterGesU> generar() {
        DataAdapterClassListG = new ArrayList<>();
        DataAdapterClassListG.clear();
        //recyclerViewR.setAdapter(recyclerViewadapterR);
        final Conexion con = new Conexion();
        final String correo="sdfasdf";
        final String pass="sdfsad";

        Thread tr = new Thread() {
            @Override
            public void run() {
                s=con.AllUser(correo,pass);
                persona a=new persona();
                p=a.listaP(s);
            }
        };
        tr.start();
        for (int i=0; i<p.length; i++){

            DataAdapterGesU GetDataAdapter3 = new DataAdapterGesU();
            if(p[i].Rol.equals("1")) {
                GetDataAdapter3.setCargo("Administrador");
            }else{
                GetDataAdapter3.setCargo("Supervisor");
            }
            GetDataAdapter3.setNombre(p[i].Nombre);
            if (p[i].Estado.equals("1")){
                GetDataAdapter3.setEstado("1");
            }else if(p[i].Estado.equals("2")){
                GetDataAdapter3.setEstado("3");
            }else if(p[i].Estado.equals("0")){
                GetDataAdapter3.setEstado("2");
            }
            //GetDataAdapter3.setEstado("1");

            DataAdapterClassListG.add(GetDataAdapter3);

        }

        return DataAdapterClassListG;
        //recyclerViewadapterR = new RecyclerAdapTalo(DataAdapterClassListR, this);
        //recyclerViewR.setAdapter(recyclerViewadapterR);
    }
}
