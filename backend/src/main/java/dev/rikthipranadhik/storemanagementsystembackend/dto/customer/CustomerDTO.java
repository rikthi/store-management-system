package dev.rikthipranadhik.storemanagementsystembackend.dto.customer;

import dev.rikthipranadhik.storemanagementsystembackend.entity.Customer.Membership;

public record CustomerDTO(Long id, String name, String email, Membership membershipType, Long storeId) {

}
