package com.laguna.sergio.ecolife;

public class DataAdapterGesUTalo {

    String NroTalo;
    String FechaTalo;
    String Estado;
    String Creditos;
    String Saldos;

    public String getCreditos() { return Creditos; }

    public void setCreditos(String creditos) { Creditos = creditos; }

    public String getSaldos() { return Saldos; }

    public void setSaldos(String saldos) { Saldos = saldos; }

    public String getNroTalo() {
        return NroTalo;
    }

    public void setNroTalo(String nroTalo) {
        NroTalo = nroTalo;
    }

    public String getFechaTalo() {
        return FechaTalo;
    }

    public void setFechaTalo(String fechaTalo) {
        FechaTalo = fechaTalo;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}
