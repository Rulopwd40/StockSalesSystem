package com.libcentro.demo.controller;

import com.libcentro.demo.services.interfaces.IestadisticaService;
import com.libcentro.demo.services.interfaces.IventaService;
import com.libcentro.demo.view.estadisticas.ReportesFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.*;
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
            Image image = estadisticaService.generarGrafica(frame.getCodigoField().getText(),
                    estado.toString().toLowerCase(),
                    (String) frame.getPeriodoBox().getSelectedItem());

            if(image==null) throw new RuntimeException ("No se genero imagen");
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

            frame.getGraphPane().add(picPane);

            picPane.revalidate();
            picPane.repaint();
            picPane.setVisible(true);
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
