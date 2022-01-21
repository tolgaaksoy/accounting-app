package com.tolgaaksoy.accountingapp.service;

import com.tolgaaksoy.accountingapp.model.dto.InvoiceRequest;
import com.tolgaaksoy.accountingapp.model.dto.InvoiceResponse;
import com.tolgaaksoy.accountingapp.model.entity.Invoice;
import com.tolgaaksoy.accountingapp.model.entity.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface InvoiceService {
    ResponseEntity<InvoiceResponse> createInvoice(InvoiceRequest invoiceRequest);

    ResponseEntity<Page<Invoice>> getInvoicePage(Pageable pageable, InvoiceStatus status);
}
