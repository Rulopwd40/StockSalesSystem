package com.libcentro.demo.services;

import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.model.dto.CategoriaDTO;
import com.libcentro.demo.repository.IcategoriaRepository;
import com.libcentro.demo.services.interfaces.IcategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService implements IcategoriaService {

    @Autowired
    IcategoriaRepository categoriaRepository;

    @Override
    public CategoriaDTO getCategoria(String nombre) {
        Categoria categoria = categoriaRepository.findByNombre(nombre);

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setId(categoria.getId());
        return categoriaDTO;
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

    @Override
    public void deleteCategoria( CategoriaDTO x) {

        Categoria categoria = categoriaRepository.findByNombre (x.getNombre());
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
