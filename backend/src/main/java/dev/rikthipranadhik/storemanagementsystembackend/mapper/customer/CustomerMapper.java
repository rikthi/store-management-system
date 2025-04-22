package dev.rikthipranadhik.storemanagementsystembackend.mapper.customer;

import dev.rikthipranadhik.storemanagementsystembackend.dto.customer.CustomerDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Customer.Customer;

public interface CustomerMapper {
    Customer fromDTO(CustomerDTO customerDTO);
    CustomerDTO toDTO(Customer customer);
}
