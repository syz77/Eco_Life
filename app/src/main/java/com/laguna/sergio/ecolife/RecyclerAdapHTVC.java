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

public class RecyclerAdapHTVC extends RecyclerView.Adapter<RecyclerAdapHTVC.ViewHolder>{

    Context context;

    List<DataAdapterHTVC> dataAdapters;

    public RecyclerAdapHTVC(List<DataAdapterHTVC> getDataAdapterHTVC, Context context){
        super();
        this.dataAdapters = getDataAdapterHTVC;
        this.context = context;
    }

    @Override
    public RecyclerAdapHTVC.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.htventcredcard, parent, false);

        RecyclerAdapHTVC.ViewHolder viewHolder = new RecyclerAdapHTVC.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapHTVC.ViewHolder viewHolder, int position) {

        DataAdapterHTVC dataAdapter =  dataAdapters.get(position);

        viewHolder.TextViewHTVCCliente.setText("Cliente: "+ dataAdapter.getNombre());//raidlvl
        viewHolder.TextViewHTVCFecha.setText("Fecha: "+ dataAdapter.getFecha());//gym
        viewHolder.TextViewHTVCDireccion.setText("Direccion: "+ dataAdapter.getDireccion());
    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    public void eliminarRecycler() {
        dataAdapters.remove(getItemCount());

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView TextViewHTVCCliente;
        public TextView TextViewHTVCFecha;
        public TextView TextViewHTVCDireccion;


        public ViewHolder(View itemView) {

            super(itemView);

            TextViewHTVCCliente = itemView.findViewById(R.id.nombreHTvc) ;
            TextViewHTVCFecha = itemView.findViewById(R.id.fechaHTvc) ;
            TextViewHTVCDireccion = itemView.findViewById(R.id.direccionHTvc) ;
        }
    }
}
