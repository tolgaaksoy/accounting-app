package com.tolgaaksoy.accountingapp.repository;

import com.tolgaaksoy.accountingapp.model.entity.Invoice;
import com.tolgaaksoy.accountingapp.model.entity.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    @Query(value = "SELECT SUM(x.amount) FROM Invoice x WHERE x.createdBy = :username")
    BigDecimal accountantTransactionVolume(@Param("username") String username);

    Page<Invoice> findAllByInvoiceStatus(Pageable pageable, InvoiceStatus status);
}
