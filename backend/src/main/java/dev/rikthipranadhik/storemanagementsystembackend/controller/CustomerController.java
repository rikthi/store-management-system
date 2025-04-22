package dev.rikthipranadhik.storemanagementsystembackend.controller;

import dev.rikthipranadhik.storemanagementsystembackend.dto.customer.CustomerDTO;
import dev.rikthipranadhik.storemanagementsystembackend.dto.customer.CustomerId;
import dev.rikthipranadhik.storemanagementsystembackend.dto.customer.ReceiptDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Customer.Customer;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.customer.CustomerMapper;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.customer.ReceiptMapper;
import dev.rikthipranadhik.storemanagementsystembackend.service.customer.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/{storeId}/customer")
@AllArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final ReceiptMapper receiptMapper;

    @GetMapping("/get/all")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(@PathVariable Long storeId) {
        return ResponseEntity.ok(
                customerService.listAllCustomers(storeId)
                        .stream().map(
                                customerMapper::toDTO
                        ).toList()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerDTO> createCustomer(@PathVariable Long storeId, @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.fromDTO(customerDTO);

        return ResponseEntity.ok(customerMapper.toDTO(
                customerService.createCustomer(customer, storeId)
        ));
    }

    @GetMapping("/receipt/get/all")
    public ResponseEntity<List<ReceiptDTO>> getAllReceipts(@PathVariable Long storeId) {
        return ResponseEntity.ok(customerService.listAllReceipts(
                storeId).stream()
                .map(receiptMapper::toDTO)
                .toList()
        );
    }

    @GetMapping("/receipt/get/{customerId}")
    public ResponseEntity<List<ReceiptDTO>> getReceiptsByCustomerId(@PathVariable("storeId") Long storeId, @PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(customerService.listReceiptsByCustomerId(customerId)
                .stream()
                .map(receiptMapper::toDTO)
                .toList()
        );
    }

    @PostMapping("/receipt/create")
    public ResponseEntity<ReceiptDTO> createReceipt(@RequestBody ReceiptDTO receiptDTO, @PathVariable Long storeId) {
        return ResponseEntity.ok(receiptMapper.toDTO(customerService.createReceipt(receiptMapper.fromDTO(receiptDTO), receiptDTO.customerId())));
    }

    @PutMapping("/update")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long storeId) {
        return ResponseEntity.ok(customerMapper.toDTO(customerService.updateCustomer(customerMapper.fromDTO(customerDTO))));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCustomer(@RequestBody CustomerId customerId, @PathVariable Long storeId) {
        customerService.deleteCustomer(customerId.id());
        return new ResponseEntity<>("Customer with id " + customerId + " has been deleted", HttpStatus.OK);
    }
}
