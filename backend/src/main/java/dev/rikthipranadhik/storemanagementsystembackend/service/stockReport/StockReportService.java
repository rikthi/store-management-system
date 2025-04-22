package dev.rikthipranadhik.storemanagementsystembackend.service.stockReport;

import dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory.Inventory;
import dev.rikthipranadhik.storemanagementsystembackend.entity.stockReport.StockReport;

import java.util.List;

public interface StockReportService {
    StockReport createStockReport(Long inventoryId);
    StockReport getStockReportByInventoryId(Long inventoryId);
    void deleteStockReport(StockReport stockReport);
    void deleteStockReportByInventoryId(Long inventoryId);
    StockReport updateStockReport(StockReport stockReport);
}
