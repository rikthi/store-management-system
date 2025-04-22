package dev.rikthipranadhik.storemanagementsystembackend.dto.employee;

import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;

import java.time.LocalDate;

public record EmployeeDTO(
        Integer id,
        String name, String gender, String phoneNumber, LocalDate dateOfBirth, String emailAddress, String address, Integer supervisorId, Long storeId
) {

}
