package com.hactually.ui;

import com.hactually.dto.FlooringOrder;
import com.hactually.dto.Order;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

public class FlooringView implements View {
    private final UserIO io;
    public FlooringView(UserIO io) {
        this.io = io;
    }
    public void print(String message) {
        System.out.println(message);
    }

    public Order getInputAndCreateOrder() {
        return new FlooringOrder();
    }

    // Add list of menu items here, and then use a stream to print each
    public void printMenu(List<String> menuItems) {
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        menuItems.forEach(System.out::println);
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    @Override
    public void printStartBanner(String message) {

    }

    @Override
    public void printSuccessBanner(String message) {

    }

    @Override
    public void printFailureBanner(String message) {

    }

    public int readInt(String prompt) {
        return io.readInt(prompt);
    }

    public Map<String, String> getFlooringOrderInfoFromInput() {
        String customerName = io.readString("Enter your name: ");
        while (!validateCustomerName(customerName)) {
            customerName = io.readString("Please enter a name with letters, numbers, commas, periods, and spaces only: ");
        }

        String orderDate = io.readString("Enter order date (MMDDYYYY): ");
        while (!validateOrderDate(orderDate)) {
            orderDate = io.readString("Please enter a date in the future in the format, MMDDYYYY: ");
        }

        String state = io.readString("Enter your state initialism: ");
        String productType = io.readString("Enter the product type");
        String area = io.readString("Enter your floor area");
        Map<String, String> map = new HashMap<>();
        map.put("customerName", customerName);
        map.put("orderDate", orderDate);
        map.put("state", state);
        map.put("productType", productType);
        map.put("area", area);
        return map;
    }

    public String[] getFlooringOrderInfo() {
        String orderDate = io.readString("Enter the order fulfill date: ");
        String orderNumber = io.readString("Enter the order number: ");
        String[] orderInfoToDelete = {orderDate, orderNumber};

        return orderInfoToDelete;
    }

    public String getFlooringOrderCustomerName(String prompt) {
        return io.readString(prompt);
    }

    public String getFlooringOrderDate(String prompt) {
        return io.readString(prompt);
    }

    public boolean validateCustomerName(String customerName) {
        return (customerName.matches("^[.a-zA-Z0-9, ]+$"));


    }

    private boolean validateOrderDate(String orderDate) {
        if (orderDate.length() != 8) {
            return false;
        }

        String orderYear = orderDate.substring(4);
        String orderMonth = orderDate.substring(0, 2);
        String orderDay = orderDate.substring(2, 4);
        LocalDate dateToday = java.time.LocalDate.now();

        String currentDateFormatted = dateToday.toString().replace("-", "");
        String orderDateFormatted = orderYear + orderMonth + orderDay;

        return (orderDateFormatted.compareTo(currentDateFormatted) >= 0);
    }

    public String getNewPropertyValue(String propertyToBeUpdated, String currentPropertyValue) {
        return io.readString("Enter a new " + propertyToBeUpdated + " (" + currentPropertyValue + ")");
    }
}
