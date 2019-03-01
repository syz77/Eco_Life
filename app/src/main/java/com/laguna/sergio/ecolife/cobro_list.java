package com.laguna.sergio.ecolife;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.laguna.sergio.ecolife.Datos.ecolifedb;
import com.laguna.sergio.ecolife.Datos.cobro;

import java.util.ArrayList;
import java.util.List;

public class cobro_list extends AppCompatActivity {
    List<DataAdapterCobro> DataAdapterClassListR;
    RecyclerView recyclerViewR;
    RecyclerView.Adapter recyclerViewadapterR;
    RecyclerView.LayoutManager recyclerViewlayoutManagerR;
    ArrayList<cobro> CobroList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cobro_list);
        DataAdapterClassListR = new ArrayList<>();
        DataAdapterClassListR.clear();
        recyclerViewR = (RecyclerView) findViewById(R.id.recicladorC);
        recyclerViewR.setHasFixedSize(true);
        recyclerViewlayoutManagerR = new LinearLayoutManager(this);
        recyclerViewR.setLayoutManager(recyclerViewlayoutManagerR);
        //generar();
    }

    public List<DataAdapterCobro> generar(ContentResolver mContentResolver, String idventacredito) {
        DataAdapterClassListR = new ArrayList<>();
        DataAdapterClassListR.clear();
        CobroList = new ArrayList<>();
        String[] args = new String[]{idventacredito};
        Cursor vc = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_COBRO, null,
                ecolifedb.EcoLifeEntry.COLUMN_COBRO_CREDITOID + "=?", args, null);
        while (vc.moveToNext()) {
            DataAdapterCobro GetDataAdapter3 = new DataAdapterCobro();
            String cnrocuota = vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_COBRO_NRO_CUOTA));
            GetDataAdapter3.setNrocuota(cnrocuota);
            String cmonto = vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_COBRO_MONTO));
            GetDataAdapter3.setMonto(cmonto);
            String csubtotal = vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_COBRO_SUBTOTAL));
            GetDataAdapter3.setSubtotal(csubtotal);
            String fechac=vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_COBRO_FECHA));
            GetDataAdapter3.setFechaCobro(fechac);
            DataAdapterClassListR.add(GetDataAdapter3);
        }


        return DataAdapterClassListR;
        //recyclerViewadapterR = new RecyclerAdapTalo(DataAdapterClassListR, this);
        //recyclerViewR.setAdapter(recyclerViewadapterR);
    }
}
