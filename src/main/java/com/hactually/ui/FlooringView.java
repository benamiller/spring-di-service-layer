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
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* <<Floor Mastery>>");
        io.print("* 1. Display Order");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Export All Data");
        io.print("* 6. Exit");
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
