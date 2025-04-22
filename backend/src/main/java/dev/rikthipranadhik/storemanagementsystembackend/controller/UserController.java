package dev.rikthipranadhik.storemanagementsystembackend.controller;


import dev.rikthipranadhik.storemanagementsystembackend.dto.RetOnCall;
import dev.rikthipranadhik.storemanagementsystembackend.dto.user.UserDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.HourlyEmployee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Manager;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.SalariedEmployee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.user.User;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.user.UserMapper;
import dev.rikthipranadhik.storemanagementsystembackend.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(path="/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final String MANAGER_ROLE = "MANAGER";
    private final String HOURLY_EMPLOYEE_ROLE = "HOURLY_EMPLOYEE";
    private final String SALARIED_EMPLOYEE_ROLE = "SALARIED_EMPLOYEE";


    public  UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @PostMapping("/login")
    public ResponseEntity<RetOnCall> login(@RequestBody UserDTO userDTO) {
        if(userDTO.email().isEmpty() || userDTO.password().isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        User user = userService.login(userDTO.email(), userDTO.password());
        if(user == null){
            return null;
        }

        String role ="";
        if (user.getEmployee() instanceof HourlyEmployee){
            role = HOURLY_EMPLOYEE_ROLE;
        } else if (user.getEmployee() instanceof SalariedEmployee) {
            role = SALARIED_EMPLOYEE_ROLE;
        } else if (user.getEmployee() instanceof Manager) {
            role = MANAGER_ROLE;
        }

        RetOnCall retOnCall = new RetOnCall(
                role,
                user.getEmployee().getStore().getId()
        );
        return ResponseEntity.ok(retOnCall);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        if(userDTO.email().isEmpty() || userDTO.password().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
         User user = userService.createUser(userMapper.fromDTO(userDTO), userDTO.employeeId());
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @GetMapping("{employeeId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("employeeId") Integer employeeId) {
        return ResponseEntity.ok(userMapper.toDTO(userService.getUserByEmployeeId(employeeId)));
    }
}
