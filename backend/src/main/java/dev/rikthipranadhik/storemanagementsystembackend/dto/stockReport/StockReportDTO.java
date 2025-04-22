package dev.rikthipranadhik.storemanagementsystembackend.dto.stockReport;

import dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory.Inventory;

import java.time.LocalDateTime;

public record StockReportDTO(Long id, Long inventoryId, Boolean isUnderMinimum, Boolean isOverMaximum) {
}
