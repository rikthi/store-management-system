package dev.rikthipranadhik.storemanagementsystembackend.mapper.attendance;

import dev.rikthipranadhik.storemanagementsystembackend.dto.attendance.AttendanceDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.attendance.Attendance;

public interface AttendanceMapper {
    Attendance fromDTO(AttendanceDTO attendanceDTO);
    AttendanceDTO toDTO(Attendance attendance);
}
