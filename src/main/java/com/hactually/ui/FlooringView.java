package com.hactually.ui;

import com.hactually.dto.FlooringOrder;
import com.hactually.dto.Order;

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

    public void printMenu() {

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
}
