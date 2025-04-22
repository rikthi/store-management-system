package dev.rikthipranadhik.storemanagementsystembackend.mapper.employee.impl;


import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.EmployeeDTO;
import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.HourlyEmployeeDTO;
import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.SalariedEmployeeDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.HourlyEmployee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.SalariedEmployee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.employee.SalariedEmployeeMapper;
import org.springframework.stereotype.Component;

@Component
public class SalariedEmployeeMapperImpl implements SalariedEmployeeMapper {


    @Override
    public SalariedEmployee fromDTO(SalariedEmployeeDTO salariedEmployeeDTO) {
        EmployeeDTO employee = salariedEmployeeDTO.employee();
        Store store = new Store();
        return new SalariedEmployee(
                employee.id(),
                employee.name(),
                employee.gender(),
                employee.phoneNumber(),
                employee.dateOfBirth(),
                employee.emailAddress(),
                employee.address(),
                null,
                store,
                salariedEmployeeDTO.salary()
        );
    }

    @Override
    public SalariedEmployeeDTO toDTO(SalariedEmployee salariedEmployee) {
        Integer supervisorId = null;
        if(salariedEmployee.getSupervisor() != null){
            supervisorId = salariedEmployee.getSupervisor().getId();
        }
        EmployeeDTO employeeDTO = new EmployeeDTO(salariedEmployee.getId(), salariedEmployee.getName(), salariedEmployee.getGender(), salariedEmployee.getPhoneNumber(), salariedEmployee.getDateOfBirth(), salariedEmployee.getEmailAddress(),salariedEmployee.getAddress(), supervisorId, salariedEmployee.getStore().getId());
        return new SalariedEmployeeDTO(
                employeeDTO,
                salariedEmployee.getSalary()
        );
    }
}
