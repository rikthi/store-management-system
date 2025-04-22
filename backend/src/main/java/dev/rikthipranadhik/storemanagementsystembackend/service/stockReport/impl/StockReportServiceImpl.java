package dev.rikthipranadhik.storemanagementsystembackend.service.stockReport.impl;

import dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory.Inventory;
import dev.rikthipranadhik.storemanagementsystembackend.entity.stockReport.StockReport;
import dev.rikthipranadhik.storemanagementsystembackend.repository.inventory.InventoryRepository;
import dev.rikthipranadhik.storemanagementsystembackend.repository.stockReport.StockReportRepository;
import dev.rikthipranadhik.storemanagementsystembackend.service.stockReport.StockReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StockReportServiceImpl implements StockReportService {

    private final StockReportRepository stockReportRepository;
    private final InventoryRepository inventoryRepository;


    @Override
    public StockReport createStockReport(Long inventoryId) {

        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(()->new IllegalArgumentException("Inventory ID not found"));
        StockReport stockReport = new StockReport();
        stockReport.setInventory(inventory);


        // Setting values to false initially
        return setStockReportValues(stockReport, inventory);
    }

    @Override
    public StockReport getStockReportByInventoryId(Long inventoryId) {
        StockReport stockReport = stockReportRepository.findByInventoryId(inventoryId).orElse(null);
        if(stockReport == null){
            createStockReport(inventoryId);
        }else{
            updateStockReport(stockReport);
        }
        return stockReportRepository.findByInventoryId(inventoryId).orElseThrow(()->new IllegalArgumentException("Stock Report not found"));
    }

    @Override
    public void deleteStockReport(StockReport stockReport) {

    }

    @Override
    public void deleteStockReportByInventoryId(Long inventoryId) {

    }

    @Override
    public StockReport updateStockReport(StockReport stockReport) {
        Inventory inventory = inventoryRepository.findById(stockReport.getInventory().getId()).orElseThrow(()->new IllegalArgumentException("Inventory ID not found"));

        return setStockReportValues(stockReport, inventory);
    }

    private StockReport setStockReportValues(StockReport stockReport, Inventory inventory) {
        stockReport.setIsUnderMinimum(false);
        stockReport.setIsOverMaximum(false);

        if(inventory.getCurrentStockLevel() < inventory.getMinimumStockLevel()){
            stockReport.setIsUnderMinimum(true);
        } else if (inventory.getCurrentStockLevel() > inventory.getMaximumStockLevel()) {
            stockReport.setIsOverMaximum(true);
        }

        return stockReportRepository.save(stockReport);
    }
}
