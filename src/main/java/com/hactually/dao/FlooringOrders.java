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
    
    public Map<String, Double[]> fetchProductTypeCosts() {
        Map<String, Double[]> map = new HashMap<>();
        Double[] arr = {2.25, 2.10};
        map.put("Carpet", arr);
        productCostAndLabourCostPerSquareFoot.put("Carpet", arr);

        return productCostAndLabourCostPerSquareFoot;
    }

    public BigDecimal getFlooringOrderTaxRate(String state) {
        return BigDecimal.valueOf(taxRatesByState.get(state));
    }

    public BigDecimal getFlooringOrderCostPerSquareFoot(String productType) {
    }
}
