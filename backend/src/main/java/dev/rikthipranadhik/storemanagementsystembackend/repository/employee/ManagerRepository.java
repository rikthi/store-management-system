package dev.rikthipranadhik.storemanagementsystembackend.repository.employee;

import dev.rikthipranadhik.storemanagementsystembackend.entity.employee.Manager;
import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,Integer> {
    List<Manager> store(Store store);

    Manager findManagerByStoreId(Long storeId);
}
