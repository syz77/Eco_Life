package com.laguna.sergio.ecolife;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
    CardView ventacard;
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
        ventacard=findViewById(R.id.ventaCard);
        //generar();
    }

    public List<DataAdapterVentaCred> generar(ContentResolver mContentResolver, String idtalo) {
        DataAdapterClassListR = new ArrayList<>();
        DataAdapterClassListR.clear();
        VentaCList = new ArrayList<>();
        //ventacard=findViewById(R.id.ventaCard);
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
            String nombre=vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_NOMBRE));
            String telefono=vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_TELEFONO));
            String zona=vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_ZONA));
            String vendedor=vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_VENDEDOR));
            String nubeid=vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_NUBEID));
            String dir=vc.getString(vc.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_VENTACRED_DIRECCION));
            v.Direccion=dir;
            v.NubeId=nubeid;
            v.Nombre=nombre;
            GetDataAdapter3.setNombre(nombre);
            v.Telefono=telefono;
            v.Zona=zona;
            v.Vendedor=vendedor;
            String[] args3=new String[]{vcid};
            int saldo=140;
            Cursor VentaSaldo = mContentResolver.query(ecolifedb.EcoLifeEntry.CONTENT_URI_COBRO, null,
                    ecolifedb.EcoLifeEntry.COLUMN_COBRO_CREDITOID+"=?", args3,null);
            if(VentaSaldo.getCount()!=0){
                VentaSaldo.moveToLast();
                String n=VentaSaldo.getString(VentaSaldo.getColumnIndexOrThrow(ecolifedb.EcoLifeEntry.COLUMN_COBRO_SUBTOTAL));
                int subtotal=Integer.parseInt(n);
                saldo=140-subtotal;
            }
            GetDataAdapter3.setSaldo(Integer.toString(saldo));
            if(saldo==0){
                //ventacard.setBackgroundColor(Color.GREEN);
            }
            VentaSaldo.close();
            DataAdapterClassListR.add(GetDataAdapter3);
            VentaCList.add(v);
            aux++;
        }
        vc.close();


        return DataAdapterClassListR;
        //recyclerViewadapterR = new RecyclerAdapTalo(DataAdapterClassListR, this);
        //recyclerViewR.setAdapter(recyclerViewadapterR);
    }
    public ArrayList<venta_credito> vcplease(){
        return VentaCList;
    }
}
