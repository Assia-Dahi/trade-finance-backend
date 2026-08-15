package com.tradefinance.trade_finance_backend.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException of(String entite, Long id) {
        return new ResourceNotFoundException(entite + " introuvable avec l'id : " + id);
    }
}