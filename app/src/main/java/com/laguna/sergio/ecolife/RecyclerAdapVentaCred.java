package com.laguna.sergio.ecolife;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapVentaCred extends RecyclerView.Adapter<RecyclerAdapVentaCred.ViewHolder> {
    Context context;

    List<DataAdapterVentaCred> dataAdapters;

    public RecyclerAdapVentaCred(List<DataAdapterVentaCred> getDataAdapterVC, Context context){

        super();

        this.dataAdapters = getDataAdapterVC;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.venta_cred_card, parent, false);

        ViewHolder viewHolder = new RecyclerAdapVentaCred.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapVentaCred.ViewHolder viewHolder, int position) {

        DataAdapterVentaCred dataAdapter =  dataAdapters.get(position);

        viewHolder.TextViewNroVC.setText("Nro de Venta a crédito: "+dataAdapter.getNro());//
        viewHolder.TextViewNombreVC.setText("Nombre Cliente: "+dataAdapter.getNombre());
        viewHolder.TextViewFechaVC.setText("Fecha: "+dataAdapter.getFecha());//
        viewHolder.TextSaldo.setText("Saldo: "+dataAdapter.getSaldo());
        if(dataAdapter.getSaldo().equals("0")){
            viewHolder.card.setCardBackgroundColor(Color.GREEN);
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

        public TextView TextViewNroVC;
        public TextView TextViewNombreVC;
        public TextView TextViewFechaVC;
        public TextView TextSaldo;
        public CardView card;


        public ViewHolder(View itemView) {

            super(itemView);

            TextViewNroVC = itemView.findViewById(R.id.nroCredito) ;
            TextViewNombreVC=itemView.findViewById(R.id.nombreCredito);
            TextViewFechaVC = itemView.findViewById(R.id.fechaCredito) ;
            TextSaldo=itemView.findViewById(R.id.saldoCredito);
            card=itemView.findViewById(R.id.ventaCard);

        }
    }
}
