package com.libcentro.demo.controller;


import com.libcentro.demo.config.AppConfig;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.dto.ProductoDTO;
import com.libcentro.demo.services.interfaces.IproductoService;
import com.libcentro.demo.view.productos.StockControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@Component
public class StockController {

    IproductoService productoService;


    List<ProductoDTO> productos;

    @Autowired
    public StockController(IproductoService productoService) {
        this.productoService = productoService;
    }

    public void stockControl(boolean modal) {
        productos= productoService.getProductosByCantidad(AppConfig.stock_alert);

        if(!productos.isEmpty()) {
            StockControl stockControl = new StockControl();

            DefaultTableModel tableModel = new DefaultTableModel();

            tableModel.addColumn("Codigo");
            tableModel.addColumn("Nombre");
            tableModel.addColumn("Cantidad");

            for(ProductoDTO producto: productos) {
                tableModel.addRow(new Object[]{
                        producto.getCodigobarras (),
                        producto.getNombre(),
                        producto.getStock()
                });
            }

            stockControl.getTable().setModel(tableModel);

            stockControl.getTable().setCellSelectionEnabled(false);

            stockControl.getButtonOK().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stockControl.dispose();
                }
            });

            stockControl.setModal(modal);

            stockControl.setVisible(true);
        }else throw new RuntimeException("No hay productos con stock menor a 10");

    }

}
