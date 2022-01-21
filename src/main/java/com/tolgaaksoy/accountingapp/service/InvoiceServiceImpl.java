package com.tolgaaksoy.accountingapp.service;

import com.tolgaaksoy.accountingapp.mapper.InvoiceMapper;
import com.tolgaaksoy.accountingapp.model.dto.InvoiceRequest;
import com.tolgaaksoy.accountingapp.model.dto.InvoiceResponse;
import com.tolgaaksoy.accountingapp.model.entity.Invoice;
import com.tolgaaksoy.accountingapp.model.entity.InvoiceStatus;
import com.tolgaaksoy.accountingapp.repository.InvoiceRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Log4j2
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Value("${invoice.limit}")
    private BigDecimal LIMIT;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private final InvoiceRepository invoiceRepository;
    private final UserService userService;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, UserService userService) {
        this.invoiceRepository = invoiceRepository;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<InvoiceResponse> createInvoice(InvoiceRequest invoiceRequest) {
        Invoice invoice = InvoiceMapper.MAPPER.toInvoice(invoiceRequest);
        HttpStatus httpStatus;
        if (checkAccountantTransactionVolume(invoice.getAmount())) {
            invoice.setInvoiceStatus(InvoiceStatus.ACCEPT);
            httpStatus = HttpStatus.OK;
        } else {
            invoice.setInvoiceStatus(InvoiceStatus.REJECT);
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
        }
        invoice.setCreatedBy(userService.getUsernameByHttpRequest(httpServletRequest));
        Invoice savedInvoice = invoiceRepository.save(invoice);
        InvoiceResponse invoiceResponse = InvoiceMapper.MAPPER.toInvoiceResponse(savedInvoice);
        return new ResponseEntity<>(invoiceResponse, httpStatus);
    }

    @Override
    public ResponseEntity<Page<Invoice>> getInvoicePage(Pageable pageable, InvoiceStatus status) {
        if (status == null) {
            return new ResponseEntity<>(invoiceRepository.findAll(pageable), HttpStatus.OK);
        }
        return new ResponseEntity<>(invoiceRepository.findAllByInvoiceStatus(pageable, status), HttpStatus.OK);
    }

    private boolean checkAccountantTransactionVolume(BigDecimal requestAmount) {
        String accountantUsername = userService.getUsernameByHttpRequest(httpServletRequest);
        BigDecimal accountantTransactionVolume = invoiceRepository.accountantTransactionVolume(accountantUsername);
        if (accountantTransactionVolume == null){
            return true;
        }
        return LIMIT.compareTo(accountantTransactionVolume.add(requestAmount)) > 0;
    }
}
