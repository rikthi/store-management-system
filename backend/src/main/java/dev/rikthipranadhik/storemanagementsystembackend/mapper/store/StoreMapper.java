package dev.rikthipranadhik.storemanagementsystembackend.mapper.store;

import dev.rikthipranadhik.storemanagementsystembackend.dto.store.StoreDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;

public interface StoreMapper {
    Store fromDTO(StoreDTO storeDTO);
    StoreDTO toDTO(Store store);
}
