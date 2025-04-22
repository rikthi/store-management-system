package dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory;

import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="Inventory")
@Getter
@Setter
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name= "inventory_id", nullable = false, unique = true)
    private Long id;

    @Column(name="category", nullable = false)
    private String category;

    @Column(name="current_stock_level")
    private Long currentStockLevel;

    @Column(name="minimum_stock_level")
    private Long minimumStockLevel;

    @Column(name="maximum_stock_level")
    private Long maximumStockLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name= "store_id", nullable=false)
    private Store store;

    public Inventory() {
    }

    public Inventory(Long id, String category, Long currentStockLevel, Long minimumStockLevel, Long maximumStockLevel, Store store) {
        this.id = id;
        this.category = category;
        this.currentStockLevel = currentStockLevel;
        this.minimumStockLevel = minimumStockLevel;
        this.maximumStockLevel = maximumStockLevel;
        this.store = store;
    }
}
