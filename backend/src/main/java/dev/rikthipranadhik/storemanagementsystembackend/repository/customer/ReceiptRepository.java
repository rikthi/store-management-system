package dev.rikthipranadhik.storemanagementsystembackend.repository.customer;

import dev.rikthipranadhik.storemanagementsystembackend.entity.Customer.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    void deleteByCustomerId(Long customerId);
    List<Receipt> findByCustomer_Store_Id(Long storeId);
    List<Receipt> findByCustomerId(Long customerId);
}
