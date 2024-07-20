package com.example.test_task.mothers;

import com.example.test_task.entities.Product;

public class ProductMother {
    public static Product.ProductBuilder<?, ?> create() {
        return Product.builder()
                .id(1L)
                .name("product")
                .category("category")
                .description("Description of product 1")
                .price(999.99);
    }
}
