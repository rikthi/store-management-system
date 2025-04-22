package dev.rikthipranadhik.storemanagementsystembackend.controller;

import dev.rikthipranadhik.storemanagementsystembackend.dto.store.StoreDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.store.StoreMapper;
import dev.rikthipranadhik.storemanagementsystembackend.service.store.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;
    private final StoreMapper storeMapper;

    public StoreController(StoreService storeService, StoreMapper storeMapper) {
        this.storeService = storeService;
        this.storeMapper = storeMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<StoreDTO>> listAllStores() {
        return ResponseEntity.ok(storeService.listAllStores()
                .stream()
                .map(storeMapper::toDTO)
                .toList());
    }

    @PostMapping("/create")
    public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) {
        return ResponseEntity.ok(
                storeMapper.toDTO(
                storeService.createStore(
                        storeMapper.fromDTO(storeDTO)
                )
                )
        );
    }

    @DeleteMapping("/delete/{storeId}")
    public ResponseEntity<String> deleteStore(@PathVariable("storeId") Long storeId) {
        Boolean deleted = storeService.deleteStore(storeId);
        if (deleted) {
            return new ResponseEntity<>("Store deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Store not deleted", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get/{storeId}")
    public ResponseEntity<StoreDTO> getStore(@PathVariable("storeId") Long storeId) {
        return ResponseEntity.ok(storeMapper.toDTO(storeService.getStore(storeId)));
    }

}
