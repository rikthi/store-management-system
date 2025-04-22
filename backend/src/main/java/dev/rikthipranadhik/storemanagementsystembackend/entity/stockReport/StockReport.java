package dev.rikthipranadhik.storemanagementsystembackend.entity.stockReport;

import dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory.Inventory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="stock_report")
@Getter
@Setter
public class StockReport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name= "stock_report_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="inventory_id", nullable = false)
    private Inventory inventory;

    @Column(name= "is_under_minimum")
    private Boolean isUnderMinimum;

    @Column(name= "is_over_maximum")
    private Boolean isOverMaximum;

    public StockReport(){
    }

    public StockReport(Long id, Inventory inventory, Boolean isUnderMinimum, Boolean isOverMaximum) {
        this.id = id;
        this.inventory = inventory;
        this.isUnderMinimum = isUnderMinimum;
        this.isOverMaximum = isOverMaximum;
    }
}
