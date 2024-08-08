package com.libcentro.demo.view;

import javax.swing.SwingUtilities;

public class Viewgen {
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                MenuFrame view1= new MenuFrame();
            }
    });
};

}
