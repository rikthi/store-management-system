package dev.rikthipranadhik.storemanagementsystembackend.mapper.attendance.impl;

import dev.rikthipranadhik.storemanagementsystembackend.dto.attendance.AttendanceDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.attendance.Attendance;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.attendance.AttendanceMapper;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.employee.EmployeeMapper;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.employee.ManagerMapper;
import org.springframework.stereotype.Component;

@Component
public class AttendanceMapperImpl implements AttendanceMapper {


    @Override
    public Attendance fromDTO(AttendanceDTO attendanceDTO) {
        Employee employee = new Employee();
        return new Attendance(
                attendanceDTO.id(),
                employee,
                null,
                attendanceDTO.punchInTime(),
                attendanceDTO.punchOutTime(),
                attendanceDTO.isVerified()
        );
    }

    @Override
    public AttendanceDTO toDTO(Attendance attendance) {

        Integer verifierId = null;
        if (attendance != null) {
            if(attendance.getVerifier() != null) {
                verifierId = attendance.getVerifier().getId();
            }
        }
        return new AttendanceDTO(
                attendance.getId(),
                attendance.getEmployee().getId(),
                verifierId,
                attendance.getPunchInTime(),
                attendance.getPunchOutTime(),
                attendance.getIsVerified()
        );
    }
}
