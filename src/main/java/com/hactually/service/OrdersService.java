package com.hactually.service;

import com.hactually.exception.ProductTypeNotFoundException;
import com.hactually.exception.TaxRateNotFoundException;

public interface OrdersService {
    void displayOrders();

    void createOrder() throws ProductTypeNotFoundException, TaxRateNotFoundException;

    void editOrder();

    void removeOrder();

    void exportData();

    void printMenu();

    int readInt(String prompt);

    void print(String message);

    void fetchTaxRates(String filename);

    void fetchProductCosts(String filename);
}
