package dev.rikthipranadhik.storemanagementsystembackend.repository.attendance;

import dev.rikthipranadhik.storemanagementsystembackend.entity.attendance.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployee_Store_Id(Long storeId);
    List<Attendance> findByEmployeeId(Integer employeeId);
    List<Attendance> findByVerifierId(Integer verifierId);
    Optional<Attendance> findTopByEmployeeIdOrderByPunchInTimeDesc(Integer employeeId);

    void deleteByEmployeeId(Integer employeeId);
}
