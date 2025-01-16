package com.libcentro.demo.view.estadisticas;

import javax.swing.*;

public class ReportesFrame extends JFrame{

    private JPanel reportesPanel;
    private JButton ventasButton;
    private JButton productosButton;
    private JButton historialButton;
    private JButton informacionButton;
    private JButton okButton;
    private JPanel contenidoPane;
    private JLabel pestanaLabel;
    private JButton generarGraficaButton;
    private JButton contabilizarButton;
    private JComboBox periodoBox;
    private JPanel graphPane;
    private JTextField codigoField;
    private JTable tablaCount;



    public ReportesFrame(){
        setContentPane(reportesPanel);
        setSize(1360,768);
        setResizable (false);
        setLocationRelativeTo(null);
        setFocusable(true);
    }

    public JPanel getGraphPane() {
        return graphPane;
    }

    public JButton getProductosButton() {
        return productosButton;
    }

    public JButton getGananciasButton() {
        return historialButton;
    }

    public JButton getInformacionButton() {
        return informacionButton;
    }

    public JButton getVentasButton() {
        return ventasButton;
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JButton getContabilizarButton() {
        return contabilizarButton;
    }

    public JButton getGenerarGraficaButton() {
        return generarGraficaButton;
    }

    public JLabel getPestanaLabel() {
        return pestanaLabel;
    }

    public JComboBox getPeriodoBox() {
        return periodoBox;
    }

    public JTextField getCodigoField() {
        return codigoField;
    }

    public void setGraphPane ( JPanel graphPane ){
        this.graphPane = graphPane;
    }
    public JTable getTablaCount (){
        return tablaCount;
    }


}
