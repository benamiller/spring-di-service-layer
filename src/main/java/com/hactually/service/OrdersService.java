package com.hactually.service;

import com.hactually.dto.Order;

import java.time.LocalDate;

public interface OrdersService {
    /*
    Orders will be in an in-memory structure belonging to dao.FlooringOrders, and
    this class will be instantiated with a dao.FlooringOrders object
     */
    public int generateOrderNumber();
    public void readFloorOrdersFromFile();

    // void because this will invoke the view's standard out methods
    public void displayFloorOrder();
    public void addFloorOrder(Order order);
    public void editFloorOrder(int orderNumber, String orderDate);
    public void removeFloorOrder(int orderNumber, String orderDate);

    // Checks if order date is in the future (i.e. tomorrow or later)
    public boolean isDateValid(LocalDate candidateDate);
}
