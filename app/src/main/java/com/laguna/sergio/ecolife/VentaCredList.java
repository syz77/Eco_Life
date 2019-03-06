package com.laguna.sergio.ecolife;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.laguna.sergio.ecolife.Datos.ecolifedb;
import com.laguna.sergio.ecolife.Datos.talonario;
import com.laguna.sergio.ecolife.Datos.venta_credito;

import java.util.ArrayList;
import java.util.List;

public class VentaCredList extends AppCompatActivity {
    List<DataAdapterVentaCred> DataAdapterClassListR;
    RecyclerView recyclerViewR;
    RecyclerView.Adapter recyclerViewadapterR;
    RecyclerView.LayoutManager recyclerViewlayoutManagerR;
    ArrayList<venta_credito> VentaCList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talonarios);
        DataAdapterClassListR = new ArrayList<>();
        DataAdapterClassListR.clear();
        recyclerViewR = (RecyclerView) findViewById(R.id.recicladorVC);
        recyclerViewR.setHasFixedSize(true);
        recyclerViewlayoutManagerR = new LinearLayoutManager(this);
        recyclerViewR.setLayoutManager(recyclerViewlayoutManagerR);
        //generar();
    }

    public List<DataAdapterVentaCred> generar(ContentResolver mContentResolver, String idtalo) {
        DataAdapterClassListR = new ArrayList<>();
        DataAdapterClassListR.clear();
        VentaCList = new ArrayList<>();
        //recyclerViewR.setAdapter(recyclerViewadapterR);
        String[] args = new String[]{idtalo};
        Cursor vc = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_VENTA_CREDITO, null,
                ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TALONARIOPID + "=?", args, null);
        int aux = 1;
        while (vc.moveToNext()) {
            venta_credito v = new venta_credito();
            DataAdapterVentaCred GetDataAdapter3 = new DataAdapterVentaCred();
            String vcid = vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry._VENTA_CREDITOID));
            GetDataAdapter3.setNro(Integer.toString(aux));
            v.Id = vcid;
            String vcf = vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_FECHA));
            GetDataAdapter3.setFecha(vcf);
            v.Fecha = vcf;
            String vce = vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_DIRECCION));
            GetDataAdapter3.setDireccion(vce);
            v.Direccion = vce;
            String nombre=vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_NOMBRE));
            String telefono=vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TELEFONO));
            String zona=vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_ZONA));
            String vendedor=vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_VENDEDOR));
            String nubeid=vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_NUBEID));
            v.NubeId=nubeid;
            v.Nombre=nombre;
            v.Telefono=telefono;
            v.Zona=zona;
            v.Vendedor=vendedor;
            DataAdapterClassListR.add(GetDataAdapter3);
            VentaCList.add(v);
            aux++;
        }


        return DataAdapterClassListR;
        //recyclerViewadapterR = new RecyclerAdapTalo(DataAdapterClassListR, this);
        //recyclerViewR.setAdapter(recyclerViewadapterR);
    }
    public ArrayList<venta_credito> vcplease(){
        return VentaCList;
    }
}
