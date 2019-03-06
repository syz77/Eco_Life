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

public class RecyclerAdapGUTCambiar extends RecyclerView.Adapter<RecyclerAdapGUTCambiar.ViewHolder>{

    Context context;

    List<DataAdapterGesU> dataAdapters;

    public RecyclerAdapGUTCambiar(List<DataAdapterGesU> getDataAdapterGesU, Context context){

        super();

        this.dataAdapters = getDataAdapterGesU;
        this.context = context;
    }

    @Override
    public RecyclerAdapGUTCambiar.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gutcambiarcard, parent, false);

        RecyclerAdapGUTCambiar.ViewHolder viewHolder = new RecyclerAdapGUTCambiar.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapGUTCambiar.ViewHolder viewHolder, int position) {

        DataAdapterGesU dataAdapter =  dataAdapters.get(position);

        //viewHolder.TextViewCargo.setText("Cargo: "+dataAdapter.getCargo());//raidlvl
        if (dataAdapter.getCargo().equals("1")){
            //viewHolder.ImaViewEstado.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.smiley));
            viewHolder.TextViewTCCargo.setText("Cargo: Supervisor");//raidlvl
        }else if (dataAdapter.getCargo().equals("2")){
            //viewHolder.ImaViewEstado.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.candado));
            viewHolder.TextViewTCCargo.setText("Cargo: Administrador");//raidlvl

        }
        viewHolder.TextViewTCNombre.setText("Nombre: "+dataAdapter.getNombre());//gym

        if (dataAdapter.getEstado().equals("1")){
            viewHolder.ImaViewTCEstado.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.smiley));
            viewHolder.TextViewTCEstado.setText("Estado: Activo");
        }else if (dataAdapter.getEstado().equals("0")){
            viewHolder.ImaViewTCEstado.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.candado));
            viewHolder.TextViewTCEstado.setText("Estado: Bloqueado");
        }else if (dataAdapter.getEstado().equals("2")) {
            viewHolder.ImaViewTCEstado.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.parque));
            viewHolder.TextViewTCEstado.setText("Estado: Vacaciones");
        }

    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    public void eliminarRecycler() {
        dataAdapters.remove(getItemCount());

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView TextViewTCCargo;
        public TextView TextViewTCNombre;
        public TextView TextViewTCEstado;
        public ImageView ImaViewTCEstado;


        public ViewHolder(View itemView) {

            super(itemView);

            TextViewTCCargo = itemView.findViewById(R.id.cargoGUTC) ;
            TextViewTCNombre = itemView.findViewById(R.id.nombreGUTC) ;
            TextViewTCEstado = itemView.findViewById(R.id.estadoGUTC) ;
            ImaViewTCEstado = itemView.findViewById(R.id.imgestadoGUTC) ;
        }
    }
}