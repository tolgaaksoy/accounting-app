package com.tolgaaksoy.accountingapp.model.entity;

import com.tolgaaksoy.accountingapp.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends BaseEntity{
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal amount;
    private String productName;
    private String billNo;
    @Enumerated(value = EnumType.STRING)
    private InvoiceStatus invoiceStatus;
    private String createdBy;
}
