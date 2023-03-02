package com.hactually.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FlooringOrders implements Orders {


    // Add the order to the file
    public void addOrder() {

    }

    // Retrieves taxRates from file
    public Map<String, Integer> getTaxRates() {

        Map<String, Integer> map = new HashMap<>();
        map.put("TX", 1);

        return map;
    }

    public Map<String, Double[]> getProductTypeCosts() {
        Map<String, Double[]> map = new HashMap<>();
        Double[] arr = {2.25, 2.10};
        map.put("Carpet", arr);

        return map;
    }
}
