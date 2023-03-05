package com.hactually.controller;

import com.hactually.service.FlooringOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FlooringController controls and delegates
 */
@Component
public class FlooringController {
    private FlooringOrdersService ordersService;
    // Should have a service layer Order object

    @Autowired
    public FlooringController(FlooringOrdersService ordersService) {
        this.ordersService = ordersService;
    }


    /**
     * Begins the program and asks the user which operation they wish to perform
     * Until the user wishes to quit, they will be prompted after each successful operation
     */
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
                    System.out.println("Display orders");
                    ordersService.displayOrders();
                    break;
                case 2:
                    System.out.println("Add order");
                    ordersService.createOrder();
                    break;
                case 3:
                    System.out.println("Edit order");
                    ordersService.editOrder();
                    break;
                case 4:
                    System.out.println("Remove order");
                    ordersService.removeOrder();
                    break;
                case 5:
                    System.out.println("Export data");
                    ordersService.exportData();
                    break;
                case 6:
                    shouldContinue = false;
                    break;
            }

            // Make some space
            System.out.println("");
        }
    }

}
