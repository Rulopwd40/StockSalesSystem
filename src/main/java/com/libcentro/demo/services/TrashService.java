package com.libcentro.demo.services;

import com.libcentro.demo.repository.IproductoRepository;
import com.libcentro.demo.services.interfaces.ialertService;
import com.libcentro.demo.services.interfaces.itrashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TrashService implements itrashService {

    private final IproductoRepository productoRepository;
    private final ialertService alertService;

    @Autowired
    public TrashService( IproductoRepository productoRepository, ialertService alertService ) {
        this.productoRepository = productoRepository;
        this.alertService = alertService;
        exec();
    }

    private void exec() {
        eliminacionTotalDeProductos ();
    }

    @Override
    public void eliminacionTotalDeProductos (){
        LocalDateTime hace365Dias = LocalDateTime.now().minusDays(365);
        int eliminados = productoRepository.eliminarProductosEliminadosHaceMasDe(hace365Dias);
        if(eliminados > 0) alertService.alert ("Se borraron " + eliminados + " productos de la base de datos. Razón: fueron eliminados hace mas de un año.");
    }
}
