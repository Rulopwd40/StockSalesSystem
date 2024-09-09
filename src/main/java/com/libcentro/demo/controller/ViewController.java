package com.libcentro.demo.controller;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.ProductoFStock;
import com.libcentro.demo.model.Venta;
import com.libcentro.demo.model.Venta_Producto;
import com.libcentro.demo.utils.PrecioFilter;
import com.libcentro.demo.view.ApfsDialog;
import com.libcentro.demo.view.MenuFrame;
import com.libcentro.demo.view.VentaFrame;
import com.libcentro.demo.utils.DoubleFilter;
import com.libcentro.demo.utils.IntegerFilter;
import com.libcentro.demo.utils.SymbolFilter;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.Optional;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class ViewController {

    MenuFrame menuFrame;
    VentaFrame ventaFrame;
    ApfsDialog apfsDialog;

    MenuController menuController;
    VentaController ventaController;

    public ViewController() {
        menuController = new MenuController(this);
        ventaController = new VentaController(this);

        menuController.openMenuView();

    }


    void openProductosView() {
        System.out.println("open productos view");
    }


    //Crear Venta
    void newVenta(){

        ventaController.openVentaView();

    }

    //Abre la ventana view, asigna restricciones y listeners


    void openEstadisticasView() {
        System.out.println("open estadisticas view");
    }











}