package com.hactually.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class FlooringOrders implements Orders {
    private Map<String, Integer> taxRatesByState = new HashMap<>();
    private Map<String, Double[]> productCostAndLabourCostPerSquareFoot = new HashMap<>();

    // Add the order to the file
    public void addOrder() {

    }

    // Retrieves taxRates from file
    public Map<String, Integer> getTaxRates(String filename) {
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
                int stateTaxRate = Integer.parseInt(stateInfoUnmarshalled[2]);
                taxRatesByState.put(stateInitialism, stateTaxRate);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Was not able to find the Tax file");
            throw new RuntimeException(e);
        }

        return taxRatesByState;
    }

    public Map<String, Double[]> getProductTypeCosts() {
        Map<String, Double[]> map = new HashMap<>();
        Double[] arr = {2.25, 2.10};
        map.put("Carpet", arr);
        productCostAndLabourCostPerSquareFoot.put("Carpet", arr);

        return productCostAndLabourCostPerSquareFoot;
    }
}
