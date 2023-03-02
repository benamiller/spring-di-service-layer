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

        boolean shouldContinue = true;

        while (shouldContinue) {

            int menuSelection = 2;

            switch (menuSelection) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
            }
        }
    }

}
