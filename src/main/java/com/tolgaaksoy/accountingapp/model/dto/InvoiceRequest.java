package com.tolgaaksoy.accountingapp.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceRequest {
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal amount;
    private String productName;
    private String billNo;
}
