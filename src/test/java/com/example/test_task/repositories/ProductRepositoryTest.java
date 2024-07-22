package com.example.test_task.repositories;

import com.example.test_task.entities.Product;
import com.example.test_task.mothers.ProductMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductRepositoryTest extends DatabaseIntegrationTest {
    @Autowired
    private ProductRepository productRepository;

    @Nested
    public class FindTopByOrderByRatingDesc {
        @Test
        void shouldReturnProductWithBestRatingWhenProductsExist() {
            saveAndFlush(
                    ProductMother.create().id(null).rating(4.5).build(),
                    ProductMother.create().id(null).rating(4.9).name("Laptop").build(),
                    ProductMother.create().id(null).rating(3.9).name("Headphones").build(),
                    ProductMother.create().id(null).rating(4.8).name("Keyboard").build()
            );

            Product actual = productRepository.findTopByOrderByRatingDesc().get();

            Product expected = ProductMother.create().id(2L).rating(4.9).name("Laptop").build();

            Assertions.assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyOptionalWhenNoProductExist() {
            Assertions.assertTrue(productRepository.findTopByOrderByRatingDesc().isEmpty());
        }

        @Test
        void shouldReturnFirstWhenNoProductWithRatingExist() {
            saveAndFlush(
                    ProductMother.create().id(null).build(),
                    ProductMother.create().id(null).name("Laptop").build(),
                    ProductMother.create().id(null).name("Headphones").build(),
                    ProductMother.create().id(null).name("Keyboard").build()
            );

            Product actual = productRepository.findTopByOrderByRatingDesc().get();

            Product expected = ProductMother.create().id(1L).build();

            Assertions.assertEquals(expected, actual);
        }
    }

    @Nested
    public class FindTopByOrderByPriceDesc {
        @Test
        void shouldReturnProductWithBestRatingWhenProductsExist() {
        saveAndFlush(
                    ProductMother.create().id(null).price(108.9).build(),
                    ProductMother.create().id(null).price(4.99).name("Headphones").build(),
                    ProductMother.create().id(null).price(5000).name("Laptop").build(),
                    ProductMother.create().id(null).price(499.99).name("Keyboard").build()
            );

            Product actual = productRepository.findTopByOrderByPriceDesc().get();

            Product expected = ProductMother.create().id(3L).price(5000).name("Laptop").build();

            Assertions.assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyOptionalWhenNoProductExist() {
            Assertions.assertTrue(productRepository.findTopByOrderByPriceDesc().isEmpty());
        }

        @Test
        void shouldReturnFirstWhenNoPricesAreSame() {
            saveAndFlush(
                    ProductMother.create().id(null).build(),
                    ProductMother.create().id(null).name("Laptop").build(),
                    ProductMother.create().id(null).name("Headphones").build(),
                    ProductMother.create().id(null).name("Keyboard").build()
            );

            Product actual = productRepository.findTopByOrderByPriceDesc().get();

            Product expected = ProductMother.create().id(1L).build();

            Assertions.assertEquals(expected, actual);
        }
    }

    @Nested
    public class FindTopByOrderByPriceAsc {
        @Test
        void shouldReturnProductWithBestRatingWhenProductsExist() {
            saveAndFlush(
                    ProductMother.create().id(null).price(108.9).build(),
                    ProductMother.create().id(null).price(4.99).name("Headphones").build(),
                    ProductMother.create().id(null).price(5000).name("Laptop").build(),
                    ProductMother.create().id(null).price(499.99).name("Keyboard").build()
            );

            Product actual = productRepository.findTopByOrderByPriceAsc().get();

            Product expected = ProductMother.create().id(2L).price(4.99).name("Headphones").build();

            Assertions.assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyOptionalWhenNoProductExist() {
            Assertions.assertTrue(productRepository.findTopByOrderByPriceAsc().isEmpty());
        }

        @Test
        void shouldReturnFirstWhenNoPricesAreSame() {
            saveAndFlush(
                    ProductMother.create().id(null).build(),
                    ProductMother.create().id(null).name("Laptop").build(),
                    ProductMother.create().id(null).name("Headphones").build(),
                    ProductMother.create().id(null).name("Keyboard").build()
            );

            Product actual = productRepository.findTopByOrderByPriceAsc().get();

            Product expected = ProductMother.create().id(1L).build();

            Assertions.assertEquals(expected, actual);
        }
    }
}
