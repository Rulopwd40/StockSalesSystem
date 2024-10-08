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
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ProductosCSV {
    List<Producto> productos = new ArrayList<Producto>();
    Map<String,List<Producto>> productosATratar = new HashMap<>();
    CategoriaService categoriaService;
    TratarCategorias tc;
    JTable table;
    DefaultTableModel tableModel;
    Set<Categoria> categoriasCreadas=new HashSet<>();


    public List<Producto> obtenerProductos(String file,CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
        String linea;
        String separador = ";";



        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(separador);

                Categoria categoria= categoriaService.getCategoria(datos[2]);
                if(categoria==null) {
                    productosATratar.putIfAbsent(datos[2],new ArrayList<Producto>());
                }
                Producto producto = new Producto();

                if (datos.length == 6) {
                    producto.setCodigo_barras(datos[0]);
                    producto.setNombre(datos[1]);
                    producto.setCategoria(categoria);
                    producto.setStock(Integer.parseInt(datos[3]));
                    producto.setCosto_compra(Float.parseFloat(datos[4]));
                    producto.setPrecio_venta(Float.parseFloat(datos[5]));
                }else{
                    throw new RuntimeException("Formato incorrecto de archivo");
                }
                if(categoria==null) {
                    productosATratar.get(datos[2]).add(producto);
                }else{
                    productos.add(producto);
                }
            }
            if(!productosATratar.isEmpty()) {
                tc = new TratarCategorias();
                table = tc.getTablaCategorias();
                tableModel = (DefaultTableModel) tc.getTablaCategorias().getModel();
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
                        anularProductos(row);


                    }
                });
                tc.getCrearButton().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int row = tc.getTablaCategorias().getSelectedRow();
                        if(row==-1){
                            JOptionPane.showMessageDialog(null,"Seleccione una columna","Error",JOptionPane.ERROR_MESSAGE);
                            throw new RuntimeException("Seleccione una columna");
                        }
                        crearCategorias(row);

                    }
                });
                tc.getElegirOtraCategoriaButton().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int row = tc.getTablaCategorias().getSelectedRow();

                        if(row==-1){
                            JOptionPane.showMessageDialog(null,"Seleccione una columna","Error",JOptionPane.ERROR_MESSAGE);
                            throw new RuntimeException("Seleccione una columna");
                        }
                        elegirOtraCategoria(row);
                    }
                });


                tc.getButtonOK().addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if(!productosATratar.isEmpty()) {
                            JOptionPane.showMessageDialog(null,"Faltan categorías por tratar","Error",JOptionPane.ERROR_MESSAGE);
                            throw new RuntimeException("Faltan tratar categorías");
                        }
                        tc.onOK();
                    }
                }
                );
                tc.addWindowListener(new WindowAdapter() {
                    public void onClose(WindowEvent e) {
                        if(!productosATratar.isEmpty()){
                            JOptionPane.showMessageDialog(null,"Faltan categorías por tratar","Error",JOptionPane.ERROR_MESSAGE);
                            throw new RuntimeException("Faltan tratar categorías");
                        }
                        tc.onOK();
                    }

                    @Override
                    public void windowStateChanged(WindowEvent e) {
                        tc.getTablaCategoriasExistentes().setEnabled(false);
                        tc.getElegirButton().setEnabled(false);
                    }
                });
                tc.setVisible(true);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return productos;
    }

    private boolean anularProductos(int row){
            String key= tableModel.getValueAt(row,0).toString();
            List<Producto> productosTratar = productosATratar.get(key);
            for(Producto producto : productosTratar) {
                producto.setCategoria(producto.getCategoria());
            }
            productos.addAll(productosTratar);
            tableModel.removeRow(row);
            productosATratar.remove(key);
            return true;
    }
    private boolean crearCategorias(int row){
            String key= tableModel.getValueAt(row,0).toString();
            Categoria categoria = new Categoria(key);
            categoriaService.saveCategoria(categoria);
            categoriasCreadas.add(categoria);

            List<Producto> productosTratar = productosATratar.get(key);

            for(Producto producto : productosTratar) {
                producto.setCategoria(categoria);
            }
            productos.addAll(productosTratar);
            productosATratar.remove(key);
            tableModel.removeRow(row);

            return true;
    }
    private boolean elegirOtraCategoria(int row) {
        String key = tableModel.getValueAt(row, 0).toString();
        final boolean[] bandera = {false};
        List<Producto> productosTratar = productosATratar.get(key);
        tc.getOptionalPane().setEnabled(true);
        tc.getTablaCategoriasExistentes().setEnabled(true);
        DefaultTableModel tableCategoriasModel = (DefaultTableModel) tc.getTablaCategoriasExistentes().getModel();
        tableCategoriasModel.setRowCount(0);
        tc.getElegirButton().setEnabled(true);

        List<Categoria> categorias = categoriaService.getAll();

        for (Categoria categoria : categorias) {
            tableCategoriasModel.addRow(new Object[]{categoria.getNombre()});
        }

        // Remove all previous listeners before adding a new one
        for (ActionListener listener : tc.getElegirButton().getActionListeners()) {
            tc.getElegirButton().removeActionListener(listener);
        }

        // Add a new listener for the button
        tc.getElegirButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowCategorias = tc.getTablaCategoriasExistentes().getSelectedRow();

                if (rowCategorias == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una columna", "Error", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException("Seleccione una columna");
                }

                bandera[0] = cambiarCategoria(tableCategoriasModel.getValueAt(rowCategorias, 0).toString(), productosTratar);

                tc.getOptionalPane().setEnabled(false);
                productosATratar.remove(key);
                tableModel.removeRow(row);
                tc.getElegirButton().setEnabled(false);
                tableCategoriasModel.setRowCount(0);
                tc.getTablaCategoriasExistentes().setEnabled(false);
            }
        });

        return bandera[0];
    }

    private boolean cambiarCategoria(String string,List<Producto> productosTratar) {
        Categoria categoria = categoriaService.getCategoria(string);
        for (Producto p : productosTratar) {
            p.setCategoria(categoria);
        }
        productos.addAll(productosTratar);

        return true;
    }
}
