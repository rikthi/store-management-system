package dev.rikthipranadhik.storemanagementsystembackend.repository.employee;

import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.SalariedEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalariedEmployeeRepository extends JpaRepository<SalariedEmployee,Integer> {

    List<SalariedEmployee> findByStoreId(Long storeId);
}
