package com.hactually.service;

public interface OrdersService {

    // validate orders here
    // call dao methods for creating, cancelling, displaying, and editing orders

    public int generateOrderNumber();
    public void readFloorOrdersFromFile();

    // void because this will invoke the view's standard out methods
    public void displayFloorOrder();
    public void editFloorOrder(int orderNumber, String orderDate);
}
