package dev.rikthipranadhik.storemanagementsystembackend.mapper.store.impl;

import dev.rikthipranadhik.storemanagementsystembackend.dto.store.StoreDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.store.StoreMapper;
import org.springframework.stereotype.Component;

@Component
public class StoreMapperImpl implements StoreMapper {
    @Override
    public Store fromDTO(StoreDTO storeDTO) {
        return new Store(
                storeDTO.id(),
                storeDTO.name(),
                storeDTO.address()
        );
    }

    @Override
    public StoreDTO toDTO(Store store) {
        return new StoreDTO(
                store.getId(),
                store.getName(),
                store.getAddress()
        );
    }
}
