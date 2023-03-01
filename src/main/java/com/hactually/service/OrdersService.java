package com.hactually.service;

import com.hactually.dto.Order;

import java.time.LocalDate;

public interface OrdersService {
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
