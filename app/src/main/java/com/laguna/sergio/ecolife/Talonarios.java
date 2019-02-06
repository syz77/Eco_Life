package com.laguna.sergio.ecolife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Talonarios extends AppCompatActivity {

    List<DataAdapterTalo> DataAdapterClassListR;
    RecyclerView recyclerViewR;
    RecyclerView.Adapter recyclerViewadapterR;
    RecyclerView.LayoutManager recyclerViewlayoutManagerR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talonarios);
        DataAdapterClassListR = new ArrayList<>();
        DataAdapterClassListR.clear();
        recyclerViewR = (RecyclerView) findViewById(R.id.recicladorT);
        recyclerViewR.setHasFixedSize(true);
        recyclerViewlayoutManagerR = new LinearLayoutManager(this);
        recyclerViewR.setLayoutManager(recyclerViewlayoutManagerR);
        //generar();
    }


    public List<DataAdapterTalo> generar(){
        DataAdapterClassListR = new ArrayList<>();
        DataAdapterClassListR.clear();
        //recyclerViewR.setAdapter(recyclerViewadapterR);

        for (int i=0; i<10; i++){

            DataAdapterTalo GetDataAdapter3 = new DataAdapterTalo();

            GetDataAdapter3.setNroTalo("55");
            //SubjectNivelRaidlista.add("raidlvl");
            GetDataAdapter3.setFechaTalo("12/05/2018");

            DataAdapterClassListR.add(GetDataAdapter3);

        }

        return DataAdapterClassListR;
        //recyclerViewadapterR = new RecyclerAdapTalo(DataAdapterClassListR, this);
        //recyclerViewR.setAdapter(recyclerViewadapterR);
    }

}
