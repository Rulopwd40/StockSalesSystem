package com.libcentro.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libcentro.demo.model.venta;
import com.libcentro.demo.repository.IventaRepository;
import com.libcentro.demo.service.interfaces.IventaService;
@Service
public class ventaService implements IventaService {
    @Autowired
    private IventaRepository ventaRepo;
    @Override
    public List<venta> getAll() {
            return ventaRepo.findAll();
    }

    @Override
    public void saveVenta(venta x) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveVenta'");
    }

    @Override
    public void deleteVenta(venta x) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteVenta'");
    }

    @Override
    public void updateVenta(venta x) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateVenta'");
    }
    
}
