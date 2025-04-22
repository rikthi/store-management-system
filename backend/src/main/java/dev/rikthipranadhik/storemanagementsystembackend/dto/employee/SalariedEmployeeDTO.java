package dev.rikthipranadhik.storemanagementsystembackend.dto.employee;

import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;

import java.math.BigDecimal;

public record SalariedEmployeeDTO(EmployeeDTO employee, BigDecimal salary) {
}
