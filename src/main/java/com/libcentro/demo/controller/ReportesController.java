package com.libcentro.demo.controller;

import com.libcentro.demo.services.interfaces.IestadisticaService;
import com.libcentro.demo.services.interfaces.IventaService;
import com.libcentro.demo.view.estadisticas.ReportesFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

@Controller
public class ReportesController {


    private IestadisticaService estadisticaService;

    private enum Estado{
        VENTA,
        PRODUCTO,
        GANANCIA

    }

    private Estado estado= Estado.VENTA;

    ReportesFrame frame;

    @Autowired
    public ReportesController(IestadisticaService estadisticaService){
        this.estadisticaService = estadisticaService;
    }

    public void openReportesFrame() {
        if (frame == null) {
        frame = new ReportesFrame();
        addFrameListeners();
        }

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
        frame.getContabilizarButton ().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed ( ActionEvent e ){
                contabilizar();
            }
        });
        frame.getOkButton ().addActionListener(new ActionListener() {
           @Override
           public void actionPerformed ( ActionEvent e ){
               cerrar();
           }
        });

    }

    private void cerrar (){
        frame.setVisible(false);
    }

    private void contabilizar (){
        String string;
        try {
            string = estadisticaService.contabilizar (frame.getCodigoField ().getText (),
                    estado.toString ().toLowerCase (),
                    (String) frame.getPeriodoBox ().getSelectedItem ());
        }catch (Exception e){
            JOptionPane.showMessageDialog(frame,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException (e.getMessage(),e);
        }
        DefaultTableModel tableModel = new DefaultTableModel(0,2);

        String[] splited = string.split ("\n");

        for(String s : splited){
            tableModel.addRow (new String[]{s.split("\\|")[0],s.split("\\|")[1]});
        }

        frame.getTablaCount ().setModel (tableModel);


    }

    private void generarGrafica() {
            frame.getGraphPane ().removeAll ();
            frame.getGraphPane ().revalidate();
            Image image;
            try {
                image = estadisticaService.generarGrafica (frame.getCodigoField ().getText (),
                        estado.toString ().toLowerCase (),
                        (String) frame.getPeriodoBox ().getSelectedItem ());

                if ( image == null ) throw new RuntimeException ("No se genero imagen");
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog (frame, "Error al generar la imagen: " + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
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
