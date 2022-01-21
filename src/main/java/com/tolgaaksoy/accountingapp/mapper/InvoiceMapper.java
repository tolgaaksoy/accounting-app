package com.tolgaaksoy.accountingapp.mapper;

import com.tolgaaksoy.accountingapp.model.dto.InvoiceRequest;
import com.tolgaaksoy.accountingapp.model.dto.InvoiceResponse;
import com.tolgaaksoy.accountingapp.model.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InvoiceMapper {

    InvoiceMapper MAPPER = Mappers.getMapper(InvoiceMapper.class);

    Invoice toInvoice(InvoiceRequest invoiceRequest);

    InvoiceResponse toInvoiceResponse(Invoice invoice);
}
