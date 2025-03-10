package com.libcentro.demo.services;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.dto.HistorialCostoDTO;
import com.libcentro.demo.model.dto.HistorialPrecioDTO;
import com.libcentro.demo.repository.IhistorialCostoRepository;
import com.libcentro.demo.repository.IhistorialPrecioRepository;
import com.libcentro.demo.services.interfaces.IhistorialService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorialService implements IhistorialService {

    private final IhistorialCostoRepository historialCostoRepository;
    private final IhistorialPrecioRepository historialPrecioRepository;

    public HistorialService ( IhistorialCostoRepository historialCostoRepository, IhistorialPrecioRepository historialPrecioRepository ){
        this.historialCostoRepository = historialCostoRepository;
        this.historialPrecioRepository = historialPrecioRepository;
    }

    @Override
    public HistorialCosto crearHistorialCosto ( Producto nuevoProducto, double costoCompra, int cantidad, HistorialCosto.Estado estado ){
            HistorialCosto historialCosto = new HistorialCosto();
            historialCosto.setCantidad(cantidad);
            historialCosto.setEstado(estado);
            historialCosto.setProducto(nuevoProducto);
            historialCosto.setCosto_compra (costoCompra);
            historialCosto.setCantidad_vendida (0);

            LocalDateTime fecha = LocalDateTime.now();
            historialCosto.setFecha(fecha);

            return historialCosto;
    }
    @Override
    public HistorialPrecio crearHistorialPrecio ( Producto producto, double precioVenta ){
        HistorialPrecio historialPrecio = new HistorialPrecio();
        historialPrecio.setPrecioVenta (precioVenta);
        historialPrecio.setProducto(producto);
        LocalDateTime fecha = LocalDateTime.now();
        historialPrecio.setFecha(fecha);

        return historialPrecio;
    }

    @Override
    public HistorialCosto findHistorialInicial ( Producto producto ){
       return historialCostoRepository.findInicial(producto.getCodigobarras (), HistorialCosto.Estado.INICIAL);
    }

    @Override
    public HistorialCosto findNext ( Producto producto ){
        return historialCostoRepository.findNext(producto.getCodigobarras (), HistorialCosto.Estado.SIGUIENTE);
    }

    @Override
    public void save ( HistorialCosto historialExistente ){
        historialCostoRepository.save(historialExistente);
    }

    @Override
    public void save ( HistorialPrecio historialPrecio ){
        historialPrecioRepository.save(historialPrecio);
    }

    @Override
    public void delete ( HistorialCosto historialCosto ){
        historialCostoRepository.delete(historialCosto);
    }

    @Override
    public void delete ( HistorialPrecio historialPrecio ){
        historialPrecioRepository.delete(historialPrecio);
    }

    @Override
    public HistorialPrecio findLastHistorialPrecio ( Producto producto ){
        return historialPrecioRepository.findFirstByProductoCodigobarrasOrderByIdDesc (producto.getCodigobarras ());
    }
    @Override
    public HistorialCosto findLastHistorialCosto ( Producto producto ){
        return historialCostoRepository.findFirstByProductoCodigobarrasOrderByIdDesc (producto.getCodigobarras ());
    }

    @Override
    public List<HistorialPrecioDTO> findAllPrecioByProducto ( String codbar ){
        return historialPrecioRepository.findAllByProducto (codbar).stream().map (HistorialPrecioDTO::new).collect(Collectors.toList ());
    }
    @Override
    public List<HistorialCostoDTO> findAllCostoByProducto ( String codbar ){
        return historialCostoRepository.findAllByProducto (codbar).stream ().map(HistorialCostoDTO::new).collect(Collectors.toList());
    }

    @Override
    public HistorialCosto findAnterior ( HistorialCosto historialExistente ){
        return historialCostoRepository.findAnteriorByFecha(historialExistente.getFecha (),historialExistente.getProducto ().getCodigobarras ());
    }

    @Override
    public HistorialCosto findSiguiente ( HistorialCosto historialCosto ){
        return historialCostoRepository.findSiguienteByFecha (historialCosto.getFecha (),historialCosto.getProducto ().getCodigobarras ());
    }

    @Override
    @Transactional
    public void deleteAllByCodigo ( String codigobarras ){
        try {
            historialCostoRepository.deleteAllByProducto (codigobarras);
            historialPrecioRepository.deleteAllByProducto (codigobarras);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }


}
