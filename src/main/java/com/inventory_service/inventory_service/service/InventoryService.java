package com.inventory_service.inventory_service.service;

import com.inventory_service.inventory_service.dto.InventoryDTO;
import com.inventory_service.inventory_service.model.Inventory;
import com.inventory_service.inventory_service.repository.InventoryRepositoy;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    InventoryRepositoy inventoryRepositoy;


    public Inventory addInventory(Inventory inventory) {
        validateInventory(inventory);
        return inventoryRepositoy.save(inventory);
    }

    public Inventory updateQuantityInventory(Long id, int quantity) {
        Inventory inventory = getInventoryById(id);
        inventory.setQuantity(inventory.getQuantity() + quantity);
        return inventoryRepositoy.save(inventory);
    }

    public Inventory updateQuantityPurchase(Long id, int quantity) {
        Inventory inventory = getInventoryById(id);
        inventory.setQuantity(inventory.getQuantity() - quantity);
        return inventoryRepositoy.save(inventory);
    }

    private void validateInventory(Inventory inventory) {
        if (inventory.getName() == null || inventory.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (inventory.getPrice() == null ) {
            throw new IllegalArgumentException("Price is required");
        }
        if (inventory.getQuantity() == null) {
            throw new IllegalArgumentException("Quantity is required");
        }
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepositoy.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    public InventoryDTO getProductById(Long id) {
        Inventory inventory = inventoryRepositoy.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return convertToDTO(inventory);
    }

    private InventoryDTO convertToDTO(Inventory inventory) {
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setId(inventory.getId());
        inventoryDTO.setName(inventory.getName());
        inventoryDTO.setPrice(inventory.getPrice());
        inventoryDTO.setQuantity(inventory.getQuantity());
        return inventoryDTO;
    }
}
