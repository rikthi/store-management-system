package dev.rikthipranadhik.storemanagementsystembackend.service.customer.impl;

import dev.rikthipranadhik.storemanagementsystembackend.entity.Customer.Customer;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Customer.Receipt;
import dev.rikthipranadhik.storemanagementsystembackend.entity.store.Store;
import dev.rikthipranadhik.storemanagementsystembackend.repository.customer.CustomerRepository;
import dev.rikthipranadhik.storemanagementsystembackend.repository.customer.ReceiptRepository;
import dev.rikthipranadhik.storemanagementsystembackend.repository.store.StoreRepository;
import dev.rikthipranadhik.storemanagementsystembackend.service.customer.CustomerService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final ReceiptRepository receiptRepository;

    @Override
    public Customer createCustomer(Customer customer, Long storeId) {
        if (customer.getId() != null){
            throw new IllegalArgumentException("Customer ID must be null");
        }

        Store store = storeRepository.findById(storeId).orElseThrow(()-> new IllegalArgumentException("Store ID not found"));
        customer.setStore(store);
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer oldCustomer =  customerRepository.findById(customer.getId()).orElseThrow(()-> new IllegalArgumentException("Customer ID not found"));
        oldCustomer.setName(customer.getName());
        oldCustomer.setEmail(customer.getEmail());
        oldCustomer.setMembershipType(customer.getMembershipType());

        return customerRepository.save(oldCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long customerId) {
        receiptRepository.deleteByCustomerId(customerId);
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<Customer> listAllCustomers(Long storeId) {
        return customerRepository.findByStoreId(storeId);
    }

    @Override
    public Receipt createReceipt(Receipt receipt, Long customerId) {
        if (receipt.getId() != null){
            throw new IllegalArgumentException("Receipt ID must be null");
        }

        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new IllegalArgumentException("Customer ID not found"));

        receipt.setCustomer(customer);

        return receiptRepository.save(receipt);
    }

    @Override
    public Receipt updateReceipt(Receipt receipt) {
        return null;
    }

    @Override
    public void deleteReceipt(Long receiptId) {
        receiptRepository.deleteByCustomerId(receiptId);
    }

    @Override
    public List<Receipt> listAllReceipts(Long storeId) {
        return receiptRepository.findByCustomer_Store_Id(storeId);
    }

    @Override
    public List<Receipt> listReceiptsByCustomerId(Long customerId) {
        return receiptRepository.findByCustomerId(customerId);
    }
}
