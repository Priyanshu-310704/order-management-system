package com.prysh.product_service.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Product name cannot be blank")
        @Size(max = 150,message = "Product name cannot exceed 150 characters")
        String name,

        @NotBlank(message = "Product sku cannot be blank")
        @Size(max = 50,message = "Product sku cannot exceed 50 characters")
        String sku,

        @Size(max = 1000,message = "Product description cannot exceed 1000 characters")
        String description,

        @NotBlank(message = "Product price cannot be blank")
        @DecimalMin(value = "0.01",message = "Product price must be greater than 0")
        @Digits(integer = 10,fraction = 2,message = "Price must have upto 10 integer digits and 2 decimal places")
        BigDecimal price,

        @NotBlank(message = "Product stock quantity cannot be empty")
        @PositiveOrZero(message = "Stock quantity cannot be negative")
        Integer stockQuantity
) {
}
