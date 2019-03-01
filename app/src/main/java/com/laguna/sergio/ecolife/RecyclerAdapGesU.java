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

public class RecyclerAdapGesU extends RecyclerView.Adapter<RecyclerAdapGesU.ViewHolder>{

    Context context;

    List<DataAdapterGesU> dataAdapters;

    public RecyclerAdapGesU(List<DataAdapterGesU> getDataAdapterGesU, Context context){

        super();

        this.dataAdapters = getDataAdapterGesU;
        this.context = context;
    }

    @Override
    public RecyclerAdapGesU.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usuarioscard, parent, false);

        RecyclerAdapGesU.ViewHolder viewHolder = new RecyclerAdapGesU.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapGesU.ViewHolder viewHolder, int position) {

        DataAdapterGesU dataAdapter =  dataAdapters.get(position);

        //viewHolder.TextViewCargo.setText("Cargo: "+dataAdapter.getCargo());//raidlvl
        if (dataAdapter.getCargo().equals("1")){
            //viewHolder.ImaViewEstado.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.smiley));
            viewHolder.TextViewCargo.setText("Cargo: Supervisor");//raidlvl
        }else if (dataAdapter.getCargo().equals("2")){
            //viewHolder.ImaViewEstado.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.candado));
            viewHolder.TextViewCargo.setText("Cargo: Administrador");//raidlvl

        }
        viewHolder.TextViewNombre.setText("Nombre: "+dataAdapter.getNombre());//gym

        if (dataAdapter.getEstado().equals("1")){
            viewHolder.ImaViewEstado.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.smiley));
            viewHolder.TextViewEstado.setText("Estado: Activo");
        }else if (dataAdapter.getEstado().equals("0")){
            viewHolder.ImaViewEstado.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.candado));
            viewHolder.TextViewEstado.setText("Estado: Bloqueado");
        }else if (dataAdapter.getEstado().equals("2")) {
            viewHolder.ImaViewEstado.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.parque));
            viewHolder.TextViewEstado.setText("Estado: Vacaciones");
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

        public TextView TextViewCargo;
        public TextView TextViewNombre;
        public TextView TextViewEstado;
        public ImageView ImaViewEstado;


        public ViewHolder(View itemView) {

            super(itemView);

            TextViewCargo = itemView.findViewById(R.id.cargoGU) ;
            TextViewNombre = itemView.findViewById(R.id.nombreGU) ;
            TextViewEstado = itemView.findViewById(R.id.estadotextGU) ;
            ImaViewEstado = itemView.findViewById(R.id.estadoGU) ;
        }
    }
}
