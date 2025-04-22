package dev.rikthipranadhik.storemanagementsystembackend.service.employee.impl;

import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.HourlyEmployee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Manager;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.SalariedEmployee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import dev.rikthipranadhik.storemanagementsystembackend.entity.user.User;
import dev.rikthipranadhik.storemanagementsystembackend.repository.attendance.AttendanceRepository;
import dev.rikthipranadhik.storemanagementsystembackend.repository.employee.EmployeeRepository;
import dev.rikthipranadhik.storemanagementsystembackend.repository.employee.HourlyEmployeeRepository;
import dev.rikthipranadhik.storemanagementsystembackend.repository.employee.ManagerRepository;
import dev.rikthipranadhik.storemanagementsystembackend.repository.employee.SalariedEmployeeRepository;
import dev.rikthipranadhik.storemanagementsystembackend.repository.store.StoreRepository;
import dev.rikthipranadhik.storemanagementsystembackend.repository.user.UserRepository;
import dev.rikthipranadhik.storemanagementsystembackend.service.employee.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

     private final EmployeeRepository employeeRepository;
     private final HourlyEmployeeRepository hourlyEmployeeRepository;
     private final SalariedEmployeeRepository salariedEmployeeRepository;
     private final StoreRepository storeRepository;
     private final ManagerRepository managerRepository;
     private final UserRepository userRepository;
     private final AttendanceRepository attendanceRepository;



    @Override
    public List<Employee> listAllEmployees(Long storeId) {
        return employeeRepository.findByStoreId(storeId);
    }

    @Override
    public SalariedEmployee createSalariedEmployee(SalariedEmployee salariedEmployee) {
         return salariedEmployeeRepository.save(salariedEmployee);
    }


    @Override
    public List<SalariedEmployee> listAllSalariedEmployees(Long storeId){
         return salariedEmployeeRepository.findByStoreId(storeId);
    }

    @Override
    public List<HourlyEmployee> listAllHourlyEmployees(Long storeId){
         return hourlyEmployeeRepository.findByStoreId(storeId);
    }

    @Override
    public HourlyEmployee createHourlyEmployee(HourlyEmployee hourlyEmployee) {
         return hourlyEmployeeRepository.save(hourlyEmployee);
    }

    @Override
    public Manager createManager(Manager manager) {
        return managerRepository.save(manager);
    }


    @Override
    public Employee createEmployee(Employee employee, Integer supervisorId, Long storeId) {
        if (employee.getId() != null){
            throw new IllegalArgumentException("Employee Already has an ID");
        }

        if (employee.getName() == null || employee.getName().isEmpty()){
            throw new IllegalArgumentException("Employee Name is Empty");
        }

        Store store;
        if (storeId != null){
            store = storeRepository.findById(storeId).orElse(null);
        }else{
            throw new  IllegalArgumentException("Store Id is Empty");
        }

        if (store == null){
            throw new  IllegalArgumentException("Store doesn't exist");
        }

        Employee supervisor = null;

        if (supervisorId != null){
            supervisor = employeeRepository.findById(supervisorId).orElse(null);
        }

        return (new Employee(
                null,
                employee.getName(),
                employee.getGender(),
                employee.getPhoneNumber(),
                employee.getDateOfBirth(),
                employee.getEmailAddress(),
                employee.getAddress(),
                supervisor,
                store
        ));
    }

    @Override
    public Employee getEmployeeById(Long storeId, Integer id){

         return employeeRepository.findById(id)
                 .orElseThrow(
                         ()->new IllegalArgumentException("Employee Not Found")
                 );
    }

    @Override
    public SalariedEmployee getSalariedEmployeeById(Integer id) {
        return salariedEmployeeRepository.findById(id).orElse(null);
    }

    @Override
    public HourlyEmployee getHourlyEmployeeById(Integer id) {
        return hourlyEmployeeRepository.findById(id).orElse(null);
    }

    @Override
    public HourlyEmployee updateHourlyEmployee(HourlyEmployee hourlyEmployee) {
        HourlyEmployee savedEmployee = hourlyEmployeeRepository.findById(hourlyEmployee.getId()).orElse(null);

        if (savedEmployee == null){
            throw new IllegalArgumentException("Employee Not Found");
        }

        savedEmployee.setName(hourlyEmployee.getName());
        savedEmployee.setGender(hourlyEmployee.getGender());
        savedEmployee.setPhoneNumber(hourlyEmployee.getPhoneNumber());
        savedEmployee.setDateOfBirth(hourlyEmployee.getDateOfBirth());
        savedEmployee.setEmailAddress(hourlyEmployee.getEmailAddress());
        User user = userRepository.findByEmployeeId(savedEmployee.getId()).orElse(null);
        if(user != null){
            user.setEmail(savedEmployee.getEmailAddress());
            userRepository.save(user);
        }
        savedEmployee.setAddress(hourlyEmployee.getAddress());
        savedEmployee.setPayScale(hourlyEmployee.getPayScale());

        return hourlyEmployeeRepository.save(savedEmployee);
    }

    @Override
    public SalariedEmployee updateSalariedEmployee(SalariedEmployee salariedEmployee) {
        SalariedEmployee savedEmployee = salariedEmployeeRepository.findById(salariedEmployee.getId()).orElse(null);

        if (savedEmployee == null){
            throw new IllegalArgumentException("Employee Not Found");
        }

        savedEmployee.setName(salariedEmployee.getName());
        savedEmployee.setGender(salariedEmployee.getGender());
        savedEmployee.setPhoneNumber(salariedEmployee.getPhoneNumber());
        savedEmployee.setDateOfBirth(salariedEmployee.getDateOfBirth());
        savedEmployee.setEmailAddress(salariedEmployee.getEmailAddress());
        User user = userRepository.findByEmployeeId(savedEmployee.getId()).orElse(null);
        if(user != null){
            user.setEmail(savedEmployee.getEmailAddress());
            userRepository.save(user);
        }
        savedEmployee.setAddress(salariedEmployee.getAddress());
        savedEmployee.setSalary(salariedEmployee.getSalary());

        return salariedEmployeeRepository.save(savedEmployee);
    }

    @Override
    public Manager updateManager(Manager manager) {
        Manager savedEmployee = managerRepository.findById(manager.getId()).orElse(null);

        if (savedEmployee == null){
            throw new IllegalArgumentException("Employee Not Found");
        }

        savedEmployee.setName(manager.getName());
        savedEmployee.setGender(manager.getGender());
        savedEmployee.setPhoneNumber(manager.getPhoneNumber());
        savedEmployee.setDateOfBirth(manager.getDateOfBirth());
        savedEmployee.setEmailAddress(manager.getEmailAddress());
        User user = userRepository.findByEmployeeId(savedEmployee.getId()).orElse(null);
        if(user != null){
            user.setEmail(savedEmployee.getEmailAddress());
            userRepository.save(user);
        }
        savedEmployee.setAddress(manager.getAddress());

        return managerRepository.save(savedEmployee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Integer employeeId) {
        userRepository.findByEmployeeId(employeeId).ifPresent(userRepository::delete);
        attendanceRepository.deleteByEmployeeId(employeeId);
        employeeRepository.deleteById(employeeId);
}

    @Override
    public Manager getManagerByStoreId(Long storeId) {
        return managerRepository.findManagerByStoreId(storeId);
    }

    public Employee getEmployeeByEmailAddress(String emailAddress){
         return employeeRepository.findByEmailAddress(emailAddress)
                 .orElseThrow(
                         () -> new IllegalArgumentException("Employee Not Found")
                 );
    }


}
