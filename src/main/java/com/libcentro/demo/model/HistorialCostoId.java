package com.libcentro.demo.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class HistorialCostoId implements Serializable {

    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "codigo_barras", nullable = false) // Solo el campo clave
    private String codigoBarrasc;  // Usa el campo de clave como parte del ID

    public HistorialCostoId ( Integer id, String codigoBarrasc ){
        this.id = id;
        this.codigoBarrasc = codigoBarrasc;
    }

    public HistorialCostoId (){

    }

    public String getCodigoBarrasc (){
        return codigoBarrasc;
    }

    public void setCodigoBarrasc ( String codigoBarrasc ){
        this.codigoBarrasc = codigoBarrasc;
    }

    public Integer getId (){
        return id;
    }

    public void setId ( Integer id ){
        this.id = id;
    }
}
