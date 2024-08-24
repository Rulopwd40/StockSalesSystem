package com.libcentro.demo.controller;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.ProductoFStock;
import com.libcentro.demo.model.Venta;
import com.libcentro.demo.utils.PrecioFilter;
import com.libcentro.demo.view.ApfsDialog;
import com.libcentro.demo.view.MenuFrame;
import com.libcentro.demo.view.VentaFrame;
import com.libcentro.demo.utils.DoubleFilter;
import com.libcentro.demo.utils.IntegerFilter;
import com.libcentro.demo.utils.SymbolFilter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.*;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class ViewController {

    MenuFrame menuFrame;
    VentaFrame ventaFrame;
    ApfsDialog apfsDialog;
    Venta venta;

    public ViewController() {
        openMenuView();

    }

    //Abre el menú, asigna restricciones y listeners
    private void openMenuView(){
        menuFrame = new MenuFrame();
        menuFrame.setVisible(true);

        // Menu Listeners
        menuFrame.getProductosButton().addActionListener(e -> openProductosView());
        menuFrame.getVentaButton().addActionListener(e -> newVenta());
        menuFrame.getReportesButton().addActionListener(e -> openEstadisticasView());

        // KeyBindings for MenuFrame
        menuFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "openVenta");

        menuFrame.getRootPane().getActionMap().put("openVenta", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newVenta();
            }
        });
    }

    private void openProductosView() {
        System.out.println("open productos view");
    }


    //Crear Venta
    private void newVenta(){

        openVentaView();
        venta = new Venta();


    }

    //Abre la ventana view, asigna restricciones y listeners
    private void openVentaView() {
        if(ventaFrame == null) {
            ventaFrame = new VentaFrame();
            ventaFrame.setVisible(true);

            // Venta Listeners
           setVentaListeners();


            //Restricciones de inputs
            setIntegerFilter(ventaFrame.getCodBar());
            setIntegerFilter(ventaFrame.getCant());

            // KeyBindings for VentaFrame
            setVentaKeyBindings();

        }
        ventaFrame.setState(Frame.NORMAL); // Restaurar si está minimizado
        ventaFrame.toFront();
        ventaFrame.requestFocus();

    }

    private void openEstadisticasView() {
        System.out.println("open estadisticas view");
    }


    //Abre el dialogo para agregar un producto fuera de stock
    private void openApfsDialog() {
        apfsDialog = new ApfsDialog();

        setSymbolFilter(apfsDialog.getNombreField());
        setIntegerFilter(apfsDialog.getCantField());
        setPrecioFilter(apfsDialog.getPrecioField());


        //Cargar datos
        apfsDialog.getButtonOK().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(apfsDialog.todosLosCamposLlenos()) {
                    String nombre = apfsDialog.getNombreField().getText();
                    String cantidad = apfsDialog.getCantField().getText();
                    String precio = apfsDialog.getPrecioField().getText();

                    DefaultTableModel model = (DefaultTableModel) ventaFrame.getTable().getModel();
                    ProductoFStock producto;
                    model.addRow(new Object[]{nombre, cantidad, "0",precio});

                    apfsDialog.onOK();
                }
            }
        });

        apfsDialog.getButtonCancel().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apfsDialog.onCancel();
            }
        });

        // call onCancel() when cross is clicked
        apfsDialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        apfsDialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                apfsDialog.onCancel();
            }
        });

        // call onCancel() on ESCAPE
        apfsDialog.getApfsPane().registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                apfsDialog.onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        apfsDialog.setVisible(true);
    }

    private void closeVentaFrame(){
        ventaFrame.setVisible(false);
        ventaFrame.dispose();
        ventaFrame = null;

    }


    //Venta
    private void setVentaKeyBindings(){
        ventaFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "focusCodBar");

        ventaFrame.getRootPane().getActionMap().put("focusCodBar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ventaFrame.getCodBar().requestFocusInWindow();
            }
        });

        ventaFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "closeVenta");

        ventaFrame.getRootPane().getActionMap().put("closeVenta", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeVentaFrame();
            }
        });


        //Open ApfsDialog
        ventaFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "openApfsDialog");

        ventaFrame.getRootPane().getActionMap().put("openApfsDialog", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openApfsDialog();
            }
        });

        //CodBar Focus
        ventaFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "setCodFocus");
        ventaFrame.getRootPane().getActionMap().put("setCodFocus", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventaFrame.setCodFocus();
            }
        });
    }

    private void setVentaListeners(){
        ventaFrame.getAgregarEnterButton().addActionListener(e -> {
            ventaFrame.setCodFocus();
            ventaFrame.getCodBar().getText();

        });
        ventaFrame.getAgregarProductoFueraDeButton().addActionListener(e -> openApfsDialog());
        ventaFrame.getCancelarEscButton().addActionListener(e -> closeVentaFrame());
        ventaFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeVentaFrame();
            }
        });
    }



    //Filters
    private void setIntegerFilter(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new IntegerFilter());
    }

    private void setDoubleFilter(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DoubleFilter());
    }

    private void setSymbolFilter(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new SymbolFilter());
    }
    private void setPrecioFilter(JTextField textField){
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new PrecioFilter());
    }
}