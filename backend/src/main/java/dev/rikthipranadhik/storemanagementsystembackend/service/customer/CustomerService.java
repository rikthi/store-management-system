package dev.rikthipranadhik.storemanagementsystembackend.service.customer;

import dev.rikthipranadhik.storemanagementsystembackend.entity.Customer.Customer;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Customer.Receipt;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer, Long storeId);
    Customer updateCustomer(Customer customer);
    void deleteCustomer(Long customerId);
    List<Customer> listAllCustomers(Long storeId);
    Receipt createReceipt(Receipt receipt, Long customerId);
    Receipt updateReceipt(Receipt receipt);
    void deleteReceipt(Long receiptId);
    List<Receipt> listAllReceipts(Long storeId);
    List<Receipt> listReceiptsByCustomerId(Long customerId);
}
