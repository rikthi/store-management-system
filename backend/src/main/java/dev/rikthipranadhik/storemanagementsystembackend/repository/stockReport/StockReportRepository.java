package dev.rikthipranadhik.storemanagementsystembackend.repository.stockReport;

import dev.rikthipranadhik.storemanagementsystembackend.entity.stockReport.StockReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockReportRepository extends JpaRepository<StockReport,Long> {
    Optional<StockReport> findByInventoryId(Long inventoryId);
    void deleteByInventoryId(Long inventoryId);
}
