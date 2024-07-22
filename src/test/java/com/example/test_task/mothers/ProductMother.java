package com.example.test_task.mothers;

import com.example.test_task.entities.Product;

import java.util.ArrayList;

public class ProductMother {
    public static Product.ProductBuilder<?, ?> create() {
        return Product.builder()
                .id(1L)
                .name("product")
                .description("Description of product 1")
                .brand("brand")
                .model("model")
                .category("Category of product 1")
                .availableAmount(1)
                .color("black")
                .price(99.99)
                .weight("1 kg")
                .images(new ArrayList<>())
                .features(new ArrayList<>());
    }
}
