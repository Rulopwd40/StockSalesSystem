package com.libcentro.demo.utils;

import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.dto.CategoriaDTO;
import com.libcentro.demo.model.dto.ProductoDTO;
import com.libcentro.demo.services.CategoriaService;
import com.libcentro.demo.view.productos.TratarCategorias;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class ProductosCSV {
    List<ProductoDTO> productos;
    Map<String,List<ProductoDTO>> productosATratar = new HashMap<>();
    CategoriaService categoriaService;
    TratarCategorias tc;
    JTable table;
    DefaultTableModel tableModel;
    List<CategoriaDTO> categoriasCreadas=new ArrayList<>();


    public List<ProductoDTO> obtenerProductos( String file, CategoriaService categoriaService) throws IOException {
        this.categoriaService = categoriaService;
        productos = new ArrayList<>();
        File csv = new File(file);
        String linea;
        String separador = ";";

        Charset charset = detectCharset(csv);


        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csv), charset))) {
            br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(separador);

                CategoriaDTO categoria= categoriaService.getCategoria(datos[2]);
                if(categoria==null) {
                    productosATratar.putIfAbsent(datos[2],new ArrayList<ProductoDTO>());
                }
                ProductoDTO producto = getProductoDTO(datos, categoria);
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

    private static ProductoDTO getProductoDTO ( String[] datos, CategoriaDTO categoria ){
        ProductoDTO producto = new ProductoDTO();

        if ( datos.length == 6) {
            producto.setCodigobarras(datos[0]);
            producto.setNombre(datos[1]);
            producto.setCategoria(categoria);
            producto.setStock(Integer.parseInt(datos[3]));
            producto.setCosto_compra(Float.parseFloat(datos[4]));
            producto.setPrecio_venta(Float.parseFloat(datos[5]));
        }else{
            throw new RuntimeException("Formato incorrecto de archivo, revise su contenido");
        }
        return producto;
    }

    private boolean anularProductos(int row){
            String key= tableModel.getValueAt(row,0).toString();
            List<ProductoDTO> productosTratar = productosATratar.get(key);
            for(ProductoDTO producto : productosTratar) {
                producto.setCategoria(producto.getCategoria());
            }
            productos.addAll(productosTratar);
            tableModel.removeRow(row);
            productosATratar.remove(key);
            return true;
    }
    private boolean crearCategorias(int row){
            String key= tableModel.getValueAt(row,0).toString();
            CategoriaDTO categoria = new CategoriaDTO();
            categoria.setNombre (key);
            categoriaService.saveCategoria(categoria);
            categoriasCreadas.add(categoria);

            List<ProductoDTO> productosTratar = productosATratar.get(key);

            for(ProductoDTO producto : productosTratar) {
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
        List<ProductoDTO> productosTratar = productosATratar.get(key);
        tc.getOptionalPane().setEnabled(true);
        tc.getTablaCategoriasExistentes().setEnabled(true);
        DefaultTableModel tableCategoriasModel = (DefaultTableModel) tc.getTablaCategoriasExistentes().getModel();
        tableCategoriasModel.setRowCount(0);
        tc.getElegirButton().setEnabled(true);

        List<CategoriaDTO> categorias = categoriaService.getAll();

        for (CategoriaDTO categoria : categorias) {
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

    private boolean cambiarCategoria(String string,List<ProductoDTO> productosTratar) {
        CategoriaDTO categoria = categoriaService.getCategoria(string);
        for (ProductoDTO p : productosTratar) {
            p.setCategoria(categoria);
        }
        productos.addAll(productosTratar);

        return true;
    }

    public static Charset detectCharset(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bom = new byte[3];
            fis.read(bom);

            // Detecta UTF-8 con BOM
            if ((bom[0] == (byte) 0xEF) && (bom[1] == (byte) 0xBB) && (bom[2] == (byte) 0xBF)) {
                return StandardCharsets.UTF_8;
            }
            // Detecta UTF-16 Big Endian (BE) con BOM
            else if ((bom[0] == (byte) 0xFE) && (bom[1] == (byte) 0xFF)) {
                return StandardCharsets.UTF_16BE;
            }
            // Detecta UTF-16 Little Endian (LE) con BOM
            else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE)) {
                return StandardCharsets.UTF_16LE;
            } else {
                // Si no tiene BOM, se puede asumir que es ISO-8859-1 o Windows-1252
                return Charset.forName("ISO-8859-1"); // O la codificación que creas más adecuada
            }
        }
    }
}
