package com.libcentro.demo.services.interfaces;

import com.libcentro.demo.model.Categoria;

import java.util.List;

public interface IcategoriaService {
    public List<Categoria> getAll();
    public void saveProducto(Categoria x);
    public void deleteProducto(Categoria x);
    public void updateProducto(Categoria x);

}
