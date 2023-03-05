package com.hactually;

import com.hactually.controller.FlooringController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        // create service layer, create view
        // pass these to the controller

        /* PRE-SPRING IMPLEMENTATION */
//        FlooringOrders flooringOrders = new FlooringOrders();
//        UserIO io = new UserIO();
//        FlooringView view = new FlooringView(io);
//        FlooringOrdersService ordersService = new FlooringOrdersService(view, flooringOrders);
//
//        App app = new App(ordersService);
//        app.start();


        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.hactually");
        appContext.refresh();

        FlooringController controller = appContext.getBean("flooringController", FlooringController.class);
        controller.start();
    }
}