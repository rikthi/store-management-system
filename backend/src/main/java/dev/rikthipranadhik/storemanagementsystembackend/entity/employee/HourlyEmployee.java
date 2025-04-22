package dev.rikthipranadhik.storemanagementsystembackend.entity.employee;

import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name= "hourly_employee")
@Getter
@Setter
public class HourlyEmployee extends Employee {


    @Column(name = "Pay_Scale", precision = 10, scale = 2)
    private BigDecimal payScale;

    public HourlyEmployee() {
    }

    public HourlyEmployee(Integer id, String name, String gender, String phoneNumber, LocalDate dateOfBirth, String emailAddress, String address, Employee supervisor, Store store, BigDecimal payScale) {
        super(id, name, gender, phoneNumber, dateOfBirth, emailAddress, address, supervisor, store);
        this.payScale = payScale;
    }
}
