package com.hactually.ui;

import com.hactually.dto.Order;

import java.time.LocalDate;
import java.util.Map;

public interface GenericView {
    void print(String message);

    Order getInputAndCreateOrder();

    int readInt(String prompt);

    Map<String, String> getFlooringOrderInfoFromInput();

    String[] getFlooringOrderInfo();

    boolean validateCustomerName(String customerName);

    /**
     * Validates the orderDate inputted by the user
     * This must be a valid date in MMDDYYYY format, and must be in the future (includes the current day)
     *
     * @param orderDate The orderDate that the user entered
     * @return Boolean indicating if the date is valid, and in the future
     */
    default boolean validateOrderDate(String orderDate) {
        if (orderDate.length() != 8) {
            return false;
        }

        if (orderDate.charAt(0) > '1' || orderDate.charAt(0) < '0') {
            return false;
        }

        if (orderDate.charAt(1) > '9' || orderDate.charAt(1) < '0') {
            return false;
        }

        if (orderDate.charAt(2) > '3' || orderDate.charAt(2) < '0') {
            return false;
        }

        if (orderDate.charAt(3) > '9' || orderDate.charAt(3) < '0') {
            return false;
        }

        String orderYear = orderDate.substring(4);
        String orderMonth = orderDate.substring(0, 2);
        String orderDay = orderDate.substring(2, 4);
        LocalDate dateToday = LocalDate.now();

        String currentDateFormatted = dateToday.toString().replace("-", "");
        String orderDateFormatted = orderYear + orderMonth + orderDay;

        return (orderDateFormatted.compareTo(currentDateFormatted) >= 0);
    }

    String getNewPropertyValue(String propertyToBeUpdated, String currentPropertyValue);
}
