package dev.rikthipranadhik.storemanagementsystembackend.mapper.inventory.impl;

import dev.rikthipranadhik.storemanagementsystembackend.dto.inventory.ItemDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory.Inventory;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory.Item;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.inventory.ItemMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemMapperImpl implements ItemMapper {
    @Override
    public Item fromDTO(ItemDTO itemDTO) {
        Inventory inventory = new Inventory();
        return new Item(
          itemDTO.id(),
                itemDTO.name(),
                itemDTO.manufactureDate(),
                itemDTO.price(),
                itemDTO.expirationDate(),
                itemDTO.discountPercentage(),
                inventory,// set later in service layer
                itemDTO.quantity()
        );
    }

    @Override
    public ItemDTO toDTO(Item item) {
        return new ItemDTO(
          item.getId(), item.getName(), item.getManufactureDate(), item.getPrice(), item.getExpirationDate(), item.getDiscountPercentage(), item.getInventory().getId(), item.getQuantity()
        );
    }
}
