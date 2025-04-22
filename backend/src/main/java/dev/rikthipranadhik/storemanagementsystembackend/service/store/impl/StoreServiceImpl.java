package dev.rikthipranadhik.storemanagementsystembackend.service.store.impl;

import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import dev.rikthipranadhik.storemanagementsystembackend.repository.store.StoreRepository;
import dev.rikthipranadhik.storemanagementsystembackend.service.store.StoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public List<Store> listAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public Store createStore(Store store) {

        if (store.getId() != null){
            throw new IllegalArgumentException("Store Id has to be empty!");
        }
        return storeRepository.save(store);
    }

    /**
     * @param storeId of store to be deleted
     * @return true if store deleted, false if store doesn't exist in database
     */
    @Override
    public Boolean deleteStore(Long storeId) {
        if (!storeRepository.existsById(storeId)) {
            return false;
        }
        storeRepository.deleteById(storeId);
        return true;
    }

    @Override
    public Store getStore(Long storeId) {
        return storeRepository.findById(storeId).orElse(null);
    }
}
