package com.libcentro.demo.service.interfaces;

import java.util.List;

import com.libcentro.demo.model.venta;

public interface IventaService {
    public List<venta> getAll();

    public void saveVenta(venta x);

    public void deleteVenta(venta x);

    public void updateVenta(venta x);
}
