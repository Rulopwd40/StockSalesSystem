package com.libcentro.demo.controller;

import com.libcentro.demo.services.interfaces.IestadisticaService;
import com.libcentro.demo.services.interfaces.IventaService;
import com.libcentro.demo.view.estadisticas.ReportesFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Controller
public class ReportesController {


    private IestadisticaService estadisticaService;
    private IventaService ventaService;
    private enum Estado{
        VENTA,
        PRODUCTO,
        GANANCIA

    }

    private Estado estado= Estado.VENTA;


    ReportesFrame frame;

    @Autowired
    public ReportesController(IestadisticaService estadisticaService, IventaService ventaService){
        this.estadisticaService = estadisticaService;
        this.ventaService = ventaService;
    }

    public void openReportesFrame() {
        frame = new ReportesFrame();

        addFrameListeners();


        frame.setVisible(true);
    }

    private void addFrameListeners() {

        frame.getVentasButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVentas();
            }
        });
        frame.getProductosButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setProducto();
            }
        });
        frame.getGananciasButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGanancias();
            }
        });
        frame.getGenerarReembolsoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReembolso();
            }
        });
        frame.getGenerarGraficaButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                generarGrafica();
            }
        });
        
    }

    private void generarGrafica() {
            estadisticaService.generarGrafica(frame.getCodigoField().getText(),
                    estado.toString().toLowerCase(),
                    (String) frame.getPeriodoBox().getSelectedItem());
    }

    private void generarReembolso() {

    }

    private void setGanancias() {
        frame.getCodigoField().setText("");
        frame.getCodigoField().setEnabled(false);
        frame.getPestanaLabel().setText("Ganancias");
        estado = Estado.GANANCIA;
    }

    private void setProducto() {
        frame.getCodigoField().setText("");
        frame.getCodigoField().setEnabled(true);
        frame.getPestanaLabel().setText("Producto");
        estado = Estado.PRODUCTO;
    }

    private void setVentas(){
        frame.getCodigoField().setText("");
        frame.getCodigoField().setEnabled(false);
        frame.getPestanaLabel().setText("Ventas");
        estado = Estado.VENTA;
    }




    
}
