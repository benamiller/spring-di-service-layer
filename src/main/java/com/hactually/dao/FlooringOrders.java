package com.hactually.dao;

import com.hactually.dto.FlooringOrder;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * FlooringOrders interacts with the file system to record, edit, remove, fetch, and backup orders
 */
@Component
public class FlooringOrders implements Orders {
    private Map<String, Double> taxRatesByState = new HashMap<>();
    private Map<String, Double[]> productCostAndLabourCostPerSquareFoot = new HashMap<>();
    private final String ORDERS_DIRECTORY = "./Orders/";
    private final String FILE_EXTENSION = ".txt";


    /**
     * Stores a FlooringOrder to disk under the correctly dated file
     * @param flooringOrder A FlooringOrder object to be persisted
     */
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

    /**
     * Retrieves taxRates from file for each state
     * @param filename The name of the file from which taxRates are to be gathered
     */
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

    /**
     * Retrieves productType labour and material costs per sq ft.
     * @param filename The name of the file from which productType costs are to be gathered
     */
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

    /**
     * Gets the taxRate for a given state (initialized i.e. CA for California)
     * Returns a negative value if no such state exists
     * @param state The state whose taxRate should be returned
     * @return The BigDecimal value of the state's taxRate
     */
    public BigDecimal getFlooringOrderTaxRate(String state) {
        if (taxRatesByState.get(state) != null) {
            return BigDecimal.valueOf(taxRatesByState.get(state));
        }
        return BigDecimal.valueOf(-10);
    }

    /**
     * Gets the cost per sq ft. of a given productType
     * Return a negative value if no such productType exists
     * @param productType The productType whose cost per sq ft. should be returned
     * @return The BigDecimal value of the material cost per sq ft.
     */
    public BigDecimal getFlooringOrderCostPerSquareFoot(String productType) {
        if (productCostAndLabourCostPerSquareFoot.get(productType) != null) {
            return BigDecimal.valueOf(productCostAndLabourCostPerSquareFoot.get(productType)[0]);
        }
        return BigDecimal.valueOf(-10);
    }

    /**
     * Gets the labour cost per sq ft. of a given productType
     * Returns a negative value if no such state exists
     * @param productType The productType whose labour cost per sq ft. should be returned
     * @return The BigDecimal value of the labour cost per sq ft.
     */
    public BigDecimal getFlooringOrderLabourCostPerSquareFoot(String productType) {
        if (productCostAndLabourCostPerSquareFoot.get(productType) != null) {
            return BigDecimal.valueOf(productCostAndLabourCostPerSquareFoot.get(productType)[1]);
        }
        return BigDecimal.valueOf(-10);
    }

    /**
     * Gets the next globally unique order number stored on disk
     * @return The next usable globally unique order number
     */
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

    /**
     * Updates the next globally unique order number to be used for the next order
     * @param orderNumber The last order number used, to be incremented by 1
     */
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

    /**
     * Removes an order, specified by orderDate and orderNumber, from disk
     * @param orderInfoToDelete The orderDate, and orderNumber in a length-2 array of Strings
     * @return Boolean which indicates if the temp file was successfully renamed
     */
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

    /**
     * Retrieves the editable information of an order: customerName, state, productType, area
     * This allows for the preview of the existing values while editing the order
     * @param orderDate The date of the order
     * @param orderNumber The number of the order
     * @return Returns a String array of updated FlooringOrder values
     */
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

    /**
     * Applies the intended edits to disk
     * @param orderInfoToEdit The orderDate and orderNumber to target the intended FlooringOrder on disk
     * @param updatedProperties The updated FlooringOrder values to replace the existing FlooringOrder
     * @return Boolean indicating if edits were successfully applied
     */
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

    /**
     * A helper method to construct a FlooringOrder with edits applied
     * The FlooringOrder is constructed to update any costs from productType changes
     * and to update taxRate from state changes
     * @param flooringOrderInfo  The newly edited values of the FlooringOrder
     * @param orderDate The orderDate used in the constructor, but is not persisted outside the filename
     * @return The FlooringOrder with the edits applied
     */
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

    /**
     * Prints all orders for a given date
     * @param file The file containing the orders to be displayed
     */
    public void displayOrdersForDate(File file) {

        Scanner scanner;
        try {

            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(file)
                    )
            );

            // ignore the header
            scanner.nextLine();
            while(scanner.hasNextLine()) {
                String flooringOrderInfo = scanner.nextLine();
                System.out.println(flooringOrderInfo);

            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Exports the orders from a list of files
     * @param files An ArrayList of files which contain orders to be backed up
     * @param backupFileName The target backup filename
     */
    public void exportFiles(ArrayList<File> files, String backupFileName) {
        // nice lambda to sort based on their order date
        // the nearest order dates are at the top
        files.sort((a, b) -> b.getName().compareTo(a.getName()));

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(backupFileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LabourCostPerSquareFoot,MaterialCost,LabourCost,Tax,Total,OrderDate");

            for (File file : files) {
                String fileName = file.getName();
                String month = fileName.substring(7, 9);
                String day = fileName.substring(9, 11);
                String year = fileName.substring(11, 15);
                String orderDate = month + "-" + day + "-" + year;

                Scanner scanner = new Scanner(
                        new BufferedReader(
                                new FileReader(file)
                        )
                );

                // Ignore the header
                scanner.nextLine();
                while (scanner.hasNextLine()) {
                    String orderString = scanner.nextLine();
                    printWriter.println(orderString + "," + orderDate);
                }


            }

            printWriter.flush();
            printWriter.close();

        } catch (IOException e) {
            System.out.println("Could not export data, please try again");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }



    }
}
