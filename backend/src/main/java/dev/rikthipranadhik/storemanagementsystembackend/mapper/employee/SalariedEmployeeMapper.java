package dev.rikthipranadhik.storemanagementsystembackend.mapper.employee;

import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.SalariedEmployeeDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.SalariedEmployee;

public interface SalariedEmployeeMapper {

    SalariedEmployee fromDTO(SalariedEmployeeDTO salariedEmployeeDTO);
    SalariedEmployeeDTO toDTO(SalariedEmployee salariedEmployee);
}
