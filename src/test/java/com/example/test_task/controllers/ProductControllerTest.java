package com.example.test_task.controllers;

import com.example.test_task.dto.ProductDto;
import com.example.test_task.entities.Product;
import com.example.test_task.mothers.ProductDtoMother;
import com.example.test_task.mothers.ProductMother;
import com.example.test_task.services.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(ProductController.class)
public class ProductControllerTest extends AbstractControllerTest {
    @MockBean
    private ProductService productService;

    @Nested
    public class GetAll {
        @Test
        public void shouldReturnStatus200AndAvailableProductsWhenProductsExist() {
            List<Product> list = List.of(
                    ProductMother.create()
                            .id(1L)
                            .name("Laptop ASUS")
                            .build(),
                    ProductMother.create()
                            .id(2L)
                            .name("Action camera Sony")
                            .build()
            );
            when(productService.getAll()).thenReturn(list);

            List<Product> actual = getData("/api/products", new TypeReference<>(){});

            Assertions.assertEquals(list, actual);
        }

        @Test
        public void shouldReturnStatus200AndEmptyListWhenNoProductExist() {
            List<Product> list = List.of();
            when(productService.getAll()).thenReturn(list);

            List<Product> actual = getData("/api/products", new TypeReference<>(){});

            Assertions.assertEquals(list, actual);
        }
    }

    @Nested
    public class GetById {
        @Test
        public void shouldReturnStatus200AndProductWhenProductExist() {
            Product product = ProductMother.create()
                    .id(2L)
                    .name("Action camera Sony")
                    .build();
            when(productService.getById(2L)).thenReturn(product);

            Product actual = getData("/api/products/2", new TypeReference<>(){});

            Assertions.assertEquals(product, actual);
        }

        @Test
        public void shouldReturnStatus400WhenProductDoesNotExist() {
            when(productService.getById(2L)).thenThrow(EntityNotFoundException.class);
            testBadRequest(HttpMethod.GET, "/api/products/2");
        }
    }

    @Nested
    public class GetByBestRating {
        @Test
        public void shouldReturnStatus200AndProductWhenProductExist() {
            Product product = ProductMother.create()
                    .id(2L)
                    .name("Action camera Sony")
                    .build();
            when(productService.getByBestRating()).thenReturn(product);

            Product actual = getData("/api/products/best-rated", new TypeReference<>(){});

            Assertions.assertEquals(product, actual);
        }

        @Test
        public void shouldReturnStatus400WhenProductDoesNotExist() {
            when(productService.getByBestRating()).thenThrow(EntityNotFoundException.class);
            testBadRequest(HttpMethod.GET, "/api/products/best-rated");
        }
    }

    @Nested
    public class GetByLowestPrice {
        @Test
        public void shouldReturnStatus200AndProductWhenProductExist() {
            Product product = ProductMother.create()
                    .id(2L)
                    .name("Action camera Sony")
                    .build();
            when(productService.getByLowestPrice()).thenReturn(product);

            Product actual = getData("/api/products/cheapest", new TypeReference<>(){});

            Assertions.assertEquals(product, actual);
        }

        @Test
        public void shouldReturnStatus400WhenProductDoesNotExist() {
            when(productService.getByLowestPrice()).thenThrow(EntityNotFoundException.class);
            testBadRequest(HttpMethod.GET, "/api/products/cheapest");
        }
    }

    @Nested
    public class GetByHighestPrice {
        @Test
        public void shouldReturnStatus200AndProductWhenProductExist() {
            Product product = ProductMother.create()
                    .id(2L)
                    .name("Action camera Sony")
                    .build();
            when(productService.getByHighestPrice()).thenReturn(product);

            Product actual = getData("/api/products/most-expensive", new TypeReference<>(){});

            Assertions.assertEquals(product, actual);
        }

        @Test
        public void shouldReturnStatus400WhenProductDoesNotExist() {
            when(productService.getByHighestPrice()).thenThrow(EntityNotFoundException.class);
            testBadRequest(HttpMethod.GET, "/api/products/most-expensive");
        }
    }

    @Nested
    public class DeleteById {
        @Test
        public void shouldReturnStatus200WhenProductExist() {
            testNoContent(HttpMethod.DELETE, "/api/products/2");
            verify(productService).deleteById(2L);
        }
    }

    @Nested
    public class Create {
        @Test
        public void shouldReturnStatus200AndProductWhenProductIsValid() {
            ProductDto dto = ProductDtoMother.create()
                    .name("Action camera")
                    .build();
            Product product = ProductMother.create()
                    .id(1L)
                    .name("Action camera")
                    .description("Action camera")
                    .build();
            when(productService.add(dto)).thenReturn(product);

            Product actual = sendData(HttpMethod.POST, "/api/products", dto, Product.class);
            verify(productService).add(dto);

            Assertions.assertEquals(product, actual);
        }

        @Test
        public void shouldReturnStatus400WhenProductIsInvalid() {
            ProductDto dto = ProductDtoMother.create()
                    .name("Action camera")
                    .price(-1)
                    .build();

            sendInvalidData(HttpMethod.POST, "/api/products", dto);
        }
    }

    @Nested
    public class Update {
        @Test
        public void shouldReturnStatus200AndProductWhenProductIsValid() {
            ProductDto dto = ProductDtoMother.create()
                    .name("Action camera")
                    .features(List.of("feature"))
                    .build();
            Product product = ProductMother.create()
                    .id(1L)
                    .name("Action camera")
                    .description("Action camera, особенности: feature")
                    .build();
            when(productService.update(1, dto)).thenReturn(product);

            Product actual = sendData(HttpMethod.PUT, "/api/products/1", dto, Product.class);

            Assertions.assertEquals(product, actual);
        }

        @Test
        public void shouldReturnStatus400WhenProductIsInvalid() {
            ProductDto dto = ProductDtoMother.create()
                    .name("Action camera")
                    .rating(-0.1)
                    .build();

            sendInvalidData(HttpMethod.PUT, "/api/products/2", dto);
        }
    }

    @Nested
    public class UploadImage {
        @Test
        @SneakyThrows
        public void shouldReturnStatus200AndProductWhenMultipartIsPresent() {
            MockMultipartFile file = new MockMultipartFile("image", "filename.jpg",
                    "image/jpeg", "test image content".getBytes());
            doNothing().when(productService).uploadImage(any(Long.class), any(MultipartFile[].class), any(String.class));

            mvc.perform(multipart("/api/products/1/uploadImage")
                            .file(file)
                            .header("host", "localhost"))
                    .andExpect(status().isNoContent());
        }

        @Test
        @SneakyThrows
        public void shouldReturnStatus400WhenMultipartIsInvalid() {
            MockMultipartFile file = new MockMultipartFile("file", "filename.jpg",
                    "image/jpeg", "test image content".getBytes());
            doNothing().when(productService).uploadImage(any(Long.class), any(MultipartFile[].class), any(String.class));

            mvc.perform(multipart("/api/products/1/uploadImage")
                            .file(file)
                            .header("host", "localhost"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @SneakyThrows
        public void shouldReturnStatus400WhenMultipartIsNotPresent() {
            Product product = ProductMother.create().build();

            mvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "/api/products/1/uploadImage")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(product)))
                    .andExpect(status().isBadRequest());
        }
    }
}
