package com.libcentro.demo.controller;

import com.libcentro.demo.view.MenuFrame;

public class main {
    public static void main(String[] args) {
        /*//el contexto 👀
        ApplicationContext contexto = SpringApplication.run(main.class, args);

        //Viewgen.main(args);
        productoController productoController = contexto.getBean(productoController.class);
        System.out.println("Hello World!");
        producto producto1 = new producto(1,"Coca-Cola","Bebida energetica",0.5f,1.5f,10,"123456789");
        productoController.saveProducto(producto1);
        productoController.getAll().size();*/

        ViewController viewController = new ViewController();

    }
}
