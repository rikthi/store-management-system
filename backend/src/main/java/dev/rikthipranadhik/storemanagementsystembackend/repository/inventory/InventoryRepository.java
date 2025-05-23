package dev.rikthipranadhik.storemanagementsystembackend.repository.inventory;

import dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory.Inventory;
import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByStoreId(Long storeId);
    Inventory findByStoreAndCategory(Store store, String category);
}
