package dev.rikthipranadhik.storemanagementsystembackend.service.user;

import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.user.User;

public interface UserService {
    User createUser(User user, Integer employeeId);
    User getUserByEmployeeId(Integer employeeId);
    Employee getEmployeeByUser(User user);
    User changeEmail(User user, String email);
    User changePassword(User user, String password);
    User login(String email, String password);
    String getEmployeeType(User user);
}
