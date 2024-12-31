package com.libcentro.demo.services.interfaces;

import java.util.List;

import com.libcentro.demo.model.Venta;
import com.libcentro.demo.model.dto.VentaDTO;

public interface IventaService {
    public List<Venta> getAll();

    public Venta saveVenta(VentaDTO x);

    public void deleteVenta(Long id);

    public void updateVenta(Venta x);

    public void vender( VentaDTO venta);
}
