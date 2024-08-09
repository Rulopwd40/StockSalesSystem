package com.libcentro.demo.view;

import javax.swing.SwingUtilities;

import com.libcentro.demo.controller.viewController;

public class Viewgen {
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                MenuFrame menu= new MenuFrame();
                ProductosFrame pview= new ProductosFrame();
                viewController vController= new viewController(menu,pview);
            }
    });
};

}
