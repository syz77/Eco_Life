package com.laguna.sergio.ecolife;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapGUTvc extends RecyclerView.Adapter<RecyclerAdapGUTvc.ViewHolder> {

    Context context;

    List<DataAdapterGesUTaloVC> dataAdapters;

    public RecyclerAdapGUTvc(List<DataAdapterGesUTaloVC> getDataAdapterGesUTaloVC, Context context) {

        super();

        this.dataAdapters = getDataAdapterGesUTaloVC;
        this.context = context;
    }

    @Override
    public RecyclerAdapGUTvc.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gutventcredcard, parent, false);

        RecyclerAdapGUTvc.ViewHolder viewHolder = new RecyclerAdapGUTvc.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapGUTvc.ViewHolder viewHolder, int position) {

        DataAdapterGesUTaloVC dataAdapter = dataAdapters.get(position);

        viewHolder.TextViewNombreVC.setText("Cliente: " +dataAdapter.getNombre());//raidlvl

        int saldo = 140- Integer.parseInt(dataAdapter.getSubtotal());

        viewHolder.TextViewCuotaVC.setText("Cuotas: " + dataAdapter.getCuota()+"      Saldo: "+ saldo);//gym
        if (Integer.parseInt(dataAdapter.getSubtotal())==140){
            viewHolder.linearLayout.setBackgroundColor(Color.parseColor("#33FF99"));
        }
        //viewHolder.TextViewSubtotal.setText("Total: " + dataAdapter.getSubtotal());//gym

        viewHolder.TextViewFechaVC.setText("Fecha: " + dataAdapter.getGUTvcFecha());//gym


    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    public void eliminarRecycler() {
        dataAdapters.remove(getItemCount());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TextViewNombreVC;
        public TextView TextViewCuotaVC;
        //public TextView TextViewSubtotal;
        public TextView TextViewFechaVC;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {

            super(itemView);

            TextViewNombreVC = itemView.findViewById(R.id.nombreGUTvc);
            TextViewCuotaVC = itemView.findViewById(R.id.cuotaGUTvc);
            //TextViewSubtotal = itemView.findViewById(R.id.subtotalGUTvc);
            TextViewFechaVC = itemView.findViewById(R.id.fechaGUTvc);
            linearLayout = itemView.findViewById(R.id.Linear_gutalovc);

        }
    }
}
