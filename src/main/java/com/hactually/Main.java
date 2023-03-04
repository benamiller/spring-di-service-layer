package com.hactually;

import com.hactually.controller.App;
import com.hactually.dao.FlooringOrders;
import com.hactually.service.FlooringOrdersService;
import com.hactually.ui.FlooringView;
import com.hactually.ui.UserIO;

public class Main {
    public static void main(String[] args) {
        // create service layer, create view
        // pass these to the controller
        FlooringOrders flooringOrders = new FlooringOrders();
        UserIO io = new UserIO();
        FlooringView view = new FlooringView(io);
        FlooringOrdersService ordersService = new FlooringOrdersService(view, flooringOrders);

        App app = new App(ordersService);
        app.start();
    }
}