package com.libcentro.demo.model.dto;

import com.libcentro.demo.model.Categoria;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriaDTO {

    private long id;
    private String nombre;

    public CategoriaDTO ( Categoria categoria ){
        categoria.setId( categoria.getId());
        categoria.setNombre( categoria.getNombre());
    }
}
