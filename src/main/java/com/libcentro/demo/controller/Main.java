package com.libcentro.demo.controller;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
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
            return;
        }

        ApplicationContext contexto = SpringApplication.run(Main.class, args);

        ViewController viewController = contexto.getBean(ViewController.class);

        viewController.openMenuView();
        viewController.openStockControlView();


    }
}
