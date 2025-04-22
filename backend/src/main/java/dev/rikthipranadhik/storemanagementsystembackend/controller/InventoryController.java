package dev.rikthipranadhik.storemanagementsystembackend.controller;

import dev.rikthipranadhik.storemanagementsystembackend.dto.inventory.InventoryDTO;
import dev.rikthipranadhik.storemanagementsystembackend.dto.inventory.ItemDTO;
import dev.rikthipranadhik.storemanagementsystembackend.dto.stockReport.StockReportDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory.Inventory;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory.Item;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.inventory.InventoryMapper;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.inventory.ItemMapper;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.stockReport.StockReportMapper;
import dev.rikthipranadhik.storemanagementsystembackend.service.inventory.InventoryService;
import dev.rikthipranadhik.storemanagementsystembackend.service.stockReport.StockReportService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/{storeId}/inventory")
@AllArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    private final InventoryMapper inventoryMapper;
    private final ItemMapper itemMapper;
    private final StockReportMapper stockReportMapper;
    private final StockReportService stockReportService;

    @GetMapping("/listAllInventories")
    public ResponseEntity<List<InventoryDTO>> listAllInventories(@PathVariable("storeId") Long storeId) {
        return ResponseEntity.ok(inventoryService.listAllInventories(storeId)
                .stream()
                .map(inventoryMapper::toDTO)
                .toList()
        );
    }

    @GetMapping("/{inventoryId}/get")
    public ResponseEntity<InventoryDTO> getInventory(@PathVariable("storeId") Long storeId, @PathVariable("inventoryId") Long inventoryId) {
        return ResponseEntity.ok(inventoryMapper.toDTO(inventoryService.getInventoryById(inventoryId)));
    }

    @GetMapping("/{inventoryId}/listAllItems")
    public ResponseEntity<List<ItemDTO>> listAllItems(@PathVariable("inventoryId") Long inventoryId, @PathVariable String storeId) {
        Inventory inventory = inventoryService.getInventoryById(inventoryId);
        if(inventory == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(inventoryService.listAllItemsInInventory(inventory)
                .stream()
                .map(itemMapper::toDTO)
                .toList()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<InventoryDTO> createInventory(@PathVariable("storeId") Long storeId, @RequestBody InventoryDTO inventoryDTO) {
        Inventory inventory = inventoryService.createInventory(inventoryMapper.fromDTO(inventoryDTO), storeId);
        if (inventory == null) {

        }
        return ResponseEntity.ok(inventoryMapper.toDTO(inventory));
    }

    @PostMapping("/{inventoryId}/addItem")
    public ResponseEntity<ItemDTO> addItem(@PathVariable("storeId") Long s, @PathVariable("inventoryId") Long inventoryId, @RequestBody ItemDTO itemDTO) {
        return ResponseEntity.ok( itemMapper.toDTO(inventoryService.createItem(
                itemMapper.fromDTO(itemDTO), inventoryId
        )));
    }

    @GetMapping("items/{itemId}/get")
    public ResponseEntity<ItemDTO> getItem(@PathVariable("storeId") Long s, @PathVariable("itemId") Long itemId) {
        return ResponseEntity.ok(itemMapper.toDTO(inventoryService.getItemById(itemId)));
    }

    @PutMapping("/updateItem")
    public ResponseEntity<ItemDTO> updateItem(@RequestBody ItemDTO itemDTO, @PathVariable Long storeId){
        return ResponseEntity.ok(itemMapper.toDTO(inventoryService.updateItem(itemMapper.fromDTO(itemDTO), itemDTO.inventoryId())));
    }

    @PutMapping("/updateInventory")
    public ResponseEntity<InventoryDTO> updateInventory(@RequestBody InventoryDTO inventoryDTO, @PathVariable Long storeId) {
        return ResponseEntity.ok(inventoryMapper.toDTO(inventoryService.updateInventory(inventoryMapper.fromDTO(inventoryDTO))));
    }

    @DeleteMapping("items/{itemId}/delete")
    public ResponseEntity<String> deleteItem(@PathVariable("storeId") Long s, @PathVariable("itemId") Long itemId) {
        inventoryService.deleteItem(itemId);
        return ResponseEntity.ok("Item deleted");
    }

    @GetMapping("{inventoryId}/stockReport")
    public ResponseEntity<StockReportDTO> getStockReport(@PathVariable("storeId") Long s, @PathVariable("inventoryId") Long inventoryId) {
        return ResponseEntity.ok(stockReportMapper.toDTO(stockReportService.getStockReportByInventoryId(inventoryId)));
    }

    @GetMapping("{inventoryId}/items/expired")
    public ResponseEntity<List<ItemDTO>> getExpiredItems(@PathVariable("storeId") Long s, @PathVariable("inventoryId") Long inventoryId) {
        return ResponseEntity.ok(
                inventoryService.getExpiredItems(inventoryId)
                        .stream()
                        .map(itemMapper::toDTO)
                        .toList()
        );
    }

    @GetMapping("{inventoryId}/items/almostExpired")
    public ResponseEntity<List<ItemDTO>> getAlmostExpiredItems(@PathVariable("storeId") Long s, @PathVariable("inventoryId") Long inventoryId) {
        return ResponseEntity.ok(
                inventoryService.getAlmostExpiredItems(inventoryId)
                        .stream()
                        .map(itemMapper::toDTO)
                        .toList()
        );
    }
}
