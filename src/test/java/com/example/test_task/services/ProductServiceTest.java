package com.example.test_task.services;

import com.example.test_task.dto.ProductDto;
import com.example.test_task.entities.Image;
import com.example.test_task.entities.Product;
import com.example.test_task.mothers.ProductDtoMother;
import com.example.test_task.mothers.ProductMother;
import com.example.test_task.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ConversionService conversionService;
    @Mock
    private RemoveBackgroundService removeBackgroundService;
    @InjectMocks
    private ProductService productService;

    @Nested
    public class GetAll {
        @Test
        void shouldGetAllWhenProductsExist() {
            List<Product> list = List.of(
                    ProductMother.create().id(null).build(),
                    ProductMother.create().id(null).name("Laptop").build(),
                    ProductMother.create().id(null).name("Headphones").build(),
                    ProductMother.create().id(null).name("Keyboard").build()
            );

            when(productRepository.findAll()).thenReturn(list);

            Assertions.assertEquals(list, productService.getAll());
        }

        @Test
        void shouldGetEmptyListWhenNoProductsExist() {
            List<Product> list = List.of();

            when(productRepository.findAll()).thenReturn(list);

            Assertions.assertEquals(list, productService.getAll());
        }
    }

    @Nested
    public class GetById {
        @Test
        void shouldGetProductWhenProductExist() {
            Product expected = ProductMother.create().id(null).build();

            when(productRepository.findById(1L)).thenReturn(Optional.of(expected));

            Assertions.assertEquals(expected, productService.getById(1L));
        }

        @Test
        void shouldThrowExceptionWhenProductDoesNotExist() {
            when(productRepository.findById(5L)).thenReturn(Optional.empty());

            Exception e = Assertions.assertThrows(EntityNotFoundException.class, () -> productService.getById(5L));
            Assertions.assertEquals("Product with id 5 not found", e.getMessage());
        }
    }

    @Nested
    public class GetByBestRating {
        @Test
        void shouldGetProductWithBestRatingWhenProductExist() {
            Product expected = ProductMother.create().id(null).build();

            when(productRepository.findTopByOrderByRatingDesc()).thenReturn(Optional.of(expected));

            Assertions.assertEquals(expected, productService.getByBestRating());
        }

        @Test
        void shouldThrowExceptionWhenProductDoesNotExist() {
            when(productRepository.findTopByOrderByRatingDesc()).thenReturn(Optional.empty());

            Exception e = Assertions.assertThrows(EntityNotFoundException.class, () -> productService.getByBestRating());
            Assertions.assertEquals("There is no product in a database", e.getMessage());
        }
    }

    @Nested
    public class GetByLowestPrice {
        @Test
        void shouldGetProductWhenProductExist() {
            Product expected = ProductMother.create().id(null).build();

            when(productRepository.findTopByOrderByPriceAsc()).thenReturn(Optional.of(expected));

            Assertions.assertEquals(expected, productService.getByLowestPrice());
        }

        @Test
        void shouldThrowExceptionWhenProductDoesNotExist() {
            when(productRepository.findTopByOrderByPriceAsc()).thenReturn(Optional.empty());

            Exception e = Assertions.assertThrows(EntityNotFoundException.class, () -> productService.getByLowestPrice());
            Assertions.assertEquals("There is no product in a database", e.getMessage());
        }
    }

    @Nested
    public class GetByHighestPrice {
        @Test
        void shouldGetProductWhenProductExist() {
            Product expected = ProductMother.create().id(null).build();

            when(productRepository.findTopByOrderByPriceDesc()).thenReturn(Optional.of(expected));

            Assertions.assertEquals(expected, productService.getByHighestPrice());
        }

        @Test
        void shouldThrowExceptionWhenProductDoesNotExist() {
            when(productRepository.findTopByOrderByPriceDesc()).thenReturn(Optional.empty());

            Exception e = Assertions.assertThrows(EntityNotFoundException.class, () -> productService.getByHighestPrice());
            Assertions.assertEquals("There is no product in a database", e.getMessage());
        }
    }

    @Nested
    public class DeleteById {
        @Test
        void shouldCallMethodWhenProductExist() {
            productService.deleteById(5L);
            Mockito.verify(productRepository).deleteById(5L);
        }
    }

    @Nested
    public class Add {
        @Test
        void shouldAddProductWhenDtoIsPasses() {
            ProductDto dto = ProductDtoMother.create().name("Headphones").brand("new brand").build();
            Product product = ProductMother.create()
                    .id(1L)
                    .name("Headphones")
                    .brand("new brand")
                    .model(null)
                    .color(null)
                    .weight(null)
                    .description("Headphones, бренд: new brand")
                    .build();
            Product convertedProduct = ProductMother.create()
                    .id(1L)
                    .name("Headphones")
                    .brand("new brand")
                    .model(null)
                    .color(null)
                    .weight(null)
                    .build();
            when(productRepository.save(product)).thenReturn(product);
            when(conversionService.convert(dto, Product.class)).thenReturn(convertedProduct);

            Product actual = productService.add(dto);
            Assertions.assertEquals(product, actual);
        }
    }

    @Nested
    public class Update {
        @Test
        void shouldUpdateProductWhenIdIsValid() {
            ProductDto dto = ProductDtoMother.create().name("Headphones").brand("new brand").build();
            Product product = ProductMother.create()
                    .id(3L)
                    .name("Headphones")
                    .brand("new brand")
                    .model(null)
                    .color(null)
                    .weight(null)
                    .description("Headphones, бренд: new brand")
                    .build();
            Product convertedProduct = ProductMother.create()
                    .id(1L)
                    .name("Headphones")
                    .brand("new brand")
                    .model(null)
                    .color(null)
                    .weight(null)
                    .build();
            when(productRepository.findById(3L)).thenReturn(Optional.of(product));
            when(productRepository.save(product)).thenReturn(product);
            when(conversionService.convert(dto, Product.class)).thenReturn(convertedProduct);

            Product actual = productService.update(3, dto);
            Assertions.assertEquals(product, actual);
        }

        @Test
        void shouldThrowExceptionWhenProductWithIdDoesNotExist() {
            ProductDto dto = ProductDtoMother.create().name("Headphones").brand("new brand").build();
            when(productRepository.findById(2L)).thenReturn(Optional.empty());

            Exception e = Assertions.assertThrows(EntityNotFoundException.class, () -> productService.update(2, dto));
            Assertions.assertEquals("Product with id 2 not found", e.getMessage());
        }
    }

    @Nested
    public class UploadImage {
        @Test
        @SneakyThrows
        void shouldAddImagesToProductWhenProductExists() {
            List<Image> images = new ArrayList<>();
            images.add(new Image());
            Image image = new Image("1_filename.jpg", "http://localhost/1_filename.jpg");
            Product product = ProductMother.create().id(null).images(images).build();

            MockMultipartFile[] files = new MockMultipartFile[]{
                    new MockMultipartFile("file", "filename.jpg",
                    "image/jpeg", "test image content".getBytes())
            };

            when(productRepository.findById(1L)).thenReturn(Optional.of(product));
            when(removeBackgroundService.removeBackground(any(MultipartFile.class), eq(1L), eq("localhost")))
                    .thenReturn(image);

            productService.uploadImage(1L, files, "localhost");
            verify(productRepository).save(product);
        }

        @Test
        void shouldThrowExceptionWhenProductWithIdDoesNotExist() {
            ProductDto dto = ProductDtoMother.create().name("Headphones").brand("new brand").build();
            when(productRepository.findById(2L)).thenReturn(Optional.empty());

            Exception e = Assertions.assertThrows(EntityNotFoundException.class, () -> productService.update(2, dto));
            Assertions.assertEquals("Product with id 2 not found", e.getMessage());
        }
    }
}
