package com.hactually.dao;

import com.hactually.dto.FlooringOrder;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class FlooringOrders implements Orders {
    private Map<String, Double> taxRatesByState = new HashMap<>();
    private Map<String, Double[]> productCostAndLabourCostPerSquareFoot = new HashMap<>();
    private final String ORDERS_DIRECTORY = "./Orders/";
    private final String FILE_EXTENSION = ".txt";

    // Add the order to the file
    public void addOrder(FlooringOrder flooringOrder) {
        String flooringOrderDate = flooringOrder.getOrderDate();
        File file = new File(ORDERS_DIRECTORY + flooringOrderDate + FILE_EXTENSION);
        if (!file.exists()) {
            try {
                FileWriter fr = new FileWriter(file, true);
                PrintWriter pr = new PrintWriter(fr);
                pr.println(flooringOrder);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Retrieves taxRates from file
    public void fetchTaxRates(String filename) {
        Scanner sc;
        try {
            sc = new Scanner(
                    new BufferedReader(
                            new FileReader(filename)
                    ));
            sc.nextLine();
            while(sc.hasNextLine()) {
                String stateInfo = sc.nextLine();
                String[] stateInfoUnmarshalled = stateInfo.split(",");
                String stateInitialism = stateInfoUnmarshalled[0];
                Double stateTaxRate = Double.parseDouble(stateInfoUnmarshalled[2]);
                taxRatesByState.put(stateInitialism, stateTaxRate);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Was not able to find the Tax file");
            throw new RuntimeException(e);
        }

    }

    public void fetchProductTypeCosts(String filename) {
        Scanner sc;
        try {
            sc = new Scanner(
                    new BufferedReader(
                            new FileReader(filename)
                    )
            );
            sc.nextLine();
            while(sc.hasNextLine()) {
                String productTypeInfo = sc.nextLine();
                String[] productTypeInfoUnmarshalled = productTypeInfo.split(",");
                String productType = productTypeInfoUnmarshalled[0];
                double costPerSquareFoot = Double.parseDouble(productTypeInfoUnmarshalled[1]);
                double labourCostPerSquareFoot  = Double.parseDouble(productTypeInfoUnmarshalled[2]);
                Double[] costs = {costPerSquareFoot, labourCostPerSquareFoot};
                productCostAndLabourCostPerSquareFoot.put(productType, costs);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal getFlooringOrderTaxRate(String state) {
        return BigDecimal.valueOf(taxRatesByState.get(state));
    }

    public BigDecimal getFlooringOrderCostPerSquareFoot(String productType) {
        return BigDecimal.valueOf(productCostAndLabourCostPerSquareFoot.get(productType)[0]);
    }

    public BigDecimal getFlooringOrderLabourCostPerSquareFoot(String productType) {
        return BigDecimal.valueOf(productCostAndLabourCostPerSquareFoot.get(productType)[1]);
    }
}
