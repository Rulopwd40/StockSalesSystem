package com.libcentro.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ViewController {

    final MenuController menuController;
    final VentaController ventaController;
    final ProductosController productosController;
    final ReportesController reportesController;
    final StockController stockController;
    final ConfiguracionController configuracionController;


    @Autowired
    public ViewController( MenuController menuController,
                           VentaController ventaController,
                           ProductosController productosController,
                           ReportesController reportesController,
                           StockController stockController, ConfiguracionController configuracionController ) {
        this.menuController = menuController;
        this.ventaController = ventaController;
        this.productosController = productosController;
        this.reportesController = reportesController;
        this.stockController = stockController;
        this.configuracionController = configuracionController;
    }

    void openMenuView() {
        menuController.openMenuView();
    }

    void openStockControlView(){
        try {
            this.stockController.stockControl(false);
        }catch(Exception ignore){
            System.out.println("Stock Control: No");
        };
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
}