package com.prysh.product_service.exception;

public class DuplicateProductSkuException extends RuntimeException{
    public DuplicateProductSkuException(String message) {
        super(message);
    }
}
