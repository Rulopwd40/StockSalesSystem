package com.libcentro.demo.services.interfaces;

import java.util.List;

import com.libcentro.demo.model.Venta;
import com.libcentro.demo.model.dto.PageDTO;
import com.libcentro.demo.model.dto.ProductoFStockDTO;
import com.libcentro.demo.model.dto.VentaDTO;
import com.libcentro.demo.model.dto.Venta_ProductoDTO;

public interface IventaService {
    public List<Venta> getAll();

    public Venta saveVenta(VentaDTO x);

    public void cambiarEstadoVenta ( String id);

    public void vender( VentaDTO venta);

    PageDTO<VentaDTO> getByPage ( String filter, int page, int page_size );

    VentaDTO getVentaById ( String value );

    void reembolsarVenta ( VentaDTO venta );

    void reembolsarProducto ( VentaDTO ventaSeleccionada, Venta_ProductoDTO vpd, int cantidadReembolsar );

    void reembolsarProducto ( VentaDTO ventaSeleccionada, ProductoFStockDTO pfsd, int cantidadReembolsar );
}
