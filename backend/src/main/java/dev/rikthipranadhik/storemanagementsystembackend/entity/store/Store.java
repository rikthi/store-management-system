package dev.rikthipranadhik.storemanagementsystembackend.entity.store;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Store")
@Getter
@Setter
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name= "store_id", updatable = false, nullable = false)
    private Long id;

    @Column(name= "name", nullable = false)
    private String name;

    @Column(name="address")
    private String address;

    public Store(){

    }

    public Store(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

}
