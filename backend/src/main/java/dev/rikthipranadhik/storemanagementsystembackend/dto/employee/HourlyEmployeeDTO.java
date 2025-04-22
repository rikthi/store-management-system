package dev.rikthipranadhik.storemanagementsystembackend.dto.employee;

import java.math.BigDecimal;

public record HourlyEmployeeDTO(EmployeeDTO employee, BigDecimal payScale) {
}
