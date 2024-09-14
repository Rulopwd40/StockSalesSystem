package com.libcentro.demo.controller;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.awt.*;

@SpringBootApplication
@ComponentScan(basePackages = {"com.libcentro.demo"})
@EntityScan(basePackages = {"com.libcentro.demo.model"})
@EnableJpaRepositories(basePackages = "com.libcentro.demo.repository")
public class Main {
    public static void main(String[] args) {


        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("Entorno es headless, no se puede abrir una ventana gráfica.");
            return; // Salimos si no hay entorno gráfico
        }


        ApplicationContext contexto = SpringApplication.run(Main.class, args);

        /*Viewgen.main(args);
        productoController productoController = contexto.getBean(productoController.class);
        System.out.println("Hello World!");
        producto producto1 = new producto(1,"Coca-Cola","Bebida energetica",0.5f,1.5f,10,"123456789");
        productoController.saveProducto(producto1);
        productoController.getAll().size();*/

        ViewController viewController = contexto.getBean(ViewController.class);

        viewController.openMenuView();

    }
}
