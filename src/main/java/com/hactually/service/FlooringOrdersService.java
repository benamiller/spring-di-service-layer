package com.hactually.service;

import com.hactually.dao.FlooringOrders;
import com.hactually.dto.FlooringOrder;
import com.hactually.ui.FlooringView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class FlooringOrdersService {

    private FlooringView view;
    private FlooringOrders flooringOrders;
    public FlooringOrdersService(FlooringView view, FlooringOrders flooringOrders) {
        this.view = view;
        this.flooringOrders = flooringOrders;
    }

    public void getOrderByDateAndOrderNumber(String orderDate, int orderNumber) {

    }
    public String displayOrder(FlooringOrder flooringOrder) {
        return flooringOrder.toString();
    }

    public void createOrder(String filename) {
        Map<String, String> flooringOrderInputs = view.getFlooringOrderInfoFromInput();
        FlooringOrder flooringOrder = new FlooringOrder();
        BigDecimal flooringOrderTaxRate = flooringOrders.getFlooringOrderTaxRate(flooringOrderInputs.get("state"));
        BigDecimal flooringOrderCostPerSquareFoot = flooringOrders.getFlooringOrderCostPerSquareFoot(flooringOrderInputs.get("productType"));
        BigDecimal flooringOrderLabourCostPerSquareFoot = flooringOrders.getFlooringOrderLabourCostPerSquareFoot(flooringOrderInputs.get("productType"));


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

}

