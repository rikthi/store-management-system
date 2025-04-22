package dev.rikthipranadhik.storemanagementsystembackend.mapper.inventory;

import dev.rikthipranadhik.storemanagementsystembackend.dto.inventory.ItemDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory.Item;

public interface ItemMapper {
    Item fromDTO(ItemDTO itemDTO);
    ItemDTO toDTO(Item item);
}
