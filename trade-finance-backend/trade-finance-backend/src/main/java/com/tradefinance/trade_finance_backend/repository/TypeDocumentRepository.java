package com.tradefinance.trade_finance_backend.repository;

import com.tradefinance.trade_finance_backend.entity.TypeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TypeDocumentRepository extends JpaRepository<TypeDocument, Long> {

    // Ex: TypeDocumentRepository.findByNom("COMMERCIAL_INVOICE")
    Optional<TypeDocument> findByNom(String nom);

    boolean existsByNom(String nom);
}