package dev.rikthipranadhik.storemanagementsystembackend.mapper.customer.impl;

import dev.rikthipranadhik.storemanagementsystembackend.dto.customer.CustomerDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Customer.Customer;
import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.customer.CustomerMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer fromDTO(CustomerDTO customerDTO) {
        Store store = new Store();
        return new Customer(
                customerDTO.id(),
                customerDTO.name(),
                customerDTO.email(),
                customerDTO.membershipType(),
                store   // Set actual store in service layer
        );
    }

    @Override
    public CustomerDTO toDTO(Customer customer) {
        return new  CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getMembershipType(),
                customer.getStore().getId()
        );
    }
}
