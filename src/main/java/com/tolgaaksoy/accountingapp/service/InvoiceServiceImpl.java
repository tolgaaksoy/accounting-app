package com.tolgaaksoy.accountingapp.service;

import com.tolgaaksoy.accountingapp.mapper.InvoiceMapper;
import com.tolgaaksoy.accountingapp.model.dto.InvoiceRequest;
import com.tolgaaksoy.accountingapp.model.dto.InvoiceResponse;
import com.tolgaaksoy.accountingapp.model.entity.Invoice;
import com.tolgaaksoy.accountingapp.model.entity.InvoiceStatus;
import com.tolgaaksoy.accountingapp.repository.InvoiceRepository;
import com.tolgaaksoy.accountingapp.response.APIResponse;
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
import java.time.Instant;

@Log4j2
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Value("${invoice.limit}")
    private BigDecimal LIMIT;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private final InvoiceRepository invoiceRepository;
    private final UserServiceImpl userServiceImpl;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, UserServiceImpl userServiceImpl) {
        this.invoiceRepository = invoiceRepository;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public ResponseEntity<APIResponse> createInvoice(InvoiceRequest invoiceRequest) {
        Invoice invoice = InvoiceMapper.MAPPER.toInvoice(invoiceRequest);
        APIResponse response = new APIResponse();
        HttpStatus httpStatus;
        if (checkAccountantTransactionVolume(invoice.getAmount())) {
            invoice.setInvoiceStatus(InvoiceStatus.ACCEPT);
            response.setMessage("Success");
            httpStatus = HttpStatus.OK;
        } else {
            invoice.setInvoiceStatus(InvoiceStatus.REJECT);
            response.setMessage("Your transaction could not be performed. Limit exceeded.");
            httpStatus = HttpStatus.NOT_ACCEPTABLE;
        }
        invoice.setCreatedBy(userServiceImpl.getUsernameByHttpRequest(httpServletRequest));
        Invoice savedInvoice = invoiceRepository.save(invoice);
        InvoiceResponse invoiceResponse = InvoiceMapper.MAPPER.toInvoiceResponse(savedInvoice);
        response.setData(invoiceResponse);
        response.setTime(Instant.now());
        response.setStatus(httpStatus.value());
        return new ResponseEntity<>(response, httpStatus);
    }

    @Override
    public ResponseEntity<APIResponse> getInvoicePage(Pageable pageable, InvoiceStatus status) {
        Page<Invoice> invoicePage;
        if (status != null) {
            invoicePage = invoiceRepository.findAllByInvoiceStatus(pageable, status);
        } else {
            invoicePage = invoiceRepository.findAll(pageable);
        }
        APIResponse response = APIResponse.builder()
                .data(invoicePage)
                .message("Success.")
                .status(200)
                .time(Instant.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private boolean checkAccountantTransactionVolume(BigDecimal requestAmount) {
        String accountantUsername = userServiceImpl.getUsernameByHttpRequest(httpServletRequest);
        BigDecimal accountantTransactionVolume = invoiceRepository.accountantTransactionVolume(accountantUsername);
        if (accountantTransactionVolume == null) {
            return true;
        }
        return LIMIT.compareTo(accountantTransactionVolume.add(requestAmount)) > 0;
    }
}
