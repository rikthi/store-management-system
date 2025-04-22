package dev.rikthipranadhik.storemanagementsystembackend.dto.attendance;

import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.EmployeeDTO;
import dev.rikthipranadhik.storemanagementsystembackend.dto.employee.ManagerDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;

import java.time.LocalDateTime;

public record AttendanceDTO (Long id, Integer employeeId, Integer verifierId, LocalDateTime punchInTime, LocalDateTime punchOutTime, Boolean isVerified){

}
