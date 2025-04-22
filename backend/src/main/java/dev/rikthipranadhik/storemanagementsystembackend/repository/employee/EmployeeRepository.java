package dev.rikthipranadhik.storemanagementsystembackend.repository.employee;

import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Employee;
import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.SalariedEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    List<Employee> findBySupervisorId(Integer supervisorId);

    Optional<Employee> findByEmailAddress(String emailAddress);

    List<Employee> findByStoreId(Long storeId);

    Optional<Employee> findByIdAndStoreId(Integer employeeId, Long storeId);

}
