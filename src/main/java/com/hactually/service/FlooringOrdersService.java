package com.hactually.service;

import com.hactually.ui.FlooringView;

import java.time.LocalDate;

public class FlooringOrdersService {
    private FlooringView view;
    public FlooringOrdersService(FlooringView view) {
        this.view = view;
    }

    public void displayOrder(String orderDate, int orderNumber) {

    }

    public void addOrder(String filename) {

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
        view.printMenu();
    }

    public int readInt(String prompt) {
        return view.readInt(prompt);
    }

    public void print(String message) {
        view.print(message);
    }
}
