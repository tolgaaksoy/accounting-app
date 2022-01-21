package com.tolgaaksoy.accountingapp.model.dto;

import com.tolgaaksoy.accountingapp.model.entity.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal amount;
    private String productName;
    private String billNo;
    private InvoiceStatus invoiceStatus;
}
