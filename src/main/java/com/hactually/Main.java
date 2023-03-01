package com.hactually;

import com.hactually.controller.App;
import com.hactually.service.FlooringOrdersService;
import com.hactually.ui.FlooringView;

public class Main {
    public static void main(String[] args) {
        // create service layer, create view
        // pass these to the controller
        FlooringView view = new FlooringView();
        FlooringOrdersService ordersService = new FlooringOrdersService();

        App app = new App(view, ordersService);
        app.start();
    }
}