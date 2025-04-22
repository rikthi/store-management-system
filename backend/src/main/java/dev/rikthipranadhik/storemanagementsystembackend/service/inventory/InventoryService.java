package dev.rikthipranadhik.storemanagementsystembackend.service.inventory;

import dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory.Inventory;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory.Item;

import java.util.List;

public interface InventoryService {
    List<Inventory> listAllInventories(Long storeId);
    Inventory createInventory(Inventory inventory,  Long storeId);
    Inventory updateInventory(Inventory inventory);
    void deleteInventory(Inventory inventory);
    Long calculateCurrentStockLevel(Inventory inventory);
    Inventory getInventoryById(Long inventoryId);
    List<Item> listAllItemsInInventory(Inventory inventory);
    Item addItemToInventory(Inventory inventory, Item item);
    Item createItem(Item item, Long inventoryId);
    Item updateItem(Item item, Long inventoryId);
    void deleteItem(Long itemId);
    Item getItemById(Long itemId);
    List<Item> getExpiredItems(Long inventoryId);

    List<Item> getAlmostExpiredItems(Long inventoryId);
}
