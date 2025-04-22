package dev.rikthipranadhik.storemanagementsystembackend.entity.Customer;


import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="customer")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="customer_id", nullable = false, unique = true)
    private Long id;

    @Column(name="customer_name")
    private String name;

    @Column(name="email", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name="membership_type")
    private Membership membershipType;

    @JoinColumn(name= "store_id", nullable=false)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Store store;


    public Customer(){

    }

    public Customer(Long id, String name, String email, Membership membershipType, Store store) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.membershipType = membershipType;
        this.store = store;
    }
}
