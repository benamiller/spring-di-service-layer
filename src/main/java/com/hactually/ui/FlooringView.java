package com.hactually.ui;

import com.hactually.dto.FlooringOrder;
import com.hactually.dto.Order;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
