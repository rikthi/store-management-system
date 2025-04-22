package dev.rikthipranadhik.storemanagementsystembackend.mapper.customer;

import dev.rikthipranadhik.storemanagementsystembackend.dto.customer.ReceiptDTO;
import dev.rikthipranadhik.storemanagementsystembackend.entity.Customer.Receipt;

public interface ReceiptMapper {

    Receipt fromDTO(ReceiptDTO receiptDTO);
    ReceiptDTO toDTO(Receipt receipt);
}
