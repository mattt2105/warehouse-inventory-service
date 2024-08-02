package com.inventory_service.inventory_service.controller;


import com.inventory_service.inventory_service.dto.InventoryDTO;
import com.inventory_service.inventory_service.model.Inventory;
import com.inventory_service.inventory_service.service.InventoryService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventorys")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;


    @PostMapping("/create")
    public ResponseEntity<Inventory> addProduct(@RequestBody Inventory inventory) {
        Inventory savedInventory = inventoryService.addInventory(inventory);
        return new ResponseEntity<>(savedInventory, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Inventory> updateProduct(@PathVariable Long id, @RequestParam @NotNull @Min(0) int quantity){
        Inventory updateInventory = inventoryService.updateQuantityInventory(id, quantity);
        return new ResponseEntity<>(updateInventory, HttpStatus.CREATED);
    }

    @PutMapping("/update/qty/purchase/{id}")
    public ResponseEntity<Inventory> updateQtyPurchase(@PathVariable Long id, @RequestParam @NotNull @Min(0) int quantity){
        Inventory updateInventory = inventoryService.updateQuantityInventory(id, quantity);
        return new ResponseEntity<>(updateInventory, HttpStatus.CREATED);
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<InventoryDTO> getProductById(@PathVariable Long id) {
    InventoryDTO data = inventoryService.getProductById(id);
    return new ResponseEntity<>(data, HttpStatus.OK);
}
}
