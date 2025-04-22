package dev.rikthipranadhik.storemanagementsystembackend.mapper.inventory;

import dev.rikthipranadhik.storemanagementsystembackend.dto.inventory.InventoryDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory.Inventory;

public interface InventoryMapper {
    Inventory fromDTO(InventoryDTO inventoryDTO);
    InventoryDTO toDTO(Inventory inventory);
}
