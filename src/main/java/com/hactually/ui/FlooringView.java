package com.hactually.ui;

import com.hactually.dto.FlooringOrder;
import com.hactually.dto.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

@Component
public class FlooringView implements GenericView {
    private UserIO io;

    @Autowired
    public FlooringView(UserIO io) {
        this.io = io;
    }
    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public Order getInputAndCreateOrder() {
        return new FlooringOrder();
    }

    // Add list of menu items here, and then use a stream to print each
    public void printMenu(List<String> menuItems) {
        io.print("MAIN MENU");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        menuItems.forEach(System.out::println);
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    /**
     * Read an integer from io
     * @param prompt The message to prompt a user for input
     * @return The input from the user
     */
    @Override
    public int readInt(String prompt) {
        return io.readInt(prompt);
    }

    /**
     * Get FlooringOrder information necessary for a FlooringOrder construction
     * @return A map of all user inputs
     */
    @Override
    public Map<String, String> getFlooringOrderInfoFromInput() {
        String customerName = io.readString("Enter your name: ");
        while (!validateCustomerName(customerName)) {
            customerName = io.readString("Please enter a name with letters, numbers, commas, periods, and spaces only: ");
        }

        String orderDate = io.readString("Enter order date (MMDDYYYY): ");
        while (!validateOrderDate(orderDate)) {
            orderDate = io.readString("Please enter a valid date in the future in the format, MMDDYYYY: ");
        }

        String state = io.readString("Enter your state initialism (CA, TX, WA, etc): ");
        String productType = io.readString("Enter the product type (Carpet, Tile, etc): ");
        String area = io.readDoubleAsString("Enter your floor area (must be at least 100 sq ft.): ", 100);
        Map<String, String> map = new HashMap<>();
        map.put("customerName", customerName);
        map.put("orderDate", orderDate);
        map.put("state", state);
        map.put("productType", productType);
        map.put("area", area);
        return map;
    }

    /**
     * Gets the orderDate and orderName necessary for targeting orders to be edited/removed/backed up
     * @return An array of Strings containing the orderDate and orderNumber
     */
    @Override
    public String[] getFlooringOrderInfo() {
        String orderDate = io.readString("Enter the order fulfill date: ");
        String orderNumber = io.readString("Enter the order number: ");
        String[] orderInfoToDelete = {orderDate, orderNumber};

        return orderInfoToDelete;
    }

    /**
     * Validates the customerName inputted by the user
     * This must contain letters, numbers, commas, periods and spaces only
     * @param customerName The candidate customerName that the user entered
     * @return Boolean indicating if the customerName is valid, and matches the constraints
     */
    @Override
    public boolean validateCustomerName(String customerName) {
        return (customerName.matches("^[.a-zA-Z0-9, ]+$"));
    }

    /**
     * Gets a new property value from the user
     * @param propertyToBeUpdated The type of property to be updated
     * @param currentPropertyValue The updated value of the property, supplied by the user
     * @return The user-updated property value
     */
    @Override
    public String getNewPropertyValue(String propertyToBeUpdated, String currentPropertyValue) {
        String newPropertyValue;
        if (propertyToBeUpdated.equals("area")) {
            newPropertyValue = io.readNewAreaDoubleAsString("Enter a new area (minimum 100 sq ft.)", 100);
        } else {
            newPropertyValue = io.readString("Enter a new " + propertyToBeUpdated + " (" + currentPropertyValue + ")");
        }

        if (propertyToBeUpdated.equals("customer name")) {
            while (!validateCustomerName(newPropertyValue)) {
                newPropertyValue = io.readString("Please enter a name with letters, numbers, commas, periods, and spaces only: ");
            }
        }

        if (newPropertyValue == null || newPropertyValue.length() == 0) {
            return currentPropertyValue;
        }

        return newPropertyValue;
    }

}
