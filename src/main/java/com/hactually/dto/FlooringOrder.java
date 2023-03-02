package com.hactually.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class FlooringOrder implements Order {
    // Map orderNumber to FlooringOrder object with orderNumber as a property, and the date
    private int orderNumber;
    private String orderDate;
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

    public FlooringOrder() {

    }
    public FlooringOrder(int orderNumber, String orderDate, String customerName, String state, BigDecimal taxRate, String productType, BigDecimal area, BigDecimal costPerSquareFoot, BigDecimal labourCostPerSquareFoot) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
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

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%.2f,%.2f",
                this.orderNumber,
                this.customerName,
                this.state,
                this.taxRate,
                this.productType,
                this.area,
                this.costPerSquareFoot,
                this.labourCostPerSquareFoot,
                this.materialCost,
                this.labourCost,
                this.tax,
                this.total);
    }
}
