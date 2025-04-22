package dev.rikthipranadhik.storemanagementsystembackend.mapper.stockReport;

import dev.rikthipranadhik.storemanagementsystembackend.dto.stockReport.StockReportDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.stockReport.StockReport;

public interface StockReportMapper {
    StockReport fromDTO(StockReportDTO stockReportDTO);
    StockReportDTO toDTO(StockReport stockReport);
}
