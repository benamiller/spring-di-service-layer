package com.hactually.exception;

public class ProductTypeNotFoundException extends Exception{
    public ProductTypeNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
