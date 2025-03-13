package com.libcentro.demo.controller;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.ProductoFStock;
import com.libcentro.demo.model.dto.*;
import com.libcentro.demo.services.HistorialService;
import com.libcentro.demo.services.ProductoService;
import com.libcentro.demo.services.VentaService;
import com.libcentro.demo.services.interfaces.IestadisticaService;
import com.libcentro.demo.utils.format.DoubleFormat;
import com.libcentro.demo.view.estadisticas.HistorialFrame;
import com.libcentro.demo.view.estadisticas.InformacionFrame;
import com.libcentro.demo.view.estadisticas.ReportesFrame;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Controller
public class ReportesController {


    private final VentaService ventaService;
    private final ProductoService productoService;
    private final HistorialService historialService;
    private IestadisticaService estadisticaService;
    private int page;

    private enum Estado{
        VENTA,
        PRODUCTO,
    }

    private Estado estado= Estado.VENTA;

    ReportesFrame reportesFrame;
    InformacionFrame informacionFrame;
    HistorialFrame historialFrame;

    PageDTO<VentaDTO> ventas;
    VentaDTO ventaSeleccionada;

    @Autowired
    public ReportesController( IestadisticaService estadisticaService, VentaService ventaService, ProductoService productoService, HistorialService historialService ){
        this.estadisticaService = estadisticaService;
        this.ventaService = ventaService;
        this.productoService = productoService;
        this.historialService = historialService;
    }

    public void openReportesFrame() {
        if ( reportesFrame == null) {
        reportesFrame = new ReportesFrame();
        addFrameListeners();
        }

        reportesFrame.setVisible(true);
    }

    private void addFrameListeners() {

        reportesFrame.getVentasButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVentas();
            }
        });
        reportesFrame.getProductosButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setProducto();
            }
        });
        reportesFrame.getGananciasButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openHistorialFrame();
            }
        });
        reportesFrame.getInformacionButton ().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openInformacionFrame();
            }
        });
        reportesFrame.getGenerarGraficaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarGrafica();
            }
        });
        reportesFrame.getContabilizarButton ().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed ( ActionEvent e ){
                contabilizar();
            }
        });
        reportesFrame.getOkButton ().addActionListener(new ActionListener() {
           @Override
           public void actionPerformed ( ActionEvent e ){
               cerrar();
           }
        });

    }

    private void cerrar (){
        reportesFrame.setVisible(false);
    }

    private void contabilizar (){
        String string;
        try {
            string = estadisticaService.contabilizar (reportesFrame.getCodigoField ().getText (),
                    estado.toString ().toLowerCase (),
                    (String) reportesFrame.getPeriodoBox ().getSelectedItem ());
        }catch (Exception e){
            JOptionPane.showMessageDialog(reportesFrame,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException (e.getMessage(),e);
        }
        DefaultTableModel tableModel = new DefaultTableModel(0,2);

        String[] splited = string.split ("\n");

        for(String s : splited){
            tableModel.addRow (new String[]{s.split("\\|")[0],s.split("\\|")[1]});
        }

        reportesFrame.getTablaCount ().setModel (tableModel);


    }

    private void generarGrafica() {
            reportesFrame.getGraphPane ().removeAll ();
            reportesFrame.getGraphPane ().revalidate();
            Image image;
            try {
                image = estadisticaService.generarGrafica (reportesFrame.getCodigoField ().getText (),
                        estado.toString ().toLowerCase (),
                        (String) reportesFrame.getPeriodoBox ().getSelectedItem ());

                if ( image == null ) throw new RuntimeException ("No se genero imagen");
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog (reportesFrame, "Error al generar la imagen: " + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                throw new RuntimeException (e);
            }
            int nuevoAncho = 900;
            int nuevoAlto = 540;
            Image imagenRedimensionada = image.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);


            JPanel picPane = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(imagenRedimensionada, 0, 0, getWidth(), getHeight(), null);
                }
            };
            picPane.setPreferredSize(new Dimension(nuevoAncho, nuevoAlto));

            reportesFrame.getGraphPane().add(picPane);

            picPane.revalidate();
            picPane.repaint();
            picPane.setVisible(true);
    }

    private void setProducto() {
        reportesFrame.getCodigoField().setText("");
        reportesFrame.getCodigoField().setEnabled(true);
        reportesFrame.getPestanaLabel().setText("Producto");
        estado = Estado.PRODUCTO;
    }

    private void setVentas(){
        reportesFrame.getCodigoField().setText("");
        reportesFrame.getCodigoField().setEnabled(false);
        reportesFrame.getPestanaLabel().setText("Ventas");
        estado = Estado.VENTA;
    }

    //Informacion
    private void openInformacionFrame() {
        this.page = 0;

        if (informacionFrame == null) {
            informacionFrame = new InformacionFrame();
            addInformacionFrameListeners();
        }

        pagina (0);

        informacionFrame.setVisible(true);
    }

    private void addInformacionFrameListeners (){
        informacionFrame.getBuscarField ().getDocument().addDocumentListener(new DocumentListener () {

            @Override
            public void insertUpdate ( DocumentEvent e ){
                updateTableVentas();
            }

            @Override
            public void removeUpdate ( DocumentEvent e ){
                updateTableVentas();
            }

            @Override
            public void changedUpdate ( DocumentEvent e ){
                updateTableVentas();
            }
        });
        informacionFrame.getAnteriorButton ().addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pagina(-1);
            }


        });
        informacionFrame.getSiguienteButton ().addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pagina(1);
            }
        });
        informacionFrame.getMostrarButton ().addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarVenta();
            }
        });
        informacionFrame.getVentaTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = informacionFrame.getVentaTable().getSelectedRow();
                if (row != -1) {
                    cambiarBoton(row);
                }
            }
        });

        informacionFrame.getEliminarButton ().addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = informacionFrame.getVentaTable().getSelectedRow();
                if (row != -1) {
                    cambiarBoton(row);
                }
                cambiarEstadoVenta (row);
            }
        });

        informacionFrame.getReembolsarButton ().addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reembolsarSeleccion();
            }
        });
        informacionFrame.getReembolsarTodoButton ().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e ){
                reembolsarVenta();
            }
        });
        informacionFrame.getPfsTable ().addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked ( MouseEvent e ){
                informacionFrame.getProductosTable ().clearSelection ();
            }
        });
        informacionFrame.getProductosTable ().addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked ( MouseEvent e ){
                informacionFrame.getPfsTable ().clearSelection ();
            }
        });
    }

    private void reembolsarVenta (){
        try {
            if(ventaSeleccionada == null) throw new RuntimeException("Seleccione una venta");
            ventaService.reembolsarVenta (ventaSeleccionada);
        }catch (RuntimeException e){
            JOptionPane.showMessageDialog (informacionFrame,"Error al reembolsar la venta: "+ e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog (informacionFrame,"Venta reembolsada correctamente");
        mostrarVenta ();
    }
    private void reembolsarSeleccion (){
        JTable table;
        int row;
        int modo;
        if(informacionFrame.getProductosTable ().getSelectedRow () != -1){
            table= informacionFrame.getProductosTable ();
            row = informacionFrame.getProductosTable ().getSelectedRow ();
            modo=0;
        }else if(informacionFrame.getPfsTable ().getSelectedRow () != -1){
            table= informacionFrame.getPfsTable ();
            row = informacionFrame.getPfsTable ().getSelectedRow ();
            modo=1;
        }else{
            JOptionPane.showMessageDialog (informacionFrame, "Seleccione un producto", "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        String input = JOptionPane.showInputDialog (null,"Cantidad a Reembolsar:","Reembolsar",JOptionPane.PLAIN_MESSAGE);

        if (input != null) {
            try {
                int cantidadReembolsar = Integer.parseInt(input);

                String id = table.getModel ().getValueAt (row,0).toString ();

                if(modo == 0){
                    Venta_ProductoDTO vpd = ventaSeleccionada.getVenta_producto ().stream ().filter (vp -> { return Objects.equals (vp.getProducto ().getCodigobarras (), id);
                    }).findFirst ().orElse(null);

                    if(vpd == null) throw new EntityNotFoundException ("Producto no encontrado");

                    ventaService.reembolsarProducto(ventaSeleccionada,vpd,cantidadReembolsar);
                    JOptionPane.showMessageDialog (informacionFrame,"Producto con codigo de barras: " + vpd.getProducto ().getCodigobarras () + " reembolsado","Reembolsado",JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                ProductoFStockDTO pfsd = ventaSeleccionada.getProducto_fstock ().stream ().filter (pfs -> pfs.getId () == Integer.parseInt(id)).findFirst ().orElse (null);
                if(pfsd == null) throw new EntityNotFoundException ("Producto no encontrado");


                ventaService.reembolsarProducto (ventaSeleccionada,pfsd,cantidadReembolsar);
                JOptionPane.showMessageDialog (informacionFrame,"Producto reembolsado");

                mostrarVenta ();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(informacionFrame, "Por favor, ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (EntityNotFoundException e) {
                JOptionPane.showMessageDialog (informacionFrame,"Producto no encontrado","Error",JOptionPane.ERROR_MESSAGE);
            } catch (RuntimeException e){
                JOptionPane.showMessageDialog (informacionFrame,e.getMessage (),"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cambiarBoton (int row){
        String estado = informacionFrame.getVentaTable().getModel().getValueAt(row, 2).toString();

        if (estado.equals("Habilitada")) {
            informacionFrame.getEliminarButton().setBackground(new Color(205, 40, 30));
            informacionFrame.getEliminarButton ().setText("Deshabilitar");
        } else {
            informacionFrame.getEliminarButton().setBackground(new Color(66, 176, 255));
            informacionFrame.getEliminarButton ().setText("Habilitar");
        }


    }

    private void pagina ( int i ){
        page = page + i;
        updateTableVentas();

        informacionFrame.getSiguienteButton ().setEnabled(page + 1 != ventas.getPages ());

        informacionFrame.getAnteriorButton ().setEnabled(page != 0);

        informacionFrame.getPageCount ().setText(page + 1 + " de " + ventas.getPages () );

    }
    private void updateTableVentas (){
        ventas = ventaService.getByPage(informacionFrame.getBuscarField ().getText (), this.page, 25);

        String[] ventaNames = {"Cod.", "Total", "Estado"};
        DefaultTableModel tableModel = new DefaultTableModel(ventaNames, 0);

        if (ventas != null && ventas.getObjects() != null) {
            for (VentaDTO venta : ventas.getObjects()) {
                tableModel.addRow(new Object[]{venta.getId(), venta.getTotal(), venta.isEstado ()? "Habilitada" : "Deshabilitada" });
            }
        }
        informacionFrame.getVentaTable().setModel(tableModel);
    }

    private void mostrarVenta (){
        int row = informacionFrame.getVentaTable().getSelectedRow();
        ;
        if (row != -1) {
            String value = informacionFrame.getVentaTable().getModel().getValueAt(row, 0).toString ();
            ventaSeleccionada = ventaService.getVentaById(value);
        } else {
            JOptionPane.showMessageDialog (null,"Seleccione una venta para mostrar.");
            return;
        }

        String[] productoNames = {"Cod.", "Nombre", "Precio", "Cantidad","Descuento","Costo de Compra"};
        String[] pfsNames = {"Id","Nombre", "Precio", "Cantidad","Descuento"};
        DefaultTableModel productosModel = new DefaultTableModel(productoNames, 0);
        DefaultTableModel pfsModel = new DefaultTableModel(pfsNames, 0);

        ventaSeleccionada.getVenta_producto ().forEach (vp -> {
            productosModel.addRow (new Object[]{vp.getProducto ().getCodigobarras (),
                    vp.getProducto ().getNombre (),
                    vp.getPrecio_venta (),
                    vp.getCantidad (),
                    vp.getDescuento (),
                    vp.getCosto_compra ()
            });
        });
        ventaSeleccionada.getProducto_fstock ().forEach (pfs -> {
            pfsModel.addRow (new Object[]{
                    pfs.getId (),
                    pfs.getNombre (),
                    pfs.getPrecio_venta (),
                    pfs.getCantidad (),
                    pfs.getDescuento ()
            });
        });

        informacionFrame.getProductosTable ().setModel(productosModel);
        informacionFrame.getPfsTable ().setModel(pfsModel);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormateada = ventaSeleccionada.getFecha().format(formatter);

        informacionFrame.getHora().setText(horaFormateada);

    }
    private void cambiarEstadoVenta(int row){
        String codbar= informacionFrame.getVentaTable ().getModel ().getValueAt (row,0).toString ();

        ventaService.cambiarEstadoVenta (codbar);
        updateTableVentas();
    }


    //Historial
    private void openHistorialFrame (){
        if(historialFrame == null){
            historialFrame = new HistorialFrame ();
            addHistorialFrameListeners();
        }

        historialFrame.setVisible(true);
    }

    private void addHistorialFrameListeners (){
        historialFrame.getBuscarButton ().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e ){
                buscarProducto();
            }
        });
        historialFrame.getOkButton ().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e ){
                historialFrame.setVisible(false);
            }
        });
    }

    private void buscarProducto (){
        DoubleFormat dformatter = new DoubleFormat();
        String codbar = historialFrame.getBuscarField ().getText ();
        if( codbar.isEmpty () ) {
            JOptionPane.showMessageDialog (historialFrame,"Complete el campo","Error",JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException ( "Complete el campo" );
        }
        List<HistorialCostoDTO> historialCostoDTOS = historialService.findAllCostoByProducto (codbar);
        List<HistorialPrecioDTO> historialPrecioDTOS = historialService.findAllPrecioByProducto (codbar);

        String[] costoNames={"Fecha", "Costo", "Cantidad","Cantidad vendida", "Estado"};
        DefaultTableModel costoModel = new DefaultTableModel (costoNames,0);
        String[] precioNames={"Fecha","Precio"};
        DefaultTableModel precioModel = new DefaultTableModel (precioNames,0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        historialCostoDTOS.forEach(hc -> {
            costoModel.addRow(new Object[]{
                    hc.getFecha().format(formatter),
                    dformatter.format(hc.getCosto_compra()),
                    hc.getCantidad(),
                    hc.getCantidad_vendida(),
                    hc.getEstado().equals(HistorialCosto.Estado.INICIAL)? "ACTUAL" : hc.getEstado ()
            });
        });

        historialPrecioDTOS.forEach(hp -> {
            precioModel.addRow(new Object[]{
                    hp.getFecha().format(formatter),
                    dformatter.format(hp.getPrecioVenta())
            });
        });

        historialFrame.getCostosTable ().setModel(costoModel);
        historialFrame.getPreciosTable ().setModel(precioModel);

    }

}
