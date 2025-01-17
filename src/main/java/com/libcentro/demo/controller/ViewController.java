package com.libcentro.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;

@Controller
public class ViewController {

    private final MenuController menuController;
    private final VentaController ventaController;
    private final ProductosController productosController;
    private final ReportesController reportesController;
    private final StockController stockController;
    private final ConfiguracionController configuracionController;
    private final BackupController backupController;

    @Autowired
    public ViewController( MenuController menuController,
                           VentaController ventaController,
                           ProductosController productosController,
                           ReportesController reportesController,
                           StockController stockController, ConfiguracionController configuracionController, BackupController backupController ) {
        this.menuController = menuController;
        this.ventaController = ventaController;
        this.productosController = productosController;
        this.reportesController = reportesController;
        this.stockController = stockController;
        this.configuracionController = configuracionController;
        this.backupController = backupController;
    }

    public void openMenuView() {
        menuController.openMenuView();
    }

    public void openStockControlView(){
        try {
            this.stockController.stockControl(false);

        }catch(Exception ignore){
            System.out.println("Stock Control: No");
        };
        this.backupController.backUpControl ();
    }


    void openProductosView() {
        productosController.openProductosFrame();
    }


    void newVenta(){
        ventaController.openVentaFrame();
    }

    void openReportesView(){ reportesController.openReportesFrame();};

    void openConfiguracionView(){
        configuracionController.openConfiguracionView ();
    }
    void openBackupView(){
        backupController.openBackupView ();
    }
}