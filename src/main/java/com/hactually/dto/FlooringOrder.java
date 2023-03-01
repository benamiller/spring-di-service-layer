package com.hactually.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FlooringOrder implements Order {
    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSquareFoot;
    private BigDecimal labourCostPerSquareFoot;

    // area * costPerSquareFoot
    private BigDecimal materialCost;

    // area * labourCostPerSquareFoot
    private BigDecimal labourCost;

    // (materialCost + labourCost) * (taxRate / 100)
    private BigDecimal tax;

    // materialCost + labourCost + tax
    private BigDecimal total;

    public FlooringOrder(int orderNumber, String customerName, String state, BigDecimal taxRate, String productType, BigDecimal area, BigDecimal costPerSquareFoot, BigDecimal labourCostPerSquareFoot) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSquareFoot = costPerSquareFoot;
        this.labourCostPerSquareFoot = labourCostPerSquareFoot;
        this.materialCost = area.multiply(costPerSquareFoot);
        this.labourCost = area.multiply(labourCostPerSquareFoot);
        this.tax = (materialCost.add(labourCost)).multiply(taxRate.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        this.total = materialCost.add(labourCost).add(tax);
    }
}
