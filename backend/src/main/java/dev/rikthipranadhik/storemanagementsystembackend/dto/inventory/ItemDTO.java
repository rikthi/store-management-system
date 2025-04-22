package dev.rikthipranadhik.storemanagementsystembackend.dto.inventory;

import dev.rikthipranadhik.storemanagementsystembackend.entity.Inventory.Inventory;

import java.time.LocalDate;

public record ItemDTO(Long id, String name, LocalDate manufactureDate, Float price, LocalDate expirationDate, Float discountPercentage, Long inventoryId, Long quantity) {
}
