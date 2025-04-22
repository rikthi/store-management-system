package dev.rikthipranadhik.storemanagementsystembackend.dto.customer;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReceiptDTO(Long id, LocalDate dateOfTransaction, String cardNo, BigDecimal totalAmount, Long customerId) {
}
