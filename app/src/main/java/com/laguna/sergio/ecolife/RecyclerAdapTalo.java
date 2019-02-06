package com.laguna.sergio.ecolife;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;

public class RecyclerAdapTalo extends RecyclerView.Adapter<RecyclerAdapTalo.ViewHolder>{

    Context context;

    List<DataAdapterTalo> dataAdapters;

    public RecyclerAdapTalo(List<DataAdapterTalo> getDataAdapterTalo, Context context){

        super();

        this.dataAdapters = getDataAdapterTalo;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.talonariocard, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapTalo.ViewHolder viewHolder, int position) {

        DataAdapterTalo dataAdapter =  dataAdapters.get(position);

        viewHolder.TextViewNroTalo.setText("Nro de talonario: "+dataAdapter.getNroTalo());//raidlvl

        viewHolder.TextViewFecha.setText("Fecha: "+dataAdapter.getFechaTalo());//gym

    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    public void eliminarRecycler() {
        dataAdapters.remove(getItemCount());

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView TextViewNroTalo;
        public TextView TextViewFecha;


        public ViewHolder(View itemView) {

            super(itemView);

            TextViewNroTalo = itemView.findViewById(R.id.nroTalonario) ;
            TextViewFecha = itemView.findViewById(R.id.fechaTalonario) ;


        }
    }
}
