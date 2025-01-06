package com.libcentro.demo.services.interfaces;

import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.model.dto.CategoriaDTO;

import java.util.List;

public interface IcategoriaService {


    CategoriaDTO getCategoriaDTO ( String nombre);

    Categoria getCategoria ( String nombre );

    public List<CategoriaDTO> getAll();
    public CategoriaDTO saveCategoria( CategoriaDTO x);
    public void deleteCategoria( CategoriaDTO x);
    public void updateCategoria(CategoriaDTO x);

}
