package com.libcentro.demo.view;


import java.awt.*;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MenuFrame  {
    
    private JFrame viewmenu;
    JButton productosButton;
    JButton preciosButton;
    JButton estadisticasButton;
    public MenuFrame() {
        initialize();
    }

    private void initialize() {
        viewmenu = new JFrame();
        viewmenu.setTitle("Menú");
        viewmenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewmenu.setSize(700, 525);
        viewmenu.setLocationRelativeTo(null);
        viewmenu.setResizable(false);
    
        // Crear panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
    
        // Crear un panel para el título con BorderLayout
        JPanel titlePanel = new JPanel(new BorderLayout());
    
        // Añadir un margen vacío en la parte superior del título para empujarlo hacia abajo
        titlePanel.add(Box.createRigidArea(new Dimension(0, 80)), BorderLayout.NORTH);
    
        // Crear etiqueta para el título y agregarla al centro del panel de título
        JLabel titleLabel = new JLabel("Menú", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
    
        // Añadir el panel de título al norte del panel principal
        mainPanel.add(titlePanel, BorderLayout.NORTH);
    
        // Crear panel para los botones con GridBagLayout
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre botones
    
        // Crear y configurar los botones
        productosButton = new JButton("Productos");
        productosButton.setPreferredSize(new Dimension(200, 200));
        preciosButton = new JButton("Precios");
        preciosButton.setPreferredSize(new Dimension(200, 200));
        estadisticasButton = new JButton("Estadísticas");
        estadisticasButton.setPreferredSize(new Dimension(200, 200));
    
        // Agregar los botones al panel con GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(productosButton, gbc);
    
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(preciosButton, gbc);
    
        gbc.gridx = 2;
        gbc.gridy = 0;
        buttonPanel.add(estadisticasButton, gbc);
    
        // Agregar el panel de botones al centro del panel principal
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
    
        // Agregar el panel principal al JFrame
        viewmenu.add(mainPanel);
    
        viewmenu.setVisible(true);
    }

    public JButton getProductosButton(){
        return productosButton;
    }
    public JButton getPreciosButton(){
        return preciosButton;
    }
    public JButton getEstadisticasButton(){
        return estadisticasButton;
    }

    public void setVisible(boolean visible) {
        viewmenu.setVisible(visible);
    }
}
