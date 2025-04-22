package dev.rikthipranadhik.storemanagementsystembackend.mapper.stockReport.impl;

import dev.rikthipranadhik.storemanagementsystembackend.dto.stockReport.StockReportDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory.Inventory;
import dev.rikthipranadhik.storemanagementsystembackend.entity.stockReport.StockReport;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.stockReport.StockReportMapper;
import org.springframework.stereotype.Component;

@Component
public class StockReportMapperImpl implements StockReportMapper {
    @Override
    public StockReport fromDTO(StockReportDTO stockReportDTO) {
        Inventory inventory = new Inventory();
        return new StockReport(
                stockReportDTO.id(),
                inventory, // set actual inventory later in service layer
                stockReportDTO.isUnderMinimum(),
                stockReportDTO.isOverMaximum()
        );
    }

    @Override
    public StockReportDTO toDTO(StockReport stockReport) {
        return new StockReportDTO(
                stockReport.getId(),
                stockReport.getInventory().getId(),
                stockReport.getIsUnderMinimum(),
                stockReport.getIsOverMaximum()
        );

    }
}
