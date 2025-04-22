package dev.rikthipranadhik.storemanagementsystembackend.service.attendance;

import dev.rikthipranadhik.storemanagementsystembackend.entity.attendance.Attendance;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceService {
    List<Attendance> listAllAttendance(Long storeId);
    Attendance createAttendance(Attendance attendance, Integer employeeId, Integer verifierId, Long storeId);
    List<Attendance> getAllAttendanceByEmployeeId(Integer employeeId, Long storeId);
    List<Attendance> getAllAttendanceBySupervisorId(Integer supervisorId);

    Attendance editAttendance(Integer employeeId, LocalDateTime punchOutTime);

    Attendance verify(Attendance attendance);
}
