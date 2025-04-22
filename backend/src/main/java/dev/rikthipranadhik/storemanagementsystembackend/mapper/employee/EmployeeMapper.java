package dev.rikthipranadhik.storemanagementsystembackend.mapper.employee;

import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.EmployeeDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;

public interface EmployeeMapper {

    Employee fromDTO(EmployeeDTO employeeDTO);
    EmployeeDTO toDTO(Employee employee);
}
