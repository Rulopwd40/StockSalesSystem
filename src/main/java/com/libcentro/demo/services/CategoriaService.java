package com.libcentro.demo.services;

import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.repository.IcategoriaRepository;
import com.libcentro.demo.services.interfaces.IcategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService implements IcategoriaService {

    @Autowired
    IcategoriaRepository icategoriaRepository;

    @Override
    public Categoria getCategoria(String nombre) {
        return icategoriaRepository.findByNombre(nombre);
    }

    @Override
    public List<Categoria> getAll() {
        return icategoriaRepository.findAll();
    }

    @Override
    public void saveCategoria(Categoria x) {
        icategoriaRepository.save(x);
    }

    @Override
    public void deleteCategoria(Categoria x) {
        icategoriaRepository.delete(x);
    }

    @Override
    public void updateCategoria(Categoria x) {
        icategoriaRepository.save(x);
    }



}
