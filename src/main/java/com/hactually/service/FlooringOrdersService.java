package com.hactually.service;

import com.hactually.dao.FlooringOrders;
import com.hactually.dto.FlooringOrder;
import com.hactually.exception.ProductTypeNotFoundException;
import com.hactually.exception.TaxRateNotFoundException;
import com.hactually.ui.FlooringView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class FlooringOrdersService {

    private FlooringView view;
    private FlooringOrders flooringOrders;

    @Autowired
    public FlooringOrdersService(FlooringView view, FlooringOrders flooringOrders) {
        this.view = view;
        this.flooringOrders = flooringOrders;
    }

    /**
     * Gets all order files and delegates displaying to the dao layer
     */
    public void displayOrders() {
        final String ordersDirectoryPath = "./Orders";

        File ordersDirectory = new File(ordersDirectoryPath);
        File[] files = ordersDirectory.listFiles();

        if (files == null) {
            return;
        }

        ArrayList<File> validFiles = new ArrayList<>();


        for (File file : files) {
            if (file.isFile()) {
                validFiles.add(file);
            }
        }

        // Could be rewritten as validFiles.forEach(this::displayOrderForDate);
        validFiles.stream().forEach((file) -> {
            flooringOrders.displayOrdersForDate(file);
        });
    }

    /**
     * Creates and persists a new FlooringOrder
     */
    public void createOrder() throws ProductTypeNotFoundException, TaxRateNotFoundException {
        Map<String, String> flooringOrderInputs = view.getFlooringOrderInfoFromInput();
        BigDecimal taxRate = flooringOrders.getFlooringOrderTaxRate(flooringOrderInputs.get("state"));
        BigDecimal costPerSquareFoot = flooringOrders.getFlooringOrderCostPerSquareFoot(flooringOrderInputs.get("productType"));
        BigDecimal labourCostPerSquareFoot = flooringOrders.getFlooringOrderLabourCostPerSquareFoot(flooringOrderInputs.get("productType"));
        BigDecimal area = BigDecimal.valueOf(Double.parseDouble(flooringOrderInputs.get("area")));
        int orderNumber = flooringOrders.getNextOrderNumber();
        // call dao to add the order to file
        // generate order number


        // see if any inputs were invalid
        if (taxRate.compareTo(BigDecimal.ZERO) > 0) {
            throw new TaxRateNotFoundException("Product type not found. Try to create an order again? :)");
        } else if (costPerSquareFoot.compareTo(BigDecimal.ZERO) > 0 || labourCostPerSquareFoot.compareTo(BigDecimal.ZERO) > 0) {
            throw new ProductTypeNotFoundException("State type not found. Try to create an order again? :)");
        }

        FlooringOrder flooringOrder = new FlooringOrder(
                orderNumber,
                flooringOrderInputs.get("orderDate"),
                flooringOrderInputs.get("customerName"),
                flooringOrderInputs.get("state"),
                taxRate,
                flooringOrderInputs.get("productType"),
                area,
                costPerSquareFoot,
                labourCostPerSquareFoot
                );
        flooringOrders.addOrder(flooringOrder);

    }

    /**
     * Edits an existing FlooringOrder on disk
     */
    public void editOrder() {

        // Asks the user for orderDate, and orderNumber
        String[] orderInfoToEdit = view.getFlooringOrderInfo();

        // pass the orderDate, and orderNumber to get current property values, if the order exists
        String[] currentOrderInfo = flooringOrders.getEditableOrderInfo(orderInfoToEdit[0], orderInfoToEdit[1]);

        String newCustomerName = view.getNewPropertyValue("customer name", currentOrderInfo[0]);
        String newState = view.getNewPropertyValue("state", currentOrderInfo[1]);
        String newProductType = view.getNewPropertyValue("product type", currentOrderInfo[2]);
        String newArea = view.getNewPropertyValue("area", currentOrderInfo[3]);

        BigDecimal taxRate = flooringOrders.getFlooringOrderTaxRate(newState);
        BigDecimal costPerSquareFoot = flooringOrders.getFlooringOrderCostPerSquareFoot(newProductType);


        System.out.println("New customer name: " + newCustomerName);
        System.out.println("New state: " + newState);
        System.out.println("New product type: " + newProductType);
        System.out.println("New area: " + newArea);

        ArrayList<String> updatedProperties = new ArrayList<>(Arrays.asList(newCustomerName, newState, newProductType, newArea));

        // create a new FlooringOrder object with updated values
        // find order number in file, and replace with object.toString
        flooringOrders.editOrder(orderInfoToEdit, updatedProperties);
    }

    /**
     * Removes a FlooringOrder
     */
    public void removeOrder() {
        String[] orderInfoToDelete = view.getFlooringOrderInfo();
        flooringOrders.removeOrder(orderInfoToDelete);
    }

    /**
     * Exports all order data ta a backup directory and file
     */
    public void exportData() {
        final String ordersDirectoryPath = "./Orders";

        File ordersDirectory = new File(ordersDirectoryPath);
        File[] files = ordersDirectory.listFiles();

        if (files == null) {
            return;
        }

        ArrayList<File> validFiles = new ArrayList<>();


        for (File file : files) {
            if (file.isFile()) {
                validFiles.add(file);
            }
        }
        System.out.println(validFiles.get(0));

        flooringOrders.exportFiles(validFiles, "./Backup/DataExport.txt");
    }

    /**
     * Display the menu of functionality
     */
    public void printMenu() {
        List<String> menuItems =
                new ArrayList<>(Arrays.asList(
                        "* 1. Display Orders",
                        "* 2. Add an Order",
                        "* 3. Edit an Order",
                        "* 4. Remove an Order",
                        "* 5. Export All Data",
                        "* 6. Exit"));
        view.printMenu(menuItems);
    }

    /**
     * Read an integer from the view
     * @param prompt The message to prompt the user
     * @return The user input
     */
    public int readInt(String prompt) {
        return view.readInt(prompt);
    }

    public void print(String message) {
        view.print(message);
    }

    /**
     * Fetches the taxRates from a given file
     * @param filename The file in which to gather taxRates for states
     */
    public void fetchTaxRates(String filename) {
        flooringOrders.fetchTaxRates(filename);
    }

    /**
     * Fetches the product and labour costs per sq ft.
     * @param filename The file in which to gather costs data for productTypes
     */
    public void fetchProductCosts(String filename) {
        flooringOrders.fetchProductTypeCosts(filename);
    }

}

