package dev.rikthipranadhik.storemanagementsystembackend.controller;

import dev.rikthipranadhik.storemanagementsystembackend.dto.AttendanceEdit;
import dev.rikthipranadhik.storemanagementsystembackend.dto.attendance.AttendanceDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.attendance.Attendance;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.attendance.AttendanceMapper;
import dev.rikthipranadhik.storemanagementsystembackend.service.attendance.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "{storeId}/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AttendanceMapper attendanceMapper;


    public AttendanceController(AttendanceService attendanceService, AttendanceMapper attendanceMapper) {
        this.attendanceService = attendanceService;
        this.attendanceMapper = attendanceMapper;
    }

    @GetMapping("")
    public List<AttendanceDTO> listAllAttendance(@PathVariable("storeId") Long storeId) {
        return attendanceService.listAllAttendance(storeId)
                .stream()
                .map(attendanceMapper::toDTO)
                .toList();
    }

    @PostMapping("/punchIn")
    public ResponseEntity<Long> punchIn(@RequestBody AttendanceDTO attendanceDTO, @PathVariable("storeId") Long storeId) {
        Attendance attendance = attendanceMapper.fromDTO(attendanceDTO);
        if (attendanceDTO.employeeId() == null) {
            return ResponseEntity.badRequest().build();
        }

        attendance = attendanceService.createAttendance(
                attendance, attendanceDTO.employeeId(), attendanceDTO.verifierId(), storeId
        );
        if (attendance == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(attendanceMapper.toDTO(attendance).id());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceByEmployeeId(@PathVariable("employeeId") Integer employeeId, @PathVariable("storeId") Long storeId) {
        if (employeeId == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(attendanceService.getAllAttendanceByEmployeeId(employeeId, storeId)
                .stream()
                .map(attendanceMapper::toDTO).collect(Collectors.toList()));
    }

    @PutMapping("/punchOut")
    public ResponseEntity<Long> punchOut(@RequestBody AttendanceEdit attendanceEdit, @PathVariable Long storeId) {
        if (attendanceEdit.id() == null || attendanceEdit.punchOutTime() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(attendanceService.editAttendance(attendanceEdit.id(), attendanceEdit.punchOutTime()).getId());
    }

    @PutMapping("verify")
    public ResponseEntity<AttendanceDTO> verify(@RequestBody AttendanceDTO attendanceDTO, @PathVariable Long storeId) {
        return ResponseEntity.ok(attendanceMapper.toDTO(attendanceService.verify(attendanceMapper.fromDTO(attendanceDTO))));
    }





}
