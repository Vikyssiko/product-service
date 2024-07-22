package com.example.test_task.mothers;

import com.example.test_task.dto.ProductDto;

import java.util.ArrayList;

public class ProductDtoMother {
    public static ProductDto.ProductDtoBuilder<?, ?> create() {
        return ProductDto.builder()
                .name("product")
                .category("Category of product 1")
                .availableAmount(1)
                .price(99.99)
                .images(new ArrayList<>())
                .features(new ArrayList<>());
    }
}
