package dev.rikthipranadhik.storemanagementsystembackend.controller;

import dev.rikthipranadhik.storemanagementsystembackend.dto.Params;
import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.EmployeeDTO;
import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.HourlyEmployeeDTO;
import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.ManagerDTO;
import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.SalariedEmployeeDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.HourlyEmployee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Manager;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.SalariedEmployee;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.employee.EmployeeMapper;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.employee.HourlyEmployeeMapper;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.employee.ManagerMapper;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.employee.SalariedEmployeeMapper;
import dev.rikthipranadhik.storemanagementsystembackend.service.employee.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "{storeId}/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;
    private final HourlyEmployeeMapper hourlyEmployeeMapper;
    private final SalariedEmployeeMapper salariedEmployeeMapper;
    private final ManagerMapper managerMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper, HourlyEmployeeMapper hourlyEmployeeMapper, SalariedEmployeeMapper salariedEmployeeMapper, ManagerMapper managerMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
        this.hourlyEmployeeMapper = hourlyEmployeeMapper;
        this.salariedEmployeeMapper = salariedEmployeeMapper;
        this.managerMapper = managerMapper;
    }

    @PostMapping("/create/hourlyEmployee")
    public ResponseEntity<HourlyEmployeeDTO> createHourlyEmployee(@RequestBody HourlyEmployeeDTO hourlyEmployeeDTO, @PathVariable("storeId") Long storeId) {
        EmployeeDTO employeeDTO = hourlyEmployeeDTO.employee();
        Employee employee = createEmployee(employeeDTO, storeId);
        HourlyEmployee hourlyEmployee = new HourlyEmployee(
                employee.getId(),
                employee.getName(),
                employee.getGender(),
                employee.getPhoneNumber(),
                employee.getDateOfBirth(),
                employee.getEmailAddress(),
                employee.getAddress(),
                employee.getSupervisor(),
                employee.getStore(),
                hourlyEmployeeDTO.payScale()
        );

        return ResponseEntity.ok(hourlyEmployeeMapper.toDTO(employeeService.createHourlyEmployee(hourlyEmployee)));
    }

    @GetMapping("")
    public List<EmployeeDTO> listAllEmployees(@PathVariable("storeId") Long storeId) {
        return employeeService.listAllEmployees(storeId)
                .stream()
                .map(employeeMapper::toDTO)
                .toList();
    }

    public List<HourlyEmployeeDTO> listAllHourlyEmployees(@PathVariable("storeId") Long storeId){
        return employeeService.listAllHourlyEmployees(storeId).stream().map(hourlyEmployeeMapper::toDTO).toList();
    }

    @PostMapping("/create")
    public Employee createEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable("storeId") Long storeId) {
        Employee employee = employeeMapper.fromDTO(employeeDTO);
        Integer supervisorId = employeeDTO.supervisorId();
        return employeeService.createEmployee(employee, supervisorId, storeId);
    }

    @PostMapping("/getEmployee")
    public  EmployeeDTO getEmployee(@RequestBody Params params , @PathVariable("storeId") Long storeId) {

        return employeeMapper.toDTO(employeeService.getEmployeeById(storeId, params.params()));
    }

    @GetMapping("/listHourlyEmployees")
    List<HourlyEmployeeDTO> listHourlyEmployees(@PathVariable("storeId") Long storeId) {
        return employeeService.listAllHourlyEmployees(storeId)
                .stream()
                .map(hourlyEmployeeMapper::toDTO)
                .toList();
    }

    @GetMapping("/listSalariedEmployees")
    List<SalariedEmployeeDTO> listSalariedEmployees(@PathVariable("storeId") Long storeId) {
        return employeeService.listAllSalariedEmployees(storeId)
                .stream()
                .map(salariedEmployeeMapper::toDTO)
                .toList();
    }

    @PostMapping("/create/manager")
    public ResponseEntity<ManagerDTO> createManager(@RequestBody ManagerDTO managerDTO, @PathVariable("storeId") Long storeId) {
        EmployeeDTO employeeDTO = managerDTO.employee();
        Employee employee = createEmployee(employeeDTO, storeId);
        Manager manager = new Manager(
                employee.getId(),
                employee.getName(),
                employee.getGender(),
                employee.getPhoneNumber(),
                employee.getDateOfBirth(),
                employee.getEmailAddress(),
                employee.getAddress(),
                employee.getSupervisor(),
                employee.getStore()
        );

        return ResponseEntity.ok(managerMapper.toDTO(employeeService.createManager(manager)));
    }

    @PostMapping("/create/salariedEmployee")
    public ResponseEntity<SalariedEmployeeDTO> createSalariedEmployee(@RequestBody SalariedEmployeeDTO salariedEmployeeDTO, @PathVariable("storeId") Long storeId) {
        EmployeeDTO employeeDTO = salariedEmployeeDTO.employee();
        Employee employee = createEmployee(employeeDTO, storeId);
        SalariedEmployee salariedEmployee= new SalariedEmployee(
                employee.getId(),
                employee.getName(),
                employee.getGender(),
                employee.getPhoneNumber(),
                employee.getDateOfBirth(),
                employee.getEmailAddress(),
                employee.getAddress(),
                employee.getSupervisor(),
                employee.getStore(),
                salariedEmployeeDTO.salary()
        );

        return ResponseEntity.ok(salariedEmployeeMapper.toDTO(employeeService.createSalariedEmployee(salariedEmployee)));
    }

    @GetMapping("/get/hourlyEmployee/{employeeId}")
    public ResponseEntity<HourlyEmployeeDTO> getHourlyEmployee(@PathVariable("employeeId") Integer employeeId, @PathVariable("storeId") Long storeId) {
        if (employeeId == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(hourlyEmployeeMapper.toDTO(employeeService.getHourlyEmployeeById(employeeId)));
    }

    @GetMapping("/get/salariedEmployee/{employeeId}")
    public ResponseEntity<SalariedEmployeeDTO> getSalariedEmployee(@PathVariable("employeeId") Integer employeeId, @PathVariable("storeId") Long storeId){
        if (employeeId == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(salariedEmployeeMapper.toDTO((employeeService.getSalariedEmployeeById(employeeId))));
    }

    @PutMapping("/update/salariedEmployee")
    public ResponseEntity<SalariedEmployeeDTO> updateSalariedEmployee(@RequestBody SalariedEmployeeDTO salariedEmployeeDTO, @PathVariable Long storeId) {
        return ResponseEntity.ok(salariedEmployeeMapper.toDTO(employeeService.updateSalariedEmployee(salariedEmployeeMapper.fromDTO(salariedEmployeeDTO))));
    }

    @PutMapping("/update/hourlyEmployee")
    public ResponseEntity<HourlyEmployeeDTO> updateHourlyEmployee(@RequestBody HourlyEmployeeDTO hourlyEmployeeDTO, @PathVariable Long storeId) {
        return ResponseEntity.ok(hourlyEmployeeMapper.toDTO(employeeService.updateHourlyEmployee(hourlyEmployeeMapper.fromDTO(hourlyEmployeeDTO))));
    }

    @DeleteMapping("delete/{employeeId}")
    public ResponseEntity<String>  deleteEmployee(@PathVariable("employeeId") Integer employeeId, @PathVariable Long storeId) {
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }

    @GetMapping("get/manager")
    public ResponseEntity<ManagerDTO> getManager(@PathVariable("storeId") Long storeId) {
        return ResponseEntity.ok(managerMapper.toDTO(employeeService.getManagerByStoreId(storeId)));
    }

    @PutMapping("update/manager")
    public ResponseEntity<ManagerDTO> updateManager(@RequestBody ManagerDTO managerDTO, @PathVariable Long storeId) {
        return ResponseEntity.ok(managerMapper.toDTO(employeeService.updateManager(managerMapper.fromDTO(managerDTO))));
            }

}