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
        File file = new File(ORDERS_DIRECTORY + "Orders_" + flooringOrderDate + FILE_EXTENSION);
        if (file.exists()) {
            try {
                FileWriter fr = new FileWriter(file, true);
                PrintWriter pr = new PrintWriter(fr);
                pr.println(flooringOrder);
                pr.flush();
                pr.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                System.out.println("PRINTING TO FILE");
                FileWriter fr = new FileWriter(file);
                PrintWriter pr = new PrintWriter(fr);
                pr.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LabourCostPerSquareFoot,MaterialCost,LabourCost,Tax,Total");
                pr.println(flooringOrder);
                pr.flush();
                pr.close();
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
            System.out.println(e);
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

    public int getNextOrderNumber() {
        Scanner sc;
        try {
            sc = new Scanner(
                    new BufferedReader(
                            new FileReader("./LAST_ORDER.txt")
                    )
            );
            String currentOrderNumber = sc.nextLine();
            int orderNumber = Integer.parseInt(currentOrderNumber);

            updateOrderNumber(orderNumber);

            return orderNumber;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateOrderNumber(int orderNumber) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("./LAST_ORDER.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(orderNumber + 1);
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean removeOrder(String[] orderInfoToDelete) {
        String orderDate = orderInfoToDelete[0];
        String orderNumberToDelete = orderInfoToDelete[1];
        String orderFileName = "./Orders/Orders_" + orderDate + ".txt";

        File tempFile = new File("./Orders/tempOrder.txt");
        File orderFile = new File(orderFileName);

        if (!orderFile.exists()) {
            return true;
        }

        Scanner scanner;
        try {

            FileWriter fileWriter = new FileWriter(tempFile);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(orderFile)
                    )
            );

            while(scanner.hasNextLine()) {
                String flooringOrderInfo = scanner.nextLine();
                String[] flooringOrderInfoUnmarshalled = flooringOrderInfo.split(",");
                String candidateOrderNumber = flooringOrderInfoUnmarshalled[0];

                if (candidateOrderNumber.equals(orderNumberToDelete)) {
                    // do not print line to temp file
                    continue;
                }

                printWriter.println(flooringOrderInfo);

            }

            printWriter.flush();
            printWriter.close();

            return tempFile.renameTo(orderFile);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] getEditableOrderInfo(String orderDate, String orderNumber) {
        File file = new File("./Orders/Orders_" + orderDate + ".txt");
        if (!file.exists()) {
            return null;
        }

        Scanner sc;
        try {
            sc = new Scanner(
                    new BufferedReader(
                            new FileReader(file)
                    )
            );

            String[] flooringOrderCurrentEditableInfo;
            // skip header
            sc.nextLine();
            while (sc.hasNextLine()) {
                String flooringOrderInfo = sc.nextLine();
                String[] flooringOrderInfoUnmarshalled = flooringOrderInfo.split(",");

                String candidateOrderNumber = flooringOrderInfoUnmarshalled[0];

                if (candidateOrderNumber.equals(orderNumber)) {
                    flooringOrderCurrentEditableInfo =
                            new String[]{
                                    flooringOrderInfoUnmarshalled[1],
                                    flooringOrderInfoUnmarshalled[2],
                                    flooringOrderInfoUnmarshalled[4],
                                    flooringOrderInfoUnmarshalled[5]};
                    return flooringOrderCurrentEditableInfo;
                }
            }

            return null;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean editOrder(String[] orderInfoToEdit, ArrayList<String> updatedProperties) {
        String orderDate = orderInfoToEdit[0];
        String orderNumberToEdit = orderInfoToEdit[1];
        String orderFileName = "./Orders/Orders_" + orderDate + ".txt";

        File tempFile = new File("./Orders/tempOrder.txt");
        File orderFile = new File(orderFileName);

        if (!orderFile.exists()) {
            return true;
        }

        Scanner scanner;
        try {

            FileWriter fileWriter = new FileWriter(tempFile);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(orderFile)
                    )
            );

            while(scanner.hasNextLine()) {
                String flooringOrderInfo = scanner.nextLine();
                String[] flooringOrderInfoUnmarshalled = flooringOrderInfo.split(",");
                String candidateOrderNumber = flooringOrderInfoUnmarshalled[0];

                String[] updatedFlooringOrderInfoUnmarshalled =
                        Arrays.copyOf(flooringOrderInfoUnmarshalled, flooringOrderInfoUnmarshalled.length);

                updatedFlooringOrderInfoUnmarshalled[1] = updatedProperties.get(0);
                updatedFlooringOrderInfoUnmarshalled[2] = updatedProperties.get(1);
                updatedFlooringOrderInfoUnmarshalled[4] = updatedProperties.get(2);
                updatedFlooringOrderInfoUnmarshalled[5] = updatedProperties.get(3);

                if (candidateOrderNumber.equals(orderNumberToEdit)) {
                    // make an updated flooringOrder with edits
                    FlooringOrder flooringOrder = makeFlooringOrderFromStringArray(updatedFlooringOrderInfoUnmarshalled, orderDate);
                    // print an updated FlooringOrder
                    printWriter.println(flooringOrder);

                    // continue so that we don't print the original order as well
                    continue;
                }

                printWriter.println(flooringOrderInfo);

            }

            printWriter.flush();
            printWriter.close();

            return tempFile.renameTo(orderFile);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private FlooringOrder makeFlooringOrderFromStringArray(String[] flooringOrderInfo, String orderDate) {
        int orderNumber = Integer.parseInt(flooringOrderInfo[0]);
        String customerName = flooringOrderInfo[1];
        String state = flooringOrderInfo[2];
        String productType = flooringOrderInfo[4];
        BigDecimal area = BigDecimal.valueOf(Double.parseDouble(flooringOrderInfo[5]));
        BigDecimal taxRate = getFlooringOrderTaxRate(state);
        BigDecimal costPerSquareFoot = getFlooringOrderCostPerSquareFoot(productType);
        BigDecimal labourCostPerSquareFoot = getFlooringOrderLabourCostPerSquareFoot(productType);


        FlooringOrder flooringOrder = new FlooringOrder(orderNumber, orderDate, customerName, state, taxRate, productType, area, costPerSquareFoot, labourCostPerSquareFoot);
        return flooringOrder;
    }
}
