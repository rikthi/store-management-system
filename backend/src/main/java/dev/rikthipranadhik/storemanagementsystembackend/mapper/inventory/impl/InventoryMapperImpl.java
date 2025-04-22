package dev.rikthipranadhik.storemanagementsystembackend.mapper.inventory.impl;

import dev.rikthipranadhik.storemanagementsystembackend.dto.inventory.InventoryDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory.Inventory;
import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.inventory.InventoryMapper;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapperImpl implements InventoryMapper {
    @Override
    public Inventory fromDTO(InventoryDTO inventoryDTO) {
        Store store = new Store();
        return new Inventory(
                inventoryDTO.id(),
                inventoryDTO.category(),
                inventoryDTO.currentStockLevel(),
                inventoryDTO.minimumStockLevel(),
                inventoryDTO.maximumStockLevel(),
                store //Set store in service layer
        );
    }

    @Override
    public InventoryDTO toDTO(Inventory inventory) {
        return new InventoryDTO(
                inventory.getId(),
                inventory.getCategory(),
                inventory.getCurrentStockLevel(),
                inventory.getMinimumStockLevel(),
                inventory.getMaximumStockLevel(),
                inventory.getStore().getId()
        );
    }
}
