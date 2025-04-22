package dev.rikthipranadhik.storemanagementsystembackend.entity.attendance;

import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Manager;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name= "Attendance")
@Getter
@Setter
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="attendance_id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name="verifier_id")
    private Employee verifier;

    @Column(name = "punch_in_time")
    private LocalDateTime punchInTime;

    @Column(name="punch_out_time")
    private LocalDateTime punchOutTime;

    @Column(name="is_verified")
    private Boolean isVerified;


    public Attendance(){

    }

    public Attendance(Long id, Employee employee, Employee verifier, LocalDateTime punchInTime, LocalDateTime punchOutTime, Boolean isVerified) {
        this.id = id;
        this.employee = employee;
        this.verifier = verifier;
        this.punchInTime = punchInTime;
        this.punchOutTime = punchOutTime;
        this.isVerified = isVerified;
    }
}
