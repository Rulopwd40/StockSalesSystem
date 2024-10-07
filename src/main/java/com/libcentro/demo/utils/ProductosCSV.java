package com.libcentro.demo.utils;

import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.services.CategoriaService;
import com.libcentro.demo.view.productos.TratarCategorias;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.*;

public class ProductosCSV {
    List<Producto> productos = new ArrayList<Producto>();
    Map<String,List<Producto>> productosATratar = new HashMap<>();
    CategoriaService categoriaService;
    TratarCategorias tc;

    public List<Producto> obtenerProductos(String file,CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
        String linea;
        String separador = ";";



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
                }else{
                    productos.add(producto);
                }
            }
            if(!productosATratar.isEmpty()) {
                tc = new TratarCategorias();

                DefaultTableModel tableModel = (DefaultTableModel) tc.getTablaCategorias().getModel();
                for(String categoria : productosATratar.keySet()) {
                    tableModel.addRow(new Object[]{categoria});
                }
                tc.getAnularButton().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int row = tc.getTablaCategorias().getSelectedRow();
                        if(row==-1){
                            JOptionPane.showMessageDialog(null,"Seleccione una columna","Error",JOptionPane.ERROR_MESSAGE);
                            throw new RuntimeException("Seleccione una columna");
                        }
                        anularProductos(tableModel.getValueAt(row,0).toString());

                    }
                });
                tc.getCrearButton().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int row = tc.getTablaCategorias().getSelectedRow();
                        if(row==-1){
                            JOptionPane.showMessageDialog(null,"Seleccione una columna","Error",JOptionPane.ERROR_MESSAGE);
                            throw new RuntimeException("Seleccione una columna");
                        }
                        crearCategorias(tableModel.getValueAt(row,0).toString());
                    }
                });
                tc.getElegirOtraCategoriaButton().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int row = tc.getTablaCategorias().getSelectedRow();
                        if(row==-1){
                            JOptionPane.showMessageDialog(null,"Seleccione una columna","Error",JOptionPane.ERROR_MESSAGE);
                            throw new RuntimeException("Seleccione una columna");
                        }
                        elegirOtraCategoria(tableModel.getValueAt(row,0).toString());
                    }
                });
                tc.getButtonOK().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if(tableModel.getRowCount() != 0){
                            JOptionPane.showMessageDialog(null,"Faltan categorías por tratar","Error",JOptionPane.ERROR_MESSAGE);
                            throw new RuntimeException("Faltan tratar categorías");
                        }
                        tc.onOK();
                    }
                }
                );
                tc.addWindowListener(new WindowAdapter() {
                    public void onClose(WindowEvent e) {
                        if(tableModel.getRowCount() != 0){
                            JOptionPane.showMessageDialog(null,"Faltan categorías por tratar","Error",JOptionPane.ERROR_MESSAGE);
                            throw new RuntimeException("Faltan tratar categorías");
                        }
                        tc.onOK();
                    }
                });
                tc.setVisible(true);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return productos;
    }

    private void anularProductos(String key){
            List<Producto> productosTratar = productosATratar.get(key);
            for(Producto producto : productosTratar) {
                producto.setCategoria(producto.getCategoria());
            }
            productos.addAll(productosTratar);
    }
    private void crearCategorias(String key){
            Categoria categoria = new Categoria(key);
            categoriaService.saveCategoria(categoria);

            List<Producto> productosTratar = productosATratar.get(key);

            for(Producto producto : productosTratar) {
                producto.setCategoria(categoria);
            }
            productos.addAll(productosTratar);

    }
    private void elegirOtraCategoria(String key){
        List<Producto> productosTratar = productosATratar.get(key);
        tc.getOptionalPane().setEnabled(true);
        tc.getTablaCategoriasExistentes().setEnabled(true);
        DefaultTableModel tableModel = (DefaultTableModel) tc.getTablaCategorias().getModel();
        tc.getElegirButton().setEnabled(true);
        List<Categoria> categorias= categoriaService.getAll();
        for(Categoria categoria : categorias) {
            tableModel.addRow(new Object[]{categoria.getNombre()});
        }


        tc.getElegirButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tc.getTablaCategorias().getSelectedRow();
                if(row==-1){
                    JOptionPane.showMessageDialog(null,"Seleccione una columna","Error",JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException("Seleccione una columna");
                }
                cambiarCategoria(tableModel.getValueAt(row,0).toString(),productosTratar);
                tc.getOptionalPane().setEnabled(false);
                tc.getTablaCategoriasExistentes().setEnabled(false);
                tc.getElegirButton().setEnabled(false);
            }
        });
    }

    private void cambiarCategoria(String string,List<Producto> productosTratar) {
        Categoria categoria = categoriaService.getCategoria(string);
        for (Producto p : productosTratar) {
            p.setCategoria(categoria);
        }
        productos.addAll(productosTratar);
    }
}
