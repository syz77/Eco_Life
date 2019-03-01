package com.laguna.sergio.ecolife;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapGesUtalo extends RecyclerView.Adapter<RecyclerAdapGesUtalo.ViewHolder> {

    Context context;

    List<DataAdapterGesUTalo> dataAdapters;

    public RecyclerAdapGesUtalo(List<DataAdapterGesUTalo> getDataAdapterGesUTalo, Context context) {

        super();

        this.dataAdapters = getDataAdapterGesUTalo;
        this.context = context;
    }

    @Override
    public RecyclerAdapGesUtalo.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gutalocard, parent, false);

        RecyclerAdapGesUtalo.ViewHolder viewHolder = new RecyclerAdapGesUtalo.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapGesUtalo.ViewHolder viewHolder, int position) {

        DataAdapterGesUTalo dataAdapter = dataAdapters.get(position);

        viewHolder.TextViewNroHTalo.setText("Talonario: " + dataAdapter.getNroTalo());//raidlvl

        viewHolder.TextViewHFecha.setText("Fecha: " + dataAdapter.getFechaTalo());//gym

        if (dataAdapter.getEstado().equals("1")){
            viewHolder.TextViewHEstado.setText("Estado: Activo");
        }else if (dataAdapter.getEstado().equals("0")){
            viewHolder.TextViewHEstado.setText("Estado: Expirado");
        }else if (dataAdapter.getEstado().equals("2")) {
            viewHolder.TextViewHEstado.setText("Estado: Pasivo");
        }

    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    public void eliminarRecycler() {
        dataAdapters.remove(getItemCount());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TextViewNroHTalo;
        public TextView TextViewHFecha;
        public TextView TextViewHEstado;

        public ViewHolder(View itemView) {

            super(itemView);

            TextViewNroHTalo = itemView.findViewById(R.id.nroGUtalo);
            TextViewHFecha = itemView.findViewById(R.id.fechaGUtalo);
            TextViewHEstado = itemView.findViewById(R.id.estadoGUtalo);

        }
    }
}