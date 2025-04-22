package dev.rikthipranadhik.storemanagementsystembackend.service.employee;


import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.HourlyEmployee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Manager;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.SalariedEmployee;

import java.util.List;

public interface EmployeeService {
    List<Employee> listAllEmployees(Long storeId);
    Employee createEmployee(Employee employee, Integer supervisorId, Long storeId);

    SalariedEmployee createSalariedEmployee(SalariedEmployee salariedEmployee);

    List<SalariedEmployee> listAllSalariedEmployees(Long storeId);

    List<HourlyEmployee> listAllHourlyEmployees(Long storeId);

    HourlyEmployee createHourlyEmployee(HourlyEmployee hourlyEmployee);

    Manager createManager(Manager manager);
    Employee getEmployeeById(Long storeId, Integer id);

    SalariedEmployee getSalariedEmployeeById(Integer id);
    HourlyEmployee getHourlyEmployeeById(Integer id);

    HourlyEmployee updateHourlyEmployee(HourlyEmployee hourlyEmployee);
    SalariedEmployee updateSalariedEmployee(SalariedEmployee salariedEmployee);

    Manager updateManager(Manager manager);

    void deleteEmployee(Integer employeeId);

    Manager getManagerByStoreId(Long storeId);
}
