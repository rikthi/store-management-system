package dev.rikthipranadhik.storemanagementsystembackend.mapper.employee.impl;

import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.EmployeeDTO;
import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.HourlyEmployeeDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.HourlyEmployee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.employee.HourlyEmployeeMapper;
import org.springframework.stereotype.Component;

@Component
public class HourlyEmployeeMapperImpl implements HourlyEmployeeMapper {


    @Override
    public HourlyEmployee fromDTO(HourlyEmployeeDTO hourlyEmployeeDTO) {
        EmployeeDTO employee = hourlyEmployeeDTO.employee();
        Store store = new Store();
        return new HourlyEmployee(
                employee.id(),
                employee.name(),
                employee.gender(),
                employee.phoneNumber(),
                employee.dateOfBirth(),
                employee.emailAddress(),
                employee.address(),
                null,
                store,
                hourlyEmployeeDTO.payScale()
        );

    }

    @Override
    public HourlyEmployeeDTO toDTO(HourlyEmployee hourlyEmployee) {
        Integer supervisorId = null;
        if(hourlyEmployee.getSupervisor() != null){
            supervisorId = hourlyEmployee.getSupervisor().getId();
        }
        EmployeeDTO employeeDTO = new EmployeeDTO(hourlyEmployee.getId(), hourlyEmployee.getName(), hourlyEmployee.getGender(), hourlyEmployee.getPhoneNumber(), hourlyEmployee.getDateOfBirth(), hourlyEmployee.getEmailAddress(),hourlyEmployee.getAddress(), supervisorId, hourlyEmployee.getStore().getId());
        return new HourlyEmployeeDTO(
                employeeDTO,
                hourlyEmployee.getPayScale()
        );
    }
}
