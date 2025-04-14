package com.libcentro.demo.utils;

import com.libcentro.demo.model.dto.CategoriaDTO;
import com.libcentro.demo.model.dto.ProductoDTO;
import com.libcentro.demo.services.interfaces.IcategoriaService;
import com.libcentro.demo.view.productos.TratarCategorias;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;


public class ProductosCSV {
    List<ProductoDTO> productos;
    Map<String,List<ProductoDTO>> productosATratar = new HashMap<>();
    IcategoriaService categoriaService;
    TratarCategorias tc;
    JTable table;
    DefaultTableModel tableModel;
    List<CategoriaDTO> categoriasCreadas=new ArrayList<>();


    public List<ProductoDTO> obtenerProductos( String file, IcategoriaService categoriaService) throws IOException {
        this.categoriaService = categoriaService;
        productos = new ArrayList<>();
        File csv = new File(file);
        String linea;
        String separador = ";";

        Charset charset = detectCharset(csv);
        StringBuilder errors = new StringBuilder();


        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csv), charset))) {
            br.readLine();

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split (separador);
                try {
                    validarLinea (datos);
                    CategoriaDTO categoria;
                    if ( !datos[2].isEmpty () ) categoria = categoriaService.getCategoriaDTO (datos[2]);
                    else categoria = null;
                    if ( categoria == null ) {
                        productosATratar.putIfAbsent (datos[2], new ArrayList<ProductoDTO> ());
                    }
                    ProductoDTO producto = getProductoDTO (datos, categoria);
                    if ( categoria == null ) {
                        productosATratar.get (datos[2]).add (producto);
                    } else {
                        productos.add (producto);
                    }
                }catch (IllegalArgumentException ex) {
                    errors.append (datos[0]).append (";").append (ex.getMessage ()).append ("\n");
                }
            }
            if(!productosATratar.isEmpty()) {
                tc= tratarCategorias();

                table = tc.getTablaCategorias();
                tableModel = (DefaultTableModel) tc.getTablaCategorias().getModel();
                for(String categoria : productosATratar.keySet()) {
                    tableModel.addRow(new Object[]{categoria});
                }
                tc.setVisible(true);
                SwingUtilities.invokeLater (new Runnable () {
                    public void run() {

                    }
                });

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(!errors.isEmpty ()) tablaErrores(errors);

        List<String> codigos = eliminarDuplicadosPorCodigo (productos);

        if(!codigos.isEmpty()) tablaDuplicados(codigos);
        return productos;
    }

    private void tablaDuplicados(List<String> codigosDuplicados) {
        // Crear el modelo de la tabla
        String[] columnas = {"Código de Barras"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        // Agregar las filas con los códigos duplicados
        for (String codigo : codigosDuplicados) {
            model.addRow(new Object[]{codigo});
        }

        // Crear la tabla
        JTable tabla = new JTable(model);

        // Colocar la tabla dentro de un JScrollPane (para agregar scroll si es necesario)
        JScrollPane scrollPane = new JScrollPane(tabla);

        // Crear el label con el mensaje
        JLabel label = new JLabel("Estos productos no fueron tratados al estar duplicados", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14)); // Puedes cambiar la fuente si lo deseas
        label.setForeground(Color.RED); // Color rojo para destacar el mensaje

        // Crear una ventana (JFrame)
        JFrame frame = new JFrame("Códigos Duplicados");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 300);  // Tamaño de la ventana
        frame.setLocationRelativeTo(null);  // Centrar la ventana

        // Establecer un layout para organizar el label y la tabla
        frame.setLayout(new BorderLayout());

        // Agregar el label en la parte superior
        frame.add(label, BorderLayout.NORTH);
        // Agregar el JScrollPane con la tabla en la parte central
        frame.add(scrollPane, BorderLayout.CENTER);
        label.requestFocusInWindow();
        // Hacer visible la ventana
        frame.setVisible(true);

        frame.toFront();
        frame.requestFocus();
    }

    public List<String> eliminarDuplicadosPorCodigo(List<ProductoDTO> productos) {
        Set<String> codigosVistos = new HashSet<>();
        List<String> codigosDuplicados = new ArrayList<>();

        Iterator<ProductoDTO> iter = productos.iterator();
        while (iter.hasNext()) {
            ProductoDTO producto = iter.next();
            String codigo = producto.getCodigobarras();

            if (!codigosVistos.add(codigo)) { // Si el código ya fue visto, es un duplicado
                codigosDuplicados.add(codigo); // Agregar código duplicado
                iter.remove(); // Eliminar el producto duplicado de la lista
            }
        }

        return codigosDuplicados; // Retornar la lista de códigos duplicados
    }

    private void tablaErrores(StringBuilder errors) {
        String[] lineasDeError = errors.toString().split("\n");

        String[] columnas = {"#","Mensaje de error"};

        Object[][] datos = new Object[lineasDeError.length][2];
        for (int i = 0; i < lineasDeError.length; i++) {
            String[] error = lineasDeError[i].split (";");
            datos[i][0] = error[0];
            datos[i][1] = error[1];
        }

        JTable tabla = new JTable(datos, columnas);
        JScrollPane scrollPane = new JScrollPane(tabla);

        JFrame frame = new JFrame("Errores de Validación");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null); // Centrar ventana
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private void validarLinea(String[] datos) {
        if (datos.length != 6) {
            throw new IllegalArgumentException("La línea debe tener exactamente 6 campos.");
        }

        if (!datos[0].matches("\\d{5,20}")) {
            throw new IllegalArgumentException("Código de barras inválido. Debe contener entre 5 y 20 dígitos numéricos.");
        }

        for (int i = 0; i < datos.length; i++) {
            if (i != 2 && (datos[i].isEmpty() || datos[i].length() >= 64)) {
                throw new IllegalArgumentException("El campo en la posición " + i + " está vacío o supera el límite de 64 caracteres.");
            }
        }

        try {
            int cantidad = Integer.parseInt(datos[3]);
            double costo = Double.parseDouble(datos[4].replace(",", "."));
            double precio = Double.parseDouble(datos[5].replace(",", "."));

            if (cantidad < 0) {
                throw new IllegalArgumentException("La cantidad no puede ser negativa.");
            }
            if (costo < 0) {
                throw new IllegalArgumentException("El costo de compra no puede ser negativo.");
            }
            if (precio < 0) {
                throw new IllegalArgumentException("El precio unitario no puede ser negativo.");
            }
            if (precio < costo) {
                throw new IllegalArgumentException("El precio unitario no puede ser menor al costo de compra.");
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error al convertir cantidad, costo o precio: deben ser valores numéricos válidos.");
        }
    }

    private TratarCategorias tratarCategorias (){
        tc = new TratarCategorias();


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
                   tc.dispose();
                   tc = null;
               }
           }
        );
        tc.addWindowListener(new WindowAdapter() {
            public void onClose(WindowEvent e) {
                if(!productosATratar.isEmpty()){
                    JOptionPane.showMessageDialog(null,"Faltan categorías por tratar","Error",JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException("Faltan tratar categorías");
                }
                tc.dispose();
                tc = null;
            }

            @Override
            public void windowStateChanged(WindowEvent e) {
                tc.getTablaCategoriasExistentes().setEnabled(false);
                tc.getElegirButton().setEnabled(false);
            }
        });
        return tc;
    }

    private static ProductoDTO getProductoDTO ( String[] datos, CategoriaDTO categoria ){
        ProductoDTO producto = new ProductoDTO();

        if ( datos.length == 6) {
            producto.setCodigobarras(datos[0]);
            producto.setNombre(datos[1]);
            producto.setCategoria(categoria);
            producto.setStock(Integer.parseInt(datos[3]));
            producto.setCosto_compra(Math.round(Double.parseDouble (datos[4].replace(',', '.')) * 100d) / 100d);
            producto.setPrecio_venta(Math.round(Double.parseDouble (datos[5].replace(',', '.')) * 100d) / 100d) ;
        }else{
            throw new RuntimeException("Formato incorrecto de archivo, revise su contenido. Forma esperada: \n" +
                    "codigo_barras | nombre | categoria | cantidad | costo_compra | precio_unitario");
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
        CategoriaDTO categoria = categoriaService.getCategoriaDTO (string);
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
