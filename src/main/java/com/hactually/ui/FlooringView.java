package com.hactually.ui;

import com.hactually.dto.FlooringOrder;
import com.hactually.dto.Order;

import java.lang.reflect.Array;
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
        String state = io.readString("Enter your state initialism: ");
        String productType = io.readString("Enter the product type");
        String area = io.readString("Enter your floor area");
        Map<String, String> map = new HashMap<>();
        map.put("customerName", customerName);
        map.put("state", state);
        map.put("productType", productType);
        map.put("area", area);
        return map;
    }
}
