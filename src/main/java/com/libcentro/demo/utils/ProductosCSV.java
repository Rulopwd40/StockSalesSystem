package com.libcentro.demo.utils;

import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.services.CategoriaService;
import com.libcentro.demo.view.productos.TratarCategorias;

import java.io.*;
import java.util.*;

public abstract class ProductosCSV {

    public static List<Producto> obtenerProductos(String file,CategoriaService categoriaService) {
        List<Producto> productos = new ArrayList<Producto>();
        String linea;
        String separador = ";";
        Map<String,List<Producto>> productosATratar = new HashMap<>();


        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(separador);

                Categoria categoria= categoriaService.getCategoria(datos[2]);
                if(categoria==null) {
                    productosATratar.putIfAbsent(datos[2],new ArrayList<Producto>());
                }
                Producto producto = new Producto();
                if (datos.length == 7) {
                    producto.setCodigo_barras(datos[0]);
                    producto.setNombre(datos[1]);
                    producto.setCategoria(categoria);
                    producto.setStock(Integer.parseInt(datos[3]));
                    producto.setCosto_compra(Float.parseFloat(datos[4]));
                    producto.setPrecio_venta(Float.parseFloat(datos[5]));
                }
                if(categoria==null) {
                    productosATratar.get(datos[2]).add(producto);
                }
            }
            if(!productosATratar.isEmpty()) {
                TratarCategorias tc = new TratarCategorias();
                tc.setVisible(true);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return productos;
    }
}
