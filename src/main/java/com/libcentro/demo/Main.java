package com.libcentro.demo;


import com.libcentro.demo.controller.ViewController;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.awt.*;

@SpringBootApplication
@EntityScan(basePackages = {"com.libcentro.demo.model"})
@EnableJpaRepositories(basePackages = "com.libcentro.demo.repository")
public class Main {

    public final static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        log.info ("Starting app");
        try {

            if ( GraphicsEnvironment.isHeadless () ) {
                log.error("El entorno es headless, no se puede abrir una ventana gráfica.");
                return;
            }

            ConfigurableApplicationContext contexto = new SpringApplicationBuilder (Main.class).headless (false).run(args);
            //ApplicationContext contexto = SpringApplication.run (Main.class, args);

            ViewController viewController = contexto.getBean (ViewController.class);

            viewController.openMenuView ();
            viewController.openStockControlView ();

        }catch (Exception e) {
            log.error("Error al iniciar la aplicación: ", e);
            log.info("Stopping app");
        };


    }
}
