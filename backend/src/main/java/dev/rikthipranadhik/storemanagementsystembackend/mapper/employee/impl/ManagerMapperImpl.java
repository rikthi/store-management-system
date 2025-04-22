package dev.rikthipranadhik.storemanagementsystembackend.mapper.employee.impl;

import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.EmployeeDTO;
import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.ManagerDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Manager;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.SalariedEmployee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.employee.EmployeeMapper;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.employee.ManagerMapper;
import org.springframework.stereotype.Component;

@Component
public class ManagerMapperImpl implements ManagerMapper {

    @Override
    public Manager fromDTO(ManagerDTO managerDTO) {
        EmployeeDTO employee = managerDTO.employee();
        Store store = new Store();
        return new Manager(
                employee.id(),
                employee.name(),
                employee.gender(),
                employee.phoneNumber(),
                employee.dateOfBirth(),
                employee.emailAddress(),
                employee.address(),
                null,
                store
        );
    }

    @Override
    public ManagerDTO toDTO(Manager manager) {
        EmployeeDTO employeeDTO = new EmployeeDTO(manager.getId(), manager.getName(), manager.getGender(), manager.getPhoneNumber(), manager.getDateOfBirth(), manager.getEmailAddress(),manager.getAddress(), null, manager.getStore().getId());
        return new ManagerDTO(
                employeeDTO
        );
    }


}
