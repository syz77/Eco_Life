package com.laguna.sergio.ecolife;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerAdapCobro extends RecyclerView.Adapter<RecyclerAdapCobro.ViewHolder> {
    Context context;

    List<DataAdapterCobro> dataAdapters;

    public RecyclerAdapCobro(List<DataAdapterCobro> getDataAdapterC, Context context){

        super();

        this.dataAdapters = getDataAdapterC;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cobro_card, parent, false);

        ViewHolder viewHolder = new RecyclerAdapCobro.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapCobro.ViewHolder viewHolder, int position) {

        DataAdapterCobro dataAdapter =  dataAdapters.get(position);

        viewHolder.TextViewNrocuotaC.setText("Nro de Cuota: "+dataAdapter.getNrocuota());//
        viewHolder.TextViewMontoC.setText("Monto: "+dataAdapter.getMonto());//
        viewHolder.TextViewSubtotalC.setText("Subtotal: "+dataAdapter.getSubtotal());
        viewHolder.TextViewFechaC.setText("Fecha de cobro: "+dataAdapter.getFechaCobro());

    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    public void eliminarRecycler() {
        dataAdapters.remove(getItemCount());

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView TextViewNrocuotaC;
        public TextView TextViewMontoC;
        public TextView TextViewSubtotalC;
        public TextView TextViewFechaC;


        public ViewHolder(View itemView) {

            super(itemView);

            TextViewNrocuotaC = itemView.findViewById(R.id.nrocuotaCobro) ;
            TextViewMontoC = itemView.findViewById(R.id.montoCobro) ;
            TextViewSubtotalC=itemView.findViewById(R.id.subtotalCobro);
            TextViewFechaC=itemView.findViewById(R.id.fechaCobro);


        }
    }
}
