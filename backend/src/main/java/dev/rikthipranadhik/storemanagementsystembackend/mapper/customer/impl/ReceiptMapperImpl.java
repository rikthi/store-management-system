package dev.rikthipranadhik.storemanagementsystembackend.mapper.customer.impl;

import dev.rikthipranadhik.storemanagementsystembackend.dto.customer.ReceiptDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Customer.Customer;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Customer.Receipt;
import dev.rikthipranadhik.storemanagementsystembackend.mapper.customer.ReceiptMapper;
import org.springframework.stereotype.Component;

@Component
public class ReceiptMapperImpl implements ReceiptMapper {

    @Override
    public Receipt fromDTO(ReceiptDTO receiptDTO) {
        Customer customer = new Customer();
        return new Receipt(
                receiptDTO.id(),
                receiptDTO.dateOfTransaction(),
                receiptDTO.cardNo(),
                receiptDTO.totalAmount(),
                customer // Set actual customer later in service layer
        );
    }

    @Override
    public ReceiptDTO toDTO(Receipt receipt) {
        return new  ReceiptDTO(
                receipt.getId(),
                receipt.getDateOfTransaction(),
                receipt.getCardNo(),
                receipt.getTotalAmount(),
                receipt.getCustomer().getId()
        );
    }
}

