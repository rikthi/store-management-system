package dev.rikthipranadhik.storemanagementsystembackend.dto.inventory;

import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;

public record InventoryDTO(Long id, String category, Long currentStockLevel, Long minimumStockLevel, Long maximumStockLevel, Long storeId) {
}
