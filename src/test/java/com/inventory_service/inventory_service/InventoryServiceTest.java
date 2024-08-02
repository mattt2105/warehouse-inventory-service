package com.inventory_service.inventory_service;

import com.inventory_service.inventory_service.dto.InventoryDTO;
import com.inventory_service.inventory_service.model.Inventory;
import com.inventory_service.inventory_service.repository.InventoryRepositoy;
import com.inventory_service.inventory_service.service.InventoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class InventoryServiceTest {

    @Mock
    private InventoryRepositoy inventoryRepositoy;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    public void addInventory_Success(){
        Inventory inventory = createInventory();
        Mockito.when(inventoryRepositoy.save(Mockito.any(Inventory.class))).thenReturn(inventory);
        Inventory savedInventory = inventoryService.addInventory(inventory);
        assertInventory(savedInventory);
    }

    @Test
    public void updateInventory_Success(){
        Inventory inventory = createInventory();
        Mockito.when(inventoryRepositoy.findById(1L)).thenReturn(Optional.of(inventory));
        Mockito.when(inventoryRepositoy.save(Mockito.any(Inventory.class))).thenReturn(inventory);
        Inventory updateInventory = inventoryService.updateQuantityInventory(1L, 5);
        Assertions.assertNotNull(updateInventory);
        Assertions.assertEquals(15, updateInventory.getQuantity());
    }

    private Inventory createInventory(){
        Inventory inventory = new Inventory();
        inventory.setName("testName");
        inventory.setPrice(new BigDecimal(70000));
        inventory.setQuantity(10);
        return inventory;
    }

    private void assertInventory(Inventory inventory){
        Assertions.assertNotNull(inventory);
        Assertions.assertEquals("testName", inventory.getName());
    }

    @Test
    public void getProductById_Success() {
        Inventory inventory = createInventory();
        Mockito.when(inventoryRepositoy.findById(1L)).thenReturn(Optional.of(inventory));

        InventoryDTO foundInventory = inventoryService.getProductById(1L);

        Assertions.assertNotNull(foundInventory);
        Assertions.assertEquals("testName", foundInventory.getName());
        Assertions.assertEquals(new BigDecimal(70000), foundInventory.getPrice());
        Assertions.assertEquals(10, foundInventory.getQuantity());
    }

    @Test
    public void getProductById_NotFound() {
        Mockito.when(inventoryRepositoy.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            inventoryService.getProductById(1L);
        });
    }
}
