package com.hactually.exception;

public class TaxRateNotFoundException extends Exception{
    public TaxRateNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
