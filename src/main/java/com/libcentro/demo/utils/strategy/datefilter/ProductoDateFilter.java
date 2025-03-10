package com.libcentro.demo.utils.strategy.datefilter;

import com.libcentro.demo.model.Venta_Producto;
import com.libcentro.demo.repository.IventaproductoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoDateFilter implements DateFilterStrategy<Venta_Producto,IventaproductoRepository, LocalDateTime> {

    @Override
    public List<Venta_Producto> filtrar ( IventaproductoRepository repository, String codigo, LocalDateTime[] fechas ){
        List<Venta_Producto> vp= repository.findByCodigo_barrasAndVentaFechaBetween (codigo,fechas[0],fechas[1]);
        if(vp.isEmpty ()) throw new RuntimeException("No se encontro información del producto");
        return vp.stream().filter (vn -> vn.getCantidad () != 0).collect(Collectors.toList ());
    }
}
