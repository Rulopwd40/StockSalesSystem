package com.libcentro.demo.services.interfaces;

import com.libcentro.demo.model.Categoria;

import java.util.List;

public interface IcategoriaService {


    Categoria getCategoria(String nombre);

    public List<Categoria> getAll();
    public void saveCategoria(Categoria x);
    public void deleteCategoria(Categoria x);
    public void updateCategoria(Categoria x);

}
