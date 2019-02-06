package com.laguna.sergio.ecolife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GestionarUser extends AppCompatActivity {
    List<DataAdapterGesU> DataAdapterClassListG;
    RecyclerView recyclerViewG;
    RecyclerView.Adapter recyclerViewadapterG;
    RecyclerView.LayoutManager recyclerViewlayoutManagerG;
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

    public List<DataAdapterGesU> generar(){
        DataAdapterClassListG = new ArrayList<>();
        DataAdapterClassListG.clear();
        //recyclerViewR.setAdapter(recyclerViewadapterR);

        for (int i=0; i<10; i++){

            DataAdapterGesU GetDataAdapter3 = new DataAdapterGesU();

            GetDataAdapter3.setCargo("Supervisor");
            GetDataAdapter3.setNombre("Juanita");
            if ((i==1) || (i==4)){
                GetDataAdapter3.setEstado("1");
            }else if((i==2) || (i==6)){
                GetDataAdapter3.setEstado("2");
            }else if((i==8) || (i==7)){
                GetDataAdapter3.setEstado("3");
            }else{
                GetDataAdapter3.setEstado("1");
            }
            //GetDataAdapter3.setEstado("1");

            DataAdapterClassListG.add(GetDataAdapter3);

        }

        return DataAdapterClassListG;
        //recyclerViewadapterR = new RecyclerAdapTalo(DataAdapterClassListR, this);
        //recyclerViewR.setAdapter(recyclerViewadapterR);
    }
}
