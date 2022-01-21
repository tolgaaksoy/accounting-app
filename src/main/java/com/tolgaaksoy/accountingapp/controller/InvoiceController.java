package com.tolgaaksoy.accountingapp.controller;

import com.tolgaaksoy.accountingapp.model.dto.InvoiceRequest;
import com.tolgaaksoy.accountingapp.model.entity.InvoiceStatus;
import com.tolgaaksoy.accountingapp.response.APIResponse;
import com.tolgaaksoy.accountingapp.service.InvoiceService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/create")
    public ResponseEntity<APIResponse> createInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        return invoiceService.createInvoice(invoiceRequest);
    }

    @GetMapping("/search")
    public ResponseEntity<APIResponse> searchByInvoiceStatus(Pageable pageable, @RequestParam(required = false) InvoiceStatus invoiceStatus) {
        return invoiceService.getInvoicePage(pageable, invoiceStatus);
    }
}
