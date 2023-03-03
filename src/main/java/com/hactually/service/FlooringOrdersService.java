package com.hactually.service;

import com.hactually.dto.FlooringOrder;
import com.hactually.ui.FlooringView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FlooringOrdersService {
    private FlooringView view;
    public FlooringOrdersService(FlooringView view) {
        this.view = view;
    }

    public void getOrderByDateAndOrderNumber(String orderDate, int orderNumber) {

    }
    public String displayOrder(FlooringOrder flooringOrder) {
        return flooringOrder.toString();
    }

    public void addOrder(String filename) {
        Map<String, String> flooringOrderInputs = view.getFlooringOrderInfoFromInput();
        System.out.println(validateCustomerName(flooringOrderInputs.get("customerName")));
        // Validate customer name (^[\.a-zA-Z0-9, ]+$)
        // Validate order date (ensure it is in the future)
    }

    public void editOrder() {

    }

    public void removeOrder() {

    }

    public void exportData() {

    }

    public boolean isFutureDate(LocalDate date) {
        return false;
    }

    public void printMenu() {
        List<String> menuItems =
                new ArrayList<>(Arrays.asList(
                        "* 1. Display Order",
                        "* 2. Add an Order",
                        "* 3. Edit an Order",
                        "* 4. Remove an Order",
                        "* 5. Export All Data",
                        "* 6. Exit"));
        view.printMenu(menuItems);
    }

    public int readInt(String prompt) {
        return view.readInt(prompt);
    }

    public void print(String message) {
        view.print(message);
    }

    public boolean validateCustomerName(String customerName) {
        return (customerName.matches("^[.a-zA-Z0-9, ]+$"));
    }
}
