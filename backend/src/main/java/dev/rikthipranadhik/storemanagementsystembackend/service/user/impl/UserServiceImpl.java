package dev.rikthipranadhik.storemanagementsystembackend.service.user.impl;

import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.HourlyEmployee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Manager;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.SalariedEmployee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.user.User;
import dev.rikthipranadhik.storemanagementsystembackend.repository.employee.EmployeeRepository;
import dev.rikthipranadhik.storemanagementsystembackend.repository.user.UserRepository;
import dev.rikthipranadhik.storemanagementsystembackend.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    public UserServiceImpl(UserRepository userRepository, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public User createUser(User user, Integer employeeId) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User ID has to be null to create a new user");
        }

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new IllegalArgumentException("User email already exists");
        }
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if  (employee == null) {
            throw new IllegalArgumentException("Employee with id " + employeeId + " does not exist");
        }
        if(!Objects.equals(employee.getEmailAddress(), user.getEmail())){
            throw new IllegalArgumentException("Email does not match with employee email");
        }
        user.setEmployee(employee);
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmployeeId(Integer employeeId) {
        return userRepository.findByEmployeeId(employeeId).orElse(null);
    }

    @Override
    public String getEmployeeType(User user){
        Employee employee = getEmployeeByUser(user);
        if (employee instanceof Manager) {
            return "MANAGER";
        }
        else if (employee instanceof HourlyEmployee) {
            return "HOURLY_EMPLOYEE";
        }
        else if (employee instanceof SalariedEmployee) {
            return "SALARIED_EMPLOYEE";
        }
        else{
            return "GENERAL_EMPLOYEE";
        }


        // TODO: Add more types of users
    }

    @Override
    public Employee getEmployeeByUser(User user) {
        if (user.getEmployee() == null){
            throw new IllegalArgumentException("User does not have an assigned employee");
        }
        return employeeRepository.findById(user.getEmployee().getId()).orElse(null);
    }

    @Override
    public User changeEmail(User user, String email) {
        return null;
    }

    /**
     *
     * @param email email
     * @param password password
     * @return null if password or email didn't match. User otherwise
     */
    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public User changePassword(User user, String password) {
        return null;
    }
}
