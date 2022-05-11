package org.acme.freezerms;

import org.acme.freezerms.domain.Freezer;
import org.acme.freezerms.domain.FreezerService;
import org.acme.freezerms.infra.api.dto.OrderDTO;
import org.acme.freezerms.infra.repo.FreezerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestReeferAllocation {
   static  FreezerRepository repo;
   static FreezerService service;

    @BeforeAll
    public static void setup() throws Exception{
        repo = new FreezerRepository();
        service = new FreezerService(repo);
    }

    @Test
    public void shouldHaveOneMatch() {
        OrderDTO order = new OrderDTO("P01", 10, "Shanghai", "San Francisco");
        OrderDTO newOrder = service.computeBestFreezerToShip(null, order);
        Assertions.assertTrue(newOrder.containerIDs.contains("C01"));
    }

    @Test
    public void shouldHaveNoMatchBecauseOfLocation() {
        OrderDTO order = new OrderDTO("P01", 10, "Shanghai", "Rio");
        OrderDTO newOrder = service.computeBestFreezerToShip(null, order);
        Assertions.assertTrue(newOrder.containerIDs.length() == 0);
    }

    @Test
    public void shouldHaveNoMatchBecauseOfQuantity() {
        OrderDTO order = new OrderDTO("P01", 120, "Shanghai", "San Francisco");
        OrderDTO newOrder = service.computeBestFreezerToShip(null, order);
        Assertions.assertTrue(newOrder.containerIDs.length() == 0);
    }

    @Test
    public void compensateOrder() {
        OrderDTO order = new OrderDTO("P01", 80, "Shanghai", "San Francisco");
        OrderDTO newOrder = service.computeBestFreezerToShip("TXID_1", order);
        System.out.println(newOrder.containerIDs);
        Assertions.assertTrue(newOrder.containerIDs.contains("C01"));
        Assertions.assertEquals(Freezer.ALLOCATED,service.getReeferById("C01").status);
        Assertions.assertEquals(20,service.getReeferById("C01").currentFreeCapacity);
        newOrder=service.compensateFreezerOrder("TXID_1", newOrder);
        Assertions.assertTrue(newOrder.containerIDs.length() == 0);
        Assertions.assertEquals(Freezer.FREE,service.getReeferById("C01").status);
        Assertions.assertEquals(100,service.getReeferById("C01").currentFreeCapacity);
    }
}
