package com.libcentro.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPageDTO {
    private List<ProductoDTO> productos;
    private int paginas;
}
