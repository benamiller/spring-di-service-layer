package com.hactually.controller;

import com.hactually.service.FlooringOrdersService;
import com.hactually.ui.FlooringView;
import com.hactually.ui.View;

public class App {

    private final FlooringOrdersService ordersService;
    // Should have a service layer Order object
    public App(FlooringOrdersService ordersService) {
        this.ordersService = ordersService;
    }

    public void start() {
        ordersService.print("view");
        ordersService.print("service");

        boolean shouldContinue = true;

        while (shouldContinue) {

            ordersService.printMenu();

            int menuSelection = ordersService.readInt("Please select one of the menu options, and enter the corresponding integer :)");

            switch (menuSelection) {
                case 1:
                    System.out.println("Display an order");
                    break;
                case 2:
                    System.out.println("Add order");
                    break;
                case 3:
                    System.out.println("Edit order");
                    break;
                case 4:
                    System.out.println("Remove order");
                    break;
                case 5:
                    System.out.println("Export data");
                    break;
                case 6:
                    shouldContinue = false;
                    break;
            }
        }
    }

}
