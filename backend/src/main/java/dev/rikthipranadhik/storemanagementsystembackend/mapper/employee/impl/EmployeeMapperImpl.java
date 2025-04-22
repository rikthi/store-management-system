package dev.rikthipranadhik.storemanagementsystembackend.mapper.employee.impl;

import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.EmployeeDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.employee.EmployeeMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapperImpl implements EmployeeMapper {


    @Override
    public Employee fromDTO(EmployeeDTO employeeDTO) {
        if  (employeeDTO == null) {
            return null;
        }
        Store store = new Store();
        return new Employee(
                employeeDTO.id(),
                employeeDTO.name(),
                employeeDTO.gender(),
                employeeDTO.phoneNumber(),
                employeeDTO.dateOfBirth(),
                employeeDTO.emailAddress(),
                employeeDTO.address(),
                null,
                store   // new random store set for now. Will be changed in employee service

//              Supervisor is set later in the service
        );
    }

    @Override
    public EmployeeDTO toDTO(Employee employee) {
        if  (employee == null) {
            return null;
        }

        Integer supervisorId = null;
        if (employee.getSupervisor() != null) {
            supervisorId = employee.getSupervisor().getId();
        }

        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getGender(),
                employee.getPhoneNumber(),
                employee.getDateOfBirth(),
                employee.getEmailAddress(),
                employee.getAddress(),
                supervisorId,
                employee.getStore().getId()
        );
    }

}
