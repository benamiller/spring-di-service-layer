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
        final String PRODUCT_COSTS_FILENAME = "./Data/Products.txt";
        final String TAX_RATES_FILENAME = "./Data/Taxes.txt";

        boolean shouldContinue = true;
        ordersService.fetchTaxRates(TAX_RATES_FILENAME);
        ordersService.fetchProductCosts(PRODUCT_COSTS_FILENAME);

        while (shouldContinue) {

            ordersService.printMenu();

            int menuSelection = ordersService.readInt("Please select one of the menu options, and enter the corresponding integer :)");

            switch (menuSelection) {
                case 1:
                    System.out.println("Display an order");
                    break;
                case 2:
                    System.out.println("Add order");
                    ordersService.createOrder();
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
