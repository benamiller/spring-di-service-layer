package com.hactually;

import com.hactually.dao.FlooringOrders;
import com.hactually.dto.FlooringOrder;
import com.hactually.service.FlooringOrdersService;
import com.hactually.ui.FlooringView;
import com.hactually.ui.UserIO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ServiceTest {
    private static FlooringOrdersService ordersService;

    @BeforeClass
    public static void init() {
        // create instance of service with dao and view
        UserIO io = new UserIO();
        FlooringView view = new FlooringView(io);
        FlooringOrders flooringOrders = new FlooringOrders();
        ordersService = new FlooringOrdersService(view, flooringOrders);
    }

    @AfterClass
    public static void reset() {
        // Restore any files
    }

    @Test
    public void doesDisplayOrderInformation() {
        BigDecimal taxRate = BigDecimal.valueOf(23);
        BigDecimal area = BigDecimal.valueOf(20);
        BigDecimal costPerSquareFoot = BigDecimal.valueOf(2.2);
        BigDecimal labourCostPerSquareFoot = BigDecimal.valueOf(2.4);
        FlooringOrder order = new FlooringOrder(10, "03032023", "Ben", "CA", taxRate, "Carpet", area, costPerSquareFoot, labourCostPerSquareFoot);
        String expected = "10,Ben,CA,23,Carpet,20,2.2,2.4,44.0,48.0,21.16,113.16";
        String actual = ordersService.displayOrder(order);

        assertEquals(expected, actual);
    }

    @Test
    public void editFlooringOrder() {
        assertEquals(1, 1);
    }

    @Test
    public void removeFlooringOrder() {
        assertEquals(1,1);
    }

    @Test
    public void fetchTaxRates() {
        FlooringOrders flooringOrders = new FlooringOrders();
        flooringOrders.fetchTaxRates("Test_Data/Taxes.txt");

        BigDecimal actualCATaxRate = flooringOrders.getFlooringOrderTaxRate("CA");
        BigDecimal expectedCATaxRate = BigDecimal.valueOf(25.00);

        BigDecimal actualTXTaxRate = flooringOrders.getFlooringOrderTaxRate("TX");
        BigDecimal expectedTXTaxRate = BigDecimal.valueOf(4.45);

        BigDecimal actualWATaxRate = flooringOrders.getFlooringOrderTaxRate("WA");
        BigDecimal expectedWATaxRate = BigDecimal.valueOf(9.25);

        BigDecimal actualKYTaxRate = flooringOrders.getFlooringOrderTaxRate("KY");
        BigDecimal expectedKYTaxRate = BigDecimal.valueOf(6.00);

        assertEquals(actualCATaxRate, expectedCATaxRate);
        assertEquals(actualTXTaxRate, expectedTXTaxRate);
        assertEquals(actualKYTaxRate, expectedKYTaxRate);
        assertEquals(actualWATaxRate, expectedWATaxRate);

    }

    @Test
    public void fetchCostPerSquareFoot() {
        FlooringOrders flooringOrders = new FlooringOrders();
        flooringOrders.fetchProductTypeCosts("Test_Data/Products.txt");

        BigDecimal actualCarpetCost = flooringOrders.getFlooringOrderCostPerSquareFoot("Carpet");
        BigDecimal expectedCarpetCost = BigDecimal.valueOf(2.25);

        BigDecimal actualLaminateCost = flooringOrders.getFlooringOrderCostPerSquareFoot("Laminate");
        BigDecimal expectedLaminateCost = BigDecimal.valueOf(1.75);

        BigDecimal actualTileCost = flooringOrders.getFlooringOrderCostPerSquareFoot("Tile");
        BigDecimal expectedTileCost = BigDecimal.valueOf(3.50);

        BigDecimal actualWoodCost = flooringOrders.getFlooringOrderCostPerSquareFoot("Wood");
        BigDecimal expectedWoodCost = BigDecimal.valueOf(5.15);

        assertEquals(expectedCarpetCost, actualCarpetCost);
        assertEquals(expectedLaminateCost, actualLaminateCost);
        assertEquals(expectedTileCost, actualTileCost);
        assertEquals(expectedWoodCost, actualWoodCost);

    }

    @Test
    public void fetchLabourCostPerSquareFoot() {
        FlooringOrders flooringOrders = new FlooringOrders();
        flooringOrders.fetchProductTypeCosts("Test_Data/Products.txt");

        BigDecimal actualCarpetCost = flooringOrders.getFlooringOrderLabourCostPerSquareFoot("Carpet");
        BigDecimal expectedCarpetCost = BigDecimal.valueOf(2.10);

        BigDecimal actualLaminateCost = flooringOrders.getFlooringOrderLabourCostPerSquareFoot("Laminate");
        BigDecimal expectedLaminateCost = BigDecimal.valueOf(2.10);

        BigDecimal actualTileCost = flooringOrders.getFlooringOrderLabourCostPerSquareFoot("Tile");
        BigDecimal expectedTileCost = BigDecimal.valueOf(4.15);

        BigDecimal actualWoodCost = flooringOrders.getFlooringOrderLabourCostPerSquareFoot("Wood");
        BigDecimal expectedWoodCost = BigDecimal.valueOf(4.75);

        assertEquals(expectedCarpetCost, actualCarpetCost);
        assertEquals(expectedLaminateCost, actualLaminateCost);
        assertEquals(expectedTileCost, actualTileCost);
        assertEquals(expectedWoodCost, actualWoodCost);

    }
}
