package com.libcentro.demo;


import com.libcentro.demo.controller.ViewController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.awt.*;

@SpringBootApplication
@EntityScan(basePackages = {"com.libcentro.demo.model"})
@EnableJpaRepositories(basePackages = "com.libcentro.demo.repository")
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("Starting application");

        try {

            if ( GraphicsEnvironment.isHeadless () ) {
                System.out.println ("Entorno es headless, no se puede abrir una ventana gráfica.");
                return;
            }

            ApplicationContext contexto = SpringApplication.run (Main.class, args);


            ViewController viewController = contexto.getBean (ViewController.class);

            viewController.openMenuView ();
            viewController.openStockControlView ();
            logger.info ("Application started");
        }catch (Exception e) {
            logger.error (e.toString(), e);
        };

        logger.info ("Application ended");
    }
}
