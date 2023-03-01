package com.hactually.controller;

import com.hactually.service.FlooringOrdersService;
import com.hactually.ui.FlooringView;
import com.hactually.ui.View;

public class App {

    private final FlooringView view;
    private final FlooringOrdersService ordersService;
    // Should have a service layer Order object
    public App(FlooringView view, FlooringOrdersService ordersService) {
        this.view = view;
        this.ordersService = ordersService;
    }

    public void start() {
        view.print("view");
        ordersService.print("service");
    }

}
