package dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory;

import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name="item")
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="item_id", nullable=false, unique=true)
    private Long id;

    @Column(name= "item_name", nullable=false)
    private String name;

    @Column(name= "manufacture_date")
    private LocalDate manufactureDate;

    @Column(name= "expiration_date")
    private LocalDate expirationDate;

    @Column(name= "price")
    private Float price;

    @Column(name= "discount_percentage")
    private Float discountPercentage;

    @Column(name="quantity")
    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="inventory_id", nullable=false)
    private Inventory inventory;

    public Item() {
    }

    public Item(Long id, String name, LocalDate manufactureDate, Float price, LocalDate expirationDate, Float discountPercentage, Inventory inventory, Long quantity) {
        this.id = id;
        this.name = name;
        this.manufactureDate = manufactureDate;
        this.price = price;
        this.expirationDate = expirationDate;
        this.discountPercentage = discountPercentage;
        this.inventory = inventory;
        this.quantity = quantity;
    }
}
