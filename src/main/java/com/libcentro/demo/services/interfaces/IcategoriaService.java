package com.libcentro.demo.services.interfaces;

import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.model.dto.CategoriaDTO;

import java.util.List;

public interface IcategoriaService {


    CategoriaDTO getCategoria( String nombre);

    public List<CategoriaDTO> getAll();
    public void saveCategoria( CategoriaDTO x);
    public void deleteCategoria( CategoriaDTO x);
    public void updateCategoria(Categoria x);

}
