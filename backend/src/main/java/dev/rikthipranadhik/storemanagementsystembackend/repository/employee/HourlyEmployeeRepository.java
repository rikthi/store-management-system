package dev.rikthipranadhik.storemanagementsystembackend.repository.employee;

import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.HourlyEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HourlyEmployeeRepository extends JpaRepository<HourlyEmployee, Integer> {

    List<HourlyEmployee> findByStoreId(Long storeId);
}
