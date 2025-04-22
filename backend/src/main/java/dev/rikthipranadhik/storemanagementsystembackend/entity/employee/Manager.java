package dev.rikthipranadhik.storemanagementsystembackend.entity.employee;

import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="manager")
@Getter
@Setter
public class Manager extends Employee {

    public Manager() {

    }

    public Manager(Integer id, String name, String gender, String phoneNumber, LocalDate dateOfBirth, String emailAddress, String address, Employee supervisor, Store store){
        super(id, name, gender, phoneNumber, dateOfBirth, emailAddress, address, supervisor, store);
    }

}
