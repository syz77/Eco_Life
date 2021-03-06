package com.laguna.sergio.ecolife;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapHTVCinfo extends RecyclerView.Adapter<RecyclerAdapHTVCinfo.ViewHolder>{

    Context context;

    List<DataAdapterHTVCinfo> dataAdapters;

    public RecyclerAdapHTVCinfo(List<DataAdapterHTVCinfo> getDataAdapterHTVCinfo, Context context){

        super();

        this.dataAdapters = getDataAdapterHTVCinfo;
        this.context = context;
    }

    @Override
    public RecyclerAdapHTVCinfo.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.htvcinfocard, parent, false);

        RecyclerAdapHTVCinfo.ViewHolder viewHolder = new RecyclerAdapHTVCinfo.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapHTVCinfo.ViewHolder viewHolder, int position) {

        DataAdapterHTVCinfo dataAdapter =  dataAdapters.get(position);

        viewHolder.TextViewFecha.setText("Fecha: "+dataAdapter.getFecha());//gym
        //viewHolder.TextViewCargo.setText("Cargo: "+dataAdapter.getCargo());//raidlvl
        viewHolder.TextViewCuota.setText("Cuota: "+dataAdapter.getCuota());
        viewHolder.TextViewMonto.setText("monto: "+dataAdapter.getMonto());
        viewHolder.TextViewSubtotal.setText("subtotal: "+dataAdapter.getSubtotal());

    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    public void eliminarRecycler() {
        dataAdapters.remove(getItemCount());

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView TextViewFecha;
        public TextView TextViewCuota;
        public TextView TextViewMonto;
        public TextView TextViewSubtotal;

        public ViewHolder(View itemView) {

            super(itemView);

            TextViewFecha = itemView.findViewById(R.id.fechahtvcinfo) ;
            TextViewCuota = itemView.findViewById(R.id.cuotahtvcinfo) ;
            TextViewMonto = itemView.findViewById(R.id.montohtvcinfo) ;
            TextViewSubtotal = itemView.findViewById(R.id.subtotalhtvcinfo) ;

        }
    }
}
