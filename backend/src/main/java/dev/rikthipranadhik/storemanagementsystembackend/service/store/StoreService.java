package dev.rikthipranadhik.storemanagementsystembackend.service.store;

import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;

import java.util.List;

public interface StoreService {
    List<Store> listAllStores();
    Store createStore(Store store);
    Boolean deleteStore(Long storeId);
    Store getStore(Long storeId);
}
