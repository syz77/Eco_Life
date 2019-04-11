package com.laguna.sergio.ecolife;

public class DataAdapterVentaCred {
    String nro;
    String nombre;
    String fecha;
    String direccion;
    String Saldo;
    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public String getNombre(){return nombre;}

    public void setNombre(String nombre){this.nombre=nombre;}

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getSaldo() {
        return Saldo;
    }

    public void setSaldo(String saldo) {
        Saldo = saldo;
    }


}
