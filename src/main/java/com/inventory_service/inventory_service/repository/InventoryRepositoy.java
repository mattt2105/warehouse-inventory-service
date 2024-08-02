package com.inventory_service.inventory_service.repository;

import com.inventory_service.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepositoy extends JpaRepository<Inventory,Long> {
}
