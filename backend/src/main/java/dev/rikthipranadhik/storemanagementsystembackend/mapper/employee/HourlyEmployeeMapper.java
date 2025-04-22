package dev.rikthipranadhik.storemanagementsystembackend.mapper.employee;

import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.HourlyEmployeeDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.HourlyEmployee;

public interface HourlyEmployeeMapper {
    HourlyEmployee fromDTO(HourlyEmployeeDTO hourlyEmployeeDTO);
    HourlyEmployeeDTO toDTO(HourlyEmployee hourlyEmployee);
}
