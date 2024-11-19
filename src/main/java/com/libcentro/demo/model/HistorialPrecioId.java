package com.libcentro.demo.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class HistorialPrecioId implements Serializable {

    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "codigo_barras", nullable = false) // Solo el campo clave
    private String codigoBarrasp;

    public HistorialPrecioId ( Integer id, String codigoBarrasp ){
        this.id = id;
        this.codigoBarrasp = codigoBarrasp;
    }

    public HistorialPrecioId (){

    }

    public Integer getId (){
        return id;
    }

    public void setId ( Integer id ){
        this.id = id;
    }

    public String getCodigoBarrasp (){
        return codigoBarrasp;
    }

    public void setCodigoBarrasp ( String codigoBarrasp ){
        this.codigoBarrasp = codigoBarrasp;
    }
}
