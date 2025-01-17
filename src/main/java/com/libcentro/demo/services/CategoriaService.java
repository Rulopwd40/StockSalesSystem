package com.libcentro.demo.services;

import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.model.dto.CategoriaDTO;
import com.libcentro.demo.repository.IcategoriaRepository;
import com.libcentro.demo.repository.IproductoRepository;
import com.libcentro.demo.services.interfaces.IcategoriaService;
import com.libcentro.demo.services.interfaces.IproductoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService implements IcategoriaService {

    private final IcategoriaRepository categoriaRepository;
    private final IproductoService productoService;

    @Autowired
    public CategoriaService(IcategoriaRepository categoriaRepository,@Lazy IproductoService productoService) {
        this.categoriaRepository = categoriaRepository;
        this.productoService = productoService;
    }

    @Override
    public CategoriaDTO getCategoriaDTO ( String nombre) {
        Categoria categoria = categoriaRepository.findByNombre(nombre);

        if(categoria != null) {
            CategoriaDTO categoriaDTO = new CategoriaDTO ();
            categoriaDTO.setNombre (categoria.getNombre ());
            categoriaDTO.setId (categoria.getId ());
            return categoriaDTO;
        }
        return null;
    }
    @Override
    public Categoria getCategoria ( String nombre ){
        Categoria categoria = categoriaRepository.findByNombre (nombre);
        if(categoria != null) {
            return categoria;
        }
        return null;
    }

    @Override
    public List<CategoriaDTO> getAll() {
        List <Categoria> categorias = categoriaRepository.findAll();

        return categorias.stream().map (c -> {
            CategoriaDTO dto = new CategoriaDTO();
            dto.setId(c.getId());
            dto.setNombre(c.getNombre());
            return dto;
        }).toList ();
    }

    @Override
    public CategoriaDTO saveCategoria( CategoriaDTO x) {
        Categoria categoria = new Categoria ();
        categoria.setNombre(x.getNombre());

         categoria = categoriaRepository.save(categoria);

        x.setId(categoria.getId());
        return x;
    }

    @Transactional
    @Override
    public void deleteCategoria( CategoriaDTO x) {
        Categoria categoria = categoriaRepository.findByNombre (x.getNombre());
        productoService.setCategoriaNull (categoria.getId());
        categoriaRepository.delete(categoria);
    }

    @Override
    public void updateCategoria(CategoriaDTO x) {
        Categoria categoria = categoriaRepository.findByNombre (x.getNombre());
        categoria.setNombre(x.getNombre());
        categoriaRepository.save(categoria);
        ;
    }



}
