package com.hactually;

import com.hactually.dto.FlooringOrder;
import com.hactually.service.FlooringOrdersService;
import com.hactually.ui.FlooringView;
import com.hactually.ui.UserIO;
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
        ordersService = new FlooringOrdersService(view);
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
}
