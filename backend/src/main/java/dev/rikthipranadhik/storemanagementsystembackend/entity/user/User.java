package dev.rikthipranadhik.storemanagementsystembackend.entity.user;

import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Entity
@Table(name="user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="user_id")
    private Long id;

    @Column(name= "email", nullable = false, unique = true)
    private String email;

    @Column(name= "password", nullable = false)
    private String password;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="employee_id")
    private Employee employee;


    public User (){

    }

    public User(Long id, String email, String password, Employee employee){
        this.id = id;
        this.password = password;
        this.email = email;
        this.employee = employee;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }




}
