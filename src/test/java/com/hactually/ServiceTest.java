package com.hactually;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServiceTest {
    @BeforeClass
    public static void init() {
        // create instance of service with dao and view
        assertEquals(1, 1);
    }

    @Test
    public void displayOrderInformation() {
        // assertEquals(expectedOrder, actualOrder)
        assertEquals(1, 2);
    }

    @Test
    public void generateOrderNumber() {
        // Look through test folder of orders to get correct order number
        assertEquals(1, 1);
    }

    @Test
    public void editFlooringOrder() {
        assertEquals(1, 1);
    }

    @Test
    public void removeFlooringOrder() {
        assertEquals(1,1);
    }
}
