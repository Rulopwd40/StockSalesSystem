package com.libcentro.demo.services.interfaces;

import java.util.List;

import com.libcentro.demo.model.Venta;
import com.libcentro.demo.model.dto.VentaDTO;

public interface IventaService {
    public List<Venta> getAll();

    public Venta saveVenta(Venta x);

    public void deleteVenta(Venta x);

    public void updateVenta(Venta x);

    public void vender( VentaDTO venta);
}
