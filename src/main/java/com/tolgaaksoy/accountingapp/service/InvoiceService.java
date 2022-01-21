package com.tolgaaksoy.accountingapp.service;

import com.tolgaaksoy.accountingapp.model.dto.InvoiceRequest;
import com.tolgaaksoy.accountingapp.model.entity.InvoiceStatus;
import com.tolgaaksoy.accountingapp.response.APIResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface InvoiceService {
    ResponseEntity<APIResponse> createInvoice(InvoiceRequest invoiceRequest);

    ResponseEntity<APIResponse> getInvoicePage(Pageable pageable, InvoiceStatus status);
}
