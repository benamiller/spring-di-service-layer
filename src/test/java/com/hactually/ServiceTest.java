package com.hactually;

import com.hactually.dao.FlooringOrders;
import com.hactually.dto.FlooringOrder;
import com.hactually.service.FlooringOrdersService;
import com.hactually.ui.FlooringView;
import com.hactually.ui.UserIO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import java.io.*;

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

    @Test
    public void exportOrders() {
        FlooringOrders flooringOrders = new FlooringOrders();

        ArrayList<File> files = new ArrayList<>();
        File file1 = new File("./Test_Orders/Orders_03032030.txt");
        File file2 = new File("./Test_Orders/Orders_06022013.txt");
        files.add(file1);
        files.add(file2);

        flooringOrders.exportFiles(files, "./Test_Backup/DataExport.txt");

        try {
            String actualOutputFileContent = new String(Files.readAllBytes(Paths.get("./Test_Backup/DataExport.txt")), StandardCharsets.UTF_8);
            String expectedOutputFileContent = new String(Files.readAllBytes(Paths.get("./Test_Backup/ExpectedDataExport.txt")), StandardCharsets.UTF_8);
            assertEquals(expectedOutputFileContent, actualOutputFileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
