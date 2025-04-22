package dev.rikthipranadhik.storemanagementsystembackend.entity.Customer;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="receipt")
@Getter
@Setter
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="receipt_id", nullable = false, unique = true)
    private Long id;


    @Column(name="card_no")
    private String cardNo;

    @Column(name="date_of_transaction")
    private LocalDate dateOfTransaction;

    @Column(name="total_amount")
    private BigDecimal totalAmount;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="customer_id", nullable = false)
    private Customer customer;


    public Receipt(){

    }

    public Receipt(Long id, LocalDate dateOfTransaction, String cardNo, BigDecimal totalAmount, Customer customer) {
        this.id = id;
        this.dateOfTransaction = dateOfTransaction;
        this.cardNo = cardNo;
        this.totalAmount = totalAmount;
        this.customer = customer;
    }
}
