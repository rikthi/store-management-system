package dev.rikthipranadhik.storemanagementsystembackend.entity.employee;


import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name= "employee")
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name= "employee_id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Lob
    @Column(name = "Gender", columnDefinition = "ENUM('Male', 'Female', 'Other')")
    private String gender;

    @Column(name = "Phone_Number", length = 20)
    private String phoneNumber;

    @Column(name = "Date_of_Birth")
    private LocalDate dateOfBirth;

    @Column(name = "Email_Address")
    private String emailAddress;

    @Lob
    @Column(name = "Address", columnDefinition = "TEXT")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id")
    private Employee supervisor;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="store_id", nullable=false)
    private Store store;


    public Employee() {
    }

    public Employee(Integer id, String name, String gender, String phoneNumber, LocalDate dateOfBirth, String emailAddress, String address, Employee supervisor,  Store store) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.emailAddress = emailAddress;
        this.address = address;
        this.supervisor = supervisor;
        this.store = store;

    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", emailAddress='" + emailAddress + '\'' +
                ", address='" + address + '\'' +
                ", supervisor=" + supervisor +
                '}';
    }


}
