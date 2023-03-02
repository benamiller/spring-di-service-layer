package com.hactually;

import com.hactually.dto.FlooringOrder;
import com.hactually.service.FlooringOrdersService;
import com.hactually.ui.FlooringView;
import com.hactually.ui.UserIO;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServiceTest {
    @BeforeClass
    public static void init() {
        // create instance of service with dao and view
        UserIO io = new UserIO();
        FlooringView view = new FlooringView(io);
        FlooringOrdersService ordersService = new FlooringOrdersService(view);
    }

    @Test
    public void displayOrderInformation() {
        // assertEquals(expectedOrder, actualOrder)

        FlooringOrder order = new FlooringOrder();
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
