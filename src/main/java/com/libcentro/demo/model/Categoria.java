package com.libcentro.demo.model;


import com.libcentro.demo.model.dto.CategoriaDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "categoria", nullable = false)
    private String nombre;

    public Categoria ( CategoriaDTO categoria ){
        this.id = categoria.getId();
        this.nombre = categoria.getNombre();
    }
}