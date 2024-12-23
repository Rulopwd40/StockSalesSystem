package com.libcentro.demo.model.id;

import lombok.Data;

import java.io.Serializable;

@Data
public class VentaProductoId implements Serializable {

    private long id_venta;
    private String codigobarras;

}
