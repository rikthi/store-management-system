package dev.rikthipranadhik.storemanagementsystembackend.repository.store;

import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {

}
