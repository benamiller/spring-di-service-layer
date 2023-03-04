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

    public void createOrder() {
        Map<String, String> flooringOrderInputs = view.getFlooringOrderInfoFromInput();
        BigDecimal taxRate = flooringOrders.getFlooringOrderTaxRate(flooringOrderInputs.get("state"));
        BigDecimal costPerSquareFoot = flooringOrders.getFlooringOrderCostPerSquareFoot(flooringOrderInputs.get("productType"));
        BigDecimal labourCostPerSquareFoot = flooringOrders.getFlooringOrderLabourCostPerSquareFoot(flooringOrderInputs.get("productType"));
        BigDecimal area = BigDecimal.valueOf(Double.parseDouble(flooringOrderInputs.get("area")));
        int orderNumber = flooringOrders.getNextOrderNumber();
        // call dao to add the order to file
        // generate order number
        FlooringOrder flooringOrder = new FlooringOrder(
                orderNumber,
                flooringOrderInputs.get("orderDate"),
                flooringOrderInputs.get("customerName"),
                flooringOrderInputs.get("state"),
                taxRate,
                flooringOrderInputs.get("productType"),
                area,
                costPerSquareFoot,
                labourCostPerSquareFoot
                );
        flooringOrders.addOrder(flooringOrder);

    }

    public void editOrder() {

    }

    public void removeOrder() {
        String[] orderInfoToDelete = view.getFlooringOrderInfoToDelete();
        flooringOrders.removeOrder(orderInfoToDelete);
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

    public void fetchTaxRates(String filename) {
        flooringOrders.fetchTaxRates(filename);
    }

    public void fetchProductCosts(String filename) {
        flooringOrders.fetchProductTypeCosts(filename);
    }

}

