package dev.rikthipranadhik.storemanagementsystembackend.service.attendance.impl;


import dev.rikthipranadhik.storemanagementsystembackend.entity.attendance.Attendance;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;
import dev.rikthipranadhik.storemanagementsystembackend.repository.attendance.AttendanceRepository;
import dev.rikthipranadhik.storemanagementsystembackend.repository.employee.EmployeeRepository;
import dev.rikthipranadhik.storemanagementsystembackend.service.attendance.AttendanceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Attendance> listAllAttendance(Long storeId) {
        return attendanceRepository.findByEmployee_Store_Id(storeId);
    }

    @Override
    public Attendance createAttendance(Attendance attendance, Integer employeeId, Integer verifierId, Long storeId) {
        if (attendance.getId() != null) {
            throw new IllegalArgumentException("New attendance ID has to be null");
        }
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if  (employee == null) {
            throw new IllegalArgumentException("Employee with id: " + employeeId + " does not exist");
        }

        if (!employee.getStore().getId().equals(storeId)) {
            throw new IllegalArgumentException("Employee with id: " + employeeId +" does not exist in the store");
        }
        attendance.setEmployee(employee);
        if(verifierId != null) {
            attendance.setVerifier(employeeRepository.findById(verifierId).orElse(null));
        }
        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> getAllAttendanceByEmployeeId(Integer employeeId, Long storeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            throw new IllegalArgumentException("Employee with id: " + employeeId + " does not exist");
        }
        if (!employee.getStore().getId().equals(storeId)) {
            return null;
        }
        return attendanceRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<Attendance> getAllAttendanceBySupervisorId(Integer verifierId) {
        return attendanceRepository.findByVerifierId(verifierId);
    }

    @Override
    public Attendance editAttendance(Integer employeeId, LocalDateTime punchOutTime) {
        Attendance attendance =  attendanceRepository.findTopByEmployeeIdOrderByPunchInTimeDesc(employeeId).orElse(null);
        if (attendance == null) {
            throw new IllegalArgumentException("Attendance does not exist");
        }

        attendance.setPunchOutTime(punchOutTime);
        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance verify(Attendance attendance){
        Attendance storedAttendance = attendanceRepository.findById(attendance.getId()).orElse(null);
        if (storedAttendance == null) {
            throw new IllegalArgumentException("Attendance does not exist");
        }
        storedAttendance.setIsVerified(attendance.getIsVerified());
        return attendanceRepository.save(storedAttendance);
    }
}
